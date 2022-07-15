package com.idg.idgcore.coe.domain.entity.audit;

import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
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
@TypeDef (name = "jsonb", typeClass = JsonBinaryType.class)
public class AuditHistoryEntity extends AbstractAuditableDomainEntity
        implements Serializable
{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private String taskCode;
    private String taskIdentifier;
    @Type (type = "jsonb")
    @Column (columnDefinition = "jsonb")
    private Payload payload;
    private String status;
    private String authorized;
    private Integer recordVersion;
    private String referenceNo;
    private String traceInfo;
    private String action;
    private String lastConfigurationAction;
}
