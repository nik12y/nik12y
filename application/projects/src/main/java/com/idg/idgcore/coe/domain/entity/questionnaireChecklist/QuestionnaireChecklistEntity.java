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
    @Column(name="question_checklist_id")
    private String questionChecklistId;
    @Column(name="question_checklist_name")
    private String questionChecklistName;
    @Column(name="question_category")
    private String questionCategory;
    @Column(name="effective_date")
    private Date effectiveDate;
    @Column(name="life_cycle_id")
    private String lifeCycleId;
    @Column(name="reference_no")
    private String referenceNo;
    @Column(name = "record_status")
    private String status;
    @Column(name = "record_version")
    private Integer recordVersion;
    @Column(name = "is_authorized")
    private String authorized;
    @Column(name = "last_configuration_action")
    private String lastConfigurationAction;


    @Embedded
    private QuestionnaireChecklistDetailsCategoryEntity questionnaireChecklistDetailsCategory;
    @Embedded
    private QuestionnaireChecklistDisplayEntity checklistDisplayEntity;

}



