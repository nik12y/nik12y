package com.idg.idgcore.coe.domain.assembler.branchtype;

import com.idg.idgcore.coe.domain.entity.branchtype.BranchTypeEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.branchtype.BranchTypeDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class BranchTypeAssembler {

    private final  ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public BranchTypeEntity convertDtoToEntity(BranchTypeDTO branchTypeDTO) {
        BranchTypeEntity branchtypeEntity = modelMapper.map(branchTypeDTO, BranchTypeEntity.class);
        return branchtypeEntity;
    }


    public  BranchTypeDTO convertEntityToDto(BranchTypeEntity branchTypeEntity) {
        BranchTypeDTO branchtypeDTO = modelMapper.map(branchTypeEntity, BranchTypeDTO.class);
        return branchtypeDTO;
    }

    public BranchTypeDTO setAuditFields (MutationEntity mutationEntity, BranchTypeDTO branchTypeDTO) {
        branchTypeDTO.setAction(mutationEntity.getAction());
        branchTypeDTO.setAuthorized(mutationEntity.getAuthorized());
        branchTypeDTO.setRecordVersion(mutationEntity.getRecordVersion());
        branchTypeDTO.setStatus(mutationEntity.getStatus());
        branchTypeDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        branchTypeDTO.setCreatedBy(mutationEntity.getCreatedBy());
        branchTypeDTO.setCreationTime(mutationEntity.getCreationTime());
        branchTypeDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        branchTypeDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        branchTypeDTO.setTaskCode(mutationEntity.getTaskCode());
        branchTypeDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return branchTypeDTO;
    }
    public char getCharValueFromBoolean(boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar(Character value) {
        return value.equals(CHAR_Y);

    }
}
