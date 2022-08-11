package com.idg.idgcore.coe.app.service.questionCategory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface IQuestionCategoryApplicationService extends IBaseApplicationService {
    TransactionStatus processQuestionCategory (SessionContext sessionContext, QuestionCategoryDTO questionCategoryDTO)
            throws FatalException, JsonProcessingException;
    void save (QuestionCategoryDTO questionCategoryDTO);
    QuestionCategoryDTO getQuestionCategoryById (SessionContext sessionContext, QuestionCategoryDTO questionCategoryDTO)
            throws FatalException, JsonProcessingException;
    List<QuestionCategoryDTO> getQuestionsCategories (SessionContext sessionContext) throws FatalException;

}
