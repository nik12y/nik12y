package com.idg.idgcore.coe.endpoint.graphql.resolver.state;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.state.StateApplicationService;
import com.idg.idgcore.coe.dto.state.StateDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StateMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private StateApplicationService stateService;

    public TransactionStatus processState (SessionContext sessionContext, StateDTO stateDTO)
            throws FatalException, JsonProcessingException {
        return this.stateService.process(sessionContext, stateDTO);
    }

}
