package com.idg.idgcore.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idg.idgcore.domain.AbstractAuditableDomainEntity;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Entity
@Table (name = "IDGC_COE_COUNTRY_CODE_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@IdClass (CountryEntityKey.class)
@JsonIgnoreProperties (ignoreUnknown = true)
public class CountryEntity extends AbstractAuditableDomainEntity implements Serializable {
    @Id
    private String countryCode;
    private String countryName;
    private Integer countryCodeMachine;
    private String countryCodeAlternate;
    private String regionCode;
    private String currencyLimit;
    private Float overallLimit;
    private char isIban;
    private char isEuMember;
    private char isBicCode;
    private char isMtGenerate;
    private char isClearingNetwork;
    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;

}
