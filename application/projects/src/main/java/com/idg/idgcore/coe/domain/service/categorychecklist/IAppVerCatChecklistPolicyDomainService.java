package com.idg.idgcore.coe.domain.service.categorychecklist;

import com.idg.idgcore.coe.domain.entity.categorychecklist.AppVerCatChecklistPolicyEntity;
import com.idg.idgcore.coe.dto.categorychecklist.AppVerCatChecklistPolicyDTO;

import java.util.List;

public interface IAppVerCatChecklistPolicyDomainService {

    AppVerCatChecklistPolicyEntity getConfigurationByCode (AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO);
    List<AppVerCatChecklistPolicyEntity> getAppVerChecklistPolicies();
    AppVerCatChecklistPolicyEntity getAppVerChecklistPolicyById (String appVerChecklistPolicyId);
    void save (AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO);
}
