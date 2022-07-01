package com.idg.idgcore.domain.entity;

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
import javax.persistence.Column;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Entity
@Table (name = "IDGC_COE_MUTATION_STAGE_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@TypeDef (name = "jsonb", typeClass = JsonBinaryType.class)
public class MutationEntity
    extends AbstractAuditableDomainEntity
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
