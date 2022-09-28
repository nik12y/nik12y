package com.idg.idgcore.coe.domain.assembler.questionnaireChecklist;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.questionnaireChecklist.QuestionnaireChecklistDetailsCategoryEntity;
import com.idg.idgcore.coe.domain.entity.questionnaireChecklist.QuestionnaireChecklistDisplayEntity;
import com.idg.idgcore.coe.domain.entity.questionnaireChecklist.QuestionnaireChecklistEntity;
import com.idg.idgcore.coe.dto.questionnaireChecklist.QuestionnaireChecklistDTO;
import com.idg.idgcore.coe.dto.questionnaireChecklist.QuestionnaireChecklistDetailsCategoryDTO;
import com.idg.idgcore.coe.dto.questionnaireChecklist.QuestionnaireChecklistDisplayDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;


@Component
@Slf4j
public class QuestionnaireChecklistAssembler extends Assembler<QuestionnaireChecklistDTO, QuestionnaireChecklistEntity> {

    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Class getSpecificDTOClass() {
        return QuestionnaireChecklistDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return QuestionnaireChecklistEntity.class;
    }

    @Override
    public QuestionnaireChecklistEntity toEntity(QuestionnaireChecklistDTO questionnaireChecklistDTO) {
        QuestionnaireChecklistEntity questionnaireChecklistEntity = super.toEntity(questionnaireChecklistDTO);
        /**
         * For Questionnaire Checklist Details List
         */
        QuestionnaireChecklistDetailsCategoryDTO detailsDTO = questionnaireChecklistDTO.getQuestionnaireChecklistDetailsCategory();
        QuestionnaireChecklistDetailsCategoryEntity detailsEntity = modelMapper.map(
                detailsDTO,
                QuestionnaireChecklistDetailsCategoryEntity.class);
        /**
         * For Questionnaire Checklist Display Details
         */
        QuestionnaireChecklistDisplayDTO displayDTO = questionnaireChecklistDTO.getQuestionnaireChecklistDisplay();
        QuestionnaireChecklistDisplayEntity displayEntity = modelMapper.map(
                displayDTO, QuestionnaireChecklistDisplayEntity.class);
        /**
         * For Questionnaire Checklist
         */
        questionnaireChecklistEntity.setQuestionChecklistId(questionnaireChecklistDTO.getQuestionaireChecklistId());
        questionnaireChecklistEntity.setQuestionChecklistName(questionnaireChecklistDTO.getQuestionaireChecklistName());
        questionnaireChecklistEntity.setQuestionCategory(questionnaireChecklistDTO.getQuestionaireCategory());
        questionnaireChecklistEntity.setQuestionnaireChecklistDetailsCategory(
                detailsEntity);
        questionnaireChecklistEntity.setChecklistDisplayEntity(displayEntity);
        try {
            questionnaireChecklistEntity.setEffectiveDate(formatter.parse(questionnaireChecklistDTO.getEffectiveDate()));
        } catch (ParseException e) {
            log.error("Parsing exception in processing effective date.", e);
            ExceptionUtil.handleException(JSON_PARSING_ERROR);
        }
        return questionnaireChecklistEntity;
    }

    @Override
    public QuestionnaireChecklistDTO toDTO(QuestionnaireChecklistEntity inEntity) {
        QuestionnaireChecklistDTO questionnaireChecklistDTO = super.toDTO(inEntity);
        /**
         * For Questionnaire Checklist Details List
         */
        QuestionnaireChecklistDetailsCategoryEntity detailsEntity = inEntity.getQuestionnaireChecklistDetailsCategory();
        QuestionnaireChecklistDetailsCategoryDTO detailsDTO = modelMapper.map(
                detailsEntity, QuestionnaireChecklistDetailsCategoryDTO.class);

        /**
         * For Questionnaire Checklist Display Details
         */
        QuestionnaireChecklistDisplayEntity checklistDisplayEntity = inEntity.getChecklistDisplayEntity();
        QuestionnaireChecklistDisplayDTO checklistDisplayDTO = modelMapper.map(
                checklistDisplayEntity, QuestionnaireChecklistDisplayDTO.class);
        /**
         * For Questionnaire Checklist
         */
        questionnaireChecklistDTO.setQuestionnaireChecklistDetailsCategory(detailsDTO);
        questionnaireChecklistDTO.setQuestionnaireChecklistDisplay(checklistDisplayDTO);
        questionnaireChecklistDTO.setEffectiveDate(formatter.format(inEntity.getEffectiveDate()));
        questionnaireChecklistDTO.setQuestionaireChecklistId(inEntity.getQuestionChecklistId());
        questionnaireChecklistDTO.setQuestionaireChecklistName(inEntity.getQuestionChecklistName());
        questionnaireChecklistDTO.setQuestionaireCategory(inEntity.getQuestionCategory());
        return questionnaireChecklistDTO;
    }
}
