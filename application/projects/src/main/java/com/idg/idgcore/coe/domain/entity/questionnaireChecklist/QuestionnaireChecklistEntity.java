package com.idg.idgcore.coe.domain.entity.questionnaireChecklist;

import com.idg.idgcore.coe.domain.entity.country.*;
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

    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;


    @OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn (name = "question_checklist_id")//questionChecklistId
    private List<QuestionnaireChecklistDetailsCategoryEntity> checklistDetailsCategoryEntities;

    @Embedded
    private QuestionnaireChecklistDisplayEntity checklistDisplayEntity;

}
