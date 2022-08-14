package com.idg.idgcore.coe.domain.assembler.questionCategory;

import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCatDetailsEntity;
import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCategoryEntity;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDTO;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDetailsDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import javax.annotation.PostConstruct;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class QuestionCategoryAssembler {

    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public QuestionCategoryDTO convertEntityToDto(QuestionCategoryEntity questionCategoryEntity) {

        List<QuestionCatDetailsEntity> questionCatDetailsList = questionCategoryEntity.getQuestionCatDetails();

        Type listType = new TypeToken<List<QuestionCategoryDetailsDTO>>() {}.getType();
        List<QuestionCategoryDetailsDTO> questionCategoryDetailsDTOList = modelMapper.map(questionCatDetailsList, listType);

        QuestionCategoryDTO questionCategoryDTO = modelMapper.map(questionCategoryEntity, QuestionCategoryDTO.class);
        questionCategoryDTO.setQuestionCategoryId(questionCategoryEntity.getQuestionCategoryId());
        questionCategoryDTO.setQuestionCategoryName(questionCategoryEntity.getQuestionCategoryName());
        questionCategoryDTO.setIsShowQuestionCategoryName(getBooleanValueFromChar(questionCategoryEntity.getIsShowQuestionCategoryName()));
        questionCategoryDTO.setQuestionDisplay(questionCategoryEntity.getQuestionDisplay());
        questionCategoryDTO.setIsEnableDocumentUpload(getBooleanValueFromChar(questionCategoryEntity.getIsEnableDocumentUpload()));
        questionCategoryDTO.setQuestionCategoryDetails(questionCategoryDetailsDTOList);

        return questionCategoryDTO;
    }

    public QuestionCategoryEntity convertDtoTOEntity(QuestionCategoryDTO questionCategoryDTO) {
        List<QuestionCategoryDetailsDTO> questionCatDetailsDToList = questionCategoryDTO.getQuestionCategoryDetails();

        Type listType = new TypeToken<List<QuestionCatDetailsEntity>>() {}.getType();
        List<QuestionCatDetailsEntity> questionCategoryDetailsList = modelMapper.map(questionCatDetailsDToList, listType);

        QuestionCategoryEntity questionCategoryEntity = modelMapper.map(questionCategoryDTO, QuestionCategoryEntity.class);
        questionCategoryEntity.setQuestionCategoryId(questionCategoryDTO.getQuestionCategoryId());
        questionCategoryEntity.setQuestionCategoryName(questionCategoryDTO.getQuestionCategoryName());
        questionCategoryEntity.setQuestionDisplay(questionCategoryDTO.getQuestionDisplay());
        questionCategoryEntity.setIsShowQuestionCategoryName(getCharValueFromBoolean(questionCategoryDTO.getIsShowQuestionCategoryName()));
        questionCategoryEntity.setIsEnableDocumentUpload(getCharValueFromBoolean(questionCategoryDTO.getIsEnableDocumentUpload()));
        questionCategoryEntity.setQuestionCatDetails(questionCategoryDetailsList);
        return questionCategoryEntity;
    }

    public QuestionCategoryDTO setAuditFields(MutationEntity mutationEntity, QuestionCategoryDTO questionCategoryDTO) {
        questionCategoryDTO.setAction(mutationEntity.getAction());
        questionCategoryDTO.setAuthorized(mutationEntity.getAuthorized());
        questionCategoryDTO.setRecordVersion(mutationEntity.getRecordVersion());
        questionCategoryDTO.setStatus(mutationEntity.getStatus());
        questionCategoryDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        questionCategoryDTO.setCreatedBy(mutationEntity.getCreatedBy());
        questionCategoryDTO.setCreationTime(mutationEntity.getCreationTime());
        questionCategoryDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        questionCategoryDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        questionCategoryDTO.setTaskCode(mutationEntity.getTaskCode());
        questionCategoryDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return questionCategoryDTO;
    }

    public char getCharValueFromBoolean (boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar (Character value) {
        return value.equals(CHAR_Y);
    }

}
