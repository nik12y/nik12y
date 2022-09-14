package com.idg.idgcore.coe.dto.currencypair;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.CURRENCY_PAIR;

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
    private String currency1Description;
    private String currency2;
    private String currency2Description;
    private String entityCodeType;
    private String entityCode;
    private String countryCode;
    private boolean throughCurrency;
    private String throughCurrencyCode;
    private String throughCurrencyDescription;
    private Integer noofunits;
    private float pointMultiplier;
    private boolean quotationMethods;
    private boolean spreadDefinition;

    public Boolean getThroughCurrency() {
        return throughCurrency;
    }

    public Boolean getQuotationMethods() {
        return quotationMethods;
    }

    public Boolean getSpreadDefinition() {
        return spreadDefinition;
    }

    @Override
    public String getTaskCode () {
        return CURRENCY_PAIR;
    }

    @Override
    public void setTaskCode(String taskCode)
    {
        super.setTaskCode(CURRENCY_PAIR);
    }

    @Override
    public String getTaskIdentifier () {
        if(this.getPairId() != null) {
            return this.getPairId()+"";
        }
        return  getCurrency1()+getCurrency2()+getEntityCodeType()+getEntityCode();
    }

}
