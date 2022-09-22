package com.idg.idgcore.coe.domain.assembler.mulbranchparameter;


import com.idg.idgcore.coe.domain.entity.currencyratetype.CurrencyRateTypeEntity;
import com.idg.idgcore.coe.domain.entity.mulbranchparameter.MulBranchParameterEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.currencyratetype.CurrencyRateTypeDTO;
import com.idg.idgcore.coe.dto.mulbranchparameter.MulBranchParameterDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.lang.reflect.Type;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class MulBranchParameterAssembler {

    private ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public MulBranchParameterEntity convertDtoToEntity(MulBranchParameterDTO mulBranchParameterDTO) {
        MulBranchParameterEntity mulBranchParameterEntity = modelMapper.map(mulBranchParameterDTO, MulBranchParameterEntity.class);
        return mulBranchParameterEntity;
    }

    public MulBranchParameterDTO convertEntityToDto(MulBranchParameterEntity mulBranchParameterEntity) {
        MulBranchParameterDTO mulBranchParameterDTO = modelMapper.map(mulBranchParameterEntity,MulBranchParameterDTO.class);
        return mulBranchParameterDTO;
    }

    public MulBranchParameterDTO setAuditFields(MutationEntity mutationEntity, MulBranchParameterDTO mulBranchParameterDTO) {
        mulBranchParameterDTO.setAction(mutationEntity.getAction());
        mulBranchParameterDTO.setAuthorized(mutationEntity.getAuthorized());
        mulBranchParameterDTO.setRecordVersion(mutationEntity.getRecordVersion());
        mulBranchParameterDTO.setStatus(mutationEntity.getStatus());
        mulBranchParameterDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        mulBranchParameterDTO.setCreatedBy(mutationEntity.getCreatedBy());
        mulBranchParameterDTO.setCreationTime(mutationEntity.getCreationTime());
        mulBranchParameterDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        mulBranchParameterDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        mulBranchParameterDTO.setTaskCode(mutationEntity.getTaskCode());
        mulBranchParameterDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return mulBranchParameterDTO;
    }

    public char getCharValueFromBoolean(boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar(Character value) {
        return value.equals(CHAR_Y);
    }
}
