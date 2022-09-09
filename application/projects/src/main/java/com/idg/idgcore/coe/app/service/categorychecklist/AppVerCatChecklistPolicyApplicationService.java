package com.idg.idgcore.coe.app.service.categorychecklist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.categorychecklist.AppVerCatChecklistPolicyAssembler;
import com.idg.idgcore.coe.domain.entity.categorychecklist.AppVerCatChecklistPolicyEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.categorychecklist.IAppVerCatChecklistPolicyDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.categorychecklist.AppVerCatChecklistPolicyDTO;
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
@Service("checklistApplicationService")
public class AppVerCatChecklistPolicyApplicationService extends AbstractApplicationService
        implements IAppVerCatChecklistPolicyApplicationService{

    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IAppVerCatChecklistPolicyDomainService appVerCatChecklistPolicyDomainService;
    @Autowired
    private AppVerCatChecklistPolicyAssembler appVerCatChecklistPolicyAssembler;

    @Override
    public AppVerCatChecklistPolicyDTO getAppVerCatChecklistPolicyById(SessionContext sessionContext, AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO)
            throws FatalException {
        if (log.isErrorEnabled()) {
            log.info("In getAppVerCatChecklistPolicyByID with parameters sessionContext{}, appVerCatChecklistPolicyDTO {}",
                    sessionContext, appVerCatChecklistPolicyDTO);
        }

        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        AppVerCatChecklistPolicyDTO result = null;
        try{
            if (isAuthorized(appVerCatChecklistPolicyDTO.getAuthorized())) {
                AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity = appVerCatChecklistPolicyDomainService.getAppVerChecklistPolicyById(
                        appVerCatChecklistPolicyDTO.getAppVerChecklistPolicyId());
                result = appVerCatChecklistPolicyAssembler.convertEntityToDto(appVerCatChecklistPolicyEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        appVerCatChecklistPolicyDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), AppVerCatChecklistPolicyDTO.class);
                result = appVerCatChecklistPolicyAssembler.setAuditFields(mutationEntity, result);
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


    public List<AppVerCatChecklistPolicyDTO> getAppVerCatChecklistPolicies (SessionContext sessionContext) throws FatalException {

        if (log.isErrorEnabled()) {
            log.info("In getAppVerCatChecklistPolicys with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<AppVerCatChecklistPolicyDTO> appVerCatChecklistPolicyDTOList = new ArrayList<>();
        try {
            List<MutationEntity> entities = mutationsDomainService.getMutations(getTaskCode());
            appVerCatChecklistPolicyDTOList.addAll(entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO = null;
                try {
                    appVerCatChecklistPolicyDTO = objectMapper.readValue(data, AppVerCatChecklistPolicyDTO.class);
                    appVerCatChecklistPolicyDTO = appVerCatChecklistPolicyAssembler.setAuditFields(entity,appVerCatChecklistPolicyDTO);
                } catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return appVerCatChecklistPolicyDTO;
            }).toList());

            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return appVerCatChecklistPolicyDTOList;
    }

    //    @Transactional
    public TransactionStatus processAppVerCatChecklistPolicies (SessionContext sessionContext, AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO)
            throws FatalException {
        if (log.isErrorEnabled()) {
            log.info("In processAppVerCatChecklistPolicys with parameters sessionContext {}, appVerCatChecklistPolicyDTO {}",
                    sessionContext, appVerCatChecklistPolicyDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(appVerCatChecklistPolicyDTO);
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
        AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO = objMapper.readValue(data, AppVerCatChecklistPolicyDTO.class);
        save(appVerCatChecklistPolicyDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return appVerCatChecklistPolicyAssembler.convertEntityToDto(appVerCatChecklistPolicyDomainService.getAppVerChecklistPolicyById(code));
    }

    @Override
    public void save(AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO){
        appVerCatChecklistPolicyDomainService.save(appVerCatChecklistPolicyDTO);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return AppVerCatChecklistPolicyDTO.builder().build().getTaskCode();
    }

}


