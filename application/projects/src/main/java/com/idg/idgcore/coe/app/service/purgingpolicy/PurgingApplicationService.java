package com.idg.idgcore.coe.app.service.purgingpolicy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.MutationAssembler;
import com.idg.idgcore.coe.domain.assembler.purgingpolicy.PurgingAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.purging.PurgingEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.purgingpolicy.IPurgingDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.purgingpolicy.PurgingDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Slf4j
@Service("purgingApplicationService")
public class PurgingApplicationService extends AbstractApplicationService
        implements IPurgingApplicationService {

    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IPurgingDomainService purgingDomainService;
    @Autowired
    private PurgingAssembler purgingAssembler;
    @Autowired
    private MutationAssembler mutationAssembler;

    public PurgingDTO getPurgingByCode (SessionContext sessionContext, PurgingDTO purgingDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getPurgingByCode with parameters sessionContext {}, purgingDTO {}",
                    sessionContext, purgingDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        PurgingDTO result = null;
        try {
            if (isAuthorized(purgingDTO.getAuthorized())) {
                PurgingEntity purgingEntity = purgingDomainService.getPurgingByCode(
                        purgingDTO.getModuleCode());
                result = purgingAssembler.convertEntityToDto(purgingEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        purgingDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), PurgingDTO.class);
                result = purgingAssembler.setAuditFields(mutationEntity,result);
                fillTransactionStatus(transactionStatus);
            }
        }
        catch (JsonProcessingException jpe) {
            ExceptionUtil.handleException(JSON_PARSING_ERROR);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return result;
    }

    public List<PurgingDTO> getPurgingAll (SessionContext sessionContext) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getPurgingAll with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<PurgingDTO> purgingDTOList = new ArrayList<>();

        try {
            List<MutationEntity> entities = mutationsDomainService.getMutations(getTaskCode());
            purgingDTOList.addAll(entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                PurgingDTO purgingDTO = null;
                try {
                    purgingDTO = objectMapper.readValue(data, PurgingDTO.class);
                    purgingDTO = purgingAssembler.setAuditFields(entity,purgingDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return purgingDTO;
            }).toList());

            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return purgingDTOList;
    }


    public TransactionStatus processPurging (SessionContext sessionContext, PurgingDTO purgingDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In processPurging with parameters sessionContext {}, countryDTO {}",
                    sessionContext, purgingDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(purgingDTO);
            fillTransactionStatus(transactionStatus);
        }
        catch (FatalException fatalException) {
            fillTransactionStatus(transactionStatus, fatalException);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            if (!Interaction.isLastInteraction()) {
                Interaction.close();
            }
        }
        return transactionStatus;
    }

    @Override
    public void addUpdateRecord (String data) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        PurgingDTO purgingDTO = objMapper.readValue(data, PurgingDTO.class);
        save(purgingDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return purgingAssembler.convertEntityToDto(purgingDomainService.getPurgingByCode(code));
    }

    @Override
    public void save (PurgingDTO purgingDTO) {
        purgingDomainService.save(purgingDTO);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return PurgingDTO.builder().build().getTaskCode();
    }

}
