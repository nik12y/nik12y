package com.idg.idgcore.coe.app.service.errorOverride;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.idg.idgcore.app.*;
import com.idg.idgcore.coe.app.config.*;
import com.idg.idgcore.coe.domain.assembler.audit.*;
import com.idg.idgcore.coe.domain.assembler.errorOverride.*;
import com.idg.idgcore.coe.domain.entity.errorOverride.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.domain.process.*;
import com.idg.idgcore.coe.domain.service.errorOverride.*;
import com.idg.idgcore.coe.domain.service.mutation.*;
import com.idg.idgcore.coe.dto.base.*;
import com.idg.idgcore.coe.dto.errorOverride.*;
import com.idg.idgcore.coe.dto.mutation.*;
import com.idg.idgcore.coe.exception.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import com.idg.idgcore.enumerations.core.*;
import lombok.extern.slf4j.*;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static com.idg.idgcore.coe.common.Constants.*;
import static com.idg.idgcore.coe.exception.Error.*;

@Slf4j
@Service ("errorOverrideService")
public class ErrorOverrideApplicationService extends AbstractApplicationService
        implements IErrorOverrideApplicationService
{
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IErrorOverrideDomainService errorOverrideDomainService;
    @Autowired
    private ErrorOverrideAssembler errorOverrideAssembler;
    @Autowired
    private MutationAssembler mutationAssembler;

    public ErrorOverrideDTO getErrorOverrideByCode (SessionContext sessionContext,
            ErrorOverrideDTO errorOverrideDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info(
                    "In getErrorOverrideByCode with parameters sessionContext {}, errorOverrideDTO {}",
                    sessionContext, errorOverrideDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ErrorOverrideDTO result = null;
        try {
            if (isAuthorized(errorOverrideDTO.getAuthorized())) {
                ErrorOverrideEntity errorOverrideEntity = errorOverrideDomainService.getRecordByErrorCodeAndBranchCode(
                        errorOverrideDTO.getErrorCode(), errorOverrideDTO.getBranchCode());
                result = errorOverrideAssembler.convertEntityToDto(errorOverrideEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        errorOverrideDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), ErrorOverrideDTO.class);
                result = errorOverrideAssembler.setAuditFields(mutationEntity, result);
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

    public List<ErrorOverrideDTO> getErrorCodes (SessionContext sessionContext)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getCountries with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<ErrorOverrideDTO> errorOverrideDTOList = new ArrayList<>();
        try {
            List<MutationEntity> unauthorizedEntities = mutationsDomainService.getUnauthorizedMutation(
                    getTaskCode(), AUTHORIZED_N);
            errorOverrideDTOList.addAll(errorOverrideDomainService.getErrorCodes().stream()
                    .map(entity -> errorOverrideAssembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
            errorOverrideDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                ErrorOverrideDTO errorOverrideDTO = null;
                try {
                    errorOverrideDTO = objectMapper.readValue(data, ErrorOverrideDTO.class);
                    errorOverrideDTO = errorOverrideAssembler.setAuditFields(entity,
                            errorOverrideDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return errorOverrideDTO;
            }).collect(Collectors.toList()));

            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return errorOverrideDTOList;
    }

    public TransactionStatus processErrorOverride (SessionContext sessionContext,
            ErrorOverrideDTO errorOverrideDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info(
                    "In processErrorOverride with parameters sessionContext {}, errorOverrideDTO {}",
                    sessionContext, errorOverrideDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            if (AUTHORIZE.equals(errorOverrideDTO.getAction()) && CLOSED.equals(
                    errorOverrideDTO.getStatus()))
                errorOverrideDTO.setIsExcluded(true);
            else
                errorOverrideDTO.setIsExcluded(false);
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(errorOverrideDTO);
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
        ErrorOverrideDTO errorOverrideDTO = objMapper.readValue(data, ErrorOverrideDTO.class);
        save(errorOverrideDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        String[] fields = code.split(FIELD_SEPARATOR);
        if (fields.length == 2) {
            ErrorOverrideDTO withAll = errorOverrideAssembler.convertEntityToDto(
                    errorOverrideDomainService.getByErrorCodeAndBranchCode(
                            fields[0], "ALL"));
            withAll.setBranchCode(fields[1]);
            return withAll;
        }
        return null;
    }

    @Override
    public void save (ErrorOverrideDTO errorOverrideDTO) {
        errorOverrideDomainService.validateAndSave(errorOverrideDTO);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return ErrorOverrideDTO.builder().build().getTaskCode();
    }

}