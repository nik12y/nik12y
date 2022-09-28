package com.idg.idgcore.coe.endpoint.graphql.resolver.errorOverride;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.errorOverride.ErrorOverrideApplicationService;
import com.idg.idgcore.coe.dto.errorOverride.ErrorOverrideDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ErrorOverrideQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private ErrorOverrideApplicationService errorOverrideService;

    public ErrorOverrideDTO getErrorOverrideByCode (SessionContext sessionContext, ErrorOverrideDTO errorOverrideDTO)
            throws FatalException, JsonProcessingException {
        return this.errorOverrideService.getByIdentifier(sessionContext, errorOverrideDTO);
    }

    public List<ErrorOverrideDTO> getErrorCodes (SessionContext sessionContext) throws FatalException {
        return this.errorOverrideService.getAll(sessionContext);
    }

}
