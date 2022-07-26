package com.idg.idgcore.coe.endpoint.graphql.resolver.capt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.dto.capt.CaptDTO;
import com.idg.idgcore.coe.app.service.capt.ICaptApplicationService;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CaptMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private ICaptApplicationService captService;

    public TransactionStatus processCapt (SessionContext sessionContext, CaptDTO captDTO)
            throws FatalException, JsonProcessingException {
        return this.captService.processCapt(sessionContext, captDTO);
    }

}