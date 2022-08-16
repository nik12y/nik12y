package com.idg.idgcore.coe.endpoint.graphql.resolver.transaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.transaction.ITransactionApplicationService;
import com.idg.idgcore.coe.dto.transaction.TransactionDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class TransactionQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private ITransactionApplicationService transactionService;

    public TransactionDTO getTransactionByCode (SessionContext sessionContext, TransactionDTO transactionDTO)
            throws FatalException, JsonProcessingException {
        return this.transactionService.getTransactionByCode(sessionContext, transactionDTO);
    }

    public List<TransactionDTO> getTransactions (SessionContext sessionContext) throws FatalException {
        return this.transactionService.getTransactions(sessionContext);
    }

}
