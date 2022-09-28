package com.idg.idgcore.coe.domain.assembler.riskcategory;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.riskcategory.RiskCategoryEntity;
import com.idg.idgcore.coe.dto.riskcategory.RiskCategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class RiskCategoryAssembler extends Assembler<RiskCategoryDTO, RiskCategoryEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return RiskCategoryDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return RiskCategoryEntity.class;
    }

}
