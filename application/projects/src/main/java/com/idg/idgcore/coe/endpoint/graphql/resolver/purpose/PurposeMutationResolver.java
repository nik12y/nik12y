package com.idg.idgcore.coe.endpoint.graphql.resolver.purpose;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.purpose.IPurposeApplicationService;
import com.idg.idgcore.coe.dto.purpose.PurposeDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PurposeMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private IPurposeApplicationService purposeService;

    public TransactionStatus processPurpose(SessionContext sessionContext, PurposeDTO purposeDTO)
            throws FatalException, JsonProcessingException {
        return this.purposeService.processPurpose(sessionContext, purposeDTO);
    }
}
