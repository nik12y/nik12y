package com.idg.idgcore.coe.domain.repository.reason;

import com.idg.idgcore.coe.domain.entity.reason.ReasonEntity;
import com.idg.idgcore.coe.domain.entity.reason.ReasonEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReasonRepository extends JpaRepository<ReasonEntity,ReasonEntityKey> {
    ReasonEntity findByPrimaryReasonCode(String primaryReasonCode);
}
