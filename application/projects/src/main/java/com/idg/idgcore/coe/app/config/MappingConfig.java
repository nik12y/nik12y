package com.idg.idgcore.coe.app.config;

import com.idg.idgcore.coe.domain.service.IMappingDomainService;
import com.idg.idgcore.coe.dto.MappingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MappingConfig {
    private final ModelMapper modelMapper = new ModelMapper();
    private List<MappingDTO> mappings;
    @Autowired
    private IMappingDomainService mappingDomainService;

    @PostConstruct
    public void init () {
        mappings = mappingDomainService.getAllMappings().stream()
                .map(mappingEntity -> modelMapper.map(mappingEntity, MappingDTO.class))
                .collect(Collectors.toList());
    }

    public List<MappingDTO> getMappings () {
        return mappings;
    }

    public MappingDTO getMappingByCrietria (String action, String role, String status) {
        log.info("In getMappingByCrietria with parameters action {}, role {}, status {}", action,
                role, status);
        return modelMapper.map(mappingDomainService.getMappingByCrietria(action, role, status),
                MappingDTO.class);
    }

}
