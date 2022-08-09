package com.idg.idgcore.coe.domain.entity.mitigant;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_MITIGANT_CODE_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(MitigantEntityKey.class)
public class MitigantEntity extends AbstractAuditableDomainEntity implements Serializable {

    @Id
    private String mitigantCode;
    private String mitigantCodeName;
    private String mitigantCodeDesc;
    private char isAllowModification;
    private char isActionable;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "mitigant_code")
    private List<MitigantRiskCodeEntity> mitigantRiskCode;

    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;

}
