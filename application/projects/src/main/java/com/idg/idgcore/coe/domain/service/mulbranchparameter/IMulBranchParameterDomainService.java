package com.idg.idgcore.coe.domain.service.mulbranchparameter;


import com.idg.idgcore.coe.domain.entity.financialAccountingYear.FinancialAccountingYearEntity;
import com.idg.idgcore.coe.domain.entity.mulbranchparameter.MulBranchParameterEntity;
import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCategoryEntity;
import com.idg.idgcore.coe.dto.mulbranchparameter.MulBranchParameterDTO;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDTO;

import java.util.List;

public interface IMulBranchParameterDomainService {

    MulBranchParameterEntity getConfigurationByCode (MulBranchParameterDTO mulBranchParameterDTO);
    List<MulBranchParameterEntity> getMulBranchParameters();
    MulBranchParameterEntity getByCurrencyCodeAndEntityCode (String currencyCode,String entityCode);
    void save (MulBranchParameterDTO mulBranchParameterDTO);
}
