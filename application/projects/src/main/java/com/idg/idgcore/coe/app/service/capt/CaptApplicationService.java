package com.idg.idgcore.coe.app.service.capt;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.capt.CaptAssembler;
import com.idg.idgcore.coe.domain.entity.capt.CaptEntity;
import com.idg.idgcore.coe.domain.service.capt.CaptDomainService;
import com.idg.idgcore.coe.dto.capt.CaptDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.CAPT;

@Slf4j
@Service ("captApplicationService")
public class CaptApplicationService extends GenericApplicationService<CaptDTO, CaptEntity,
        CaptDomainService, CaptAssembler> {

    protected CaptApplicationService() {
        super(CAPT);
    }

    public String getTaskCode () {
        return CaptDTO.builder().build().getTaskCode();
    }

}