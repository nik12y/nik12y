package com.idg.idgcore.coe.domain.service.questionCategory;

import com.idg.idgcore.coe.domain.assembler.questionCategory.QuestionCategoryAssembler;
import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCategoryEntity;
import com.idg.idgcore.coe.domain.repository.questionCategory.IQuestionCategoryRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class QuestionCategoryDomainService extends DomainService<QuestionCategoryDTO, QuestionCategoryEntity> {

    @Autowired
    private IQuestionCategoryRepository iQuestionCategoryRepository;

    @Autowired
    private QuestionCategoryAssembler questionCategoryAssembler;

    public QuestionCategoryEntity getConfigurationByCode(QuestionCategoryDTO questionCategoryDTO) {
        QuestionCategoryEntity questionCategoryEntity = null;
        try {
            questionCategoryEntity = this.iQuestionCategoryRepository.findByQuestionCategoryId(questionCategoryDTO.getQuestionCategoryId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return questionCategoryEntity;
    }

    @Override
    public QuestionCategoryEntity getEntityByIdentifier(String questionCategoryId) {
        QuestionCategoryEntity questionCategoryEntity = null;
        try {
            questionCategoryEntity = this.iQuestionCategoryRepository.findByQuestionCategoryId(questionCategoryId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return questionCategoryEntity;
    }

    @Override
    public List<QuestionCategoryEntity> getAllEntities() {
        return this.iQuestionCategoryRepository.findAll();
    }

    public void save(QuestionCategoryDTO questionCategoryDTO) {
        try {
            QuestionCategoryEntity questionCategoryEntity = this.questionCategoryAssembler.toEntity(questionCategoryDTO);
            this.iQuestionCategoryRepository.save(questionCategoryEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}
