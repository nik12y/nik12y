package com.idg.idgcore.coe.domain.entity.module;

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
@Table (name = "IDGC_COE_MODULE_CODE_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@IdClass (ModuleEntityKey.class)
@JsonIgnoreProperties (ignoreUnknown = true)
public class ModuleEntity extends AbstractAuditableDomainEntity implements Serializable
{
    @Id
    private String moduleCode;
    private String moduleName;
    private Integer bankCode;
    private Integer moduleUsers;
    private Integer moduleCurrentUser;
    private char isLicensed;
    private char isPurgeAvailable;
    private char isUdf;
    private char isInstalled;

    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;

}
