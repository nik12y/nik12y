package com.idg.idgcore.coe.domain.service.module;

import com.idg.idgcore.coe.dto.module.ModuleDTO;
import com.idg.idgcore.coe.domain.entity.module.ModuleEntity;

import java.util.List;

public interface IModuleDomainService {
    ModuleEntity getConfigurationByCode (ModuleDTO moduleDTO);
    List<ModuleEntity> getModules ();
    ModuleEntity getModuleByCode (String moduleCode);
    void save (ModuleDTO moduleDTO);

}
