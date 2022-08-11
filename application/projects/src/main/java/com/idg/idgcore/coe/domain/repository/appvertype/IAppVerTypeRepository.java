package com.idg.idgcore.coe.domain.repository.appvertype;

import com.idg.idgcore.coe.domain.entity.appvertype.AppVerTypeEntity;
import com.idg.idgcore.coe.domain.entity.appvertype.AppVerTypeEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppVerTypeRepository extends JpaRepository<AppVerTypeEntity, AppVerTypeEntityKey> {
    AppVerTypeEntity findByVerificationTypeId(String verificationTypeId);
}
