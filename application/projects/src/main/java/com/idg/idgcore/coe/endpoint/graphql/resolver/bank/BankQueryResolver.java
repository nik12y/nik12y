package com.idg.idgcore.coe.endpoint.graphql.resolver.bank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.bank.IBankApplicationService;
import com.idg.idgcore.coe.dto.bank.BankDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class BankQueryResolver implements GraphQLQueryResolver {
    @Autowired
    IBankApplicationService bankService;

    public BankDTO getBankByCode (SessionContext sessionContext, BankDTO bankDTO)
            throws FatalException, JsonProcessingException {
        return this.bankService.getBankByCode(sessionContext, bankDTO);
    }

    public List<BankDTO> getBanks (SessionContext sessionContext)
            throws FatalException {
        return this.bankService.getBanks(sessionContext);
    }


}
