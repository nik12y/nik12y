package com.idg.idgcore.coe.endpoint.graphql.resolver.currencyconfiguration;

import com.idg.idgcore.coe.app.service.currencyconfiguration.*;
import com.idg.idgcore.coe.dto.currencyconfiguration.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import graphql.kickstart.tools.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Slf4j
@Component
public class CurrencyConfigurationQueryResolver implements GraphQLQueryResolver {

    private static final String CLASS_NAME="CurrencyConfigurationQueryResolver.";
    private static final String ENTERED_STRING="Entered into ";
    @Autowired
    private ICurrencyConfigurationService currencyConfigurationService;

    public CurrencyDetailsDTO getCurrencyDetails(SessionContext sessionContext,
            CountryCodeDTO countryCodeDTO) throws FatalException{
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"getCurrencyDetails() with SessionContext{} and CountryCodeDTO{}"
                    ,sessionContext,countryCodeDTO);
        }
        return currencyConfigurationService.getCurrencyDetails(sessionContext, countryCodeDTO.getCountryCode());
    }

    public List<CurrencyDetailsDTO> getCurrenciesList(SessionContext sessionContext)throws FatalException{
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"getCurrenciesList() with SessionContext{}",sessionContext);
        }
        return currencyConfigurationService.getCurrencies(sessionContext);
    }

}
