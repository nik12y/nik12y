package com.idg.idgcore.coe.domain.service.financialAccountingYear;

import com.idg.idgcore.coe.domain.entity.financialAccountingYear.*;
import com.idg.idgcore.coe.dto.financialAccountingYear.*;

import java.util.*;

public interface IFinancialAccountingYearDomainService {
    FinancialAccountingYearEntity getConfigurationByCode (
            FinancialAccountingYearDTO financialAccountingYearDTO);
    List<FinancialAccountingYearEntity> getFinancialAccountingYears ();
    FinancialAccountingYearEntity getFinancialAccountingYearByCode (String bankCode);
    void save (FinancialAccountingYearDTO financialAccountingYearDTO);

}
