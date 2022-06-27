package com.idg.idgcore.app.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Configuration
public class ServiceBeanConfig {
    public ModelMapper modelMapper = new ModelMapper();
    private HashMap<String, String> beanMappings;

    @PostConstruct
    public void init () {
        beanMappings = new HashMap<>();
        beanMappings.put("COUNTRY",
                "countryApplicationService&&com.idc.module.core.app.service.CountryApplicationService.class");
        beanMappings.put("STATE",
                "stateApplicationService&&com.idc.module.core.app.service.StateApplicationService.class");
        beanMappings.put("BRANCHTYPE",
                "branchTypeApplicationService&&com.idc.module.core.app.service.BranchTypeApplicationService.class");
    }

    public HashMap<String, String> getBeanConfig () {
        return beanMappings;
    }

}
