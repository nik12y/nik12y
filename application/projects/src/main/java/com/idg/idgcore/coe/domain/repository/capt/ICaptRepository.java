package com.idg.idgcore.coe.domain.repository.capt;

import com.idg.idgcore.coe.domain.entity.capt.CaptEntity;
import com.idg.idgcore.coe.domain.entity.capt.CaptEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICaptRepository extends JpaRepository<CaptEntity, CaptEntityKey> {
    CaptEntity findByClearingPaymentTypeCode(String clearingPaymentTypeCode);
}
