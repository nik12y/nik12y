package com.idg.idgcore.coe.app.service.verificationcategory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.verificationcategory.AppVerCategoryConfigAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.verificationcategory.AppVerCategoryConfigEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.verificationcategory.IAppVerCategoryConfigDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.verificationcategory.AppVerCategoryConfigDTO;
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
@Service("verificationCategoryApplicationService")
public class AppVerCategoryApplicationService extends AbstractApplicationService
        implements IAppVerCategoryApplicationService {

    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IAppVerCategoryConfigDomainService appVerCategoryConfigDomainService;
    @Autowired
    private AppVerCategoryConfigAssembler appVerCategoryConfigAssembler;

    public AppVerCategoryConfigDTO getAppVerCategoryConfigByID(SessionContext sessionContext, AppVerCategoryConfigDTO appVerCategoryConfigDTO)
            throws FatalException {
        if (log.isErrorEnabled()) {
            log.info("In getAppVerCategoryConfigByID with parameters sessionContext{}, appVerCategoryConfigDTO {}",
                    sessionContext, appVerCategoryConfigDTO);
        }

        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        AppVerCategoryConfigDTO result = null;
        try{
            if (isAuthorized(appVerCategoryConfigDTO.getAuthorized())) {
                AppVerCategoryConfigEntity appVerCategoryConfigEntity = appVerCategoryConfigDomainService.getAppVerCategoryConfigByID(
                        appVerCategoryConfigDTO.getAppVerificationCategoryId());
                result = appVerCategoryConfigAssembler.convertEntityToDto(appVerCategoryConfigEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        appVerCategoryConfigDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), AppVerCategoryConfigDTO.class);
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



    public List<AppVerCategoryConfigDTO> getAppVerCategoryConfigs (SessionContext sessionContext) throws FatalException {

        if (log.isErrorEnabled()) {
            log.info("In getAppVerCategoryConfigs with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<AppVerCategoryConfigDTO> appVerCategoryConfigDTOList = new ArrayList<>();
        try {
            List<MutationEntity> unauthorizedEntities = mutationsDomainService.getUnauthorizedMutation(
                    getTaskCode(),AUTHORIZED_N);
            appVerCategoryConfigDTOList.addAll(appVerCategoryConfigDomainService.getAppVerCategoryConfigs().stream()
                    .map(entity -> appVerCategoryConfigAssembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
            appVerCategoryConfigDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                AppVerCategoryConfigDTO appVerCategoryConfigDTO = null;
                try {
                    appVerCategoryConfigDTO = objectMapper.readValue(data, AppVerCategoryConfigDTO.class);
                    appVerCategoryConfigDTO = appVerCategoryConfigAssembler.setAuditFields(entity,appVerCategoryConfigDTO);
                } catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return appVerCategoryConfigDTO;
            }).collect(Collectors.toList()));
            appVerCategoryConfigDTOList = appVerCategoryConfigDTOList.stream().collect(
                    Collectors.groupingBy(AppVerCategoryConfigDTO::getAppVerificationCategoryId, Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparing(AppVerCategoryConfigDTO::getRecordVersion)),
                            Optional::get))).values().stream().collect(Collectors.toList());
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return appVerCategoryConfigDTOList;
    }




    //    @Transactional
    public TransactionStatus processAppVerCategoryConfigs (SessionContext sessionContext, AppVerCategoryConfigDTO appVerCategoryConfigDTO)
            throws FatalException {
        if (log.isErrorEnabled()) {
            log.info("In processAppVerCategoryConfigs with parameters sessionContext {}, appVerCategoryConfigDTO {}",
                    sessionContext, appVerCategoryConfigDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(appVerCategoryConfigDTO);
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
        AppVerCategoryConfigDTO appVerCategoryConfigDTO = objMapper.readValue(data, AppVerCategoryConfigDTO.class);
        save(appVerCategoryConfigDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return appVerCategoryConfigAssembler.convertEntityToDto(appVerCategoryConfigDomainService.getAppVerCategoryConfigByID(code));
    }

    @Override
    public void save(AppVerCategoryConfigDTO appVerCategoryConfigDTO){
        appVerCategoryConfigDomainService.save(appVerCategoryConfigDTO);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return AppVerCategoryConfigDTO.builder().build().getTaskCode();
    }



}
