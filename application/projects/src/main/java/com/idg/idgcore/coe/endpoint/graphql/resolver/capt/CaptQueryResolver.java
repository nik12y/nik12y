package com.idg.idgcore.coe.endpoint.graphql.resolver.capt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.dto.capt.CaptDTO;
import com.idg.idgcore.coe.app.service.capt.ICaptApplicationService;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CaptQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private ICaptApplicationService captService;

    public CaptDTO getCaptByCode (SessionContext sessionContext, CaptDTO captDTO)
            throws FatalException, JsonProcessingException {
        return this.captService.getCaptByCode(sessionContext, captDTO);
    }

    public List<CaptDTO> getCaptAll (SessionContext sessionContext) throws FatalException {
        return this.captService.getCaptAll(sessionContext);
    }

}
