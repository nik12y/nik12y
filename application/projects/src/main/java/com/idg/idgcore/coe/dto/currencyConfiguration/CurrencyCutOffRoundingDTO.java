package com.idg.idgcore.coe.dto.currencyConfiguration;

import com.fasterxml.jackson.annotation.*;
import com.idg.idgcore.coe.dto.base.*;
import lombok.*;

@ToString (callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@JsonInclude (JsonInclude.Include.NON_NULL)
public class CurrencyCutOffRoundingDTO extends CoreEngineBaseDTO {

    private String currencyCode;
    private short days;
    private short hours;
    private short minutes;
    private boolean enableMt101Remit;
    private boolean enableMt103Stp;
    private boolean indexFlag;
    private boolean enableMt202Cov;
    private boolean clsCurrencyFlag;
    private boolean validateTag50fFlag;
    private String preferredHoliday1;
    private String preferredHoliday2;
    private String preferredHoliday3;
    private String roundingRule;
    private float roundingUnit;
    private String amountFormatMaskPtt;
    private boolean euroTransactionCurrency;
    private String inLegCurrency;
    private String outLegCurrency;
    private boolean euroCloseFlag;
    private float creditExchangeLimit;
    private float debitExchangeLimit;

    @Override
    public String getTaskCode() {
        return "CurrencyCutOffRoundingDTO";
    }
}
