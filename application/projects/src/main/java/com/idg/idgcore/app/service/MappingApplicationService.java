package com.idg.idgcore.app.service;

import com.idg.idgcore.app.dto.MappingDTO;
import com.idg.idgcore.domain.service.IMappingDomainService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.*;

@Slf4j
public class MappingApplicationService implements IMappingApplicationService {
    public static ModelMapper mapper = new ModelMapper();
    @Autowired
    private IMappingDomainService mappingDomainService;

    @Override
    public MappingDTO getMappingByCrietria (String action, String role, String status) {
        return mapper.map(mappingDomainService.getMappingByCrietria(action, role, status),
                MappingDTO.class);
    }

    @Override
    public List<MappingDTO> getMappings () {
        return mappingDomainService.getAllMappings().stream().map(mappingEntity -> {
            return mapper.map(mappingEntity, MappingDTO.class);
        }).collect(Collectors.toList());
    }
}
