package com.idg.idgcore.coe.dto.currencyconfiguration;

import com.idg.idgcore.coe.domain.entity.currencyconfiguration.*;
import lombok.*;

@Setter
@Getter
public class CurrencyDetailsDTO {

    private String currencyCode;
    private String currencyName;
    private short isoNumericCode;
    private String countryName;
    private String countryCode;

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
        this.currencyCode = currencyConfigurationEntity.getCurrencyCode();
        this.currencyName = currencyConfigurationEntity.getCurrencyName();
        this.isoNumericCode = currencyConfigurationEntity.getIsoNumericCode();
        this.countryCode = currencyConfigurationEntity.getCountryConfigurationEntity().getCountryCode();
        this.countryName = currencyConfigurationEntity.getCountryConfigurationEntity().getCountryName();
        this.locale = currencyConfigurationEntity.getCurrencyDetailConfigurationEntity().getLocale();
        this.alternateCurrencyCode = currencyConfigurationEntity.getCurrencyDetailConfigurationEntity().getAlternateCurrencyCode();
        this.currencySymbol = currencyConfigurationEntity.getCurrencyDetailConfigurationEntity().getCurrencySymbol();
        this.currencyDescription = currencyConfigurationEntity.getCurrencyDetailConfigurationEntity().getCurrencyDescription();
        this.currencyCategory = currencyConfigurationEntity.getCurrencyDetailConfigurationEntity().getCurrencyCategory();
        this.decimals = currencyConfigurationEntity.getCurrencyDetailConfigurationEntity().getDecimals();
        this.dayDivisor = currencyConfigurationEntity.getCurrencyDetailConfigurationEntity().getDayDivisor();
        this.spotDays = currencyConfigurationEntity.getCurrencyDetailConfigurationEntity().getSpotDays();
        this.fxNettingDays = currencyConfigurationEntity.getCurrencyDetailConfigurationEntity().getFxNettingDays();
        this.settlementDays = currencyConfigurationEntity.getCurrencyDetailConfigurationEntity().getSettlementDays();
        this.calenderId = currencyConfigurationEntity.getCurrencyDetailConfigurationEntity().getCalenderId();
        this.beforeFormattingFlag = currencyConfigurationEntity.getCurrencySymbolConfigurationEntity().isBeforeFormattingFlag();
        this.afterFormattingFlag = currencyConfigurationEntity.getCurrencySymbolConfigurationEntity().isAfterFormattingFlag();
        this.includeSpaceFormattingFlag = currencyConfigurationEntity.getCurrencySymbolConfigurationEntity().isIncludeSpaceFormattingFlag();
        this.days = currencyConfigurationEntity.getCurrencyCutOffEntity().getDays();
        this.hours = currencyConfigurationEntity.getCurrencyCutOffEntity().getHours();
        this.minutes = currencyConfigurationEntity.getCurrencyCutOffEntity().getMinutes();
        this.minutes = currencyConfigurationEntity.getCurrencyCutOffEntity().getDays();
        this.enableMt101Remit = currencyConfigurationEntity.getCurrencyCutOffEntity().isEnableMt101Remit();
        this.enableMt103Stp = currencyConfigurationEntity.getCurrencyCutOffEntity().isEnableMt103Stp();
        this.indexFlag = currencyConfigurationEntity.getCurrencyCutOffEntity().isIndexFlag();
        this.enableMt202Cov = currencyConfigurationEntity.getCurrencyCutOffEntity().isEnableMt202Cov();
        this.clsCurrencyFlag = currencyConfigurationEntity.getCurrencyCutOffEntity().isClsCurrencyFlag();
        this.validateTag50fFlag = currencyConfigurationEntity.getCurrencyCutOffEntity().isValidateTag50fFlag();
        this.preferredHoliday1 = currencyConfigurationEntity.getCurrencyCutOffEntity().getPreferredHoliday1();
        this.preferredHoliday2 = currencyConfigurationEntity.getCurrencyCutOffEntity().getPreferredHoliday2();
        this.preferredHoliday3 = currencyConfigurationEntity.getCurrencyCutOffEntity().getPreferredHoliday3();
        this.roundingRule = currencyConfigurationEntity.getRoundingRuleConfigurationEntity().getRoundingRule();
        this.roundingUnit = currencyConfigurationEntity.getRoundingRuleConfigurationEntity().getRoundingUnit();
        this.amountFormatMaskPtt = currencyConfigurationEntity.getRoundingRuleConfigurationEntity().getAmountFormatMaskPtt();
        this.euroTransactionCurrency = currencyConfigurationEntity.getRoundingRuleConfigurationEntity().isEuroTransactionCurrency();
        this.inLegCurrency = currencyConfigurationEntity.getRoundingRuleConfigurationEntity().getInLegCurrency();
        this.outLegCurrency = currencyConfigurationEntity.getRoundingRuleConfigurationEntity().getOutLegCurrency();
        this.euroCloseFlag = currencyConfigurationEntity.getRoundingRuleConfigurationEntity().isEuroCloseFlag();
        this.creditExchangeLimit = currencyConfigurationEntity.getRoundingRuleConfigurationEntity().getCreditExchangeLimit();
        this.debitExchangeLimit = currencyConfigurationEntity.getRoundingRuleConfigurationEntity().getDebitExchangeLimit();
        this.extraFieldName = currencyConfigurationEntity.getRoundingRuleConfigurationEntity().getExtraFieldName();
        this.extraFiledvalue = currencyConfigurationEntity.getRoundingRuleConfigurationEntity().getExtraFiledvalue();
        this.currencyCountryCode = currencyConfigurationEntity.getRoundingRuleConfigurationEntity().getCurrencyCountryCode();
        this.currencyCountryName = currencyConfigurationEntity.getRoundingRuleConfigurationEntity().getCurrencyCountryName();
    }

}
