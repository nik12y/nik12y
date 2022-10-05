package com.idg.idgcore.coe.domain.service.virtualRelationship;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.domain.entity.virtualRelationship.VirtualRelationshipEntity;
import org.json.simple.parser.ParseException;

public interface IVirtualEntityRelationshipDomain {

    VirtualRelationshipEntity save(final VirtualRelationshipEntity hierarchyEntity) throws JsonProcessingException, ParseException;

    VirtualRelationshipEntity getById(String id);


}
