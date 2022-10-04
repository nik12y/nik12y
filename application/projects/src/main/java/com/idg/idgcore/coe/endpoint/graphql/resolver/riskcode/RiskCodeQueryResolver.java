package com.idg.idgcore.coe.endpoint.graphql.resolver.riskcode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.riskcode.RiskCodeApplicationService;
import com.idg.idgcore.coe.dto.riskcode.RiskCodeDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RiskCodeQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private RiskCodeApplicationService riskCodeApplicationService;

    public RiskCodeDTO getRiskCodeByCode (SessionContext sessionContext, RiskCodeDTO riskCodeDTO)
            throws FatalException, JsonProcessingException {
        return this.riskCodeApplicationService.getByIdentifier(sessionContext, riskCodeDTO);
    }

    public List<RiskCodeDTO> getRiskCodes (SessionContext sessionContext)
            throws FatalException {
        return this.riskCodeApplicationService.getAll(sessionContext);
    }
}
