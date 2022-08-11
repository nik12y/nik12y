package com.idg.idgcore.coe.endpoint.graphql.resolver.question;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.country.ICountryApplicationService;
import com.idg.idgcore.coe.app.service.question.IQuestionApplicationService;
import com.idg.idgcore.coe.dto.country.CountryDTO;
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
    private IQuestionApplicationService iQuestionApplicationService;

    public QuestionDTO getQuestionById (SessionContext sessionContext, QuestionDTO questionDTO)
            throws FatalException, JsonProcessingException {
        return this.iQuestionApplicationService.getQuestionById(sessionContext, questionDTO);
    }

    public List<QuestionDTO> getQuestions (SessionContext sessionContext) throws FatalException {
        return this.iQuestionApplicationService.getQuestions(sessionContext);
    }

}
