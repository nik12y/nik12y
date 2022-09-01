package com.idg.idgcore.coe.app.service.questionCategory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.MutationAssembler;
import com.idg.idgcore.coe.domain.assembler.questionCategory.QuestionCategoryAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCategoryEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.questionCategory.IQuestionCategoryDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDTO;
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
@Service("questionCategoryApplicationService")
public class QuestionCategoryApplicationService extends AbstractApplicationService
        implements IQuestionCategoryApplicationService {
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IQuestionCategoryDomainService iQuestionCategoryDomainService;
    @Autowired
    private QuestionCategoryAssembler questionCategoryAssembler;
    @Autowired
    private MutationAssembler mutationAssembler;

    public QuestionCategoryDTO getQuestionCategoryById(SessionContext sessionContext, QuestionCategoryDTO questionCategoryDTO) throws FatalException, JsonProcessingException {
        if (log.isInfoEnabled()) {
            log.info("In getQuestionCategoryById with parameters sessionContext {}, questionCategoryDTO {}",
                    sessionContext, questionCategoryDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        QuestionCategoryDTO result = null;
        try {
            if (isAuthorized(questionCategoryDTO.getAuthorized())) {
                QuestionCategoryEntity questionCategoryEntity = iQuestionCategoryDomainService.getQuestionCategoryById(
                        questionCategoryDTO.getQuestionCategoryId());
                result = questionCategoryAssembler.convertEntityToDto(questionCategoryEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        questionCategoryDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), QuestionCategoryDTO.class);
                result = questionCategoryAssembler.setAuditFields(mutationEntity,result);
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

    public List<QuestionCategoryDTO> getQuestionsCategories(SessionContext sessionContext) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getQuestionsCategories with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<QuestionCategoryDTO> questionCategoryDTOList = new ArrayList<>();

        try {
            List<MutationEntity> entities = mutationsDomainService.getMutations(getTaskCode());
            questionCategoryDTOList.addAll(entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                QuestionCategoryDTO questionCategoryDTO = null;
                try {
                    questionCategoryDTO = objectMapper.readValue(data, QuestionCategoryDTO.class);
                    questionCategoryDTO = questionCategoryAssembler.setAuditFields(entity,questionCategoryDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return questionCategoryDTO;
            }).toList());

            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return questionCategoryDTOList;
    }


    public TransactionStatus processQuestionCategory(SessionContext sessionContext, QuestionCategoryDTO questionCategoryDTO) throws FatalException, JsonProcessingException {
        if (log.isInfoEnabled()) {
            log.info("In processQuestionCategory with parameters sessionContext {}, questionCategoryDTO {}",
                    sessionContext, questionCategoryDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(questionCategoryDTO);
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
    public void addUpdateRecord(String data) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        QuestionCategoryDTO questionCategoryDTO = objMapper.readValue(data, QuestionCategoryDTO.class);
        save(questionCategoryDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode(String code) {
        return questionCategoryAssembler.convertEntityToDto(iQuestionCategoryDomainService.getQuestionCategoryById(code));
    }

    @Override
    public void save(QuestionCategoryDTO questionCategoryDTO) {
        iQuestionCategoryDomainService.save(questionCategoryDTO);
    }
    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return QuestionCategoryDTO.builder().build().getTaskCode();
    }

}
