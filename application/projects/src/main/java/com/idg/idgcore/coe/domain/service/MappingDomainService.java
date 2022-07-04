package com.idg.idgcore.coe.domain.service;

import com.idg.idgcore.coe.domain.entity.MappingEntity;
import com.idg.idgcore.coe.domain.repository.IMappingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MappingDomainService implements IMappingDomainService {
    @Autowired
    private IMappingRepository mappingRepository;
    @Override
    public List<MappingEntity> getAllMappings () {
        return mappingRepository.findAll();
    }
    @Override
    public MappingEntity getMappingByCrietria (String action, String role, String status) {
        return mappingRepository.findByActionAndRoleAndStatus(action, role, status);
    }

}

