package com.idg.idgcore.coe.app.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.HashMap;

import static com.idg.idgcore.coe.common.Constants.*;
import static com.idg.idgcore.coe.common.Constants.BANK_IDENTIFIER_SERVICE;

@Configuration
public class ServiceBeanConfig {
    private Map<String, String> beanMappings;

    @PostConstruct
    public void init () {
        beanMappings = new HashMap<>();
        beanMappings.put(COUNTRY, COUNTRY_SERVICE);
        beanMappings.put(CITY, CITY_SERVICE);
        beanMappings.put(BANK_IDENTIFIER, BANK_IDENTIFIER_SERVICE);
    }

    public Map<String, String> getBeanConfig () {
        return beanMappings;
    }

}
