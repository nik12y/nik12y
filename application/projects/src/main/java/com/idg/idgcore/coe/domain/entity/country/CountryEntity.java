package com.idg.idgcore.coe.domain.entity.country;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "IDGC_COE_COUNTRY_CODE_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@IdClass (CountryEntityKey.class)
public class CountryEntity extends AbstractAuditableDomainEntity implements Serializable {
    @Id
    @Column (name="country_code")
    private String countryCode;
    @Column (name="country_name")
    private String countryName;
    @Column (name="country_code_machine")
    private String countryCodeMachine;
    @Column (name="country_code_alternate")
    private String countryCodeAlternate;
    @Column (name="region_code")
    private String regionCode;
    @Column (name="currency_limit")
    private String currencyLimit;
    @Column (name="overall_limit")
    private Float overallLimit;
    @Column (name="is_iban")
    private char isIban;
    @Column (name="is_eu_member")
    private char isEuMember;
    @Column (name="is_bic_code")
    private char isBicCode;
    @Column (name="is_mt_generate")
    private char isMtGenerate;
    @Column (name="is_clearing_network")
    private char isClearingNetwork;
    @Column (name="record_status")
    private String status;
    @Column (name="record_version")
    private Integer recordVersion;
    @Column (name="is_authorized")
    private String authorized;
    @Column (name="last_configuration_action")
    private String lastConfigurationAction;
    @Column (name="life_cycle_id")
    private String lifeCycleId;
    @Column (name="reference_no")
    private String referenceNo;
}
