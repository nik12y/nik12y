package com.idg.idgcore.coe.domain.entity.currencypair;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "idgc_mcy_cnfg_currency_pair")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public class CurrencyPairEntity extends AbstractAuditableDomainEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pair_id")
    private Integer pairId;

    @Column(name = "currency_1")
    private String currency1;

    @Column(name = "currency_1_name")
    private String currency1Name;

    @Column(name = "currency_2")
    private String currency2;

    @Column(name = "currency_2_name")
    private String currency2Name;

    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "entity_code")
    private String entityCode;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "through_currency")
    private char throughCurrency;

    @Column(name = "through_currency_code")
    private String throughCurrencyCode;

    @Column(name = "through_currency_description")
    private String throughCurrencyDescription;

    @Column(name = "no_of_units")
    private Integer noofunits;

    @Column(name = "point_multiplier")
    private float pointMultiplier;

    @Column(name = "quotation_methods")
    private char quotationMethods;

    @Column(name = "spread_definition")
    private char spreadDefinition;

    @Column(name = "life_cycle_id")
    private String lifeCycleId;

    @Column(name = "reference_no")
    private String referenceNo;

    @Column(name = "record_version")
    private Integer recordVersion;

    @Column(name = "record_status")
    private String status;

    @Column(name = "is_authorized")
    private String authorized;

    @Column(name = "last_configuration_action")
    private String lastConfigurationAction;

}
