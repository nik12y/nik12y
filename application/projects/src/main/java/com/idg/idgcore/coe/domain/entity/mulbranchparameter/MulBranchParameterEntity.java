package com.idg.idgcore.coe.domain.entity.mulbranchparameter;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="idgc_mcy_cnfg_branch_parameter")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass (MulBranchParameterEntityKey.class)
public class MulBranchParameterEntity extends AbstractAuditableDomainEntity implements Serializable{

    @Id
    @Column(name= "currency_code")
    private String currencyCode;

    @Id
    @Column(name= "entity_code")
    private String entityCode;

    @Id
    @Column(name= "entity_type")
    private String entityType;

    @Column(name= "entity_level")
    private String entityLevel;

    @Column(name= "entity_name")
    private String entityName;

    @Column(name= "currency_name")
    private String currencyName;

    @Column(name= "spot_days")
    private Integer spotDays;

    @Column(name= "generation_of_payment_message_prior_to_settlement_days")
    private Integer generationOfPaymentMessage;

    @Column(name= "generation_of_receive_messages")
    private Integer generationOfReceiveMessages;

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
