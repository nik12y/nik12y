package com.idg.idgcore.coe.domain.assembler.purpose;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.purpose.PurposeEntity;
import com.idg.idgcore.coe.dto.purpose.PurposeDTO;
import org.springframework.stereotype.Component;

@Component
public class PurposeAssembler extends Assembler<PurposeDTO, PurposeEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return PurposeDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return PurposeEntity.class;
    }
}
