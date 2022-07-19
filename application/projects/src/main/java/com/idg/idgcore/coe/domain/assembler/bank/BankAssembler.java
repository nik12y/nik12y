package com.idg.idgcore.coe.domain.assembler.bank;


import com.idg.idgcore.coe.domain.entity.bank.BankEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.bank.BankDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BankAssembler {

    private ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public BankEntity convertDtoToEntity(BankDTO bankDTO){

        return modelMapper.map(bankDTO, BankEntity.class);
    }

    public BankDTO convertEntityToDto(BankEntity bankEntity){

        return modelMapper.map(bankEntity, BankDTO.class);
    }


    public BankDTO setAuditFields (MutationEntity mutationEntity, BankDTO bankDTO) {
        bankDTO.setAction(mutationEntity.getAction());
        bankDTO.setAuthorized(mutationEntity.getAuthorized());
        bankDTO.setRecordVersion(mutationEntity.getRecordVersion());
        bankDTO.setStatus(mutationEntity.getStatus());
        bankDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        bankDTO.setCreatedBy(mutationEntity.getCreatedBy());
        bankDTO.setCreationTime(mutationEntity.getCreationTime());
        bankDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        bankDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        bankDTO.setTaskCode(mutationEntity.getTaskCode());
        bankDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return bankDTO;
    }


}
