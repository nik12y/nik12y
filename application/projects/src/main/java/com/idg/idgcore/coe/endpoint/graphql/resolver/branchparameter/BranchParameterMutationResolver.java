package com.idg.idgcore.coe.endpoint.graphql.resolver.branchparameter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.branchparameter.BranchParameterApplicationService;
import com.idg.idgcore.coe.dto.branchparameter.BranchParameterDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BranchParameterMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private BranchParameterApplicationService branchParameterService;

    public TransactionStatus processBranchParameter (SessionContext sessionContext, BranchParameterDTO branchParameterDTO)
            throws FatalException, JsonProcessingException {
        return this.branchParameterService.process(sessionContext, branchParameterDTO);
    }

}
