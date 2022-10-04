package com.idg.idgcore.coe.domain.assembler.mulbranchparameter;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.mulbranchparameter.MulBranchParameterEntity;
import com.idg.idgcore.coe.dto.mulbranchparameter.MulBranchParameterDTO;
import org.springframework.stereotype.Component;

@Component
public class MulBranchParameterAssembler extends Assembler<MulBranchParameterDTO,MulBranchParameterEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return MulBranchParameterDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return MulBranchParameterEntity.class;
    }

}
