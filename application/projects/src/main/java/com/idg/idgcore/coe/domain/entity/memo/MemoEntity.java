package com.idg.idgcore.coe.domain.entity.memo;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "IDGC_COE_MEMO")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
public class MemoEntity extends AbstractAuditableDomainEntity
        implements Serializable
{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private String referenceNo;
    private String moduleId;
    private String taskCode;
    private String taskIdentifier;
    private Integer recordVersion;
    private String memoComments;
    private String status;
}
