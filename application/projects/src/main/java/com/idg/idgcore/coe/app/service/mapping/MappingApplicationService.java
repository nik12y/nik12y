package com.idg.idgcore.coe.app.service.mapping;

import com.idg.idgcore.coe.dto.mapping.MappingDTO;
import com.idg.idgcore.coe.domain.service.mapping.IMappingDomainService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MappingApplicationService implements IMappingApplicationService {
    private final ModelMapper mapper = new ModelMapper();
    @Autowired
    private IMappingDomainService mappingDomainService;

    @Override
    public MappingDTO getMappingByCrietria (String action, String role, String status) {
        return mapper.map(mappingDomainService.getMappingByCrietria(action, role, status),
                MappingDTO.class);
    }

    @Override
    public List<MappingDTO> getMappings () {
        return mappingDomainService.getAllMappings().stream()
                .map(mappingEntity -> mapper.map(mappingEntity, MappingDTO.class))
                .collect(Collectors.toList());
    }

}
