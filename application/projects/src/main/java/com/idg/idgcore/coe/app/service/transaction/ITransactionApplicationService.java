package com.idg.idgcore.coe.app.service.transaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.transaction.TransactionDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface ITransactionApplicationService extends IBaseApplicationService {
    TransactionStatus processTransaction (SessionContext sessionContext, TransactionDTO dto)
            throws FatalException, JsonProcessingException;
    void save (TransactionDTO transactionDTO);
    TransactionDTO getTransactionByCode (SessionContext sessionContext, TransactionDTO transactionDTO)
            throws FatalException, JsonProcessingException;
    List<TransactionDTO> getTransactions (SessionContext sessionContext) throws FatalException;

}
