package com.idg.idgcore.coe.domain.repository.bank;

import com.idg.idgcore.coe.domain.entity.bank.BankEntity;
import com.idg.idgcore.coe.domain.entity.bank.BankEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBankRepository extends JpaRepository<BankEntity, BankEntityKey> {
    BankEntity findByBankCode (String bankCode);
}
