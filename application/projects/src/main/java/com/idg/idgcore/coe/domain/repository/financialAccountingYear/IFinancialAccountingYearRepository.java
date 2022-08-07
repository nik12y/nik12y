package com.idg.idgcore.coe.domain.repository.financialAccountingYear;

import com.idg.idgcore.coe.domain.entity.financialAccountingYear.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface IFinancialAccountingYearRepository extends JpaRepository<FinancialAccountingYearEntity,Integer> {
    FinancialAccountingYearEntity findByBankCode (String bankCode);
}
