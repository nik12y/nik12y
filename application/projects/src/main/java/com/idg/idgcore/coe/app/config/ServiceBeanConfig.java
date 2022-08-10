package com.idg.idgcore.coe.app.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.HashMap;

import static com.idg.idgcore.coe.common.Constants.*;


@Configuration
public class ServiceBeanConfig {
    private Map<String, String> beanMappings;

    @PostConstruct
    public void init () {
        beanMappings = new HashMap<>();
        beanMappings.put(COUNTRY, COUNTRY_SERVICE);
        beanMappings.put(CITY, CITY_SERVICE);
        beanMappings.put(BRANCHTYPE, BRANCHTYPE_SERVICE);
        beanMappings.put(GROUP_BANKING,GROUP_BANKING_SERVICE);
        beanMappings.put(LANGUAGE, LANGUAGE_SERVICE);
        beanMappings.put(BANK_IDENTIFIER, BANK_IDENTIFIER_SERVICE);
        beanMappings.put(PURPOSE, PURPOSE_SERVICE);
        beanMappings.put(STATE, STATE_SERVICE);
        beanMappings.put(BANK, BANK_SERVICE);
        beanMappings.put(MODULE, MODULE_SERVICE);
        beanMappings.put(CAPT, CAPT_SERVICE);
        beanMappings.put(BRANCH_SYSTEM_DATE, BRANCH_SYSTEM_DATE_SERVICE);
    }

    public Map<String, String> getBeanConfig () {
        return beanMappings;
    }

}
