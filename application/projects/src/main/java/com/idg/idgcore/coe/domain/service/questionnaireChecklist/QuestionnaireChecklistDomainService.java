package com.idg.idgcore.coe.domain.service.questionnaireChecklist;

import com.idg.idgcore.coe.domain.assembler.questionnaireChecklist.QuestionnaireChecklistAssembler;
import com.idg.idgcore.coe.domain.entity.questionnaireChecklist.QuestionnaireChecklistEntity;
import com.idg.idgcore.coe.domain.repository.questionnaireChecklist.IQuestionnaireChecklistRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.questionnaireChecklist.QuestionnaireChecklistDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class QuestionnaireChecklistDomainService extends DomainService<QuestionnaireChecklistDTO,
        QuestionnaireChecklistEntity> {

    @Autowired
    private IQuestionnaireChecklistRepository questionnaireChecklistRepository;
    @Autowired
    private QuestionnaireChecklistAssembler questionnaireChecklistAssembler;

    @Override
    public QuestionnaireChecklistEntity getEntityByIdentifier(String questionChecklistId) {
        QuestionnaireChecklistEntity questionnaireChecklistEntity = null;
        try {
            questionnaireChecklistEntity = this.questionnaireChecklistRepository.findByQuestionChecklistId(
                    questionChecklistId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return questionnaireChecklistEntity;
    }

    @Override
    public List<QuestionnaireChecklistEntity> getAllEntities() {
        return this.questionnaireChecklistRepository.findAll();
    }

    public void save (QuestionnaireChecklistDTO questionnaireChecklistDTO) {
        try {
            QuestionnaireChecklistEntity questionnaireChecklistEntity = questionnaireChecklistAssembler.toEntity(
                    questionnaireChecklistDTO);
            this.questionnaireChecklistRepository.save(questionnaireChecklistEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}
