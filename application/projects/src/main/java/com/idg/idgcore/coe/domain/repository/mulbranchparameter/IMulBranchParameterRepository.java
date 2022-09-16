package com.idg.idgcore.coe.domain.repository.mulbranchparameter;

import com.idg.idgcore.coe.domain.entity.mulbranchparameter.MulBranchParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMulBranchParameterRepository extends JpaRepository<MulBranchParameterEntity,Integer> {

    MulBranchParameterEntity findByBranchParamId(Integer branchParamId);

}
