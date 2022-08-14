package com.idg.idgcore.coe.domain.entity.bankgroup;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_GROUP_BANKING_CODES_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(GroupBankingEntityKey.class)
public class GroupBankingEntity extends AbstractAuditableDomainEntity implements Serializable {

    @Id
    @Column(name = "bank_group_code")
    private String bankGroupCode;
    @Column(name = "bank_group_name")
    private String bankGroupName;
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
