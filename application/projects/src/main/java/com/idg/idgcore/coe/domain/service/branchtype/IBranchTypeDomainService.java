package com.idg.idgcore.coe.domain.service.branchtype;

import com.idg.idgcore.coe.domain.entity.branchtype.BranchTypeEntity;
import com.idg.idgcore.coe.dto.branchtype.BranchTypeDTO;

import java.util.List;

public interface IBranchTypeDomainService {
    BranchTypeEntity getConfigurationByBranchTypeCode (BranchTypeDTO branchtypeDTO);
    List<BranchTypeEntity> getBranches ();
    BranchTypeEntity getBranchTypeByCode (String BranchTypeCode);
    void save (BranchTypeDTO branchTypeDTO);

}
