package com.idg.idgcore.coe.domain.repository.verificationcategory;

import com.idg.idgcore.coe.domain.entity.verificationcategory.AppVerCategoryConfigEntity;
import com.idg.idgcore.coe.domain.entity.verificationcategory.AppVerCategoryConfigEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppVerCategoryConfigRepository extends JpaRepository<AppVerCategoryConfigEntity, AppVerCategoryConfigEntityKey> {
    AppVerCategoryConfigEntity findByAppVerificationCategoryId (String appVerificationCategoryId);
}
