package com.idg.idgcore.coe.domain.assembler.question;

import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.question.QuestionEntity;
import com.idg.idgcore.coe.dto.question.QuestionDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.idg.idgcore.coe.common.Constants.*;

@Component
public class QuestionAssembler {

    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public QuestionEntity convertDtoToEntity(QuestionDTO questionDTO) {
        QuestionEntity questionEntity = modelMapper.map(questionDTO, QuestionEntity.class);
        questionEntity.setQuestionId(questionDTO.getQuestionId());
        questionEntity.setQuestionName(questionDTO.getQuestionName());
        questionEntity.setQuestionText(questionDTO.getQuestionText());
        questionEntity.setQuestionDescription(questionDTO.getQuestionDescription());
        questionEntity.setAnswerDisplayType(questionDTO.getAnswerDisplayType());
        questionEntity.setManualFactBased(questionDTO.getManualFactBased());
        questionEntity.setAnswerValue(questionDTO.getAnswerValue());
        return questionEntity;
    }

    public QuestionDTO convertEntityToDto(QuestionEntity questionEntity) {
        QuestionDTO questionDTO = modelMapper.map(questionEntity, QuestionDTO.class);
        questionDTO.setQuestionId(questionEntity.getQuestionId());
        questionDTO.setQuestionName(questionEntity.getQuestionName());
        questionDTO.setQuestionText(questionEntity.getQuestionText());
        questionDTO.setQuestionDescription(questionEntity.getQuestionDescription());
        questionDTO.setAnswerDisplayType(questionEntity.getAnswerDisplayType());
        questionDTO.setManualFactBased(questionEntity.getManualFactBased());
        questionDTO.setAnswerValue(questionEntity.getAnswerValue());
        return questionDTO;
    }

    public QuestionDTO setAuditFields(MutationEntity mutationEntity, QuestionDTO questionDTO) {
        questionDTO.setAction(mutationEntity.getAction());
        questionDTO.setAuthorized(mutationEntity.getAuthorized());
        questionDTO.setRecordVersion(mutationEntity.getRecordVersion());
        questionDTO.setStatus(mutationEntity.getStatus());
        questionDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        questionDTO.setCreatedBy(mutationEntity.getCreatedBy());
        questionDTO.setCreationTime(mutationEntity.getCreationTime());
        questionDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        questionDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        questionDTO.setTaskCode(mutationEntity.getTaskCode());
        questionDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return questionDTO;
    }


}
