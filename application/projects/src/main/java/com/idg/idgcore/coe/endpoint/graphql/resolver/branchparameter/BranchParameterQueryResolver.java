package com.idg.idgcore.coe.endpoint.graphql.resolver.branchparameter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.branchparameter.BranchParameterApplicationService;
import com.idg.idgcore.coe.dto.branchparameter.BranchParameterDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class BranchParameterQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private BranchParameterApplicationService branchParameterService;

    public BranchParameterDTO getBranchParameterByBranchCode (SessionContext sessionContext, BranchParameterDTO branchParameterDTO)
            throws FatalException, JsonProcessingException {
        return this.branchParameterService.getByIdentifier(sessionContext, branchParameterDTO);
    }

    public List<BranchParameterDTO> getBranchParameters(SessionContext sessionContext)
            throws FatalException {
        return this.branchParameterService.getAll(sessionContext);
    }

    public List<BranchParameterDTO> searchBranchParameter (SessionContext sessionContext, BranchParameterDTO branchParameterDTO) throws FatalException, JsonProcessingException {
        return this.branchParameterService.searchBranchParameter(sessionContext, branchParameterDTO);
    }

}
