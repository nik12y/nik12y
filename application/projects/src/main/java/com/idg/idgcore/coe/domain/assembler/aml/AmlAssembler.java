package com.idg.idgcore.coe.domain.assembler.aml;

import com.idg.idgcore.coe.domain.entity.aml.AmlEntity;
import com.idg.idgcore.coe.domain.entity.aml.LimitEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.aml.AmlDTO;
import com.idg.idgcore.coe.dto.aml.LimitDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class AmlAssembler {
    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public AmlEntity convertDtoToEntity(AmlDTO amlDTO) {
        LimitDTO limitDTO = amlDTO.getLimit();
        LimitEntity limitEntity = modelMapper.map(limitDTO, LimitEntity.class);

        AmlEntity amlEntity = modelMapper.map(amlDTO, AmlEntity.class);
        amlEntity.setLimitEntity(limitEntity);
        amlEntity.setIsProductType(getCharValueFromBoolean(amlDTO.getIsProductType()));

        return amlEntity;
    }

    public AmlDTO convertEntityToDto(AmlEntity amlEntity) {

        LimitEntity limitEntity = amlEntity.getLimitEntity();
        LimitDTO limitDTO = modelMapper.map(limitEntity, LimitDTO.class);

        AmlDTO amlDTO = modelMapper.map(amlEntity, AmlDTO.class);
        amlDTO.setLimit(limitDTO);
        amlDTO.setProductType(getBooleanValueFromChar(amlEntity.getIsProductType()));

        return amlDTO;
    }

    public AmlDTO setAuditFields (MutationEntity mutationEntity, AmlDTO amlDTO ) {
        amlDTO.setAction(mutationEntity.getAction());
        amlDTO.setAuthorized(mutationEntity.getAuthorized());
        amlDTO.setRecordVersion(mutationEntity.getRecordVersion());
        amlDTO.setStatus(mutationEntity.getStatus());
        amlDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        amlDTO.setCreatedBy(mutationEntity.getCreatedBy());
        amlDTO.setCreationTime(mutationEntity.getCreationTime());
        amlDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        amlDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        amlDTO.setTaskCode(mutationEntity.getTaskCode());
        amlDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return amlDTO;
    }
    public char getCharValueFromBoolean (boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar (Character value) {
        return value.equals(CHAR_Y);
    }
}
