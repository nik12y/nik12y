package com.idg.idgcore.coe.dto.questionnaireChecklist;

import com.fasterxml.jackson.annotation.*;
import com.idg.idgcore.coe.domain.entity.questionnaireChecklist.*;
import com.idg.idgcore.coe.dto.base.*;
import lombok.*;
import lombok.experimental.*;

import java.util.*;

import static com.idg.idgcore.coe.common.Constants.*;

@ToString (callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude (JsonInclude.Include.NON_NULL)
public class QuestionnaireChecklistDTO extends CoreEngineBaseDTO {

    private String questionChecklistId;
    private String questionChecklistName;
    private String questionCategory;
    private String effectiveDate;

    private QuestionnaireChecklistDetailsCategoryDTO questionnaireChecklistDetailsCategory;
    private QuestionnaireChecklistDisplayDTO questionnaireChecklistDisplay;

    @Override
    public String getTaskCode () {
        return QUE_CHECKLIST;
    }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(QUE_CHECKLIST);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getQuestionChecklistId();
    }
}
