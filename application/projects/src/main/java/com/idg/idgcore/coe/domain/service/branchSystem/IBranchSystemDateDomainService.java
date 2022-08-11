package com.idg.idgcore.coe.domain.service.branchSystem;

import com.idg.idgcore.coe.domain.entity.branchSystem.BranchSystemDateEntity;
import com.idg.idgcore.coe.dto.branchSystem.BranchSystemDateDTO;

import java.util.List;

public interface IBranchSystemDateDomainService {

    BranchSystemDateEntity getConfigurationByCode (BranchSystemDateDTO branchSystemDateDTO);
    List<BranchSystemDateEntity> getBranchSystemDateAll();
    BranchSystemDateEntity getBranchSystemDateByCode (String branchCode);
    void save (BranchSystemDateDTO branchSystemDateDTO);
}
