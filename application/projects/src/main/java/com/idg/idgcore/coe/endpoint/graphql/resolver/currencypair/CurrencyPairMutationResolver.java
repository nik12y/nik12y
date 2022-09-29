package com.idg.idgcore.coe.endpoint.graphql.resolver.currencypair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.currencypair.ICurrencyPairApplicationService;
import com.idg.idgcore.coe.dto.currencypair.CurrencyPairDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CurrencyPairMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private ICurrencyPairApplicationService currencyPairApplicationService;

    public TransactionStatus processCurrencyPair (SessionContext sessionContext, CurrencyPairDTO currencyPairDTO)
            throws FatalException, JsonProcessingException {
        return this.currencyPairApplicationService.processCurrencyPair(sessionContext, currencyPairDTO);
    }

}