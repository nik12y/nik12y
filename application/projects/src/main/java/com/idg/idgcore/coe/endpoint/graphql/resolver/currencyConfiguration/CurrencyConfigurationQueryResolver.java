package com.idg.idgcore.coe.endpoint.graphql.resolver.currencyConfiguration;

import com.idg.idgcore.coe.app.service.currencyConfiguration.*;
import com.idg.idgcore.coe.dto.currencyConfiguration.*;
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

    public List<CurrencyConfigurationDTO> getCurrenciesList(SessionContext sessionContext)throws FatalException{
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"getCurrenciesList() with SessionContext{}",sessionContext);
        }
        return currencyConfigurationService.getCurrencies(sessionContext);
    }

    public CurrencyConfigurationDTO getCurrencyDetails(SessionContext sessionContext,CurrencyDetailsInputDTO currencyDetailsInputDTO)throws FatalException{
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"getCurrenciesList() with SessionContext{}",sessionContext);
        }
        return currencyConfigurationService.getCurrencyDetails(sessionContext,currencyDetailsInputDTO);
    }

}
