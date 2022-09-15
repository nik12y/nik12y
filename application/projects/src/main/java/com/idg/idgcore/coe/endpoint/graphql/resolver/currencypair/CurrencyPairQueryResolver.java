package com.idg.idgcore.coe.endpoint.graphql.resolver.currencypair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.currencypair.ICurrencyPairApplicationService;
import com.idg.idgcore.coe.dto.currencypair.CurrencyPairDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CurrencyPairQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private ICurrencyPairApplicationService currencyPairApplicationService;

    public CurrencyPairDTO getCurrencyPairById(SessionContext sessionContext, CurrencyPairDTO currencyPairDTO)
            throws FatalException, JsonProcessingException {
        return this.currencyPairApplicationService.getCurrencyPairById(sessionContext, currencyPairDTO);
    }

    public List<CurrencyPairDTO> getCurrencyPairs (SessionContext sessionContext) throws FatalException {
        return this.currencyPairApplicationService.getCurrencyPairs(sessionContext);
    }

    public CurrencyPairDTO getByCurrency1AndCurrency2AndEntityTypeAndEntityCode(SessionContext sessionContext, CurrencyPairDTO currencyPairDTO)
            throws FatalException, JsonProcessingException {
        return this.currencyPairApplicationService.getByCurrency1AndCurrency2AndEntityTypeAndEntityCode(sessionContext, currencyPairDTO);
    }

}