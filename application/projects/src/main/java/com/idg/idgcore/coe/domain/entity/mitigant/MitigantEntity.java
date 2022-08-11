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
import javax.persistence.IdClass;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
    @Column(name = "mitigant_code")
    private String mitigantCode;
    @Column(name = "mitigant_code_name")
    private String mitigantCodeName;
    @Column(name = "mitigant_code_desc")
    private String mitigantCodeDesc;
    @Column(name = "is_allow_modification")
    private char isAllowModification;
    @Column(name = "is_actionable")
    private char isActionable;

    @Column(name = "life_cycle_id")
    private String lifeCycleId;
    @Column(name = "reference_no")
    private String referenceNo;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "mitigant_code")
    private List<MitigantRiskCodeEntity> mitigantRiskCode;

    @Column(name = "record_status")
    private String status;
    @Column(name = "record_version")
    private Integer recordVersion;
    @Column(name = "is_authorized")
    private String authorized;
    @Column(name = "last_configuration_action")
    private String lastConfigurationAction;

}
