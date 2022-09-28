package com.idg.idgcore.coe.app.service.riskcategory;


import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.riskcategory.RiskCategoryAssembler;
import com.idg.idgcore.coe.domain.entity.riskcategory.RiskCategoryEntity;
import com.idg.idgcore.coe.domain.service.riskcategory.RiskCategoryDomainService;
import com.idg.idgcore.coe.dto.riskcategory.RiskCategoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.RISKCATEGORY;

@Slf4j
@Service("riskCategoryApplicationService")
public class RiskCategoryApplicationService extends GenericApplicationService<RiskCategoryDTO,
        RiskCategoryEntity,
        RiskCategoryDomainService,
        RiskCategoryAssembler> {

    protected RiskCategoryApplicationService() {
        super(RISKCATEGORY);
    }

    public String getTaskCode () {
        return RiskCategoryDTO.builder().build().getTaskCode();
        }
}
