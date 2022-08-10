package com.idg.idgcore.coe.domain.entity.currencyconfiguration;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rounding_rules_config")
public class RoundingRuleDetailsEntity {

    @Id
    @Column(name = "currency_code")
    private String currencyCode;

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

    @Column(name = "country_code")
    private String currencyCountryCode;

    @Column(name = "country_name")
    private String currencyCountryName;

}
