package com.idg.idgcore.coe.domain.repository.branchSystemDate;

import com.idg.idgcore.coe.domain.entity.branchSystemDate.BranchSystemDateEntity;
import com.idg.idgcore.coe.domain.entity.branchSystemDate.BranchSystemDateEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBranchSystemDateRepository extends JpaRepository<BranchSystemDateEntity, BranchSystemDateEntityKey> {

    BranchSystemDateEntity findByBranchCode(String branchCode);
}
