package com.idg.idgcore.coe.endpoint.graphql.resolver.reason;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.reason.IReasonApplicationService;
import com.idg.idgcore.coe.dto.reason.ReasonDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ReasonQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private IReasonApplicationService reasonService;

    public ReasonDTO getReasonByCode (SessionContext sessionContext, ReasonDTO reasonDTO)
            throws FatalException, JsonProcessingException {
        return this.reasonService.getReasonByCode(sessionContext, reasonDTO);
    }

    public List<ReasonDTO> getReasons (SessionContext sessionContext) throws FatalException {
        return this.reasonService.getReasons(sessionContext);
    }

}
