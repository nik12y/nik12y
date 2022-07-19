package com.idg.idgcore.coe.domain.assembler.purpose;

import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.purpose.PurposeEntity;
import com.idg.idgcore.coe.dto.purpose.PurposeDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class PurposeAssembler {

    private ModelMapper modelMapper = new ModelMapper();

    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public PurposeEntity convertDtoToEntity(PurposeDTO purposeDTO) {
        PurposeEntity purposeEntity = modelMapper.map(purposeDTO, PurposeEntity.class);
        return purposeEntity;
    }

    public PurposeDTO convertEntityToDto(PurposeEntity purposeEntity) {
        PurposeDTO purposeDTO = modelMapper.map(purposeEntity, PurposeDTO.class);
        return purposeDTO;
    }

    public PurposeDTO setAuditFields (MutationEntity mutationEntity, PurposeDTO purposeDTO) {
        purposeDTO.setAction(mutationEntity.getAction());
        purposeDTO.setAuthorized(mutationEntity.getAuthorized());
        purposeDTO.setRecordVersion(mutationEntity.getRecordVersion());
        purposeDTO.setStatus(mutationEntity.getStatus());
        purposeDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        purposeDTO.setCreatedBy(mutationEntity.getCreatedBy());
        purposeDTO.setCreationTime(mutationEntity.getCreationTime());
        purposeDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        purposeDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        purposeDTO.setTaskCode(mutationEntity.getTaskCode());
        purposeDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return purposeDTO;
    }


}
