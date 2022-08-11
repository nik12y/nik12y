package com.idg.idgcore.coe.domain.repository.purgingpolicy;

import com.idg.idgcore.coe.domain.entity.purging.PurgingEntity;
import com.idg.idgcore.coe.domain.entity.purging.PurgingEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPurgingRepository extends JpaRepository<PurgingEntity, PurgingEntityKey> {

    PurgingEntity findByModuleCode (String moduleCode);
}
