package com.idg.idgcore.coe.domain.assembler.state;



import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.state.StateEntity;
import com.idg.idgcore.coe.dto.state.StateDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class StateAssembler {

    private ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public StateEntity convertDtoToEntity(StateDTO stateDTO) {
        StateEntity stateEntity = modelMapper.map(stateDTO, StateEntity.class);
        //Check why audit values are not updating
        return stateEntity;
    }

    public StateDTO convertEntityToDto(StateEntity stateEntity) {
        StateDTO stateDTO = modelMapper.map(stateEntity, StateDTO.class);
        return stateDTO;
    }

    public StateDTO setAuditFields (MutationEntity mutationEntity, StateDTO stateDTO) {
        stateDTO.setAction(mutationEntity.getAction());
        stateDTO.setAuthorized(mutationEntity.getAuthorized());
        stateDTO.setRecordVersion(mutationEntity.getRecordVersion());
        stateDTO.setStatus(mutationEntity.getStatus());
        stateDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        stateDTO.setCreatedBy(mutationEntity.getCreatedBy());
        stateDTO.setCreationTime(mutationEntity.getCreationTime());
        stateDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        stateDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        stateDTO.setTaskCode(mutationEntity.getTaskCode());
        stateDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return stateDTO;
    }

}
