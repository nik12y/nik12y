package com.idg.idgcore.coe.endpoint.graphql.resolver.questionCategory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.questionCategory.QuestionCategoryApplicationService;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QuestionCategoryMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private QuestionCategoryApplicationService questionCategoryApplicationService;

    public TransactionStatus processQuestionCategory (SessionContext sessionContext, QuestionCategoryDTO questionCategoryDTO)
            throws FatalException, JsonProcessingException {
        return this.questionCategoryApplicationService.process(sessionContext, questionCategoryDTO);
    }
}
