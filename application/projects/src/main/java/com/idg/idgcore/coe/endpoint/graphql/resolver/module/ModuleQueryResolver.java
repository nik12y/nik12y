package com.idg.idgcore.coe.endpoint.graphql.resolver.module;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.dto.module.ModuleDTO;
import com.idg.idgcore.coe.app.service.module.IModuleApplicationService;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ModuleQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private IModuleApplicationService moduleService;

    public ModuleDTO getModuleByCode (SessionContext sessionContext, ModuleDTO moduleDTO)
            throws FatalException, JsonProcessingException {
        return this.moduleService.getModuleByCode(sessionContext, moduleDTO);
    }

    public List<ModuleDTO> getModules (SessionContext sessionContext) throws FatalException {
        return this.moduleService.getModules(sessionContext);
    }

}
