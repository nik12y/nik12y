package com.idg.idgcore.coe.domain.assembler.city;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.city.CityEntity;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.dto.city.CityDTO;
import org.springframework.stereotype.Component;

@Component
public class CityAssembler extends Assembler<CityDTO, CityEntity> {
    @Override
    public Class getSpecificDTOClass() {
        return CityDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return CityEntity.class;
    }

}
