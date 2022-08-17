package com.idg.idgcore.coe.domain.entity.branchtype;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Entity
@Table(name = "IDGC_COE_BRANCH_TYPE_CNFG")

@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(BranchTypeEntityKey.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchTypeEntity extends AbstractAuditableDomainEntity
        implements Serializable {


    @Id
    @Column(name = "branch_type_code")
    private String branchTypeCode;

    @Column(name = "branch_type_name")
    private String branchTypeName;

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





