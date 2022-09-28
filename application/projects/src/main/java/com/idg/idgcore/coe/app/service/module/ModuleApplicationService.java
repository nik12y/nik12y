package com.idg.idgcore.coe.app.service.module;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.module.ModuleAssembler;
import com.idg.idgcore.coe.domain.entity.module.ModuleEntity;
import com.idg.idgcore.coe.domain.service.module.ModuleDomainService;
import com.idg.idgcore.coe.dto.module.ModuleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.MODULE;

@Slf4j
@Service ("moduleApplicationService")
public class ModuleApplicationService extends GenericApplicationService<ModuleDTO, ModuleEntity,
        ModuleDomainService, ModuleAssembler> {

    protected ModuleApplicationService() {
        super(MODULE);
    }

    public String getTaskCode () {
        return ModuleDTO.builder().build().getTaskCode();
    }

}