package com.idg.idgcore.coe.app.service.riskcode;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.riskcode.RiskCodeAssembler;
import com.idg.idgcore.coe.domain.entity.riskcode.RiskCodeEntity;
import com.idg.idgcore.coe.domain.service.riskcode.RiskCodeDomainService;
import com.idg.idgcore.coe.dto.riskcode.RiskCodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.RISKCODE;

@Slf4j
@Service("riskCodeApplicationService")
public class RiskCodeApplicationService extends GenericApplicationService<RiskCodeDTO,
        RiskCodeEntity,
        RiskCodeDomainService,
        RiskCodeAssembler> {

    protected RiskCodeApplicationService() {
        super(RISKCODE);
    }

    public String getTaskCode () {
        return RiskCodeDTO.builder().build().getTaskCode();
    }
}
