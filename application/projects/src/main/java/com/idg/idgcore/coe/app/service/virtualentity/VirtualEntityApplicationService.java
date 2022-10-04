package com.idg.idgcore.coe.app.service.virtualentity;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.virtualentity.VirtualEntityAssembler;
import com.idg.idgcore.coe.domain.entity.virtualentity.VirtualEntity;
import com.idg.idgcore.coe.domain.service.virtualentity.VirtualEntityDomainService;
import com.idg.idgcore.coe.dto.virtualentity.VirtualEntityDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.VIRTUAL_ENTITY;

@Slf4j
@Service("virtualEntityApplicationService")
public class VirtualEntityApplicationService extends GenericApplicationService<VirtualEntityDTO, VirtualEntity,
        VirtualEntityDomainService, VirtualEntityAssembler> {

    protected VirtualEntityApplicationService() {
        super(VIRTUAL_ENTITY);
    }

    public String getTaskCode () {
        return VirtualEntityDTO.builder().build().getTaskCode();
    }
}
