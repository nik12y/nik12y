package com.idg.idgcore.coe.domain.service.riskcategory;

import com.idg.idgcore.coe.domain.entity.riskcategory.RiskCategoryEntity;
import com.idg.idgcore.coe.dto.riskcategory.RiskCategoryDTO;

import java.util.List;

public interface IRiskCategoryDomainService {
    RiskCategoryEntity getConfigurationByCode (RiskCategoryDTO riskCategoryDTO);
    List<RiskCategoryEntity> getRiskCategories ();
    RiskCategoryEntity getRiskCategoryByCode (String riskCategoryCode);
    void save (RiskCategoryDTO riskcategoryDTO);

}
