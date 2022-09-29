package com.idg.idgcore.coe.endpoint.graphql.resolver.currencyratetype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.currencyratetype.CurrencyRateTypeApplicationService;
import com.idg.idgcore.coe.dto.currencyratetype.CurrencyRateTypeDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CurrencyRateTypeMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private CurrencyRateTypeApplicationService currencyRateTypeService;

    public TransactionStatus processCurrencyRateType(SessionContext sessionContext, CurrencyRateTypeDTO currencyRateTypeDTO)
            throws FatalException, JsonProcessingException {
        return this.currencyRateTypeService.process(sessionContext, currencyRateTypeDTO);
    }
}
