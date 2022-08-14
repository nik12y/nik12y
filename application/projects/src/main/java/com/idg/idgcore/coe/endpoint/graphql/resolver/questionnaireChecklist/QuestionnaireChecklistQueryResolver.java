package com.idg.idgcore.coe.endpoint.graphql.resolver.questionnaireChecklist;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.questionnaireChecklist.*;
import com.idg.idgcore.coe.dto.questionnaireChecklist.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import graphql.kickstart.tools.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Slf4j
@Component
public class QuestionnaireChecklistQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private IQuestionnaireChecklistApplicationService appService;

    public QuestionnaireChecklistDTO getQuestionnaireChecklistById (SessionContext sessionContext, QuestionnaireChecklistDTO dto)
            throws FatalException, JsonProcessingException {
        return this.appService.getQuestionnaireChecklistById(sessionContext, dto);
    }

    public List<QuestionnaireChecklistDTO> getQuestionnaireChecklists (SessionContext sessionContext) throws FatalException {
        return this.appService.getQuestionnaireChecklists(sessionContext);
    }

}
