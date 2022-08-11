package com.idg.idgcore.coe.domain.service.questionCategory;

import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCategoryEntity;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDTO;

import java.util.List;

public interface IQuestionCategoryDomainService {

    QuestionCategoryEntity getConfigurationByCode (QuestionCategoryDTO questionCategoryDTO);
    List<QuestionCategoryEntity> getQuestionsCategories ();
    QuestionCategoryEntity getQuestionCategoryById (String questionCategoryId);
    void save (QuestionCategoryDTO questionCategoryDTO);
}
