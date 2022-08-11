package com.idg.idgcore.coe.domain.entity.branchparameter;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

//@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "IDGC_COE_BRANCH_PARAMETERS_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@IdClass (BranchParameterEntityKey.class)
public class BranchParameterEntity extends AbstractAuditableDomainEntity implements Serializable {
    @Id
    @Column (name="branch_code")
    private String branchCode;
    @Column (name="branch_name")
    private String branchName;
    @Column (name="bank_code")
    private String bankCode;
    @Column (name="branch_concise_name")
    private String branchConciseName;
    @Column (name="is_branch_available_status")
    private char isBranchAvailableStatus;
    @Column (name="bank_identifier_code")
    private String bankIdentifierCode;
    @Column (name="branch_type")
    private String branchType;
    @Column (name="life_cycle_id")
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

    @Embedded
    private BranchParameterAddressEntity branchParameterAddressEntity;
    @Embedded
    private BranchParameterContactInfoEntity branchParameterContactInfoEntity;
    @Embedded
    private BranchParameterGeneralEntity branchParameterGeneralEntity;
    @Embedded
    private BranchParameterClearingEntity branchParameterClearingEntity;
    @Embedded
    private BranchParameterAtmEntity branchParameterAtmEntity;
    @Embedded
    private BranchParameterTimezoneEntity branchParameterTimezoneEntity;
    @Embedded
    private BranchParameterGlobalInterdictEntity branchParameterGlobalInterdictEntity;
    @Embedded
    private BranchParameterTranDuplicateEntity branchParameterTranDuplicateEntity;
    @Embedded
    private BranchParameterLocalCurrencyEntity branchParameterLocalCurrencyEntity;
    @Embedded
    private BranchParameterMiscellaneousEntity branchParameterMiscellaneousEntity;

}
