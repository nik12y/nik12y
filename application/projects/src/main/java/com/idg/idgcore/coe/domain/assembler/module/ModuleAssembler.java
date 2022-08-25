package com.idg.idgcore.coe.domain.assembler.module;

import com.idg.idgcore.coe.domain.entity.module.ModuleEntity;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.dto.module.ModuleDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class ModuleAssembler {
    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public ModuleEntity convertDtoToEntity (ModuleDTO moduleDTO) {
        ModuleEntity moduleEntity = modelMapper.map(moduleDTO, ModuleEntity.class);
        moduleEntity.setModuleCode(moduleDTO.getModuleCode());
        moduleEntity.setModuleName(moduleDTO.getModuleName());
        moduleEntity.setBankCode(moduleDTO.getBankCode());
        moduleEntity.setModuleCurrentUser(moduleDTO.getModuleCurrentUsers());
        moduleEntity.setModuleUsers(moduleDTO.getModuleUsers());
        moduleEntity.setIsLicensed(getCharValueFromBoolean(moduleDTO.isLicensed()));
        moduleEntity.setIsPurgeAvailable(getCharValueFromBoolean(moduleDTO.isPurgeAvailable()));
        moduleEntity.setIsUdf(getCharValueFromBoolean(moduleDTO.isUserDefinedFields()));
        moduleEntity.setIsInstalled(getCharValueFromBoolean(moduleDTO.isInstalled()));
        return moduleEntity;
    }

    public ModuleDTO convertEntityToDto (ModuleEntity moduleEntity) {
        ModuleDTO moduleDTO = modelMapper.map(moduleEntity, ModuleDTO.class);
        moduleDTO.setModuleCode(moduleEntity.getModuleCode());
        moduleDTO.setModuleName(moduleEntity.getModuleName());
        moduleDTO.setBankCode(moduleEntity.getBankCode());
        moduleDTO.setModuleUsers(moduleEntity.getModuleUsers());
        moduleDTO.setModuleCurrentUsers(moduleEntity.getModuleCurrentUser());
        moduleDTO.setLicensed(getBooleanValueFromChar(moduleEntity.getIsLicensed()));
        moduleDTO.setPurgeAvailable(getBooleanValueFromChar(moduleEntity.getIsPurgeAvailable()));
        moduleDTO.setUserDefinedFields(getBooleanValueFromChar(moduleEntity.getIsUdf()));
        moduleDTO.setInstalled(getBooleanValueFromChar(moduleEntity.getIsInstalled()));
        return moduleDTO;
    }

    public ModuleDTO setAuditFields (MutationEntity  mutationEntity, ModuleDTO moduleDTO) {
        moduleDTO.setAction(mutationEntity.getAction());
        moduleDTO.setAuthorized(mutationEntity.getAuthorized());
        moduleDTO.setRecordVersion(mutationEntity.getRecordVersion());
        moduleDTO.setStatus(mutationEntity.getStatus());
        moduleDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        moduleDTO.setCreatedBy(mutationEntity.getCreatedBy());
        moduleDTO.setCreationTime(mutationEntity.getCreationTime());
        moduleDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        moduleDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        moduleDTO.setTaskCode(mutationEntity.getTaskCode());
        moduleDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return moduleDTO;
    }

    public char getCharValueFromBoolean (boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar (Character value) {
        return value.equals(CHAR_Y);
    }

}
