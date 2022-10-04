package com.idg.idgcore.coe.endpoint.graphql.resolver.virtualentity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.virtualentity.VirtualEntityApplicationService;
import com.idg.idgcore.coe.dto.virtualentity.VirtualEntityDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VirtualEntityMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private VirtualEntityApplicationService virtualEntityService;

    public TransactionStatus processVirtualEntity (SessionContext sessionContext, VirtualEntityDTO virtualEntityDTO)
            throws FatalException, JsonProcessingException {
        return this.virtualEntityService.process(sessionContext, virtualEntityDTO);
    }
}
