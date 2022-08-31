package com.idg.idgcore.coe.dto.currencyconfiguration;

import com.fasterxml.jackson.annotation.*;
import com.idg.idgcore.coe.domain.entity.currencyconfiguration.*;
import com.idg.idgcore.coe.dto.base.*;
import lombok.*;

import static com.idg.idgcore.coe.common.Constants.*;

@ToString (callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@JsonInclude (JsonInclude.Include.NON_NULL)
public class CurrencyDetailsDTO extends CoreEngineBaseDTO {

    private String countryCode;
    private String currencyCode;
    private String currencyName;
    private short isoNumericCode;
    private String countryName;
    private String locale;
    private String alternateCurrencyCode;
    private String currencySymbol;
    private String currencyDescription;
    private String currencyCategory;
    private short decimals;
    private String dayDivisor;
    private short spotDays;
    private short fxNettingDays;
    private short settlementDays;
    private String calenderId;

    private boolean beforeFormattingFlag;

    private boolean afterFormattingFlag;

    private boolean includeSpaceFormattingFlag;

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
    private int roundingUnit;
    private String amountFormatMaskPtt;
    private boolean euroTransactionCurrency;
    private String inLegCurrency;
    private String outLegCurrency;
    private boolean euroCloseFlag;
    private float creditExchangeLimit;
    private float debitExchangeLimit;
    private String extraFieldName;
    private String extraFiledvalue;
    private String currencyCountryCode;
    private String currencyCountryName;



    public CurrencyDetailsDTO (CurrencyConfigurationDetailsEntity currencyConfigurationEntity){
        this.countryCode = currencyConfigurationEntity.getCountryCode();
        this.countryName = currencyConfigurationEntity.getCountryName();
        this.currencyCode = currencyConfigurationEntity.getCurrencyCode();
        this.currencyName = currencyConfigurationEntity.getCurrencyName();
        this.isoNumericCode = currencyConfigurationEntity.getIsoNumericCode();
        this.locale = currencyConfigurationEntity.getLocale();
        this.alternateCurrencyCode = currencyConfigurationEntity.getAlternateCurrencyCode();
        this.currencySymbol = currencyConfigurationEntity.getCurrencySymbol();
        this.currencyDescription = currencyConfigurationEntity.getCurrencyDescription();
        this.currencyCategory = currencyConfigurationEntity.getCurrencyCategory();
        this.decimals = currencyConfigurationEntity.getDecimals();
        this.dayDivisor = currencyConfigurationEntity.getDayDivisor();
        this.spotDays = currencyConfigurationEntity.getSpotDays();
        this.fxNettingDays =  currencyConfigurationEntity.getFxNettingDays();
        this.settlementDays = currencyConfigurationEntity.getSettlementDays();
        this.beforeFormattingFlag = currencyConfigurationEntity.isBeforeFormattingFlag();
        this.afterFormattingFlag = currencyConfigurationEntity.isAfterFormattingFlag();
        this.includeSpaceFormattingFlag = currencyConfigurationEntity.isIncludeSpaceFormattingFlag();
        this.calenderId = currencyConfigurationEntity.getCalenderId();
        this.days = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().getDays();
        this.hours = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().getHours();
        this.minutes = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().getMinutes();
        this.enableMt101Remit = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().isEnableMt101Remit();
        this.enableMt103Stp = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().isEnableMt103Stp();
        this.indexFlag = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().isIndexFlag();
        this.enableMt202Cov = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().isEnableMt202Cov();
        this.clsCurrencyFlag = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().isClsCurrencyFlag();
        this.validateTag50fFlag = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().isValidateTag50fFlag();
        this.preferredHoliday1 = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().getPreferredHoliday1();
        this.preferredHoliday2 = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().getPreferredHoliday2();
        this.preferredHoliday3 = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().getPreferredHoliday3();
        this.roundingRule = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().getRoundingRule();
        this.roundingUnit = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().getRoundingUnit();
        this.amountFormatMaskPtt = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().getAmountFormatMaskPtt();
        this.euroTransactionCurrency = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().isEuroTransactionCurrency();
        this.inLegCurrency = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().getInLegCurrency();
        this.outLegCurrency = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().getOutLegCurrency();
        this.euroCloseFlag = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().isEuroCloseFlag();
        this.creditExchangeLimit = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().getCreditExchangeLimit();
        this.debitExchangeLimit = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().getDebitExchangeLimit();
        this.extraFieldName = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().getExtraFieldName();
        this.extraFiledvalue = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().getExtraFiledvalue();
        this.currencyCountryCode = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().getCurrencyCountryCode();
        this.currencyCountryName = currencyConfigurationEntity.getCurrencyCutOffRoundingRuleEntity().getCurrencyCountryName();
    }

    @Override
    public String getTaskCode ()
    {
        return CURRENCY;
    }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(CURRENCY);
    }

    @Override
    public String getTaskIdentifier ()
    {
        return this.getCountryCode();
    }

}
