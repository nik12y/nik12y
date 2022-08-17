package com.idg.idgcore.coe.domain.service.questionnaireChecklist;

import com.idg.idgcore.coe.domain.assembler.questionnaireChecklist.*;
import com.idg.idgcore.coe.domain.entity.questionnaireChecklist.*;
import com.idg.idgcore.coe.domain.repository.questionnaireChecklist.*;
import com.idg.idgcore.coe.dto.questionnaireChecklist.*;
import com.idg.idgcore.coe.exception.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

import static com.idg.idgcore.coe.exception.Error.*;

@Slf4j
@Service
public class QuestionnaireChecklistDomainService implements
        IQuestionnaireChecklistDomainService
{
    @Autowired
    private IQuestionnaireChecklistRepository questionnaireChecklistRepository;
    @Autowired
    private QuestionnaireChecklistAssembler questionnaireChecklistAssembler;

    public QuestionnaireChecklistEntity getConfigurationById (
            QuestionnaireChecklistDTO questionnaireChecklistDTO) {
        QuestionnaireChecklistEntity questionnaireChecklistEntity = null;
        try {
            questionnaireChecklistEntity = this.questionnaireChecklistRepository.findByQuestionChecklistId(
                    questionnaireChecklistDTO.getQuestionChecklistId());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return questionnaireChecklistEntity;
    }

    public List<QuestionnaireChecklistEntity> getQuestionnaireChecklists () {
        return this.questionnaireChecklistRepository.findAll();
    }

    @Override
    public QuestionnaireChecklistEntity getQuestionnaireChecklistById (String questionChecklistId) {
        QuestionnaireChecklistEntity questionnaireChecklistEntity = null;
        try {
            questionnaireChecklistEntity = this.questionnaireChecklistRepository.findByQuestionChecklistId(
                    questionChecklistId);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return questionnaireChecklistEntity;
    }

    public void save (QuestionnaireChecklistDTO questionnaireChecklistDTO) {
        try {
            QuestionnaireChecklistEntity questionnaireChecklistEntity = questionnaireChecklistAssembler.convertDtoToEntity(
                    questionnaireChecklistDTO);
            this.questionnaireChecklistRepository.save(questionnaireChecklistEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }

}
