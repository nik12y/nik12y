package com.idg.idgcore.coe.endpoint.graphql.resolver.branchtype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.branchtype.IBranchTypeApplicationService;
import com.idg.idgcore.coe.dto.branchtype.BranchTypeDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class BranchTypeQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private IBranchTypeApplicationService branchtypeService;

    public BranchTypeDTO getBranchTypeByCode (SessionContext sessionContext, BranchTypeDTO branchtypeDTO)
            throws FatalException, JsonProcessingException {
        return this.branchtypeService.getBranchTypeByCode(sessionContext, branchtypeDTO);
    }

    public List<BranchTypeDTO> getBranches (SessionContext sessionContext)
            throws FatalException {
        return this.branchtypeService.getBranches(sessionContext);
    }
}
