package com.idg.idgcore.coe.app.service.mitigant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.MutationAssembler;
import com.idg.idgcore.coe.domain.assembler.mitigant.MitigantAssembler;
import com.idg.idgcore.coe.domain.entity.mitigant.MitigantEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mitigant.IMitigantDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.mitigant.MitigantDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Slf4j
@Service("mitigantApplicationService")
public class MitigantApplicationService extends AbstractApplicationService implements IMitigantApplicationService {

    ModelMapper mapper = new ModelMapper();

    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;

    @Autowired
    private IMitigantDomainService mitigantDomainService;

    @Autowired
    private MitigantAssembler mitigantAssembler;

    @Autowired
    private MutationAssembler mutationAssembler;

    public TransactionStatus processMitigant(SessionContext sessionContext, MitigantDTO mitigantDTO) throws FatalException, JsonProcessingException {
        if(log.isInfoEnabled()) {
            log.info("In processMitigant with parameters sessionContext {}, mitigantDTO {}", sessionContext, mitigantDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(mitigantDTO);
            fillTransactionStatus(transactionStatus);
        }
        catch (FatalException fatalException) {
            fillTransactionStatus(transactionStatus, fatalException);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            if(!Interaction.isLastInteraction()) {
                Interaction.close();
            }
        }
        return transactionStatus;
    }


    @Override
    public void addUpdateRecord(String data) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        MitigantDTO mitigantDTO = objMapper.readValue(data, MitigantDTO.class);
        save(mitigantDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode(String code) {
        return mitigantAssembler.convertEntityToDto(mitigantDomainService.getMitigantByCode(code));
    }

    @Override
    public void save(MitigantDTO mitigantDTO) {
        mitigantDomainService.save(mitigantDTO);
    }

    public MitigantDTO getMitigantByCode(SessionContext sessionContext, MitigantDTO mitigantDTO) throws FatalException {
        if(log.isInfoEnabled()) {
            log.info("In getMitigantByCode with parameters sessionContext {}, mitigantDTO {}", sessionContext, mitigantDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        MitigantDTO result = null;
        try {
            if(isAuthorized(mitigantDTO.getAuthorized())) {
                MitigantEntity mitigantEntity = mitigantDomainService.getMitigantByCode(mitigantDTO.getMitigantCode());
                result = mitigantAssembler.convertEntityToDto(mitigantEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        mitigantDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), MitigantDTO.class);
                result = mitigantAssembler.setAuditFields(mutationEntity,result);
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

    public List<MitigantDTO> getMitigantAll(SessionContext sessionContext) throws FatalException {
        if(log.isInfoEnabled()) {
            log.info("In getMitigantAll with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<MitigantDTO> mitigantDTOList = new ArrayList<>();

        try {
            List<MutationEntity> entities = mutationsDomainService.getMutations(getTaskCode());
            mitigantDTOList.addAll(entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                MitigantDTO mitigantDTO = null;
                try {
                    mitigantDTO = objectMapper.readValue(data, MitigantDTO.class);
                    mitigantDTO = mitigantAssembler.setAuditFields(entity,mitigantDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return mitigantDTO;
            }).toList());

            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return mitigantDTOList;
    }

    private boolean isAuthorized(final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return MitigantDTO.builder().build().getTaskCode();
    }
}
