package com.idg.idgcore.coe.domain.service;

import com.idg.idgcore.coe.domain.entity.MappingEntity;

import java.util.List;

public interface IMappingDomainService {
    List<MappingEntity> getAllMappings ();
    MappingEntity getMappingByCrietria (String action, String role, String status);
}
