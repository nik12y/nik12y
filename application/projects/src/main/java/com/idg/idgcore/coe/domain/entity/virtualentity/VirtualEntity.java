package com.idg.idgcore.coe.domain.entity.virtualentity;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_VE_CODE_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(VirtualEntityKey.class)
public class VirtualEntity extends AbstractAuditableDomainEntity implements Serializable {

    @Id
    @Column(name = "entity_code")
    private String entityCode;

    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "parent_entity_type")
    private String parentEntityType;

    @Column(name = "parent_entity_code")
    private String parentEntityCode;

    @Column(name = "is_default")
    private char isDefault;

    @Column(name = "effective_date")
    private Date effectiveDate;

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
