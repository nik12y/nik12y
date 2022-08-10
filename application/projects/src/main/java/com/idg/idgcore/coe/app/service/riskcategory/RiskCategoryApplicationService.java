package com.idg.idgcore.coe.app.service.riskcategory;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.MutationAssembler;
import com.idg.idgcore.coe.domain.assembler.riskcategory.RiskCategoryAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.riskcategory.RiskCategoryEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.riskcategory.IRiskCategoryDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.riskcategory.RiskCategoryDTO;
import com.idg.idgcore.coe.exception.Error;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Slf4j
@Service("riskCategoryApplicationService")
public class RiskCategoryApplicationService extends AbstractApplicationService
    implements IRiskCategoryApplicationService
{
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;

    @Autowired
    private MappingConfig mappingconfig;

    @Autowired
    private IMutationsDomainService mutationdomainservice;

    @Autowired
    private IRiskCategoryDomainService riskcategorydomainservice;

    @Autowired
    private RiskCategoryAssembler riskcategoryassembler;

    @Autowired
    private MutationAssembler mutationassembler;






    @Transactional
    public TransactionStatus processRiskCategory(SessionContext sessionContext, RiskCategoryDTO riskcategorydto) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In processCountry with parameters sessionContext {}, riskcategorydto {}",
                    sessionContext, riskcategorydto);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(riskcategorydto);
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
    public void save(RiskCategoryDTO riskcategorydto)
    {riskcategorydomainservice.save(riskcategorydto);

    }


    public RiskCategoryDTO getRiskCategoryByCode(SessionContext sessionContext, RiskCategoryDTO riskcategorydto) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getRiskCategoryByCode with parameters sessionContext {}, riskcategory {} ",
                    sessionContext, riskcategorydto);
        }

        TransactionStatus transactionstatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);

        RiskCategoryDTO result = null;
        try {
            if (isAuthorized(riskcategorydto.getAuthorized())) {
                RiskCategoryEntity riskcategoryentity = riskcategorydomainservice.getRiskCategoryByCode(
                        riskcategorydto.getRiskCategoryCode());
                result = riskcategoryassembler.convertEntityToDto(riskcategoryentity);
            } else {
                MutationEntity mutationentity = mutationdomainservice.getConfigurationByCode(
                        riskcategorydto.getTaskIdentifier());
                ObjectMapper objectmapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationentity.getPayload(), PayloadDTO.class);
                result = objectmapper.readValue(payload.getData(), RiskCategoryDTO.class);
                result = riskcategoryassembler.setAuditFields(mutationentity, result);
                fillTransactionStatus(transactionstatus);
            }
        } catch (JsonProcessingException jpe) {
            ExceptionUtil.handleException(Error.JSON_PARSING_ERROR);
        } catch (Exception exception) {
            fillTransactionStatus(transactionstatus, exception);
        } finally {
            Interaction.close();
        }
        return result;
    }


    public List<RiskCategoryDTO> getRiskCategories(SessionContext sessionContext) throws FatalException {
        if (log.isInfoEnabled()) {

            log.info("In getRiskCategories with parameters sessionContext {} ", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext,TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<RiskCategoryDTO> riskCategoryDTOList = new ArrayList<>();

        try{
            List<MutationEntity> unauthorizedEntities = mutationdomainservice.getUnauthorizedMutation(
                    getTaskCode(),AUTHORIZED_N);
            riskCategoryDTOList.addAll(riskcategorydomainservice.getRiskCategories().stream()
                    .map(entity -> riskcategoryassembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
            riskCategoryDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
                String data = entity.getPayload().getData();
               RiskCategoryDTO riskCategoryDTO = null;
                try {
                    riskCategoryDTO = objectMapper.readValue(data, RiskCategoryDTO.class);
                    riskCategoryDTO = riskcategoryassembler.setAuditFields(entity,riskCategoryDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return riskCategoryDTO;
            }).collect(Collectors.toList()));
            riskCategoryDTOList = riskCategoryDTOList.stream().collect(
                    Collectors.groupingBy(RiskCategoryDTO::getRiskCategoryCode, Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparing(RiskCategoryDTO::getRecordVersion)),
                            Optional::get))).values().stream().collect(Collectors.toList());
            fillTransactionStatus(transactionStatus);


        }catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return riskCategoryDTOList;

    }


    @Override
    public void addUpdateRecord(String data) throws JsonProcessingException {

        ObjectMapper objMapper = new ObjectMapper();
        RiskCategoryDTO  riskCategoryDTO = objMapper.readValue(data, RiskCategoryDTO.class);
        save(riskCategoryDTO);
    }

    @Override
public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return riskcategoryassembler.convertEntityToDto(riskcategorydomainservice.getRiskCategoryByCode(code));
        }

private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
        }



private String getTaskCode () {
        return RiskCategoryDTO.builder().build().getTaskCode();
        }
}
