package com.idg.idgcore.domain.entity;

import com.idg.idgcore.domain.AbstractAuditDomainEntity;
import com.vladmihalcea.hibernate.type.json.*;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table (name = "IDGC_COE_AUDIT_HISTORY_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@TypeDef (name = "jsonb", typeClass = JsonBinaryType.class)
public class AuditHistoryEntity extends AbstractAuditDomainEntity
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
    private String recordVersion;
    private String referenceNo;
    private String traceInfo;
    private String action;
    private String lastConfigurationAction;


   /* private String createdBy;
    @JsonSerialize (using = LocalDateTimeSerializer.class)
    @JsonDeserialize (using = LocalDateTimeDeserializer.class)
    @JsonInclude (JsonInclude.Include.NON_NULL)
    private LocalDateTime creationTime;
    private String lastUpdatedBy;
    @JsonSerialize (using = LocalDateTimeSerializer.class)
    @JsonDeserialize (using = LocalDateTimeDeserializer.class)
    @JsonInclude (JsonInclude.Include.NON_NULL)
    private LocalDateTime lastUpdationTime;*/

}
