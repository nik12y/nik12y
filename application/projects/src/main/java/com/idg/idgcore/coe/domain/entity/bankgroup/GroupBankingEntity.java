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

    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;

}
