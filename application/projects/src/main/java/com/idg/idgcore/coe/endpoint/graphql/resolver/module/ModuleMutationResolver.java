package com.idg.idgcore.coe.endpoint.graphql.resolver.module;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.module.ModuleApplicationService;
import com.idg.idgcore.coe.dto.module.ModuleDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ModuleMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private ModuleApplicationService moduleService;

    public TransactionStatus processModule (SessionContext sessionContext, ModuleDTO moduleDTO)
            throws FatalException, JsonProcessingException {
        return this.moduleService.process(sessionContext, moduleDTO);
    }

}
