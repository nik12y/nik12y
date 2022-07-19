package com.idg.idgcore.coe.endpoint.graphql.resolver.state;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.state.IStateApplicationService;
import com.idg.idgcore.coe.dto.state.StateDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StateQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private IStateApplicationService stateService;

    public StateDTO getStateByCode (SessionContext sessionContext, StateDTO stateDTO)
            throws FatalException, JsonProcessingException {
        return this.stateService.getStateByCode(sessionContext, stateDTO);
    }

    public List<StateDTO> getStates (SessionContext sessionContext)
            throws FatalException {
        return this.stateService.getStates(sessionContext);
    }
}