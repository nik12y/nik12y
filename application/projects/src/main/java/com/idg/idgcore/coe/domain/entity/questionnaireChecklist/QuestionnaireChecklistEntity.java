package com.idg.idgcore.coe.domain.entity.questionnaireChecklist;

import com.idg.idgcore.domain.*;
import lombok.*;

import javax.persistence.*;
import java.io.*;
import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "IDGC_COE_QUESTION_CHECKLIST_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@IdClass (QuestionnaireChecklistEntityKey.class)
public class QuestionnaireChecklistEntity extends AbstractAuditableDomainEntity implements Serializable {

    @Id
    private String questionChecklistId;
    private String questionChecklistName;
    private String questionCategory;
    private Date effectiveDate;

    private String lifeCycleId;
    private String referenceNo;
    @Column(name = "record_status")
    private String status;
    @Column(name = "record_version")
    private Integer recordVersion;
    @Column(name = "is_authorized")
    private String authorized;
    private String lastConfigurationAction;


    @Embedded
    private QuestionnaireChecklistDetailsCategoryEntity questionnaireChecklistDetailsCategory;
    @Embedded
    private QuestionnaireChecklistDisplayEntity checklistDisplayEntity;

}



