package com.idg.idgcore.coe.domain.service.mapping;

import com.idg.idgcore.coe.domain.entity.mapping.MappingEntity;

import java.util.List;

public interface IMappingDomainService {
    List<MappingEntity> getAllMappings ();
    MappingEntity getMappingByCrietria (String action, String role, String status);
}
