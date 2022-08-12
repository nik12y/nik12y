package com.idg.idgcore.coe.domain.assembler.zakat;

import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.zakat.ZakatEntity;
import com.idg.idgcore.coe.dto.zakat.ZakatDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ZakatAssembler {
    private ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public ZakatEntity convertDtoToEntity(ZakatDTO zakatDTO){
        return modelMapper.map(zakatDTO, ZakatEntity.class);
    }

    public ZakatDTO convertEntityToDto(ZakatEntity zakatEntity){
        return modelMapper.map(zakatEntity, ZakatDTO.class);
    }


    public ZakatDTO setAuditFields (MutationEntity mutationEntity, ZakatDTO zakatDTO) {
        zakatDTO.setAction(mutationEntity.getAction());
        zakatDTO.setAuthorized(mutationEntity.getAuthorized());
        zakatDTO.setRecordVersion(mutationEntity.getRecordVersion());
        zakatDTO.setStatus(mutationEntity.getStatus());
        zakatDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        zakatDTO.setCreatedBy(mutationEntity.getCreatedBy());
        zakatDTO.setCreationTime(mutationEntity.getCreationTime());
        zakatDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        zakatDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        zakatDTO.setTaskCode(mutationEntity.getTaskCode());
        zakatDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return zakatDTO;
    }
}
