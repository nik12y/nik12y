package com.idg.idgcore.coe.domain.entity.currencyratetype;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="IDGC_MCY_CNFG_EXCHG_RATE_TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(CurrencyRateTypeEntityKey.class)
public class CurrencyRateTypeEntity extends AbstractAuditableDomainEntity implements Serializable{

    @Id
    @Column(name ="currency_rate_type")
    private String currencyRateType;
    @Column(name = "description")
    private String description;
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


