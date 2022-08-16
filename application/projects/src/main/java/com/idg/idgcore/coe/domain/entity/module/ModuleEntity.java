package com.idg.idgcore.coe.domain.entity.module;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @Column(name="module_code")
    private String moduleCode;
    @Column(name="module_name")
    private String moduleName;
    @Column(name="bank_code")
    private String bankCode;
    @Column(name="module_users")
    private Integer moduleUsers;
    @Column(name="module_current_user")
    private Integer moduleCurrentUser;
    @Column(name="is_licensed")
    private char isLicensed;
    @Column(name="is_purge_available")
    private char isPurgeAvailable;
    @Column(name="is_udf")
    private char isUdf;
    @Column(name="is_installed")
    private char isInstalled;
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

}
