package com.idg.idgcore.coe.endpoint.graphql.resolver.currencyAmountInWord;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.currencyAmountInWord.*;
import com.idg.idgcore.coe.dto.currencyAmountInWord.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import graphql.kickstart.tools.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Slf4j
@Component
public class CurrencyAmountInWordMutationResolver implements GraphQLMutationResolver {

    private static final String CLASS_NAME="CurrencyAmountInWordMutationResolver.";
    private static final String ENTERED_STRING="Entered into ";

    @Autowired
    CurrencyAmountInWordService currencyAmountInWordService;

    public TransactionStatus processAmountInWords (SessionContext sessionContext, CurrencyAmountInWordDTO currencyAmountInWordDTO)
            throws FatalException, JsonProcessingException {
        log.info(ENTERED_STRING+CLASS_NAME+"processAmountInWords() with SessionContext{} and CurrencyAmountInWordsDTO{}"
                ,sessionContext, currencyAmountInWordDTO);
        return this.currencyAmountInWordService.process(sessionContext, currencyAmountInWordDTO);
    }
}
