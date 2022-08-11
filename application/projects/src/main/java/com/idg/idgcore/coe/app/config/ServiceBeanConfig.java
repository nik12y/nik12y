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
        beanMappings.put(CATEGORY, VERIFICATION_CATEGORY_SERVICE);
        beanMappings.put(CHECKLIST, CHECKLIST_SERVICE);
        beanMappings.put(BRANCH_PARAMETER, BRANCH_PARAMETER_SERVICE);
        beanMappings.put(BANKPARAMETER, BANKPARAMETER_SERVICE);
        beanMappings.put(IBAN, IBAN_SERVICE);
        beanMappings.put(AML, AML_SERVICE);
        beanMappings.put(APP_VERIFICATION_TYPE, APP_VERIFICATION_TYPE_SERVICE);
        beanMappings.put(PURGING_POLICY, PURGING_POLICY_SERVICE);
        beanMappings.put(BRANCH_SYSTEM_DATE, BRANCH_SYSTEM_DATE_SERVICE);
        beanMappings.put(MITIGANT, MITIGANT_SERVICE);
        beanMappings.put(RISKCATEGORY,RISKCATEGORY_SERVICE);
        beanMappings.put(RISKCODE,RISKCODE_SERVICE);
        beanMappings.put(QUE_CHECKLIST, QUE_CHECKLIST_SERVICE);
        beanMappings.put(FINANCIAL_ACCOUNTING_YEAR, FINANCIAL_ACCOUNTING_YEAR_SERVICE);
        beanMappings.put(ERROR_OVERRIDE,ERROR_OVERRIDE_SERVICE);
        beanMappings.put(QUESTION,QUESTION_SERVICE);
    }

    public Map<String, String> getBeanConfig () {
        return beanMappings;
    }

}
