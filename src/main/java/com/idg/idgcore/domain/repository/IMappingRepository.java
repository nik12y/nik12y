package com.idg.idgcore.domain.repository;

import com.idg.idgcore.domain.entity.MappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMappingRepository extends JpaRepository<MappingEntity, Integer> {
    MappingEntity findByActionAndRoleAndStatus (String action, String role, String status);
}
