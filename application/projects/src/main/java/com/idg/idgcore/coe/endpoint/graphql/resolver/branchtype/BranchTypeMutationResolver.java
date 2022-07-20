package com.idg.idgcore.coe.endpoint.graphql.resolver.branchtype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.branchtype.IBranchTypeApplicationService;
import com.idg.idgcore.coe.dto.branchtype.BranchTypeDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BranchTypeMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private IBranchTypeApplicationService branchtypeService;

    public TransactionStatus processBranchType (SessionContext sessionContext, BranchTypeDTO branchtypeDTO)
            throws FatalException, JsonProcessingException {
        return this.branchtypeService.processBranchType(sessionContext, branchtypeDTO);
    }

}
