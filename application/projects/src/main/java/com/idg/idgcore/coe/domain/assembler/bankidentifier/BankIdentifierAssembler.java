package com.idg.idgcore.coe.domain.assembler.bankidentifier;

import com.idg.idgcore.coe.domain.entity.bankidentifier.BankIdentifierEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BankIdentifierAssembler {

    private ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public BankIdentifierEntity convertDtoToEntity(BankIdentifierDTO bankIdentifierDTO){
        BankIdentifierEntity bankIdentifierEntity = modelMapper.map(bankIdentifierDTO, BankIdentifierEntity.class);
        return bankIdentifierEntity;
    }

    public BankIdentifierDTO convertEntityToDto(BankIdentifierEntity bankIdentifierEntity){
        BankIdentifierDTO bankIdentifierDTO = modelMapper.map(bankIdentifierEntity, BankIdentifierDTO.class);
        return bankIdentifierDTO;
    }
    public BankIdentifierDTO setAuditFields (MutationEntity mutationEntity, BankIdentifierDTO bankIdentifierDTO) {
        bankIdentifierDTO.setAction(mutationEntity.getAction());
        bankIdentifierDTO.setAuthorized(mutationEntity.getAuthorized());
        bankIdentifierDTO.setRecordVersion(mutationEntity.getRecordVersion());
        bankIdentifierDTO.setStatus(mutationEntity.getStatus());
        bankIdentifierDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        bankIdentifierDTO.setCreatedBy(mutationEntity.getCreatedBy());
        bankIdentifierDTO.setCreationTime(mutationEntity.getCreationTime());
        bankIdentifierDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        bankIdentifierDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        bankIdentifierDTO.setTaskCode(mutationEntity.getTaskCode());
        bankIdentifierDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return bankIdentifierDTO;
    }
}
