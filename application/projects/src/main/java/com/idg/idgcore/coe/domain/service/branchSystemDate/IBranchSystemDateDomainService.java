package com.idg.idgcore.coe.domain.service.branchSystemDate;

import com.idg.idgcore.coe.domain.entity.branchSystemDate.BranchSystemDateEntity;
import com.idg.idgcore.coe.dto.branchSystemDate.BranchSystemDateDTO;

import java.util.List;

public interface IBranchSystemDateDomainService {

    BranchSystemDateEntity getConfigurationByCode (BranchSystemDateDTO branchSystemDateDTO);
    List<BranchSystemDateEntity> getBranchSystemDateAll();
    BranchSystemDateEntity getBranchSystemDateByCode (String branchCode);
    void save (BranchSystemDateDTO branchSystemDateDTO);
}
