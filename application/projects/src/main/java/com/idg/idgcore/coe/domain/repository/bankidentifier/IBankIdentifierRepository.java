package com.idg.idgcore.coe.domain.repository.bankidentifier;

import com.idg.idgcore.coe.domain.entity.bankidentifier.BankIdentifierEntity;
import com.idg.idgcore.coe.domain.entity.bankidentifier.BankIdentifierEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBankIdentifierRepository extends JpaRepository<BankIdentifierEntity, BankIdentifierEntityKey> {
    BankIdentifierEntity findByBankIdentifierCode(String bankIdentifierCode);
}
