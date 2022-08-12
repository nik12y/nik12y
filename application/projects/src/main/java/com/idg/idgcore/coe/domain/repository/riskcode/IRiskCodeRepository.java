package com.idg.idgcore.coe.domain.repository.riskcode;

import com.idg.idgcore.coe.domain.entity.riskcode.RiskCodeEntity;
import com.idg.idgcore.coe.domain.entity.riskcode.RiskCodeEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRiskCodeRepository extends JpaRepository<RiskCodeEntity, RiskCodeEntityKey> {
    RiskCodeEntity findByRiskCode (String riskCode);
}
