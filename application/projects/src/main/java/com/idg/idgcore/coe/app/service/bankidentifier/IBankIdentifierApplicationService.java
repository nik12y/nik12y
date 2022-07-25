package com.idg.idgcore.coe.app.service.bankidentifier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface IBankIdentifierApplicationService extends IBaseApplicationService {

    TransactionStatus processBankIdentifier (SessionContext sessionContext, BankIdentifierDTO dto) throws FatalException,
            JsonProcessingException;
    void save (BankIdentifierDTO bankIdentifierDTO);
    BankIdentifierDTO getBankIdentifierByCode (SessionContext sessionContext, BankIdentifierDTO bankIdentifierDTO)
            throws FatalException, JsonProcessingException;
    List<BankIdentifierDTO> getBankIdentifiers (SessionContext sessionContext)
            throws FatalException;
}
