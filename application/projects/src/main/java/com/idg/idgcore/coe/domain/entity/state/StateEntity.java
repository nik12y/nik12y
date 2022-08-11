package com.idg.idgcore.coe.domain.entity.state;


import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.InheritanceType;
import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.IdClass;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_STATE_CODE_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(StateEntityKey.class)
public class StateEntity extends AbstractAuditableDomainEntity
        implements Serializable {
    @Id
    @Column(name="state_code")
    private String stateCode;

    @Column(name="state_name")
    private String stateName;

    @Column(name = "country_code", updatable = false)
    private String countryCode;

    @Column(name="life_cycle_id")
    private String lifeCycleId;

    @Column(name="reference_no")
    private String referenceNo;

    @Column(name="record_status")
    private String status;

    @Column(name="record_version")
    private Integer recordVersion;

    @Column(name="is_authorized")
    private String authorized;

    @Column(name="last_configuration_action")
    private String lastConfigurationAction;
}