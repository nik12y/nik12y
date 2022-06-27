package com.idg.idgcore.domain.entity;

import com.fasterxml.jackson.annotation.*;
import com.idg.idgcore.domain.AbstractAuditDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table (name = "IDGC_COE_COUNTRY_CODE_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@IdClass(CountryEntityKey.class)
@JsonIgnoreProperties (ignoreUnknown = true)
public class CountryEntity extends AbstractAuditDomainEntity
        implements Serializable
{
    @Id
    private String countryCode;
    private String countryName;
    private Integer numericCountryCode;
    private Integer alternateCountryCode;
    private String regionCode;
    private String limitCurrency;
    private Integer overallLimit;
    private boolean ibanRequired;
    private boolean euMember;
    private boolean clearingCodeBicPlus;
    private boolean generateMt205;
    private boolean defaultClearingNetwork;

    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;

}
