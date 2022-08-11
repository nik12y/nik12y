package com.idg.idgcore.coe.domain.repository.branchparameter;

import com.idg.idgcore.coe.domain.entity.branchparameter.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface IBranchParameterRepository extends JpaRepository<BranchParameterEntity,BranchParameterEntityKey> {
    BranchParameterEntity findByBranchCode (String branchParameterCode);
}
