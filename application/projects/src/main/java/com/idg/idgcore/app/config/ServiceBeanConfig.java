package com.idg.idgcore.app.config;

import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.HashMap;

@Configuration
public class ServiceBeanConfig {
    private Map<String, String> beanMappings;

    @PostConstruct
    public void init () {
        beanMappings = new HashMap<>();
        beanMappings.put("COUNTRY",
                "countryApplicationService");

        beanMappings.put("CITY",
                "cityApplicationService");
    }

    public Map<String, String> getBeanConfig () {
        return beanMappings;
    }

}
