package com.idg.idgcore.coe.domain.assembler.aml;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.aml.AmlEntity;
import com.idg.idgcore.coe.domain.entity.aml.LimitEntity;
import com.idg.idgcore.coe.dto.aml.AmlDTO;
import com.idg.idgcore.coe.dto.aml.LimitDTO;
import org.springframework.stereotype.Component;

@Component
public class AmlAssembler extends Assembler<AmlDTO, AmlEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return AmlDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return AmlEntity.class;
    }

    @Override
    public AmlEntity toEntity(AmlDTO amlDTO) {
        AmlEntity amlEntity = super.toEntity(amlDTO);
        LimitDTO limitDTO = amlDTO.getLimit();
        LimitEntity limitEntity = modelMapper.map(limitDTO, LimitEntity.class);
        amlEntity.setLimitEntity(limitEntity);
        amlEntity.setIsProductType(getCharValueFromBoolean(amlDTO.getIsProductType()));
        return amlEntity;
    }

    @Override
    public AmlDTO toDTO(AmlEntity amlEntity) {
        AmlDTO amlDTO = super.toDTO(amlEntity);
        LimitEntity limitEntity = amlEntity.getLimitEntity();
        LimitDTO limitDTO = modelMapper.map(limitEntity, LimitDTO.class);
        amlDTO.setLimit(limitDTO);
        amlDTO.setProductType(getBooleanValueFromChar(amlEntity.getIsProductType()));
        return amlDTO;
    }

}
