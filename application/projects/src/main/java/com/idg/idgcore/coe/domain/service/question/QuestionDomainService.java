package com.idg.idgcore.coe.domain.service.question;

import com.idg.idgcore.coe.domain.assembler.question.QuestionAssembler;
import com.idg.idgcore.coe.domain.entity.question.QuestionEntity;
import com.idg.idgcore.coe.domain.repository.question.IQuestionRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.question.QuestionDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class QuestionDomainService extends DomainService<QuestionDTO, QuestionEntity> {
    @Autowired
    private IQuestionRepository iQuestionRepository;
    @Autowired
    private QuestionAssembler questionAssembler;

    @Override
    public QuestionEntity getEntityByIdentifier(String questionId) {
        QuestionEntity questionEntity = null;
        try {
            questionEntity = this.iQuestionRepository.findByQuestionId(questionId);
        } catch (Exception e) {
            log.error(e.getMessage());
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return questionEntity;
    }

    @Override
    public List<QuestionEntity> getAllEntities() {
        return this.iQuestionRepository.findAll();
    }

    public void save (QuestionDTO questionDTO) {
        try {
            QuestionEntity questionEntity = questionAssembler.toEntity(questionDTO);
            this.iQuestionRepository.save(questionEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}
