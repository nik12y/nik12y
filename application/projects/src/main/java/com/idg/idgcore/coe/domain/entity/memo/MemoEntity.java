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
public class MemoEntity extends AbstractAuditableDomainEntity implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "memo_id")
    private Integer id;
    @Column (name = "module_id")
    private String moduleId;
    @Column (name = "task_code")
    private String taskCode;
    @Column (name = "task_identifier")
    private String taskIdentifier;
    @Column (name = "memo_comments")
    private String memoComments;
    @Column (name = "record_status")
    private String status;
    @Column (name = "record_version")
    private Integer recordVersion;
    @Column (name = "life_cycle_id")
    private String lifeCycleId;
    @Column (name = "reference_no")
    private String referenceNo;
}
