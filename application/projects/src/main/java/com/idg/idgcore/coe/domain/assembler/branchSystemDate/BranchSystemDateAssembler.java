package com.idg.idgcore.coe.domain.assembler.branchSystemDate;

import com.idg.idgcore.coe.domain.entity.branchSystemDate.BranchSystemDateEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.branchSystemDate.BranchSystemDateDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.*;

@Component
public class BranchSystemDateAssembler {

    private final ModelMapper modelMapper = new ModelMapper();
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @PostConstruct
    private void setMapperConfig () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public BranchSystemDateEntity convertDtoToEntity (BranchSystemDateDTO branchSystemDTO) {
        BranchSystemDateEntity branchSystemEntity = modelMapper.map(branchSystemDTO, BranchSystemDateEntity.class);
        branchSystemEntity.setBranchCode(branchSystemDTO.getBranchCode());
        return branchSystemEntity;
    }

    public BranchSystemDateDTO convertEntityToDto (BranchSystemDateEntity branchSystemEntity) {
        BranchSystemDateDTO branchSystemDTO = modelMapper.map(branchSystemEntity, BranchSystemDateDTO.class);

        branchSystemDTO.setCurrentWorkingDate(formatter.format(branchSystemEntity.getCurrentWorkingDate()));
        branchSystemDTO.setPreviousWorkingDate(formatter.format(branchSystemEntity.getPreviousWorkingDate()));
        branchSystemDTO.setNextWorkingDate(formatter.format(branchSystemEntity.getNextWorkingDate()));

        branchSystemDTO.setBranchCode(branchSystemEntity.getBranchCode());
        return branchSystemDTO;
    }

    public BranchSystemDateDTO setAuditFields (MutationEntity mutationEntity, BranchSystemDateDTO branchSystemDTO) {
        branchSystemDTO.setAction(mutationEntity.getAction());
        branchSystemDTO.setAuthorized(mutationEntity.getAuthorized());
        branchSystemDTO.setRecordVersion(mutationEntity.getRecordVersion());
        branchSystemDTO.setStatus(mutationEntity.getStatus());
        branchSystemDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        branchSystemDTO.setCreatedBy(mutationEntity.getCreatedBy());
        branchSystemDTO.setCreationTime(mutationEntity.getCreationTime());
        branchSystemDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        branchSystemDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        branchSystemDTO.setTaskCode(mutationEntity.getTaskCode());
        branchSystemDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return branchSystemDTO;
    }

}