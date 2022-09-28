package com.idg.idgcore.coe.endpoint.graphql.resolver.branchtype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.branchtype.BranchTypeApplicationService;
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
    private BranchTypeApplicationService branchtypeService;

    public BranchTypeDTO getBranchTypeByCode (SessionContext sessionContext, BranchTypeDTO branchtypeDTO)
            throws FatalException, JsonProcessingException {
        return this.branchtypeService.getByIdentifier(sessionContext, branchtypeDTO);
    }

    public List<BranchTypeDTO> getBranches (SessionContext sessionContext)
            throws FatalException {
        return this.branchtypeService.getAll(sessionContext);
    }
}
