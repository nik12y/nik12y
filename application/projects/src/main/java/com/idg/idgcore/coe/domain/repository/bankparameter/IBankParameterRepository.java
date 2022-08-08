package com.idg.idgcore.coe.domain.repository.bankparameter;

import com.idg.idgcore.coe.domain.entity.bankparameter.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface IBankParameterRepository extends JpaRepository<BankParameterEntity,BankParameterEntityKey> {
    BankParameterEntity findByBankCode (String bankParameterCode);
}
