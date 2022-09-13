package com.idg.idgcore.coe.app.service.currencyConfiguration;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.base.*;
import com.idg.idgcore.coe.dto.currencyConfiguration.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;

import java.util.*;

public interface ICurrencyConfigurationService extends IBaseApplicationService {

    public TransactionStatus processCurrencyConfiguration(SessionContext sessionContext,
            CurrencyConfigurationDTO currencyConfigurationDTO) throws FatalException,
            JsonProcessingException;

    public FormattedAmountDTO processAmountFormatting(SessionContext sessionContext,AmountInputDTO amountInputDTO)throws FatalException;

    public FormattedAmountDTO processAmountRounding(SessionContext sessionContext,AmountInputDTO amountInputDTO)throws FatalException;

    public List<CurrencyConfigurationDTO> getCurrencies(SessionContext sessionContext) throws FatalException;

    public CurrencyConfigurationDTO getCurrencyDetails(SessionContext sessionContext,CurrencyDetailsInputDTO currencyDetailsInputDTO) throws FatalException;

}
