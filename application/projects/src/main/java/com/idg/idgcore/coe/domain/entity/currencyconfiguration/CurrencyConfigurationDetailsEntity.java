package com.idg.idgcore.coe.domain.entity.currencyconfiguration;

import com.idg.idgcore.domain.*;
import lombok.*;

import javax.persistence.*;
import java.io.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "idgc_mcy_cnfg_currency_dtls")
public class CurrencyConfigurationDetailsEntity extends AbstractAuditableDomainEntity
        implements Serializable
{

    @Id
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

    @OneToOne
    @JoinColumn(name = "country_code")
    private CurrencyCutOffRoundingRuleEntity currencyCutOffRoundingRuleEntity;

}
