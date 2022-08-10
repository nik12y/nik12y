package com.idg.idgcore.coe.domain.entity.branchSystem;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.IdClass;
import javax.persistence.Id;
import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_BRANCH_SYSTEM_DATE_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(BranchSystemDateEntityKey.class)
public class BranchSystemDateEntity extends AbstractAuditableDomainEntity implements Serializable {


    @Id
    @Column(name = "branch_code")
    private String branchCode;
    @Column(name = "current_working_date")
    private Date currentWorkingDate;
    @Column(name = "previous_working_date")
    private Date previousWorkingDate;
    @Column(name = "next_working_date")
    private Date nextWorkingDate;
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
