package com.idg.idgcore.coe.dto.question;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

import static com.idg.idgcore.coe.common.Constants.QUESTION;


@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionDTO extends CoreEngineBaseDTO  {

    private String questionId;
    private String questionName;
    private String questionText;
    private String questionDescription;
    private String answerDisplayType;
    private String manualFactBased;
    private String answerValue;

    @Override
    public String getTaskCode() {
        return QUESTION;
    }

    @Override
    public void setTaskCode(String taskCode) {
        super.setTaskCode(QUESTION);
    }

    @Override
    public String getTaskIdentifier() {
        return this.getQuestionId();
    }
}
