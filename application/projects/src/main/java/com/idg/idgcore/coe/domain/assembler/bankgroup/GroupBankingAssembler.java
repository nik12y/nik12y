package com.idg.idgcore.coe.domain.assembler.bankgroup;

import com.idg.idgcore.coe.domain.entity.bankgroup.GroupBankingEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.bankgroup.GroupBankingDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GroupBankingAssembler {
    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public GroupBankingEntity convertDtoToEntity(GroupBankingDTO groupBankingDTO) {
        return modelMapper.map(groupBankingDTO, GroupBankingEntity.class);
    }

    public GroupBankingDTO convertEntityToDto(GroupBankingEntity groupBankingEntity) {
        return modelMapper.map(groupBankingEntity, GroupBankingDTO.class);
    }

    public GroupBankingDTO setAuditFields(MutationEntity mutationEntity, GroupBankingDTO groupBankingDTO) {
        groupBankingDTO.setAction(mutationEntity.getAction());
        groupBankingDTO.setAuthorized(mutationEntity.getAuthorized());
        groupBankingDTO.setRecordVersion(mutationEntity.getRecordVersion());
        groupBankingDTO.setStatus(mutationEntity.getStatus());
        groupBankingDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        groupBankingDTO.setCreatedBy(mutationEntity.getCreatedBy());
        groupBankingDTO.setCreationTime(mutationEntity.getCreationTime());
        groupBankingDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        groupBankingDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        groupBankingDTO.setTaskCode(mutationEntity.getTaskCode());
        groupBankingDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return groupBankingDTO;
    }

    }

