package com.idg.idgcore.coe.endpoint.graphql.resolver.branchparameter;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.branchparameter.*;
import com.idg.idgcore.coe.dto.branchparameter.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import graphql.kickstart.tools.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Slf4j
@Component
public class BranchParameterQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private IBranchParameterApplicationService branchParameterService;

    public BranchParameterDTO getBranchParameterByBranchCode (SessionContext sessionContext, BranchParameterDTO branchParameterDTO)
            throws FatalException, JsonProcessingException {
        return this.branchParameterService.getBranchParameterByBranchCode(sessionContext, branchParameterDTO);
    }

    public List<BranchParameterDTO> getBranchParameters(SessionContext sessionContext)
            throws FatalException {
        return this.branchParameterService.getBranchParameters(sessionContext);
    }
}
