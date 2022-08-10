package com.idg.idgcore.coe.domain.entity.currencyconfiguration;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "currency_dtls_config")
public class CurrencyDetailEntity {

    @Id
    @Column(name = "currency_code")
    private String currencyCode;

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
}
