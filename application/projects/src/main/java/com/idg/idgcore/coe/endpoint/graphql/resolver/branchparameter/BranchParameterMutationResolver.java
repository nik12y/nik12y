package com.idg.idgcore.coe.endpoint.graphql.resolver.branchparameter;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.branchparameter.*;
import com.idg.idgcore.coe.dto.branchparameter.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import graphql.kickstart.tools.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Slf4j
@Component
public class BranchParameterMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private IBranchParameterApplicationService branchParameterService;

    public TransactionStatus processBranchParameter (SessionContext sessionContext, BranchParameterDTO branchParameterDTO)
            throws FatalException, JsonProcessingException {
        return this.branchParameterService.processBranchParameter(sessionContext, branchParameterDTO);
    }

}
