package com.idg.idgcore.coe.endpoint.graphql.resolver.questionCategory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.questionCategory.QuestionCategoryApplicationService;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class QuestionCategoryQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private QuestionCategoryApplicationService questionCategoryApplicationService;

    public QuestionCategoryDTO getQuestionCategoryById (SessionContext sessionContext, QuestionCategoryDTO questionCategoryDTO)
            throws FatalException, JsonProcessingException {
        return this.questionCategoryApplicationService.getByIdentifier(sessionContext, questionCategoryDTO);
    }

    public List<QuestionCategoryDTO> getQuestionsCategories (SessionContext sessionContext) throws FatalException {
        return this.questionCategoryApplicationService.getAll(sessionContext);
    }
}
