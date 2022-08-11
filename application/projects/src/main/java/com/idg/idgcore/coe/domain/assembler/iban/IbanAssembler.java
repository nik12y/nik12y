package com.idg.idgcore.coe.domain.assembler.iban;

import com.idg.idgcore.coe.domain.entity.iban.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.dto.iban.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.*;

import javax.annotation.*;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class IbanAssembler {

    private ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public IbanEntity convertDtoToEntity(IbanDTO ibanDTO) {
        /**
         * For Iban Bban
         */
        IbanBbanDTO ibanBbanDTO= ibanDTO.getIbanBban();
        IbanBbanEntity ibanBbanEntity = modelMapper.map(ibanBbanDTO, IbanBbanEntity.class);
        ibanBbanEntity.setBankIdentifierPosition(ibanBbanDTO.getBankIdentifierPosition());
        ibanBbanEntity.setBankIdentifierLength(ibanBbanDTO.getBankIdentifierLength());
        ibanBbanEntity.setBranchIdentifierPosition(ibanBbanDTO.getBranchIdentifierPosition());
        ibanBbanEntity.setAccountNumberLength(ibanBbanDTO.getAccountNumberLength());
        /**
         * Set Entity values
         */
        IbanEntity ibanEntity = modelMapper.map(ibanDTO, IbanEntity.class);
        ibanEntity.setIbanBbanEntity(ibanBbanEntity);

        /**
         * Set Iban
         */
        ibanEntity.setIbanCountryCode(ibanDTO.getIbanCountryCode());
        ibanEntity.setIbanCountryPosition(ibanDTO.getIbanCountryPosition());
        ibanEntity.setIbanCountryCodeLength(ibanDTO.getIbanCountryCodeLength());
        ibanEntity.setIbanCheckDigitPosition(ibanDTO.getIbanCheckDigitPosition());
        ibanEntity.setIbanCheckDigitLength(ibanDTO.getIbanCheckDigitLength());
        return ibanEntity;
    }

    public IbanDTO convertEntityToDto(IbanEntity ibanEntity) {

        /**
         * For Iban Bban
         */
        IbanBbanEntity ibanBbanEntity= ibanEntity.getIbanBbanEntity();
        IbanBbanDTO ibanBbanDTO = modelMapper.map(ibanBbanEntity, IbanBbanDTO.class);
        ibanBbanDTO.setBankIdentifierPosition(ibanBbanEntity.getBankIdentifierPosition());
        ibanBbanDTO.setBankIdentifierLength(ibanBbanEntity.getBankIdentifierLength());
        ibanBbanDTO.setBranchIdentifierPosition(ibanBbanEntity.getBranchIdentifierPosition());
        ibanBbanDTO.setAccountNumberLength(ibanBbanEntity.getAccountNumberLength());
        /**
         * Set Entity values
         */
        IbanDTO ibanDTO = modelMapper.map(ibanEntity, IbanDTO.class);
        ibanDTO.setIbanBban(ibanBbanDTO);
        /**
         * Set Iban Entity values
         */
        ibanDTO.setIbanCountryCode(ibanEntity.getIbanCountryCode());
        ibanDTO.setIbanCountryPosition(ibanEntity.getIbanCountryPosition());
        ibanDTO.setIbanCountryCodeLength(ibanEntity.getIbanCountryCodeLength());
        ibanDTO.setIbanCheckDigitPosition(ibanEntity.getIbanCheckDigitPosition());
        ibanDTO.setIbanCheckDigitLength(ibanEntity.getIbanCheckDigitLength());
        return ibanDTO;
    }

    public IbanDTO setAuditFields (MutationEntity mutationEntity, IbanDTO ibanDTO) {
        ibanDTO.setAction(mutationEntity.getAction());
        ibanDTO.setAuthorized(mutationEntity.getAuthorized());
        ibanDTO.setRecordVersion(mutationEntity.getRecordVersion());
        ibanDTO.setStatus(mutationEntity.getStatus());
        ibanDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        ibanDTO.setCreatedBy(mutationEntity.getCreatedBy());
        ibanDTO.setCreationTime(mutationEntity.getCreationTime());
        ibanDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        ibanDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        ibanDTO.setTaskCode(mutationEntity.getTaskCode());
        ibanDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return ibanDTO;
    }


}
