package com.idg.idgcore.coe.domain.entity.mitigant;

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
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @Column(name = "risk_code")
    private String riskCode;
    @Column(name = "risk_name")
    private String riskName;

}
