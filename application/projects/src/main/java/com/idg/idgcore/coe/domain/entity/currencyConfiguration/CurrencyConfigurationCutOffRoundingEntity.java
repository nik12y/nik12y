package com.idg.idgcore.coe.domain.entity.currencyConfiguration;

import com.idg.idgcore.domain.*;
import lombok.*;

import javax.persistence.*;
import java.io.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "idgc_mcy_cnfg_cut_off_rounding_rules")
@Inheritance(strategy = InheritanceType.JOINED)
public class CurrencyConfigurationCutOffRoundingEntity extends AbstractAuditableDomainEntity
        implements Serializable
{

    @Id
    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "days")
    private short days;

    @Column(name = "hours")
    private short hours;

    @Column(name = "minutes")
    private short minutes;

    @Column(name = "enable_mt_103_remit")
    private char enableMt101Remit;

    @Column(name = "enable_mt_103_stp")
    private char enableMt103Stp;

    @Column(name = "index_flag")
    private char indexFlag;

    @Column(name = "enable_mt_202cov")
    private char enableMt202Cov;

    @Column(name = "cls_currency_flag")
    private char clsCurrencyFlag;

    @Column(name = "validate_tag_50f_flag")
    private char validateTag50fFlag;

    @Column(name = "preferred_holiday_1")
    private String preferredHoliday1;

    @Column(name = "preferred_holiday_2")
    private String preferredHoliday2;

    @Column(name = "preferred_holiday_3")
    private String preferredHoliday3;

    @Column(name = "rounding_rule")
    private String roundingRule;

    @Column(name = "rounding_unit")
    private float roundingUnit;

    @Column(name = "amt_formt_mask_ptt")
    private String amountFormatMaskPtt;

    @Column(name = "eur_trans_curr")
    private char euroTransactionCurrency;

    @Column(name = "in_leg_currency")
    private String inLegCurrency;

    @Column(name = "out_leg_currency")
    private String outLegCurrency;

    @Column(name = "euro_close_flag")
    private char euroCloseFlag;

    @Column(name = "credit_exchange_limit")
    private float creditExchangeLimit;

    @Column(name = "debit_exchange_limit")
    private float debitExchangeLimit;

    @Column (name="record_status")
    private String status;

    @Column (name="record_version")
    private Integer recordVersion;

    @Column (name="is_authorized")
    private String authorized;

    @Column (name="last_configuration_action")
    private String lastConfigurationAction;


}
