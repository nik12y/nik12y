package com.idg.idgcore.coe.app.service.mulbranchparameter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;

import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.currencyratetype.CurrencyRateTypeDTO;
import com.idg.idgcore.coe.dto.mulbranchparameter.MulBranchParameterDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface IMulBranchParameterApplicationService extends IBaseApplicationService {

    TransactionStatus processMulBranchParameter(SessionContext sessionContext, MulBranchParameterDTO mulBranchParameterDTO) throws FatalException,
            JsonProcessingException;
    void save (MulBranchParameterDTO mulBranchParameterDTO);
    MulBranchParameterDTO getByCurrencyCodeAndEntityCodeAndEntityType(SessionContext sessionContext,MulBranchParameterDTO mulBranchParameterDTO)
            throws FatalException, JsonProcessingException;
    List<MulBranchParameterDTO> getMulBranchParameters(SessionContext sessionContext)
            throws FatalException;
}
