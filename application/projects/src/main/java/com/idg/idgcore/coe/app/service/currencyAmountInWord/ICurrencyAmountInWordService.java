package com.idg.idgcore.coe.app.service.currencyAmountInWord;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.base.*;
import com.idg.idgcore.coe.dto.currencyAmountInWord.*;
import com.idg.idgcore.coe.dto.currencyConfiguration.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;

import java.util.*;

public interface ICurrencyAmountInWordService extends IBaseApplicationService {

    public TransactionStatus processAmountInWords(SessionContext sessionContext,
            CurrencyAmountInWordDTO currencyAmountInWordDTO) throws FatalException,
            JsonProcessingException;

    public List<CurrencyAmountInWordDTO> getAmountInWordsList(SessionContext sessionContext) throws FatalException,
            JsonProcessingException;

    public CurrencyAmountInWordDTO getAmountInWordsDetails(SessionContext sessionContext,
            CurrencyDetailsInputDTO currencyDetailsInputDTO) throws FatalException,
            JsonProcessingException;

}
