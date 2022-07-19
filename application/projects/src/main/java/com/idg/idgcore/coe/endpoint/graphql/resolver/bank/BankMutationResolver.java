package com.idg.idgcore.coe.endpoint.graphql.resolver.bank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.bank.IBankApplicationService;
import com.idg.idgcore.coe.dto.bank.BankDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BankMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private IBankApplicationService bankService;

    public TransactionStatus processBank (SessionContext sessionContext, BankDTO bankDTO)
            throws FatalException, JsonProcessingException {
        return this.bankService.processBank(sessionContext, bankDTO);
    }
}
