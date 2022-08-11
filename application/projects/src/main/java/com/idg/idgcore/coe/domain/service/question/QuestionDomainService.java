package com.idg.idgcore.coe.domain.service.question;

import com.idg.idgcore.coe.domain.assembler.question.QuestionAssembler;
import com.idg.idgcore.coe.domain.entity.question.QuestionEntity;
import com.idg.idgcore.coe.domain.repository.question.IQuestionRepository;
import com.idg.idgcore.coe.dto.question.QuestionDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class QuestionDomainService implements IQuestionDomainService {
    @Autowired
    private IQuestionRepository iQuestionRepository;
    @Autowired
    private QuestionAssembler questionAssembler;

    public QuestionEntity getConfigurationByCode (QuestionDTO questionDTO) {
        QuestionEntity questionEntity = null;
        try {
            questionEntity = this.iQuestionRepository.findByQuestionId(questionDTO.getQuestionId());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return questionEntity;
    }

    public List<QuestionEntity> getQuestions () {
        return this.iQuestionRepository.findAll();
    }

    public QuestionEntity getQuestionById (String questionId) {
        QuestionEntity questionEntity = null;
        try {
            questionEntity = this.iQuestionRepository.findByQuestionId(questionId);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return questionEntity;
    }

    public void save (QuestionDTO questionDTO) {
        try {
            QuestionEntity questionEntity = questionAssembler.convertDtoToEntity(questionDTO);
            this.iQuestionRepository.save(questionEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }

}
