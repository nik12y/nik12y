package com.idg.idgcore.coe.domain.repository.errorOverride;

import com.idg.idgcore.coe.domain.entity.errorOverride.ErrorOverrideEntity;
import com.idg.idgcore.coe.domain.entity.errorOverride.ErrorOverrideEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IErrorOverrideRepository extends JpaRepository<ErrorOverrideEntity,ErrorOverrideEntityKey> {
    ErrorOverrideEntity findByErrorCode (String errorCode);
    ErrorOverrideEntity findByErrorCodeAndBranchCode (String errorCode, String branchCode);
    ErrorOverrideEntity findByErrorCodeAndBranchCodeAndIsExcluded (String errorCode, String branchCode, boolean b);
    ErrorOverrideEntity findByErrorCodeAndBranchCodeAndStatusNot (String errorCode, String all, String closed);

}
