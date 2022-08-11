package com.idg.idgcore.coe.domain.repository.categorychecklist;

import com.idg.idgcore.coe.domain.entity.categorychecklist.AppVerCatChecklistPolicyEntity;
import com.idg.idgcore.coe.domain.entity.categorychecklist.AppVerCatChecklistPolicyEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppVerCatChecklistPolicyRepository extends JpaRepository<AppVerCatChecklistPolicyEntity, AppVerCatChecklistPolicyEntityKey> {
    AppVerCatChecklistPolicyEntity findByAppVerChecklistPolicyId(String appVerChecklistPolicyId);
}
