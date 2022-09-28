package com.idg.idgcore.coe.endpoint.graphql.resolver.errorOverride;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.errorOverride.ErrorOverrideApplicationService;
import com.idg.idgcore.coe.dto.errorOverride.ErrorOverrideDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ErrorOverrideMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private ErrorOverrideApplicationService errorOverrideService;

    public TransactionStatus processErrorOverride (SessionContext sessionContext, ErrorOverrideDTO errorOverrideDTO)
            throws FatalException, JsonProcessingException {
        return this.errorOverrideService.process(sessionContext, errorOverrideDTO);
    }

}
