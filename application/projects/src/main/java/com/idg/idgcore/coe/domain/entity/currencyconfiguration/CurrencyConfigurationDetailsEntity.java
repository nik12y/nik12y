package com.idg.idgcore.coe.domain.entity.currencyconfiguration;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "currency_config")
public class CurrencyConfigurationDetailsEntity {

    @Id
    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "currency_name")
    private String currencyName;

    @Column(name = "iso_numeric_code")
    private short isoNumericCode;

    @OneToOne
    @JoinColumn(name = "currency_code")
    private CountryDetailsEntity countryConfigurationEntity;

    @OneToOne
    @JoinColumn(name = "currency_code")
    private CurrencyDetailEntity currencyDetailConfigurationEntity;

    @OneToOne
    @JoinColumn(name = "currency_code")
    private CurrencySymbolDetailsEntity currencySymbolConfigurationEntity;

    @OneToOne
    @JoinColumn(name = "currency_code")
    private CurrencyCutOffDetailsEntity currencyCutOffEntity;

    @OneToOne
    @JoinColumn(name = "currency_code")
    private RoundingRuleDetailsEntity roundingRuleConfigurationEntity;

}
