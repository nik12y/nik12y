package com.idg.idgcore.coe.app.service.currencyconfiguration;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.base.*;
import com.idg.idgcore.coe.dto.currencyconfiguration.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;

import java.util.*;

public interface ICurrencyConfigurationService extends IBaseApplicationService {

    public CurrencyDetailsDTO getCurrencyDetails(SessionContext sessionContext,String countryCode) throws FatalException;

    public TransactionStatus processCurrencyConfiguration(SessionContext sessionContext,
            CurrencyDetailsDTO currencyDetailsDTO) throws FatalException,
            JsonProcessingException;
    public List<CurrencyDetailsDTO> getCurrencies(SessionContext sessionContext) throws FatalException;

    public FormattedAmountDTO processAmountFormatting(SessionContext sessionContext,AmountInputDTO amountInputDTO)throws FatalException;

}
