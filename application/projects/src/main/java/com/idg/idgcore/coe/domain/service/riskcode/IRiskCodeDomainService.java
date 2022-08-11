package com.idg.idgcore.coe.domain.service.riskcode;

import com.idg.idgcore.coe.domain.entity.riskcode.RiskCodeEntity;
import com.idg.idgcore.coe.dto.riskcode.RiskCodeDTO;

import java.util.List;

public interface IRiskCodeDomainService {
    RiskCodeEntity getConfigurationByCode (RiskCodeDTO riskCodeDTO);
    List<RiskCodeEntity> getRiskCodes ();
    RiskCodeEntity getRiskCodeByCode (String riskCode);
    void save (RiskCodeDTO riskcodeDTO);

}
