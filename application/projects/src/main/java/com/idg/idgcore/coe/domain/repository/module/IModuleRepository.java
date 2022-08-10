package com.idg.idgcore.coe.domain.repository.module;

import com.idg.idgcore.coe.domain.entity.module.ModuleEntity;
import com.idg.idgcore.coe.domain.entity.module.ModuleEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IModuleRepository extends JpaRepository<ModuleEntity, ModuleEntityKey> {
    ModuleEntity findByModuleCode (String ModuleCode);
}
