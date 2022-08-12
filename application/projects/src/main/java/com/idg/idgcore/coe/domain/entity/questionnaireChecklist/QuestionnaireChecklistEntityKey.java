package com.idg.idgcore.coe.domain.entity.questionnaireChecklist;

import com.idg.idgcore.domain.*;
import lombok.*;

import javax.persistence.*;
import java.io.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table (name = "IDGC_COE_QUESTION_CHECKLIST_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
public class QuestionnaireChecklistEntityKey extends AbstractDomainKey
        implements Serializable
{
    @Id
    private String questionChecklistId;


    @Override
    public String keyAsString() {
        return questionChecklistId;
    }
}
