package com.idg.idgcore.coe.domain.service.virtualRelationship;

import com.idg.idgcore.coe.domain.entity.virtualRelationship.VirtualRelationshipEntity;
import com.idg.idgcore.coe.domain.repository.virtualRelationship.VirtualEntityRelationShipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VirtualEntityRelationshipDomainService implements IVirtualEntityRelationshipDomain {

    @Autowired
    private VirtualEntityRelationShipRepository virtualEntityRelationShipRepository;

    @Override
    public VirtualRelationshipEntity save(VirtualRelationshipEntity hierarchyEntity)  {
        return  this.virtualEntityRelationShipRepository.save(hierarchyEntity);
    }

    @Override
    public VirtualRelationshipEntity getById(String id) {
        return this.virtualEntityRelationShipRepository.getById(id);
    }

}
