package com.idg.idgcore.coe.endpoint.graphql.resolver.currencyAmountInWord;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.currencyAmountInWord.*;
import com.idg.idgcore.coe.dto.currencyAmountInWord.*;
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
public class CurrencyAmountInWordQueryResolver implements GraphQLQueryResolver {

    private static final String CLASS_NAME="CurrencyAmountInWordQueryResolver.";
    private static final String ENTERED_STRING="Entered into ";

    @Autowired
    ICurrencyAmountInWordService iCurrencyAmountInWordService;

    public List<CurrencyAmountInWordDTO> getAmountInWordsList(SessionContext sessionContext) throws
            FatalException, JsonProcessingException {

            log.info(ENTERED_STRING+CLASS_NAME+"getAmountInWordsList() with SessionContext{}",sessionContext);

        return iCurrencyAmountInWordService.getAmountInWordsList(sessionContext);
    }

    public CurrencyAmountInWordDTO getAmountInWordsDetails(SessionContext sessionContext,CurrencyDetailsInputDTO currencyDetailsInputDTO)
            throws FatalException, JsonProcessingException {

            log.info(ENTERED_STRING+CLASS_NAME+"getAmountInWordsDetails() with SessionContext{}",sessionContext);

        return iCurrencyAmountInWordService.getAmountInWordsDetails(sessionContext,currencyDetailsInputDTO);
    }
}
