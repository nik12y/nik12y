package com.idg.idgcore.coe.app.service.purgingpolicy;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.purgingpolicy.PurgingAssembler;
import com.idg.idgcore.coe.domain.entity.purging.PurgingEntity;
import com.idg.idgcore.coe.domain.service.purgingpolicy.PurgingDomainService;
import com.idg.idgcore.coe.dto.purgingpolicy.PurgingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.PURGING_POLICY;

@Slf4j
@Service("purgingApplicationService")
public class PurgingApplicationService extends GenericApplicationService<PurgingDTO,
        PurgingEntity,
        PurgingDomainService,
        PurgingAssembler> {


    protected PurgingApplicationService() {
        super(PURGING_POLICY);
    }

    public String getTaskCode () {
        return PurgingDTO.builder().build().getTaskCode();
    }

}
