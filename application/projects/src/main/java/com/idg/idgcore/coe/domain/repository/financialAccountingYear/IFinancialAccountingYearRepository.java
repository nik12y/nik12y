package com.idg.idgcore.coe.domain.repository.financialAccountingYear;

import com.idg.idgcore.coe.domain.entity.financialAccountingYear.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface IFinancialAccountingYearRepository extends JpaRepository<FinancialAccountingYearEntity,FinancialAccountingYearEntityKey> {
    FinancialAccountingYearEntity findByBankCode (String bankCode);
    FinancialAccountingYearEntity getByBankCodeAndBranchCodeAndFinancialAccountingYearCode (
            String bankCode, String branchCode, String financialAccountingYearCode);
    FinancialAccountingYearEntity findByBranchCodeAndStartDateLessThanEqualAndEndDateGreaterThanEqual (
            String branchCode, Date startDate,
            Date endDate);
}
