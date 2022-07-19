package com.idg.idgcore.coe.domain.repository.purpose;

import com.idg.idgcore.coe.domain.entity.purpose.PurposeEntity;
import com.idg.idgcore.coe.domain.entity.purpose.PurposeEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPurposeRepository extends JpaRepository<PurposeEntity, PurposeEntityKey> {

    PurposeEntity findByPurposeCode(String purposeCode);
}
