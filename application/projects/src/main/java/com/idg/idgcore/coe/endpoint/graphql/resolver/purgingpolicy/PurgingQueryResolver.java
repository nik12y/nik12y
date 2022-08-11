package com.idg.idgcore.coe.endpoint.graphql.resolver.purgingpolicy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.purgingpolicy.IPurgingApplicationService;
import com.idg.idgcore.coe.dto.purgingpolicy.PurgingDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class PurgingQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private IPurgingApplicationService purgingService;

    public PurgingDTO getPurgingByCode (SessionContext sessionContext, PurgingDTO purgingDTO)
            throws FatalException, JsonProcessingException {
        return this.purgingService.getPurgingByCode(sessionContext, purgingDTO);
    }

    public List<PurgingDTO> getPurgingAll (SessionContext sessionContext) throws FatalException {
        return this.purgingService.getPurgingAll(sessionContext);
    }
}
