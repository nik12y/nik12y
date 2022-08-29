package com.idg.idgcore.coe.endpoint.graphql.resolver.currencyconfiguration;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.currencyconfiguration.*;
import com.idg.idgcore.coe.dto.currencyconfiguration.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import graphql.kickstart.tools.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Slf4j
@Component
public class CurrencyConfigurationMutationResolver implements GraphQLMutationResolver {

    private static final String CLASS_NAME="CurrencyConfigurationMutationResolver.";
    private static final String ENTERED_STRING="Entered into ";
    @Autowired
    ICurrencyConfigurationService iCurrencyConfigurationService;

    public TransactionStatus processCurrencyConfiguration (SessionContext sessionContext,
            CurrencyDetailsDTO currencyDetailsDTO)
            throws FatalException, JsonProcessingException {
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"processCurrencyConfiguration() with SessionContext{} and CurrencyDetailsDTO{}"
                    ,sessionContext,currencyDetailsDTO);
        }
        return this.iCurrencyConfigurationService.processCurrencyConfiguration(sessionContext,
                currencyDetailsDTO);
    }

    public FormattedAmountDTO processAmountFormatting(SessionContext sessionContext,AmountInputDTO amountInputDTO)
            throws FatalException, JsonProcessingException{
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"processAmountFormatting() with SessionContext{} and AmountInputDTO{}"
                    ,sessionContext,amountInputDTO);
        }
        return this.iCurrencyConfigurationService.processAmountFormatting(sessionContext,amountInputDTO);
    }
}
