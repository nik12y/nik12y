package com.idg.idgcore.coe.domain.repository.mitigant;

import com.idg.idgcore.coe.domain.entity.mitigant.MitigantEntity;
import com.idg.idgcore.coe.domain.entity.mitigant.MitigantEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMitigantRepository extends JpaRepository<MitigantEntity, MitigantEntityKey> {

    MitigantEntity findByMitigantCode(String mitigantCode);
}
