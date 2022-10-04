package com.idg.idgcore.coe.endpoint.graphql.resolver.branchSystemDate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.branchSystemDate.BranchSystemApplicationService;
import com.idg.idgcore.coe.dto.branchSystemDate.BranchSystemDateDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class BranchSystemDateQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private BranchSystemApplicationService branchSystemApplicationService;

    public BranchSystemDateDTO getBranchSystemDateByCode (SessionContext sessionContext, BranchSystemDateDTO branchSystemDateDTO)
            throws FatalException, JsonProcessingException {
        return this.branchSystemApplicationService.getByIdentifier(sessionContext, branchSystemDateDTO);
    }

    public List<BranchSystemDateDTO> getBranchSystemDateAll (SessionContext sessionContext) throws FatalException {
        return this.branchSystemApplicationService.getAll(sessionContext);
    }
}
