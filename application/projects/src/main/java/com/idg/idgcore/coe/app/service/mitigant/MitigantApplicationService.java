package com.idg.idgcore.coe.app.service.mitigant;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.mitigant.MitigantAssembler;
import com.idg.idgcore.coe.domain.entity.mitigant.MitigantEntity;
import com.idg.idgcore.coe.domain.service.mitigant.MitigantDomainService;
import com.idg.idgcore.coe.dto.mitigant.MitigantDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.MITIGANT;

@Slf4j
@Service("mitigantApplicationService")
public class MitigantApplicationService extends GenericApplicationService<MitigantDTO,
        MitigantEntity,
        MitigantDomainService,
        MitigantAssembler> {

    protected MitigantApplicationService() {
        super(MITIGANT);
    }

    public String getTaskCode () {
        return MitigantDTO.builder().build().getTaskCode();
    }
}
