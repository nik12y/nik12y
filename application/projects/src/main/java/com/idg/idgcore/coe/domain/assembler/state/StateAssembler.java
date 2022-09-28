package com.idg.idgcore.coe.domain.assembler.state;


import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.state.StateEntity;
import com.idg.idgcore.coe.dto.state.StateDTO;
import org.springframework.stereotype.Component;


@Component
public class StateAssembler extends Assembler<StateDTO, StateEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return StateDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return StateEntity.class;
    }
}
