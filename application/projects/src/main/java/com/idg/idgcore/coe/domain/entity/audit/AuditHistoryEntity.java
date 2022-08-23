package com.idg.idgcore.coe.domain.entity.audit;

import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Type;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "IDGC_COE_AUDIT_HISTORY_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@TypeDef (name = "json", typeClass = JsonType.class)
public class AuditHistoryEntity extends AbstractAuditableDomainEntity
        implements Serializable
{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name="audit_id")
    private Integer id;
    @Column (name="task_code")
    private String taskCode;
    @Column (name="task_identifier")
    private String taskIdentifier;
    @Type (type = "json")
    @Column (name="json_payload", columnDefinition = "clob")
    private Payload payload;
    @Column (name="record_status")
    private String status;
    @Column (name="is_authorized")
    private String authorized;
    @Column (name="record_version")
    private Integer recordVersion;
    @Column (name="life_cycle_id")
    private String lifeCycleId;
    @Column (name="reference_no")
    private String referenceNo;
    @Column (name="trace_info")
    private String traceInfo;
    @Column (name="record_action")
    private String action;
    @Column (name="last_configuration_action")
    private String lastConfigurationAction;
}
