package com.idg.idgcore.coe.dto.currencypair;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.*;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrencyPairDTO extends CoreEngineBaseDTO {

    private Integer pairId;
    private String currency1;
    private String currency1Name;
    private String currency2;
    private String currency2Name;
    private String entityType;
    private Integer entityLevel;
    private String entityCode;
    private String entityName;
    private String countryCode;
    private String countryName;
    private boolean throughCurrency;
    private String throughCurrencyCode;
    private String throughCurrencyDescription;
    private Integer noofunits;
    private float pointMultiplier;
    private boolean quotationMethods;
    private boolean spreadDefinition;

    public boolean getThroughCurrency() {
        return throughCurrency;
    }

    public boolean getQuotationMethods() {
        return quotationMethods;
    }

    public boolean getSpreadDefinition() {
        return spreadDefinition;
    }

    @Override
    public String getTaskCode () {
        return CURRENCY_PAIR;
    }

    @Override
    public void setTaskCode(String taskCode) {
        super.setTaskCode(CURRENCY_PAIR);
    }

    @Override
    public String getTaskIdentifier () {
        return  currency1 + FIELD_SEPARATOR + currency2 + FIELD_SEPARATOR + entityType + FIELD_SEPARATOR + entityCode;
    }

    public static Class<? extends CoreEngineBaseDTO> getSpecificType() {
        return CurrencyPairDTO.class;
    }

}
