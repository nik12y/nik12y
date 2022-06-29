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
@Table (name = "IDGC_COE_MUTATION_STAGE_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@TypeDef (name = "jsonb", typeClass = JsonBinaryType.class)
public class MutationEntity
    extends AbstractAuditDomainEntity
    implements Serializable
{
    @Id
    private String taskIdentifier;
    private String taskCode;
    @Type (type = "jsonb")
    @Column (columnDefinition = "jsonb")
    private Payload payload;
    private String status;
    private String authorized;
    private Integer recordVersion;
    private String action;
    private String lastConfigurationAction;

}
