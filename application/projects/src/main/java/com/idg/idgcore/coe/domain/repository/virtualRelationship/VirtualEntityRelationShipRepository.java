package com.idg.idgcore.coe.domain.repository.virtualRelationship;


import com.idg.idgcore.coe.domain.entity.virtualRelationship.VirtualRelationshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VirtualEntityRelationShipRepository extends JpaRepository<VirtualRelationshipEntity, Integer> {

    VirtualRelationshipEntity getById(String id);
}
