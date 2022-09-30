package com.idg.idgcore.coe.endpoint.graphql.resolver.mulbranchparameter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.mulbranchparameter.MulBranchParameterApplicationService;
import com.idg.idgcore.coe.dto.mulbranchparameter.MulBranchParameterDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MulBranchParameterMutationResolver implements GraphQLMutationResolver {
    @Autowired
    MulBranchParameterApplicationService iMulBranchParameterApplicationService;

    public TransactionStatus processMulBranchParameter(SessionContext sessionContext, MulBranchParameterDTO mulBranchParameterDTO)
            throws FatalException, JsonProcessingException {
        return this.iMulBranchParameterApplicationService.process(sessionContext, mulBranchParameterDTO);

    }
}
