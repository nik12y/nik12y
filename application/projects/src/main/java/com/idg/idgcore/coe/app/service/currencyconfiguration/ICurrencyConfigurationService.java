package com.idg.idgcore.coe.app.service.currencyconfiguration;

import com.idg.idgcore.coe.dto.currencyconfiguration.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;

import java.util.*;

public interface ICurrencyConfigurationService {

    public List<CurrenciesDTO> getAllCurrencyList(SessionContext sessionContext) throws FatalException;
    public List<WeekDaysDTO> getWeekAllDays(SessionContext sessionContext) throws FatalException;
    public List<DayDivisorDTO> getDayDivisor(SessionContext sessionContext) throws FatalException;
    public List<RoundingRuleDTO> getRoundingRules(SessionContext sessionContext) throws FatalException;
    public CurrencyDetailsDTO getCurrencyDetails(SessionContext sessionContext,String currencyCode) throws FatalException;

}
