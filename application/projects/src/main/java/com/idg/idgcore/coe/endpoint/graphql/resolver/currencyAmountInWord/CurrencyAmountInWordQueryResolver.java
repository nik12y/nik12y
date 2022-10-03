package com.idg.idgcore.coe.endpoint.graphql.resolver.currencyAmountInWord;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.currencyAmountInWord.*;
import com.idg.idgcore.coe.dto.currencyAmountInWord.*;
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
    CurrencyAmountInWordService currencyAmountInWordService;

    public CurrencyAmountInWordDTO getAmountInWordsDetails (SessionContext sessionContext,
            CurrencyAmountInWordsInputDTO currencyAmountInWordsInputDTO)
            throws FatalException, JsonProcessingException {
        log.info(ENTERED_STRING+CLASS_NAME+"getAmountInWordsDetails() with SessionContext{} and CurrencyAmountInWordsInputDTO{}"
                ,sessionContext, currencyAmountInWordsInputDTO);
        return this.currencyAmountInWordService.getAmountInWordsDetails(sessionContext,currencyAmountInWordsInputDTO);
    }

    public List<CurrencyAmountInWordDTO> getAmountInWordsList (SessionContext sessionContext) throws FatalException {
        log.info(ENTERED_STRING+CLASS_NAME+"getAmountInWordsList() with SessionContext{} and CurrencyAmountInWordDTO{}"
                ,sessionContext);
        return this.currencyAmountInWordService.getAll(sessionContext);
    }
}
