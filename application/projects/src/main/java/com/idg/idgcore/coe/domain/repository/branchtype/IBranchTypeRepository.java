package com.idg.idgcore.coe.domain.repository.branchtype;

import com.idg.idgcore.coe.domain.entity.branchtype.BranchTypeEntity;
import com.idg.idgcore.coe.domain.entity.branchtype.BranchTypeEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBranchTypeRepository extends JpaRepository<BranchTypeEntity, BranchTypeEntityKey> {
    BranchTypeEntity findByBranchTypeCode (String branchTypeCode);
}

