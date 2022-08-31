package com.idg.idgcore.coe.domain.entity.currencyconfiguration;

import com.idg.idgcore.domain.*;
import lombok.*;

import javax.persistence.*;
import java.io.*;

@Entity
@Setter
@Table(name = "idgc_mcy_cnfg_currency_dtls")
@SecondaryTable(name = "idgc_mcy_cnfg_cut_off_rounding_rules", foreignKey = @ForeignKey(name = "country_code"))
public class CurrencyConfigurationEntity extends AbstractAuditableDomainEntity
        implements Serializable
{
    @Id
    @Getter
    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "currency_name")
    private String currencyName;

    @Column(name = "iso_numeric_code")
    private short isoNumericCode;

    @Column(name = "locale")
    private String locale;

    @Column(name = "alternate_currency_code")
    private String alternateCurrencyCode;

    @Column(name = "currency_symbol")
    private String currencySymbol;

    @Column(name = "currency_desc")
    private String currencyDescription;

    @Column(name = "currency_category")
    private String currencyCategory;

    @Column(name = "decimals")
    private short decimals;

    @Column(name = "day_divisor")
    private String dayDivisor;

    @Column(name = "spot_days")
    private short spotDays;

    @Column(name = "fx_netting_days")
    private short fxNettingDays;

    @Column(name = "settlement_days")
    private short settlementDays;

    @Column(name = "calender_code")
    private String calenderId;

    @Column(name = "before_formatting")
    private boolean beforeFormattingFlag;

    @Column(name = "after_formatting")
    private boolean afterFormattingFlag;

    @Column(name = "include_space_formatting")
    private boolean includeSpaceFormattingFlag;

    @Column(name="life_cycle_id")
    private String lifeCycleId;

    @Column (name="reference_no")
    private String referenceNo;

    @Column (name="record_status")
    private String status;

    @Column (name="record_version")
    private Integer recordVersion;

    @Column (name="is_authorized")
    private String authorized;

    @Column (name="last_configuration_action")
    private String lastConfigurationAction;

    @Column(name = "days" , table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private short days;

    @Column(name = "hours" , table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private short hours;

    @Column(name = "minutes" , table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private short minutes;

    @Column(name = "enable_mt_103_remit" , table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private boolean enableMt101Remit;

    @Column(name = "enable_mt_103_stp" , table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private boolean enableMt103Stp;

    @Column(name = "index_flag" , table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private boolean indexFlag;

    @Column(name = "enable_mt_202cov" , table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private boolean enableMt202Cov;

    @Column(name = "cls_currency_flag" , table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private boolean clsCurrencyFlag;

    @Column(name = "validate_tag_50f_flag" , table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private boolean validateTag50fFlag;

    @Column(name = "preferred_holiday_1" , table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private String preferredHoliday1;

    @Column(name = "preferred_holiday_2" , table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private String preferredHoliday2;

    @Column(name = "preferred_holiday_3" , table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private String preferredHoliday3;

    @Column(name = "rounding_rule" , table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private String roundingRule;

    @Column(name = "rounding_unit", table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private short roundingUnit;

    @Column(name = "amt_formt_mask_ptt", table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private String amountFormatMaskPtt;

    @Column(name = "eur_trans_curr", table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private boolean euroTransactionCurrency;

    @Column(name = "in_leg_currency", table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private String inLegCurrency;

    @Column(name = "out_leg_currency", table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private String outLegCurrency;

    @Column(name = "euro_close_flag", table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private boolean euroCloseFlag;

    @Column(name = "credit_exchange_limit", table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private float creditExchangeLimit;

    @Column(name = "debit_exchange_limit", table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private float debitExchangeLimit;

    @Column(name = "extra_field_name", table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private String extraFieldName;

    @Column(name = "extra_field_value", table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private String extraFiledvalue;

    @Column(name = "currency_country_code", table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private String currencyCountryCode;

    @Column(name = "currency_country_name", table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private String currencyCountryName;

    @Column(name="life_cycle_id" , table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private String tb2LifeCycleId;

    @Column (name="reference_no" , table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private String tb2ReferenceNo;

    @Column (name="record_status" , table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private String tb2Status;

    @Column (name="record_version" , table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private Integer tb2RecordVersion;

    @Column (name="is_authorized" , table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private String tb2Authorized;

    @Column (name="last_configuration_action" , table = "idgc_mcy_cnfg_cut_off_rounding_rules")
    private String tb2LastConfigurationAction;
}
