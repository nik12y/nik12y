package com.idg.idgcore.coe.domain.service.module;

import com.idg.idgcore.coe.dto.module.ModuleDTO;
import com.idg.idgcore.coe.domain.assembler.module.ModuleAssembler;
import com.idg.idgcore.coe.domain.entity.module.ModuleEntity;
import com.idg.idgcore.coe.domain.repository.module.IModuleRepository;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class ModuleDomainService implements IModuleDomainService {
    @Autowired
    private IModuleRepository moduleRepository;
    @Autowired
    private ModuleAssembler moduleAssembler;

    public ModuleEntity getConfigurationByCode (ModuleDTO moduleDTO) {
        ModuleEntity moduleEntity = null;
        try {
            moduleEntity = this.moduleRepository.findByModuleCode(moduleDTO.getModuleCode());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return moduleEntity;
    }

    public List<ModuleEntity> getModules () {
        return this.moduleRepository.findAll();
    }

    public ModuleEntity getModuleByCode (String moduleCode) {
        ModuleEntity moduleEntity = null;
        try {
            moduleEntity = this.moduleRepository.findByModuleCode(moduleCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return moduleEntity;
    }

    public void save (ModuleDTO moduleDTO) {
        try {
            ModuleEntity moduleEntity = moduleAssembler.convertDtoToEntity(moduleDTO);
            this.moduleRepository.save(moduleEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }

}
