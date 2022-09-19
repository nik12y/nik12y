package com.idg.idgcore.coe.app.service.bankparameter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.*;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.coe.dto.bankparameter.BankParameterDTO;

import java.util.*;

public interface IBankParameterApplicationService extends IBaseApplicationService {
    TransactionStatus processBankParameter(SessionContext sessionContext, BankParameterDTO dto) throws FatalException,
            JsonProcessingException;
    void save (BankParameterDTO bankParameterDTO);
    BankParameterDTO getBankParameterByBankCode (SessionContext sessionContext, BankParameterDTO bankParameterDTO)
            throws FatalException, JsonProcessingException;
    List<BankParameterDTO> getBankParameters(SessionContext sessionContext)
            throws FatalException;
    List<BankParameterDTO> searchBankParameter (SessionContext sessionContext,
            BankParameterDTO bankParameterDTO)
            throws FatalException, JsonProcessingException;
}
