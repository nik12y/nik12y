package com.idg.idgcore.coe.app.service.appvertype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.appvertype.AppVerTypeAssembler;
import com.idg.idgcore.coe.domain.entity.appvertype.AppVerTypeEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.appvertype.IAppVerTypeDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.appvertype.AppVerTypeDTO;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Slf4j
@Service("appVerTypeApplicationService")
public class AppVerTypeApplicationService extends AbstractApplicationService implements IAppVerTypeApplicationService {

    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IAppVerTypeDomainService appVerTypeDomainService;
    @Autowired
    private AppVerTypeAssembler appVerTypeAssembler;

    public AppVerTypeDTO getAppVerTypeByID(SessionContext sessionContext, AppVerTypeDTO appVerTypeDTO) throws FatalException, JsonProcessingException {
        if (log.isErrorEnabled()) {
            log.info("In getAppVerTypeByID with parameters sessionContext{}, appVerTypeDTO {}",
                    sessionContext, appVerTypeDTO);
        }

        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        AppVerTypeDTO result = null;
        try {
            if (isAuthorized(appVerTypeDTO.getAuthorized())) {
                AppVerTypeEntity appVerTypeEntity = appVerTypeDomainService.getAppVerTypeByID(
                        appVerTypeDTO.getVerificationTypeId());
                result = appVerTypeAssembler.convertEntityToDto(appVerTypeEntity);
            } else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        appVerTypeDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), AppVerTypeDTO.class);
                result = appVerTypeAssembler.setAuditFields(mutationEntity, result);
                fillTransactionStatus(transactionStatus);
            }
        } catch (JsonProcessingException jpe) {
            ExceptionUtil.handleException(JSON_PARSING_ERROR);
        } catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        } finally {
            Interaction.close();
        }
        return result;
    }

    public List<AppVerTypeDTO> getAppVerTypes(SessionContext sessionContext) throws FatalException {
        if (log.isErrorEnabled()) {
            log.info("In getAppVerTypes with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<AppVerTypeDTO> appVerTypeDTOList = new ArrayList<>();
        try {
            List<MutationEntity> unauthorizedEntities = mutationsDomainService.getUnauthorizedMutation(
                    getTaskCode(), AUTHORIZED_N);
            appVerTypeDTOList.addAll(appVerTypeDomainService.getAppVerTypes().stream()
                    .map(entity -> appVerTypeAssembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
            appVerTypeDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                AppVerTypeDTO appVerTypeDTO = null;
                try {
                    appVerTypeDTO = objectMapper.readValue(data, AppVerTypeDTO.class);
                    appVerTypeDTO = appVerTypeAssembler.setAuditFields(entity, appVerTypeDTO);
                } catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return appVerTypeDTO;
            }).collect(Collectors.toList()));
            appVerTypeDTOList = appVerTypeDTOList.stream().collect(
                    Collectors.groupingBy(AppVerTypeDTO::getVerificationTypeId, Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparing(AppVerTypeDTO::getRecordVersion)),
                            Optional::get))).values().stream().collect(Collectors.toList());
            fillTransactionStatus(transactionStatus);
        } catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        } finally {
            Interaction.close();
        }
        return appVerTypeDTOList;
    }

    public TransactionStatus processAppVerType(SessionContext sessionContext, AppVerTypeDTO appVerTypeDTO) throws FatalException, JsonProcessingException {
        if (log.isErrorEnabled()) {
            log.info("In processAppVerType with parameters sessionContext {}, appVerTypeDTO {}",
                    sessionContext, appVerTypeDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(appVerTypeDTO);
            fillTransactionStatus(transactionStatus);
        } catch (FatalException fatalException) {
            fillTransactionStatus(transactionStatus, fatalException);
        } catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        } finally {
            if (!Interaction.isLastInteraction()) {
                Interaction.close();
            }
        }
        return transactionStatus;
    }

    public void addUpdateRecord(String data) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        AppVerTypeDTO appVerTypeDTO = objMapper.readValue(data, AppVerTypeDTO.class);
        save(appVerTypeDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode(String code) {
        return appVerTypeAssembler.convertEntityToDto(appVerTypeDomainService.getAppVerTypeByID(code));
    }

    @Override
    public void save(AppVerTypeDTO appVerTypeDTO) {
        appVerTypeDomainService.save(appVerTypeDTO);

    }

    private boolean isAuthorized(final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode() {
        return AppVerTypeDTO.builder().build().getTaskCode();
    }

}
