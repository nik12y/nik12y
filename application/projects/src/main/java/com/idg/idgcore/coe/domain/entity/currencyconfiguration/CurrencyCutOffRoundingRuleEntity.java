package com.idg.idgcore.coe.domain.entity.currencyconfiguration;

import com.idg.idgcore.domain.*;
import lombok.*;

import javax.persistence.*;
import java.io.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "idgc_mcy_cnfg_cut_off_rounding_rules")
public class CurrencyCutOffRoundingRuleEntity extends AbstractAuditableDomainEntity
        implements Serializable
{

    @Id
    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "days")
    private short days;

    @Column(name = "hours")
    private short hours;

    @Column(name = "minutes")
    private short minutes;

    @Column(name = "enable_mt_103_remit")
    private boolean enableMt101Remit;

    @Column(name = "enable_mt_103_stp")
    private boolean enableMt103Stp;

    @Column(name = "index_flag")
    private boolean indexFlag;

    @Column(name = "enable_mt_202cov")
    private boolean enableMt202Cov;

    @Column(name = "cls_currency_flag")
    private boolean clsCurrencyFlag;

    @Column(name = "validate_tag_50f_flag")
    private boolean validateTag50fFlag;

    @Column(name = "preferred_holiday_1")
    private String preferredHoliday1;

    @Column(name = "preferred_holiday_2")
    private String preferredHoliday2;

    @Column(name = "preferred_holiday_3")
    private String preferredHoliday3;

    @Column(name = "rounding_rule")
    private String roundingRule;

    @Column(name = "rounding_unit")
    private short roundingUnit;

    @Column(name = "amt_formt_mask_ptt")
    private String amountFormatMaskPtt;

    @Column(name = "eur_trans_curr")
    private boolean euroTransactionCurrency;

    @Column(name = "in_leg_currency")
    private String inLegCurrency;

    @Column(name = "out_leg_currency")
    private String outLegCurrency;

    @Column(name = "euro_close_flag")
    private boolean euroCloseFlag;

    @Column(name = "credit_exchange_limit")
    private float creditExchangeLimit;

    @Column(name = "debit_exchange_limit")
    private float debitExchangeLimit;

    @Column(name = "extra_field_name")
    private String extraFieldName;

    @Column(name = "extra_field_value")
    private String extraFiledvalue;

    @Column(name = "currency_country_code")
    private String currencyCountryCode;

    @Column(name = "currency_country_name")
    private String currencyCountryName;

}
