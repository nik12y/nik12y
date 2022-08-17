package com.idg.idgcore.coe.domain.repository.transaction;

import com.idg.idgcore.coe.domain.entity.transaction.TransactionEntity;
import com.idg.idgcore.coe.domain.entity.transaction.TransactionEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepository extends JpaRepository<TransactionEntity,TransactionEntityKey> {
    TransactionEntity findByTransactionCode (String transactionCode);
}
