package com.idg.idgcore.coe.endpoint.graphql.resolver.question;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.country.ICountryApplicationService;
import com.idg.idgcore.coe.app.service.question.IQuestionApplicationService;
import com.idg.idgcore.coe.dto.country.CountryDTO;
import com.idg.idgcore.coe.dto.question.QuestionDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QuestionMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private IQuestionApplicationService iQuestionApplicationService;

    public TransactionStatus processQuestion (SessionContext sessionContext, QuestionDTO questionDTO)
            throws FatalException, JsonProcessingException {
        return this.iQuestionApplicationService.processQuestion(sessionContext, questionDTO);
    }

}
