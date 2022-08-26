package com.idg.idgcore.coe.domain.assembler.currencyratetype;

import com.idg.idgcore.coe.domain.entity.currencyratetype.CurrencyRateTypeEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.currencyratetype.CurrencyRateTypeDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CurrencyRateTypeAssembler {

    private ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public CurrencyRateTypeEntity convertDtoToEntity(CurrencyRateTypeDTO currencyRateTypeDTO){
        CurrencyRateTypeEntity currencyRateTypeEntity = modelMapper.map(currencyRateTypeDTO, CurrencyRateTypeEntity.class);
        return currencyRateTypeEntity;
    }

    public CurrencyRateTypeDTO convertEntityToDto(CurrencyRateTypeEntity currencyRateTypeEntity){
        CurrencyRateTypeDTO currencyRateTypeDTO = modelMapper.map(currencyRateTypeEntity,CurrencyRateTypeDTO.class);
        return currencyRateTypeDTO;
    }
    public CurrencyRateTypeDTO setAuditFields (MutationEntity mutationEntity, CurrencyRateTypeDTO currencyRateTypeDTO)
    {
        currencyRateTypeDTO.setAction(mutationEntity.getAction());
        currencyRateTypeDTO.setAuthorized(mutationEntity.getAuthorized());
        currencyRateTypeDTO.setRecordVersion(mutationEntity.getRecordVersion());
        currencyRateTypeDTO.setStatus(mutationEntity.getStatus());
        currencyRateTypeDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        currencyRateTypeDTO.setCreatedBy(mutationEntity.getCreatedBy());
        currencyRateTypeDTO.setCreationTime(mutationEntity.getCreationTime());
        currencyRateTypeDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        currencyRateTypeDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        currencyRateTypeDTO.setTaskCode(mutationEntity.getTaskCode());
        currencyRateTypeDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return currencyRateTypeDTO;
    }
}
