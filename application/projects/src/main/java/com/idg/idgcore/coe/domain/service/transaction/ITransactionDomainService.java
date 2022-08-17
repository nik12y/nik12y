package com.idg.idgcore.coe.domain.service.transaction;

import com.idg.idgcore.coe.domain.entity.transaction.TransactionEntity;
import com.idg.idgcore.coe.dto.transaction.TransactionDTO;

import java.util.List;

public interface ITransactionDomainService {
    TransactionEntity getConfigurationByCode (TransactionDTO transactionDTO);
    List<TransactionEntity> getTransactions ();
    TransactionEntity getTransactionByCode (String transactionCode);
    void save (TransactionDTO transactionDTO);

}
