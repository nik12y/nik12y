package com.idg.idgcore.coe.app.service.currencyratetype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.currencyratetype.CurrencyRateTypeDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface ICurrencyRateTypeApplicationService extends IBaseApplicationService {

    TransactionStatus processCurrencyRateType (SessionContext sessionContext, CurrencyRateTypeDTO dto) throws FatalException,
            JsonProcessingException;
    void save (CurrencyRateTypeDTO currencyRateTypeDTO);
    CurrencyRateTypeDTO getCurrencyRateTypeByType (SessionContext sessionContext, CurrencyRateTypeDTO currencyRateTypeDTO)
            throws FatalException, JsonProcessingException;
    List<CurrencyRateTypeDTO> getCurrencyRateTypes(SessionContext sessionContext)
            throws FatalException;
}
