package com.idg.idgcore.coe.endpoint.graphql.resolver.transaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.transaction.TransactionApplicationService;
import com.idg.idgcore.coe.dto.transaction.TransactionDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransactionMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private TransactionApplicationService transactionService;

    public TransactionStatus processTransaction (SessionContext sessionContext, TransactionDTO transactionDTO)
            throws FatalException, JsonProcessingException {
        return this.transactionService.process(sessionContext, transactionDTO);
    }

}
