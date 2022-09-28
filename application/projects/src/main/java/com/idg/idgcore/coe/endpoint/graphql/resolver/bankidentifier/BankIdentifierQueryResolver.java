package com.idg.idgcore.coe.endpoint.graphql.resolver.bankidentifier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.bankidentifier.BankIdentifierApplicationService;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class BankIdentifierQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private BankIdentifierApplicationService bankIdentifierService;

    public BankIdentifierDTO getBankIdentifierByCode(SessionContext sessionContext, BankIdentifierDTO bankIdentifierDTO)
            throws FatalException, JsonProcessingException {
        return this.bankIdentifierService.getByIdentifier(sessionContext, bankIdentifierDTO);
    }

    public List<BankIdentifierDTO> getBankIdentifiers(SessionContext sessionContext)
            throws FatalException{
        return this.bankIdentifierService.getAll(sessionContext);
    }
}
