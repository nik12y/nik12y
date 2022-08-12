package com.idg.idgcore.coe.app.service.questionnaireChecklist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.*;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.questionnaireChecklist.QuestionnaireChecklistDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.domain.assembler.questionnaireChecklist.QuestionnaireChecklistAssembler;
import com.idg.idgcore.coe.domain.entity.questionnaireChecklist.QuestionnaireChecklistEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.questionnaireChecklist.IQuestionnaireChecklistDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.exception.*;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
import com.idg.idgcore.datatypes.exceptions.FatalException;
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
@Service ("questionnaireChecklistApplicationService")
public class QuestionnaireChecklistApplicationService extends AbstractApplicationService
        implements IQuestionnaireChecklistApplicationService
{
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IQuestionnaireChecklistDomainService domainService;
    @Autowired
    private QuestionnaireChecklistAssembler assembler;
    @Autowired
    private MutationAssembler mutationAssembler;

    public QuestionnaireChecklistDTO getQuestionnaireChecklistById (SessionContext sessionContext, QuestionnaireChecklistDTO questionnaireChecklistDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getQuestionnaireChecklistByCode with parameters sessionContext {}, questionnaireChecklistDTO {}",
                    sessionContext, questionnaireChecklistDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        QuestionnaireChecklistDTO result = null;
        try {
            if (isAuthorized(questionnaireChecklistDTO.getAuthorized())) {
                QuestionnaireChecklistEntity questionnaireChecklistEntity = domainService.getQuestionnaireChecklistById(
                        questionnaireChecklistDTO.getQuestionChecklistId());
                result = assembler.convertEntityToDto(questionnaireChecklistEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        questionnaireChecklistDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), QuestionnaireChecklistDTO.class);
                result = assembler.setAuditFields(mutationEntity,result);
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

    public List<QuestionnaireChecklistDTO> getQuestionnaireChecklists (SessionContext sessionContext) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getCountries with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<QuestionnaireChecklistDTO> questionnaireChecklistDTOList = new ArrayList<>();

        try {
            List<MutationEntity> unauthorizedEntities = mutationsDomainService.getUnauthorizedMutation(
                    getTaskCode(),AUTHORIZED_N);
            questionnaireChecklistDTOList.addAll(domainService.getQuestionnaireChecklists().stream()
                    .map(entity -> assembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
            questionnaireChecklistDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                QuestionnaireChecklistDTO questionnaireChecklistDTO = null;
                try {
                    questionnaireChecklistDTO = objectMapper.readValue(data, QuestionnaireChecklistDTO.class);
                    questionnaireChecklistDTO = assembler.setAuditFields(entity,questionnaireChecklistDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return questionnaireChecklistDTO;
            }).collect(Collectors.toList()));
            questionnaireChecklistDTOList = questionnaireChecklistDTOList.stream().collect(
                    Collectors.groupingBy(QuestionnaireChecklistDTO::getQuestionChecklistId, Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparing(QuestionnaireChecklistDTO::getRecordVersion)),
                            Optional::get))).values().stream().collect(Collectors.toList());
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return questionnaireChecklistDTOList;
    }

    public TransactionStatus processQuestionnaireChecklist (SessionContext sessionContext, QuestionnaireChecklistDTO questionnaireChecklistDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In processQuestionnaireChecklist with parameters sessionContext {}, questionnaireChecklistDTO {}",
                    sessionContext, questionnaireChecklistDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(questionnaireChecklistDTO);
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
        QuestionnaireChecklistDTO questionnaireChecklistDTO = objMapper.readValue(data, QuestionnaireChecklistDTO.class);
        save(questionnaireChecklistDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String questionChecklistId) {
        return assembler.convertEntityToDto(domainService.getQuestionnaireChecklistById(questionChecklistId));
    }

    @Override
    public void save (QuestionnaireChecklistDTO questionnaireChecklistDTO) {
        domainService.save(questionnaireChecklistDTO);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return QuestionnaireChecklistDTO.builder().build().getTaskCode();
    }

}