package com.idg.idgcore.coe.domain.assembler.audit;

import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.dto.mutation.*;
import org.modelmapper.*;
import org.modelmapper.convention.*;
import org.springframework.stereotype.*;

import javax.annotation.*;

@Component
public class MutationAssembler {
    private ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public MutationDTO setAuditFields (MutationEntity mutationEntity)
    {
        MutationDTO mutationDTO = new MutationDTO();
        mutationDTO.setCreatedBy(mutationEntity.getCreatedBy());
        mutationDTO.setCreationTime(mutationEntity.getCreationTime());
        mutationDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        mutationDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        mutationDTO.setTaskCode(mutationEntity.getTaskCode());
        mutationDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return mutationDTO;
    }
}
