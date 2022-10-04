package com.idg.idgcore.coe.endpoint.graphql.resolver.bankidentifier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.bankidentifier.BankIdentifierApplicationService;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BankIdentifierMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private BankIdentifierApplicationService bankIdentifierService;

    public TransactionStatus processBankIdentifier(SessionContext sessionContext, BankIdentifierDTO bankIdentifierDTO)
            throws FatalException, JsonProcessingException {
        return this.bankIdentifierService.process(sessionContext, bankIdentifierDTO);
    }
}
