package com.idg.idgcore.coe.domain.entity.questionnaireChecklist;

import com.idg.idgcore.domain.*;
import lombok.*;

import javax.persistence.*;
import java.io.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "IDGC_COE_QUESTION_CHECKLIST_DETAILS_CNFG")
@ToString
public class QuestionnaireChecklistDetailsCategoryEntity extends AbstractAuditableDomainEntity
        implements Serializable
{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "question_checklist_details_id")
    private Long questionChecklistDetailsId;
    @Column (name = "question_checklist_id")
    private String questionChecklistId;

    private String questionCategoryId;

}
