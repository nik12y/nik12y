package com.idg.idgcore.coe.endpoint.graphql.resolver.purgingpolicy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.purgingpolicy.PurgingApplicationService;
import com.idg.idgcore.coe.dto.purgingpolicy.PurgingDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PurgingMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private PurgingApplicationService purgingApplicationService;

    public TransactionStatus processPurging (SessionContext sessionContext, PurgingDTO purgingDTO)
            throws FatalException, JsonProcessingException {
        return this.purgingApplicationService.process(sessionContext, purgingDTO);
    }
}
