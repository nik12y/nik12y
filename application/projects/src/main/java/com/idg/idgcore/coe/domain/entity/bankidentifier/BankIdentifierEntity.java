package com.idg.idgcore.coe.domain.entity.bankidentifier;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_BANK_IDENTIFIER_CODE_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(BankIdentifierEntityKey.class)
public class BankIdentifierEntity  extends AbstractAuditableDomainEntity implements Serializable {

    @Id
    @Column(name = "bank_identifier_code")
    private String bankIdentifierCode;
    @Column(name = "bank_identifier_code_name")
    private String bankIdentifierCodeName;
    @Column(name = "bank_address1")
    private String bankAddress1;
    @Column(name = "bank_address2")
    private String bankAddress2;
    @Column(name = "bank_address3")
    private String bankAddress3;
    @Column(name = "bank_address4")
    private String bankAddress4;
    @Column(name = "internal_address")
    private String internalAddress;
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
