package com.idg.idgcore.coe.endpoint.graphql.resolver.currencyConfiguration;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.currencyConfiguration.*;
import com.idg.idgcore.coe.dto.currencyConfiguration.*;
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
            CurrencyConfigurationDTO currencyConfigurationDTO)
            throws FatalException, JsonProcessingException {
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"processCurrencyTest() with SessionContext{} and CurrencyTestDTO{}"
                    ,sessionContext, currencyConfigurationDTO);
        }
        return this.iCurrencyConfigurationService.processCurrencyConfiguration(sessionContext,
                currencyConfigurationDTO);
    }

    public FormattedAmountDTO processAmountFormatting(SessionContext sessionContext,AmountInputDTO amountInputDTO)
            throws FatalException, JsonProcessingException{
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"processAmountFormatting() with SessionContext{} and AmountInputDTO{}"
                    ,sessionContext,amountInputDTO);
        }
        return this.iCurrencyConfigurationService.processAmountFormatting(sessionContext,amountInputDTO);
    }

    public FormattedAmountDTO processAmountRounding(SessionContext sessionContext,AmountInputDTO amountInputDTO)
            throws FatalException, JsonProcessingException{
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"processAmountRounding() with SessionContext{} and AmountInputDTO{}"
                    ,sessionContext,amountInputDTO);
        }
        return this.iCurrencyConfigurationService.processAmountRounding(sessionContext,amountInputDTO);
    }
}
