package com.idg.idgcore.coe.domain.service.module;

import com.idg.idgcore.coe.dto.module.ModuleDTO;
import com.idg.idgcore.coe.domain.assembler.module.ModuleAssembler;
import com.idg.idgcore.coe.domain.entity.module.ModuleEntity;
import com.idg.idgcore.coe.domain.repository.module.IModuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ModuleDomainService implements IModuleDomainService {
    @Autowired
    private IModuleRepository moduleRepository;

    @Autowired
    private ModuleAssembler moduleAssembler;

    public ModuleEntity getConfigurationByCode (ModuleDTO moduleDTO) {
        return this.moduleRepository.findByModuleCode(moduleDTO.getModuleCode());
    }

    public List<ModuleEntity> getModules () {
        return this.moduleRepository.findAll();
    }

    public ModuleEntity getModuleByCode (String moduleCode) {
        return this.moduleRepository.findByModuleCode(moduleCode);
    }

    public void save (ModuleDTO moduleDTO) {
        ModuleEntity moduleEntity =moduleAssembler.convertDtoToEntity(moduleDTO);
        this.moduleRepository.save(moduleEntity);
    }
}
