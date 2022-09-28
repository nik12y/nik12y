package com.idg.idgcore.coe.domain.assembler.riskcode;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.riskcode.RiskCodeEntity;
import com.idg.idgcore.coe.dto.riskcode.RiskCodeDTO;
import org.springframework.stereotype.Component;

@Component
public class RiskCodeAssembler extends Assembler<RiskCodeDTO, RiskCodeEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return RiskCodeDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return RiskCodeEntity.class;
    }

    @Override
    public RiskCodeEntity toEntity(RiskCodeDTO riskCodeDTO) {
        RiskCodeEntity riskCodeEntity = super.toEntity(riskCodeDTO);
        riskCodeEntity.setIsAllowDetailsModified(getCharValueFromBoolean(riskCodeDTO.getIsAllowDetailsModified()));
        return riskCodeEntity;
    }

    public RiskCodeDTO convertEntityToDto(RiskCodeEntity riskCodeEntity) {
        RiskCodeDTO riskCodeDTO = modelMapper.map(riskCodeEntity, RiskCodeDTO.class);
        return riskCodeDTO;
    }

    @Override
    public RiskCodeDTO toDTO(RiskCodeEntity riskCodeEntity) {
        RiskCodeDTO riskCodeDTO = super.toDTO(riskCodeEntity);
        riskCodeDTO.setIsAllowDetailsModified(getBooleanValueFromChar(riskCodeEntity.getIsAllowDetailsModified()));
        return riskCodeDTO;
    }
}
