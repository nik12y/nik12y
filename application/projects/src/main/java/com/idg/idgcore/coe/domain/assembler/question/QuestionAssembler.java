package com.idg.idgcore.coe.domain.assembler.question;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.question.QuestionEntity;
import com.idg.idgcore.coe.dto.question.QuestionDTO;
import org.springframework.stereotype.Component;

@Component
public class QuestionAssembler extends Assembler<QuestionDTO, QuestionEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return QuestionDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return QuestionEntity.class;
    }

//    public QuestionEntity convertDtoToEntity(QuestionDTO questionDTO) {
//        QuestionEntity questionEntity = modelMapper.map(questionDTO, QuestionEntity.class);
//        questionEntity.setQuestionId(questionDTO.getQuestionId());
//        questionEntity.setQuestionName(questionDTO.getQuestionName());
//        questionEntity.setQuestionText(questionDTO.getQuestionText());
//        questionEntity.setQuestionDescription(questionDTO.getQuestionDescription());
//        questionEntity.setAnswerDisplayType(questionDTO.getAnswerDisplayType());
//        questionEntity.setManualFactBased(questionDTO.getManualFactBased());
//        questionEntity.setAnswerValue(questionDTO.getAnswerValue());
//        return questionEntity;
//    }
//
//    public QuestionDTO convertEntityToDto(QuestionEntity questionEntity) {
//        QuestionDTO questionDTO = modelMapper.map(questionEntity, QuestionDTO.class);
//        questionDTO.setQuestionId(questionEntity.getQuestionId());
//        questionDTO.setQuestionName(questionEntity.getQuestionName());
//        questionDTO.setQuestionText(questionEntity.getQuestionText());
//        questionDTO.setQuestionDescription(questionEntity.getQuestionDescription());
//        questionDTO.setAnswerDisplayType(questionEntity.getAnswerDisplayType());
//        questionDTO.setManualFactBased(questionEntity.getManualFactBased());
//        questionDTO.setAnswerValue(questionEntity.getAnswerValue());
//        return questionDTO;
//    }
//
//    public QuestionDTO setAuditFields(MutationEntity mutationEntity, QuestionDTO questionDTO) {
//        questionDTO.setAction(mutationEntity.getAction());
//        questionDTO.setAuthorized(mutationEntity.getAuthorized());
//        questionDTO.setRecordVersion(mutationEntity.getRecordVersion());
//        questionDTO.setStatus(mutationEntity.getStatus());
//        questionDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
//        questionDTO.setCreatedBy(mutationEntity.getCreatedBy());
//        questionDTO.setCreationTime(mutationEntity.getCreationTime());
//        questionDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
//        questionDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
//        questionDTO.setTaskCode(mutationEntity.getTaskCode());
//        questionDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
//        return questionDTO;
//    }


}
