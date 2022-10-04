package com.idg.idgcore.coe.app.service.reason;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.reason.ReasonAssembler;
import com.idg.idgcore.coe.domain.entity.reason.ReasonEntity;
import com.idg.idgcore.coe.domain.service.reason.ReasonDomainService;
import com.idg.idgcore.coe.dto.reason.ReasonDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.REASON;

@Slf4j
@Service ("reasonApplicationService")
public class ReasonApplicationService extends GenericApplicationService<ReasonDTO,
        ReasonEntity,
        ReasonDomainService,
        ReasonAssembler> {

    protected ReasonApplicationService() {
        super(REASON);
    }

    public String getTaskCode () {
        return ReasonDTO.builder().build().getTaskCode();
    }
}