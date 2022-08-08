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
    @Column (name="task_identifier")
    private String taskIdentifier;
    @Id
    @Column (name="task_code")
    private String taskCode;
    @Type (type = "json")
    @Column (columnDefinition = "clob",name="json_payload")
    private Payload payload;
    @Column (name="reference_no")
    private String referenceNo;
    @Column (name="record_status")
    private String status;
    @Column (name="is_authorized")
    private String authorized;
    @Column (name="record_version")
    private Integer recordVersion;
    @Column (name="record_action")
    private String action;
    @Column (name="last_configuration_action")
    private String lastConfigurationAction;

}
