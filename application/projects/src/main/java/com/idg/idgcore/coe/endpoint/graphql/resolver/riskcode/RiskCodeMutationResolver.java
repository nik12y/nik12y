package com.idg.idgcore.coe.endpoint.graphql.resolver.riskcode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.riskcode.RiskCodeApplicationService;
import com.idg.idgcore.coe.dto.riskcode.RiskCodeDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RiskCodeMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private RiskCodeApplicationService riskCodeApplicationService;

    public TransactionStatus processRiskCode (SessionContext sessionContext, RiskCodeDTO riskcodeDTO)
            throws FatalException, JsonProcessingException {
        return riskCodeApplicationService.process(sessionContext, riskcodeDTO);
    }
}
