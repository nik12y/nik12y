package com.idg.idgcore.coe.domain.entity.currencyConfiguration;

import com.idg.idgcore.domain.*;
import lombok.*;

import javax.persistence.*;
import java.io.*;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "idgc_mcy_cnfg_currency_dtls")
@Inheritance(strategy = InheritanceType.JOINED)

public class CurrencyConfigurationEntity extends AbstractAuditableDomainEntity
        implements Serializable
{

    @Id
    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "currency_name")
    private String currencyName;

    @Column(name = "iso_numeric_code")
    private short isoNumericCode;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "country_name")
    private String countryName;

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

    @Column(name = "calender_type_id")
    private String calenderId;

    @Column(name = "before_formatting")
    private char beforeFormattingFlag;

    @Column(name = "after_formatting")
    private char afterFormattingFlag;

    @Column(name = "include_space_formatting")
    private char includeSpaceFormattingFlag;

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

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name = "currency_code")
    private CurrencyConfigurationCutOffRoundingEntity currencyConfigurationCutOffRoundingEntity;

    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_code",insertable = false, updatable = false)
    private List<CurrencyCountryMapEntity> currencyCountryMapEntity;

    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_code",insertable = false, updatable = false)
    private List<CurrencyExtraFieldMapEntity> currencyExtraFieldMapEntity;


}
