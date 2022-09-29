package com.idg.idgcore.coe.domain.repository.virtualentity;

import com.idg.idgcore.coe.domain.entity.virtualentity.VirtualEntity;
import com.idg.idgcore.coe.domain.entity.virtualentity.VirtualEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVirtualEntityRepository extends JpaRepository<VirtualEntity, VirtualEntityKey> {

    VirtualEntity findByEntityCode(String entityCode);
}
