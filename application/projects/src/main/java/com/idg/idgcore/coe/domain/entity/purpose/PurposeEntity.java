package com.idg.idgcore.coe.domain.entity.purpose;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_PURPOSE_CODE_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(PurposeEntityKey.class)
public class PurposeEntity extends AbstractAuditableDomainEntity implements Serializable {

    @Id
    @Column(name = "purpose_code")
    private String purposeCode;
    @Column(name = "purpose_name")
    private String purposeName;
    @Column(name = "purpose_description")
    private String purposeDescription;
    @Column(name = "purpose_type")
    private String purposeType;
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
