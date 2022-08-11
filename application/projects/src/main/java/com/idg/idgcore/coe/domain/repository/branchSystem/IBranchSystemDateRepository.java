package com.idg.idgcore.coe.domain.repository.branchSystem;

import com.idg.idgcore.coe.domain.entity.branchSystem.BranchSystemDateEntity;
import com.idg.idgcore.coe.domain.entity.branchSystem.BranchSystemDateEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBranchSystemDateRepository extends JpaRepository<BranchSystemDateEntity, BranchSystemDateEntityKey> {

    BranchSystemDateEntity findByBranchCode(String branchCode);
}
