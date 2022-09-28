package com.idg.idgcore.coe.endpoint.graphql.resolver.questionnaireChecklist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.questionnaireChecklist.QuestionnaireChecklistApplicationService;
import com.idg.idgcore.coe.dto.questionnaireChecklist.QuestionnaireChecklistDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class QuestionnaireChecklistQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private QuestionnaireChecklistApplicationService appService;

    public QuestionnaireChecklistDTO getQuestionnaireChecklistById (SessionContext sessionContext, QuestionnaireChecklistDTO dto)
            throws FatalException, JsonProcessingException {
        return this.appService.getByIdentifier(sessionContext, dto);
    }

    public List<QuestionnaireChecklistDTO> getQuestionnaireChecklists (SessionContext sessionContext) throws FatalException {
        return this.appService.getAll(sessionContext);
    }

}
