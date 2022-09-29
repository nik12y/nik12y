package com.idg.idgcore.coe.endpoint.graphql.resolver.currencyratetype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.currencyratetype.CurrencyRateTypeApplicationService;
import com.idg.idgcore.coe.dto.currencyratetype.CurrencyRateTypeDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CurrencyRateTypeQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private CurrencyRateTypeApplicationService currencyRateTypeApplicationService;

    public CurrencyRateTypeDTO getCurrencyRateTypeByType(SessionContext sessionContext, CurrencyRateTypeDTO currencyRateTypeDTO)
            throws FatalException, JsonProcessingException
    {
        return this.currencyRateTypeApplicationService.getByIdentifier(sessionContext,currencyRateTypeDTO);
    }

    public List<CurrencyRateTypeDTO> getCurrencyRateTypes(SessionContext sessionContext)
            throws FatalException{
        return this.currencyRateTypeApplicationService.getAll(sessionContext);
    }
}
