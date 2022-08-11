package com.idg.idgcore.coe.domain.repository.riskcategory;

import com.idg.idgcore.coe.domain.entity.riskcategory.RiskCategoryEntity;
import com.idg.idgcore.coe.domain.entity.riskcategory.RiskCategoryEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRiskCategoryRepository extends JpaRepository<RiskCategoryEntity, RiskCategoryEntityKey> {
    RiskCategoryEntity findByRiskCategoryCode (String riskCategoryCode);
}
