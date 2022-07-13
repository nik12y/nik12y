package com.idg.idgcore.coe.domain.repository.mapping;

import com.idg.idgcore.coe.domain.entity.mapping.MappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMappingRepository extends JpaRepository<MappingEntity, Integer> {
    MappingEntity findByActionAndRoleAndStatus (String action, String role, String status);
}
