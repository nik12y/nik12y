package com.idg.idgcore.coe.domain.assembler.city;

import com.idg.idgcore.coe.domain.entity.city.CityEntity;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.dto.city.CityDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CityAssembler {
    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public CityEntity convertDtoToEntity (CityDTO cityDTO) {

        return modelMapper.map(cityDTO, CityEntity.class);
    }

    public CityDTO setAuditFields (MutationEntity mutationEntity, CityDTO cityDTO) {
        cityDTO.setAction(mutationEntity.getAction());
        cityDTO.setAuthorized(mutationEntity.getAuthorized());
        cityDTO.setRecordVersion(mutationEntity.getRecordVersion());
        cityDTO.setStatus(mutationEntity.getStatus());
        cityDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        cityDTO.setCreatedBy(mutationEntity.getCreatedBy());
        cityDTO.setCreationTime(mutationEntity.getCreationTime());
        cityDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        cityDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        cityDTO.setTaskCode(mutationEntity.getTaskCode());
        cityDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return cityDTO;
    }

    public CityDTO convertEntityToDto (CityEntity cityEntity) {

        return modelMapper.map(cityEntity, CityDTO.class);
    }

}
