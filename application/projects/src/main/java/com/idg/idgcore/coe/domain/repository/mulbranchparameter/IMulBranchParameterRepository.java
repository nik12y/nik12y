package com.idg.idgcore.coe.domain.repository.mulbranchparameter;

import com.idg.idgcore.coe.domain.entity.financialAccountingYear.FinancialAccountingYearEntity;
import com.idg.idgcore.coe.domain.entity.mulbranchparameter.MulBranchParameterEntity;
import com.idg.idgcore.coe.domain.entity.mulbranchparameter.MulBranchParameterEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMulBranchParameterRepository extends JpaRepository<MulBranchParameterEntity, MulBranchParameterEntityKey> {

    MulBranchParameterEntity getByCurrencyCodeAndEntityCode (String currencyCode,String entityCode);
}
