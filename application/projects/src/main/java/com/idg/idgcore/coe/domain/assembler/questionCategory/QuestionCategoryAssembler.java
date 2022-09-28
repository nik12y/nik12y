package com.idg.idgcore.coe.domain.assembler.questionCategory;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCatDetailsEntity;
import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCategoryEntity;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDTO;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDetailsDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionCategoryAssembler extends Assembler<QuestionCategoryDTO, QuestionCategoryEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return QuestionCategoryDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return QuestionCategoryEntity.class;
    }

    @Override
    public QuestionCategoryDTO toDTO(QuestionCategoryEntity questionCategoryEntity) {
        QuestionCategoryDTO questionCategoryDTO = super.toDTO(questionCategoryEntity);
        List<QuestionCatDetailsEntity> questionCatDetailsList = questionCategoryEntity.getQuestionCatDetails();
        List<QuestionCategoryDetailsDTO> questionCategoryDetailsDTOList=new ArrayList<>();
        questionCategoryDetailsDTOList.addAll(questionCatDetailsList.stream().map(entity -> {
            return modelMapper.map(entity, QuestionCategoryDetailsDTO.class);
//            QuestionCategoryDetailsDTO questionCategoryDetailsDTO=new QuestionCategoryDetailsDTO();
//            questionCategoryDetailsDTO.setQuestionId(entity.getQuestionId());
//            questionCategoryDetailsDTO.setQuestionNature(entity.getQuestionNature());
//            questionCategoryDetailsDTO.setParentQuestionId(entity.getParentQuestionId());
//            questionCategoryDetailsDTO.setDisplayCondition(entity.getDisplayCondition());
//            questionCategoryDetailsDTO.setStatus(entity.getStatus());
//            questionCategoryDetailsDTO.setAuthorized(entity.getAuthorized());
//            questionCategoryDetailsDTO.setRecordVersion(entity.getRecordVersion());
//            questionCategoryDetailsDTO.setLastConfigurationAction(entity.getLastConfigurationAction());
//            return questionCategoryDetailsDTO;
        }).collect(Collectors.toList()));

//        questionCategoryDTO.setQuestionCategoryId(questionCategoryEntity.getQuestionCategoryId());
//        questionCategoryDTO.setQuestionCategoryName(questionCategoryEntity.getQuestionCategoryName());
        questionCategoryDTO.setIsShowQuestionCategoryName(getBooleanValueFromChar(questionCategoryEntity.getIsShowQuestionCategoryName()));
//        questionCategoryDTO.setQuestionDisplay(questionCategoryEntity.getQuestionDisplay());
        questionCategoryDTO.setIsEnableDocumentUpload(getBooleanValueFromChar(questionCategoryEntity.getIsEnableDocumentUpload()));
        questionCategoryDTO.setQuestionCategoryDetails(questionCategoryDetailsDTOList);
        return questionCategoryDTO;
    }

    @Override
    public QuestionCategoryEntity toEntity(QuestionCategoryDTO questionCategoryDTO) {
        QuestionCategoryEntity questionCategoryEntity = super.toEntity(questionCategoryDTO);
        List<QuestionCategoryDetailsDTO> questionCatDetailsDToList = questionCategoryDTO.getQuestionCategoryDetails();

        List<QuestionCatDetailsEntity> questionCategoryDetailsList=new ArrayList<>();
        questionCategoryDetailsList.addAll(questionCatDetailsDToList.stream().map(dto->{
            return modelMapper.map(dto, QuestionCatDetailsEntity.class);
        }).collect(Collectors.toList()));

//        questionCategoryEntity.setQuestionCategoryId(questionCategoryDTO.getQuestionCategoryId());
//        questionCategoryEntity.setQuestionCategoryName(questionCategoryDTO.getQuestionCategoryName());
//        questionCategoryEntity.setQuestionDisplay(questionCategoryDTO.getQuestionDisplay());
        questionCategoryEntity.setIsShowQuestionCategoryName(getCharValueFromBoolean(questionCategoryDTO.getIsShowQuestionCategoryName()));
        questionCategoryEntity.setIsEnableDocumentUpload(getCharValueFromBoolean(questionCategoryDTO.getIsEnableDocumentUpload()));
        questionCategoryEntity.setQuestionCatDetails(questionCategoryDetailsList);
        return questionCategoryEntity;
    }

}
