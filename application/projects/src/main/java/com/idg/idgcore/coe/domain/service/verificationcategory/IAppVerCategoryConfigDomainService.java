package com.idg.idgcore.coe.domain.service.verificationcategory;

import com.idg.idgcore.coe.domain.entity.verificationcategory.AppVerCategoryConfigEntity;
import com.idg.idgcore.coe.dto.verificationcategory.AppVerCategoryConfigDTO;

import java.util.List;

public interface IAppVerCategoryConfigDomainService {

        AppVerCategoryConfigEntity getConfigurationByCode (AppVerCategoryConfigDTO appVerCategoryConfigDTO);
        List<AppVerCategoryConfigEntity> getAppVerCategoryConfigs();
        AppVerCategoryConfigEntity getAppVerCategoryConfigByID (String appVerAppVerificationCategoryId);
        void save (AppVerCategoryConfigDTO appVercategoryConfigDTO);

    }

