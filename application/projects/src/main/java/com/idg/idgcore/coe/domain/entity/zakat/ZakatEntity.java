package com.idg.idgcore.coe.domain.entity.zakat;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_ZAKAT_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(ZakatEntityKey.class)
public class ZakatEntity extends AbstractAuditableDomainEntity implements Serializable {
    @Id
    @Column(name = "zakat_year")
    private Integer zakatYear;
    @Column(name = "start_date_of_ramadan")
    private Date startDateOfRamadan;
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