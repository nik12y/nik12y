package com.idg.idgcore.coe.domain.service.module;

import com.idg.idgcore.coe.domain.assembler.module.ModuleAssembler;
import com.idg.idgcore.coe.domain.entity.module.ModuleEntity;
import com.idg.idgcore.coe.domain.repository.module.IModuleRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.module.ModuleDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class ModuleDomainService extends DomainService<ModuleDTO, ModuleEntity> {
    @Autowired
    private IModuleRepository moduleRepository;
    @Autowired
    private ModuleAssembler moduleAssembler;

    @Override
    public ModuleEntity getEntityByIdentifier(String moduleCode) {
        ModuleEntity moduleEntity = null;
        try {
            moduleEntity = this.moduleRepository.findByModuleCode(moduleCode);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return moduleEntity;
    }

    @Override
    public List<ModuleEntity> getAllEntities() {
        return this.moduleRepository.findAll();
    }

    public void save (ModuleDTO moduleDTO) {
        try {
            ModuleEntity moduleEntity = moduleAssembler.toEntity(moduleDTO);
            this.moduleRepository.save(moduleEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }

}
