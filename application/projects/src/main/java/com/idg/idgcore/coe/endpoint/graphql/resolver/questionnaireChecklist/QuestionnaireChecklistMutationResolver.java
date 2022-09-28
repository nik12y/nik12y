package com.idg.idgcore.coe.endpoint.graphql.resolver.questionnaireChecklist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.questionnaireChecklist.QuestionnaireChecklistApplicationService;
import com.idg.idgcore.coe.dto.questionnaireChecklist.QuestionnaireChecklistDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QuestionnaireChecklistMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private QuestionnaireChecklistApplicationService appService;

    public TransactionStatus processQuestionnaireChecklist (SessionContext sessionContext, QuestionnaireChecklistDTO dto)
            throws FatalException, JsonProcessingException {
        return this.appService.process(sessionContext, dto);
    }

}
