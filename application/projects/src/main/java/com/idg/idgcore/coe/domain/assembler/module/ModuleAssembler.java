package com.idg.idgcore.coe.domain.assembler.module;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.module.ModuleEntity;
import com.idg.idgcore.coe.dto.module.ModuleDTO;
import org.springframework.stereotype.Component;

@Component
public class ModuleAssembler extends Assembler<ModuleDTO, ModuleEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return ModuleDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return ModuleEntity.class;
    }

    @Override
    public ModuleEntity toEntity (ModuleDTO moduleDTO) {
        ModuleEntity moduleEntity = modelMapper.map(moduleDTO, ModuleEntity.class);
        moduleEntity.setIsLicensed(getCharValueFromBoolean(moduleDTO.isLicensed()));
        moduleEntity.setIsPurgeAvailable(getCharValueFromBoolean(moduleDTO.isPurgeAvailable()));
        moduleEntity.setIsUdf(getCharValueFromBoolean(moduleDTO.isUserDefinedFields()));
        moduleEntity.setIsInstalled(getCharValueFromBoolean(moduleDTO.isInstalled()));
        return moduleEntity;
    }

    @Override
    public ModuleDTO toDTO (ModuleEntity moduleEntity) {
        ModuleDTO moduleDTO = modelMapper.map(moduleEntity, ModuleDTO.class);
        moduleDTO.setLicensed(getBooleanValueFromChar(moduleEntity.getIsLicensed()));
        moduleDTO.setPurgeAvailable(getBooleanValueFromChar(moduleEntity.getIsPurgeAvailable()));
        moduleDTO.setUserDefinedFields(getBooleanValueFromChar(moduleEntity.getIsUdf()));
        moduleDTO.setInstalled(getBooleanValueFromChar(moduleEntity.getIsInstalled()));
        return moduleDTO;
    }
}
