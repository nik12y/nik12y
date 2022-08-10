package com.idg.idgcore.coe.domain.assembler.questionnaireChecklist;

import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.domain.entity.questionnaireChecklist.*;
import com.idg.idgcore.coe.dto.questionnaireChecklist.*;
import org.modelmapper.*;
import org.modelmapper.convention.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

@Component
public class QuestionnaireChecklistAssembler {
    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public QuestionnaireChecklistEntity convertDtoToEntity (
            QuestionnaireChecklistDTO questionnaireChecklistDTO) {
        /**
         * For Questionnaire Checklist Details List
         */
        List<QuestionnaireChecklistDetailsCategoryDTO> checklistDetailsCategoryDTOList = questionnaireChecklistDTO.getQuestionnaireChecklistDetailsCategory();
        List<QuestionnaireChecklistDetailsCategoryEntity> checklistDetailsCategoryEntityList = new ArrayList<>();
        checklistDetailsCategoryEntityList.addAll(
                checklistDetailsCategoryDTOList.stream().map(entity -> {
                    return modelMapper.map(entity,
                            QuestionnaireChecklistDetailsCategoryEntity.class);
                }).collect(Collectors.toList()));
        /**
         * For Questionnaire Checklist Display Details
         */
        QuestionnaireChecklistDisplayDTO questionnaireChecklistDisplayDTO = questionnaireChecklistDTO.getQuestionnaireChecklistDisplay();
        QuestionnaireChecklistDisplayEntity checklistDetailsCategoryDTO = modelMapper.map(
                questionnaireChecklistDisplayDTO, QuestionnaireChecklistDisplayEntity.class);
        /**
         * For Questionnaire Checklist
         */
        QuestionnaireChecklistEntity questionnaireChecklistEntity = modelMapper.map(
                questionnaireChecklistDTO, QuestionnaireChecklistEntity.class);
        questionnaireChecklistEntity.setChecklistDetailsCategoryEntities(
                checklistDetailsCategoryEntityList);
        questionnaireChecklistEntity.setChecklistDisplayEntity(checklistDetailsCategoryDTO);
        return questionnaireChecklistEntity;
    }

    public QuestionnaireChecklistDTO convertEntityToDto (QuestionnaireChecklistEntity inEntity) {
        /**
         * For Questionnaire Checklist Details List
         */
        List<QuestionnaireChecklistDetailsCategoryDTO> checklistDetailsDTOList = new ArrayList<>();
        List<QuestionnaireChecklistDetailsCategoryEntity> checklistDetailsCategoryEntityList = inEntity.getChecklistDetailsCategoryEntities();
        checklistDetailsDTOList.addAll(checklistDetailsCategoryEntityList.stream().map(dto -> {
            QuestionnaireChecklistDetailsCategoryDTO checklistDetailsDTO = new QuestionnaireChecklistDetailsCategoryDTO();
            checklistDetailsDTO.setQuestionCategoryId(dto.getQuestionCategoryId());
            return checklistDetailsDTO;
        }).collect(Collectors.toList()));
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
        questionnaireChecklistDTO.setQuestionnaireChecklistDetailsCategory(checklistDetailsDTOList);
        questionnaireChecklistDTO.setQuestionnaireChecklistDisplay(checklistDisplayDTO);
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
