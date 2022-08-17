//package com.idg.idgcore.coe.app.service.module;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.idg.idgcore.app.AbstractApplicationService;
//import com.idg.idgcore.app.Interaction;
//import com.idg.idgcore.coe.app.config.MappingConfig;
//import com.idg.idgcore.coe.domain.assembler.audit.*;
//import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
//import com.idg.idgcore.coe.dto.module.ModuleDTO;
//import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
//import com.idg.idgcore.coe.domain.assembler.module.ModuleAssembler;
//import com.idg.idgcore.coe.domain.entity.module.ModuleEntity;
//import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
//import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
//import com.idg.idgcore.coe.domain.service.module.IModuleDomainService;
//import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
//import com.idg.idgcore.coe.exception.*;
//import com.idg.idgcore.dto.context.SessionContext;
//import com.idg.idgcore.datatypes.core.TransactionStatus;
//import com.idg.idgcore.enumerations.core.TransactionMessageType;
//import com.idg.idgcore.datatypes.exceptions.FatalException;
//import lombok.extern.slf4j.Slf4j;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;
//import java.util.Arrays;
//
//import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
//import static com.idg.idgcore.coe.common.Constants.CHECKER;
//import static com.idg.idgcore.coe.common.Constants.DRAFT;
//import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;
//
//@Slf4j
//@Service ("moduleApplicationService")
//public class ModuleApplicationService extends AbstractApplicationService
//        implements IModuleApplicationService
//{
//    ModelMapper mapper = new ModelMapper();
//    @Autowired
//    private IProcessConfiguration process;
//    @Autowired
//    private MappingConfig mappingConfig;
//    @Autowired
//    private IMutationsDomainService mutationsDomainService;
//    @Autowired
//    private IModuleDomainService moduleDomainService;
//    @Autowired
//    private ModuleAssembler moduleAssembler;
//    @Autowired
//    private MutationAssembler mutationAssembler;
//
//    public ModuleDTO getModuleByCode (SessionContext sessionContext, ModuleDTO moduleDTO)
//            throws FatalException {
//        if (log.isInfoEnabled()) {
//            log.info("In getModuleByCode with parameters sessionContext {}, moduleDTO {}",
//                    sessionContext, moduleDTO);
//        }
//        TransactionStatus transactionStatus = fetchTransactionStatus();
//        Interaction.begin(sessionContext);
//        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
//        ModuleDTO result = null;
//        try {
//            if (isAuthorized(moduleDTO.getAuthorized())) {
//                ModuleEntity moduleEntity = moduleDomainService.getModuleByCode(
//                        moduleDTO.getModuleCode());
//                result = moduleAssembler.convertEntityToDto(moduleEntity);
//            }
//            else {
//                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
//                        moduleDTO.getTaskIdentifier());
//                ObjectMapper objectMapper = new ObjectMapper();
//                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
//                result = objectMapper.readValue(payload.getData(), ModuleDTO.class);
//                result = moduleAssembler.setAuditFields(mutationEntity,result);
//                fillTransactionStatus(transactionStatus);
//            }
//        }
//        catch (JsonProcessingException jpe) {
//            ExceptionUtil.handleException(JSON_PARSING_ERROR);
//        }
//        catch (Exception exception) {
//            fillTransactionStatus(transactionStatus, exception);
//        }
//        finally {
//            Interaction.close();
//        }
//        return result;
//    }
//
//    public List<ModuleDTO> getModules (SessionContext sessionContext) throws FatalException {
//        if (log.isInfoEnabled()) {
//            log.info("In getCountries with parameters sessionContext {}", sessionContext);
//        }
//        TransactionStatus transactionStatus = fetchTransactionStatus();
//        Interaction.begin(sessionContext);
//        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<ModuleDTO> moduleDTOList = new ArrayList<>();
//
//        try {
//            List<MutationEntity> unauthorizedEntities = mutationsDomainService.getUnauthorizedMutation(
//                    getTaskCode(),AUTHORIZED_N);
//            moduleDTOList.addAll(moduleDomainService.getModules().stream()
//                    .map(entity -> moduleAssembler.convertEntityToDto(entity))
//                    .collect(Collectors.toList()));
//            moduleDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
//                String data = entity.getPayload().getData();
//                ModuleDTO moduleDTO = null;
//                try {
//                    moduleDTO = objectMapper.readValue(data, ModuleDTO.class);
//                    moduleDTO = moduleAssembler.setAuditFields(entity,moduleDTO);
//                }
//                catch (JsonProcessingException e) {
//                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
//                }
//                return moduleDTO;
//            }).collect(Collectors.toList()));
//            moduleDTOList = moduleDTOList.stream().collect(
//                    Collectors.groupingBy(ModuleDTO::getModuleCode, Collectors.collectingAndThen(
//                            Collectors.maxBy(Comparator.comparing(ModuleDTO::getRecordVersion)),
//                            Optional::get))).values().stream().collect(Collectors.toList());
//            fillTransactionStatus(transactionStatus);
//        }
//        catch (Exception exception) {
//            fillTransactionStatus(transactionStatus, exception);
//        }
//        finally {
//            Interaction.close();
//        }
//        return moduleDTOList;
//    }
//
//    @Transactional
//    public TransactionStatus processModule (SessionContext sessionContext, ModuleDTO moduleDTO)
//            throws FatalException {
//        if (log.isInfoEnabled()) {
//            log.info("In processModule with parameters sessionContext {}, moduleDTO {}",
//                    sessionContext, moduleDTO);
//        }
//        TransactionStatus transactionStatus = fetchTransactionStatus();
//        try {
//            Interaction.begin(sessionContext);
//            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
//            process.process(moduleDTO);
//            fillTransactionStatus(transactionStatus);
//        }
//        catch (FatalException fatalException) {
//            fillTransactionStatus(transactionStatus, fatalException);
//        }
//        catch (Exception exception) {
//            fillTransactionStatus(transactionStatus, exception);
//        }
//        finally {
//            if (!Interaction.isLastInteraction()) {
//                Interaction.close();
//            }
//        }
//        return transactionStatus;
//    }
//
//    @Override
//    public void addUpdateRecord (String data) throws JsonProcessingException {
//        ObjectMapper objMapper = new ObjectMapper();
//        ModuleDTO moduleDTO = objMapper.readValue(data, ModuleDTO.class);
//        save(moduleDTO);
//    }
//
//    @Override
//    public CoreEngineBaseDTO getConfigurationByCode (String code) {
//        return moduleAssembler.convertEntityToDto(moduleDomainService.getModuleByCode(code));
//    }
//
//    @Override
//    public void save (ModuleDTO moduleDTO) {
//        moduleDomainService.save(moduleDTO);
//    }
//
//    private boolean isAuthorized (final String authorized) {
//        Predicate<String> isAuthorized = s -> s.equals("Y");
//        return isAuthorized.test(authorized);
//    }
//
//    private String getTaskCode () {
//        return ModuleDTO.builder().build().getTaskCode();
//    }
//
//}