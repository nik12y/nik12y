package com.idg.idgcore.coe.endpoint.graphql.resolver.branchSystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.branchSystemDate.IBranchSystemApplicationService;
import com.idg.idgcore.coe.dto.branchSystem.BranchSystemDateDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BranchSystemDateMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private IBranchSystemApplicationService branchSystemApplicationService;

    public TransactionStatus processBranchSystemDate (SessionContext sessionContext, BranchSystemDateDTO branchSystemDateDTO)
            throws FatalException, JsonProcessingException {
        return this.branchSystemApplicationService.processBranchSystemDate(sessionContext, branchSystemDateDTO);
    }
}
