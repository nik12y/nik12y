package com.idg.idgcore.coe.domain.assembler.purgingpolicy;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.purging.PurgingEntity;
import com.idg.idgcore.coe.dto.purgingpolicy.PurgingDTO;
import org.springframework.stereotype.Component;

@Component
public class PurgingAssembler extends Assembler<PurgingDTO, PurgingEntity> {


    @Override
    public Class getSpecificDTOClass() {
        return PurgingDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return PurgingEntity.class;
    }
}
