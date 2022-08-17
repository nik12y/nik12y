package com.idg.idgcore.coe.endpoint.graphql.resolver.reason;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.reason.IReasonApplicationService;
import com.idg.idgcore.coe.dto.reason.ReasonDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReasonMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private IReasonApplicationService reasonService;

    public TransactionStatus processReason (SessionContext sessionContext, ReasonDTO reasonDTO)
            throws FatalException, JsonProcessingException {
        return this.reasonService.processReason(sessionContext, reasonDTO);
    }

}
