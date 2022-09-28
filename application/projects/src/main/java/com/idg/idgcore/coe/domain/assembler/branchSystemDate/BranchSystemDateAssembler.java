package com.idg.idgcore.coe.domain.assembler.branchSystemDate;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.branchSystemDate.BranchSystemDateEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.branchSystemDate.BranchSystemDateDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.*;

@Component
public class BranchSystemDateAssembler extends Assembler<BranchSystemDateDTO, BranchSystemDateEntity> {

    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Class getSpecificDTOClass() {
        return BranchSystemDateDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return BranchSystemDateEntity.class;
    }

    @Override
    public BranchSystemDateDTO toDTO(BranchSystemDateEntity branchSystemEntity) {
        BranchSystemDateDTO branchSystemDTO = super.toDTO(branchSystemEntity);
        branchSystemDTO.setCurrentWorkingDate(formatter.format(branchSystemEntity.getCurrentWorkingDate()));
        branchSystemDTO.setPreviousWorkingDate(formatter.format(branchSystemEntity.getPreviousWorkingDate()));
        branchSystemDTO.setNextWorkingDate(formatter.format(branchSystemEntity.getNextWorkingDate()));
        return branchSystemDTO;
    }

}
