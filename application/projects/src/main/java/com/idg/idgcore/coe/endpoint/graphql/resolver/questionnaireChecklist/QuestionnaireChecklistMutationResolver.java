package com.idg.idgcore.coe.endpoint.graphql.resolver.questionnaireChecklist;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.questionnaireChecklist.*;
import com.idg.idgcore.coe.dto.questionnaireChecklist.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import graphql.kickstart.tools.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Slf4j
@Component
public class QuestionnaireChecklistMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private IQuestionnaireChecklistApplicationService appService;

    public TransactionStatus processQuestionnaireChecklist (SessionContext sessionContext, QuestionnaireChecklistDTO dto)
            throws FatalException, JsonProcessingException {
        return this.appService.processQuestionnaireChecklist(sessionContext, dto);
    }

}
