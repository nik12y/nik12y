package com.idg.idgcore.coe.endpoint.graphql.resolver;

import com.idg.idgcore.app.lov.LOVManagerApplicationService;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.dto.lov.LOVRequest;
import com.idg.idgcore.dto.lov.LOVResponse;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LOVQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private LOVManagerApplicationService lovManagerApplicationService;

    public LOVResponse getLOV(SessionContext sessionContext, LOVRequest lovRequest) throws FatalException {
        return lovManagerApplicationService.fetchLOVValues(sessionContext,lovRequest);
    }
}
