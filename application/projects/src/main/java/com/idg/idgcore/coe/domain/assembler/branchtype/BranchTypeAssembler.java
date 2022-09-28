package com.idg.idgcore.coe.domain.assembler.branchtype;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.branchtype.BranchTypeEntity;
import com.idg.idgcore.coe.dto.branchtype.BranchTypeDTO;
import org.springframework.stereotype.Component;

@Component
public class BranchTypeAssembler extends Assembler<BranchTypeDTO, BranchTypeEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return BranchTypeDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return BranchTypeEntity.class;
    }
}
