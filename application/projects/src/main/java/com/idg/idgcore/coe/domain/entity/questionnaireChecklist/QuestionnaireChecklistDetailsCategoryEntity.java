package com.idg.idgcore.coe.domain.entity.questionnaireChecklist;

import lombok.*;

import javax.persistence.*;
import java.io.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QuestionnaireChecklistDetailsCategoryEntity implements Serializable {

    private String questionCategoryId;

}
