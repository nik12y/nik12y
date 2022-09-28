package com.idg.idgcore.coe.endpoint.graphql.resolver.question;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.question.QuestionApplicationService;
import com.idg.idgcore.coe.dto.question.QuestionDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class QuestionQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private QuestionApplicationService questionApplicationService;

    public QuestionDTO getQuestionById (SessionContext sessionContext, QuestionDTO questionDTO)
            throws FatalException, JsonProcessingException {
        return this.questionApplicationService.getByIdentifier(sessionContext, questionDTO);
    }

    public List<QuestionDTO> getQuestions (SessionContext sessionContext) throws FatalException {
        return this.questionApplicationService.getAll(sessionContext);
    }

}
