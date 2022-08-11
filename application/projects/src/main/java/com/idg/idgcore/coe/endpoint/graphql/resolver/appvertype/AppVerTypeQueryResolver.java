package com.idg.idgcore.coe.endpoint.graphql.resolver.appvertype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.appvertype.IAppVerTypeApplicationService;
import com.idg.idgcore.coe.dto.appvertype.AppVerTypeDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AppVerTypeQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private IAppVerTypeApplicationService appVerTypeApplicationService;

    public AppVerTypeDTO getAppVerTypeById(SessionContext sessionContext, AppVerTypeDTO appVerTypeDTO)
            throws FatalException, JsonProcessingException {
        return this.appVerTypeApplicationService.getAppVerTypeByID(sessionContext, appVerTypeDTO);
    }

    public List<AppVerTypeDTO> getAppVerTypes(SessionContext sessionContext)
            throws FatalException {
        return this.appVerTypeApplicationService.getAppVerTypes(sessionContext);
    }

}
