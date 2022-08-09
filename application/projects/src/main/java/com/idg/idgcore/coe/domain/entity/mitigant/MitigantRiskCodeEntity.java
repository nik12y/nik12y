package com.idg.idgcore.coe.domain.entity.mitigant;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_MITIGANT_RISK_CODE_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public class MitigantRiskCodeEntity extends AbstractAuditableDomainEntity implements Serializable {

    @Id
    @Column(name = "mitigant_risk_code_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mitigantRiskCodeId;
    private String riskCode;
    private String riskName;

}
