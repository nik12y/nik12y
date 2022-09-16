package com.idg.idgcore.coe.app.service.currencypair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.currencypair.CurrencyPairDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface ICurrencyPairApplicationService extends IBaseApplicationService {

    TransactionStatus processCurrencyPair (SessionContext sessionContext, CurrencyPairDTO dto) throws FatalException,
            JsonProcessingException;
    void addUpdateRecord(String data) throws JsonProcessingException;
    CoreEngineBaseDTO getConfigurationByCode(String code);
    void save (CurrencyPairDTO currencyPairDTO);
    CurrencyPairDTO getCurrencyPairById(SessionContext sessionContext, CurrencyPairDTO currencyPairDTO)
            throws FatalException, JsonProcessingException;
    List<CurrencyPairDTO> getCurrencyPairs (SessionContext sessionContext)
            throws FatalException;
}
