package com.idg.idgcore.coe.domain.assembler.questionnaireChecklist;

import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.domain.entity.questionnaireChecklist.*;
import com.idg.idgcore.coe.dto.questionnaireChecklist.*;
import org.modelmapper.*;
import org.modelmapper.convention.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.text.*;
import java.util.Date;

@Component
public class QuestionnaireChecklistAssembler {
    private final ModelMapper modelMapper = new ModelMapper();

    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @PostConstruct
    private void setMapperConfig () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public QuestionnaireChecklistEntity convertDtoToEntity (
            QuestionnaireChecklistDTO questionnaireChecklistDTO) throws ParseException {
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
        QuestionnaireChecklistEntity questionnaireChecklistEntity = modelMapper.map(
                questionnaireChecklistDTO, QuestionnaireChecklistEntity.class);
        questionnaireChecklistEntity.setQuestionnaireChecklistDetailsCategory(
                detailsEntity);
        questionnaireChecklistEntity.setChecklistDisplayEntity(displayEntity);
        questionnaireChecklistEntity.setEffectiveDate(formatter.parse(questionnaireChecklistDTO.getEffectiveDate()));
        return questionnaireChecklistEntity;
    }

    public QuestionnaireChecklistDTO convertEntityToDto (QuestionnaireChecklistEntity inEntity) {
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
        QuestionnaireChecklistDTO questionnaireChecklistDTO = modelMapper.map(inEntity,
                QuestionnaireChecklistDTO.class);
        questionnaireChecklistDTO.setQuestionnaireChecklistDetailsCategory(detailsDTO);
        questionnaireChecklistDTO.setQuestionnaireChecklistDisplay(checklistDisplayDTO);
        questionnaireChecklistDTO.setEffectiveDate(formatter.format(inEntity.getEffectiveDate()));
        return questionnaireChecklistDTO;
    }

    public QuestionnaireChecklistDTO setAuditFields (MutationEntity mutationEntity,
            QuestionnaireChecklistDTO questionnaireChecklistDTO) {
        questionnaireChecklistDTO.setAction(mutationEntity.getAction());
        questionnaireChecklistDTO.setAuthorized(mutationEntity.getAuthorized());
        questionnaireChecklistDTO.setRecordVersion(mutationEntity.getRecordVersion());
        questionnaireChecklistDTO.setStatus(mutationEntity.getStatus());
        questionnaireChecklistDTO.setLastConfigurationAction(
                mutationEntity.getLastConfigurationAction());
        questionnaireChecklistDTO.setCreatedBy(mutationEntity.getCreatedBy());
        questionnaireChecklistDTO.setCreationTime(mutationEntity.getCreationTime());
        questionnaireChecklistDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        questionnaireChecklistDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        questionnaireChecklistDTO.setTaskCode(mutationEntity.getTaskCode());
        questionnaireChecklistDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return questionnaireChecklistDTO;
    }

}