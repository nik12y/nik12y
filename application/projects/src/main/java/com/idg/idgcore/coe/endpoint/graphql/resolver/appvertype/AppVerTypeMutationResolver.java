package com.idg.idgcore.coe.endpoint.graphql.resolver.appvertype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.appvertype.IAppVerTypeApplicationService;
import com.idg.idgcore.coe.dto.appvertype.AppVerTypeDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppVerTypeMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private IAppVerTypeApplicationService appVerTypeApplicationService;

    public TransactionStatus processAppVerType(SessionContext sessionContext, AppVerTypeDTO appVerTypeDTO)
            throws FatalException, JsonProcessingException {
        return this.appVerTypeApplicationService.processAppVerType(sessionContext, appVerTypeDTO);
    }
}
