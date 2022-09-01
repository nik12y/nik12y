package com.idg.idgcore.coe.app.service.purpose;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.MutationAssembler;
import com.idg.idgcore.coe.domain.assembler.purpose.PurposeAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.purpose.PurposeEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.purpose.IPurposeDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.country.CountryDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.purpose.PurposeDTO;
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
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.idg.idgcore.coe.common.Constants.*;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Slf4j
@Service("purposeApplicationService")
public class PurposeApplicationService extends AbstractApplicationService implements IPurposeApplicationService
{
    ModelMapper mapper = new ModelMapper();

    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;

    @Autowired
    private IPurposeDomainService purposeDomainService;

    @Autowired
    private PurposeAssembler purposeAssembler;

    @Autowired
    private MutationAssembler mutationAssembler;

    public PurposeDTO getPurposeByCode(SessionContext sessionContext, PurposeDTO purposeDTO) throws FatalException {
        if(log.isInfoEnabled()) {
            log.info("In getPurposeByCode with parameters sessionContext {}, purposeDTO {}", sessionContext, purposeDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        PurposeDTO result = null;
        try {
            if(isAuthorized(purposeDTO.getAuthorized())) {
                PurposeEntity purposeEntity = purposeDomainService.getPurposeByCode(purposeDTO.getPurposeCode());
                result = purposeAssembler.convertEntityToDto(purposeEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        purposeDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), PurposeDTO.class);
                result = purposeAssembler.setAuditFields(mutationEntity,result);
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

    public List<PurposeDTO> getPurposes(SessionContext sessionContext) throws FatalException {
        if(log.isInfoEnabled()) {
            log.info("In getPurposes with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<PurposeDTO> purposeDTOList = new ArrayList<>();

        try {
            List<MutationEntity> entities = mutationsDomainService.getMutations(getTaskCode());
            purposeDTOList.addAll(entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                PurposeDTO purposeDTO = null;
                try {
                    purposeDTO = objectMapper.readValue(data, PurposeDTO.class);
                    purposeDTO = purposeAssembler.setAuditFields(entity,purposeDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return purposeDTO;
            }).toList());

            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return purposeDTOList;
    }

    @Transactional
    public TransactionStatus processPurpose(SessionContext sessionContext, PurposeDTO purposeDTO) throws  FatalException {
        if(log.isInfoEnabled()) {
            log.info("In processPurpose with parameters sessionContext {}, purposeDTO {}", sessionContext, purposeDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(purposeDTO);
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
        PurposeDTO purposeDTO = objMapper.readValue(data, PurposeDTO.class);
        save(purposeDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode(String code) {
        return purposeAssembler.convertEntityToDto(purposeDomainService.getPurposeByCode(code));
    }

    @Override
    public void save(PurposeDTO purposeDTO) {
        purposeDomainService.save(purposeDTO);
    }

    private boolean isAuthorized(final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode() {
        return PurposeDTO.builder().build().getTaskCode();
    }
}



