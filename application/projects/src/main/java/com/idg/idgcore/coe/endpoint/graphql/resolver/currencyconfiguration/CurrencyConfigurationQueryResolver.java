package com.idg.idgcore.coe.endpoint.graphql.resolver.currencyconfiguration;

import com.idg.idgcore.coe.app.service.currencyconfiguration.*;
import com.idg.idgcore.coe.dto.currencyconfiguration.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import graphql.kickstart.tools.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class CurrencyConfigurationQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private ICurrencyConfigurationService currencyConfigurationService;


    public List<CurrenciesDTO> getAllCurrencyList(SessionContext sessionContext) throws FatalException {
       return currencyConfigurationService.getAllCurrencyList(sessionContext);
    }

    public List<WeekDaysDTO> getWeekAllDays(SessionContext sessionContext) throws FatalException{
        return currencyConfigurationService.getWeekAllDays(sessionContext);
    }

    public List<DayDivisorDTO> getDayDivisor(SessionContext sessionContext) throws FatalException{
        return currencyConfigurationService.getDayDivisor(sessionContext);
    }

    public List<RoundingRuleDTO> getRoundingRules(SessionContext sessionContext) throws FatalException{
        return currencyConfigurationService.getRoundingRules(sessionContext);
    }

    public CurrencyDetailsDTO getCurrencyDetails(SessionContext sessionContext,CurrencyCodeDTO currencyCodeDTO) throws FatalException{
        return currencyConfigurationService.getCurrencyDetails(sessionContext,currencyCodeDTO.getCurrencyCode());
    }


}
