package com.idg.idgcore.coe.endpoint.graphql.resolver.bankparameter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.bankparameter.BankParameterApplicationService;
import com.idg.idgcore.coe.dto.bankparameter.BankParameterDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BankParameterMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private BankParameterApplicationService bankParameterService;

    public TransactionStatus processBankParameter (SessionContext sessionContext, BankParameterDTO bankParameterDTO)
            throws FatalException, JsonProcessingException {
            return this.bankParameterService.process(sessionContext, bankParameterDTO);
    }

}
