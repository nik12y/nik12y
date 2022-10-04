package com.idg.idgcore.coe.endpoint.graphql.resolver.virtualentity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.virtualentity.VirtualEntityApplicationService;
import com.idg.idgcore.coe.dto.virtualentity.VirtualEntityDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class VirtualEntityQueryResolver implements GraphQLQueryResolver  {

    @Autowired
    private VirtualEntityApplicationService virtualEntityApplicationService;

    public VirtualEntityDTO getVirtualEntityByEntityCode(SessionContext sessionContext, VirtualEntityDTO virtualEntityDTO) throws FatalException, JsonProcessingException {
        return this.virtualEntityApplicationService.getByIdentifier(sessionContext, virtualEntityDTO);
    }

    public List<VirtualEntityDTO> getVirtualEntityAll(SessionContext sessionContext) throws FatalException{
        return this.virtualEntityApplicationService.getAll(sessionContext);
    }
}
