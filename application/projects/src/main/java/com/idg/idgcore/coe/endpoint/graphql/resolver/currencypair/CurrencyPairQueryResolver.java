package com.idg.idgcore.coe.endpoint.graphql.resolver.currencypair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.currencypair.CurrencyPairApplicationService;
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
    private CurrencyPairApplicationService currencyPairApplicationService;

    public CurrencyPairDTO getByCurrency1AndCurrency2AndEntityTypeAndEntityCode(SessionContext sessionContext, CurrencyPairDTO dto) throws FatalException, JsonProcessingException {
        return this.currencyPairApplicationService.getByIdentifier(sessionContext, dto);
    }

    public List<CurrencyPairDTO> getCurrencyPairs (SessionContext sessionContext) throws FatalException {
        return this.currencyPairApplicationService.getAll(sessionContext);
    }
}