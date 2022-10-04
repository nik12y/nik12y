package com.idg.idgcore.coe.domain.assembler.mitigant;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.mitigant.MitigantEntity;
import com.idg.idgcore.coe.domain.entity.mitigant.MitigantRiskCodeEntity;
import com.idg.idgcore.coe.dto.mitigant.MitigantDTO;
import com.idg.idgcore.coe.dto.mitigant.MitigantRiskCodeDTO;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class MitigantAssembler extends Assembler<MitigantDTO, MitigantEntity> {


    @Override
    public Class getSpecificDTOClass() {
        return MitigantDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return MitigantEntity.class;
    }

    @Override
    public MitigantEntity toEntity(MitigantDTO mitigantDTO) {
        List<MitigantRiskCodeDTO> mitigantRiskCodeDTOList = mitigantDTO.getMitigantRiskCode();
        Type listType = new TypeToken<List<MitigantRiskCodeEntity>>(){}.getType();
        List<MitigantRiskCodeEntity> mitigantRiskCodeEntityList = modelMapper.map(mitigantRiskCodeDTOList,listType);
        MitigantEntity mitigantEntity = modelMapper.map(mitigantDTO, MitigantEntity.class);
        mitigantEntity.setMitigantRiskCode(mitigantRiskCodeEntityList);
        mitigantEntity.setIsAllowModification(getCharValueFromBoolean(mitigantDTO.getIsAllowModification()));
        mitigantEntity.setIsActionable(getCharValueFromBoolean(mitigantDTO.getIsActionable()));
        return mitigantEntity;
    }

    @Override
    public MitigantDTO toDTO(MitigantEntity mitigantEntity) {
        List<MitigantRiskCodeEntity> mitigantRiskCodeEntityList = mitigantEntity.getMitigantRiskCode();
        Type listType = new TypeToken<List<MitigantRiskCodeDTO>>(){}.getType();
        List<MitigantRiskCodeDTO> mitigantRiskCodeDTOList = modelMapper.map(mitigantRiskCodeEntityList,listType);
        MitigantDTO mitigantDTO = modelMapper.map(mitigantEntity, MitigantDTO.class);
        mitigantDTO.setMitigantRiskCode(mitigantRiskCodeDTOList);
        mitigantDTO.setIsAllowModification(getBooleanValueFromChar(mitigantEntity.getIsAllowModification()));
        mitigantDTO.setIsActionable(getBooleanValueFromChar(mitigantEntity.getIsActionable()));
        return mitigantDTO;
    }
}
