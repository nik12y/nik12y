package com.idg.idgcore.coe.domain.entity.bank;


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
@Table(name = "IDGC_COE_BANK_CODES_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(BankEntityKey.class)
public class BankEntity extends AbstractAuditableDomainEntity
        implements Serializable {

    @Id
    @Column(name = "bank_code")
    private String bankCode;
    @Column(name = "bank_code_regulatory")
    private String bankCodeRegulatory;
    @Column(name = "bank_name")
    private String bankName;


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
