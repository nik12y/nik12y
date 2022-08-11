package com.idg.idgcore.coe.domain.entity.purging;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_PURGING_POLICY_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(PurgingEntityKey.class)
public class PurgingEntity extends AbstractAuditableDomainEntity implements Serializable {

    @Id
    @Column(name = "module_code")
    private String moduleCode;
    @Column(name = "tran_maintenance_status")
    private String tranMaintenanceStatus;
    @Column(name = "retention_period")
    private int retentionPeriod;
    @Column(name = "life_cycle_id")
    private String lifeCycleId;
    @Column(name = "reference_no")
    private String referenceNo;
    @Column(name = "record_status")
    private String status;
    @Column(name = "record_version")
    private Integer recordVersion;
    @Column(name = "is_authorized")
    private String authorized;
    @Column(name = "last_configuration_action")
    private String lastConfigurationAction;
}
