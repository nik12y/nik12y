package com.idg.idgcore.coe.domain.entity.mutation;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "IDGC_COE_MUTATION_STAGE_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@TypeDef (name = "json", typeClass = JsonType.class)
@IdClass (MutationEntityKey.class)
public class MutationEntity
    extends AbstractAuditableDomainEntity
    implements Serializable
{
    @Id
    private String taskIdentifier;
    @Id
    private String taskCode;
    @Type (type = "json")
    @Column (columnDefinition = "clob")
    private Payload payload;
    private String status;
    private String authorized;
    private Integer recordVersion;
    private String action;
    private String lastConfigurationAction;
}
