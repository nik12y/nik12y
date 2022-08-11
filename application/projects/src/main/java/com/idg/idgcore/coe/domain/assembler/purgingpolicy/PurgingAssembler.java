package com.idg.idgcore.coe.domain.assembler.purgingpolicy;

import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.purging.PurgingEntity;
import com.idg.idgcore.coe.dto.purgingpolicy.PurgingDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class PurgingAssembler {

    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public PurgingEntity convertDtoToEntity (PurgingDTO purgingDTO) {
        PurgingEntity purgingEntity = modelMapper.map(purgingDTO, PurgingEntity.class);
        purgingEntity.setModuleCode(purgingDTO.getModuleCode());
        purgingEntity.setTranMaintenanceStatus(purgingDTO.getTranMaintenanceStatus());
        purgingEntity.setRetentionPeriod(purgingDTO.getRetentionPeriod());
        return purgingEntity;
    }

    public PurgingDTO convertEntityToDto (PurgingEntity purgingEntity) {
        PurgingDTO purgingDTO = modelMapper.map(purgingEntity, PurgingDTO.class);
        purgingDTO.setModuleCode(purgingEntity.getModuleCode());
        purgingDTO.setTranMaintenanceStatus(purgingEntity.getTranMaintenanceStatus());
        purgingDTO.setRetentionPeriod(purgingEntity.getRetentionPeriod());
        return purgingDTO;
    }

    public PurgingDTO setAuditFields (MutationEntity mutationEntity, PurgingDTO purgingDTO) {
        purgingDTO.setAction(mutationEntity.getAction());
        purgingDTO.setAuthorized(mutationEntity.getAuthorized());
        purgingDTO.setRecordVersion(mutationEntity.getRecordVersion());
        purgingDTO.setStatus(mutationEntity.getStatus());
        purgingDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        purgingDTO.setCreatedBy(mutationEntity.getCreatedBy());
        purgingDTO.setCreationTime(mutationEntity.getCreationTime());
        purgingDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        purgingDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        purgingDTO.setTaskCode(mutationEntity.getTaskCode());
        purgingDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return purgingDTO;
    }

    public char getCharValueFromBoolean (boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar (Character value) {
        return value.equals(CHAR_Y);
    }

}
