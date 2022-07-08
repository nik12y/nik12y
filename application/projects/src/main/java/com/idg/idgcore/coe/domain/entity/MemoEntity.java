package com.idg.idgcore.coe.domain.entity;

import com.idg.idgcore.app.*;
import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "IDGC_COE_MEMO")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@IdClass (MemoEntityKey.class)
public class MemoEntity extends AbstractAuditableDomainEntity
        implements Serializable
{
    @Id
    private String referenceNo;
    private String moduleId;
    private String taskCode;
    private String taskIdentifier;
    private Integer recordVersion;
    private String comments;
    private String status;
}
