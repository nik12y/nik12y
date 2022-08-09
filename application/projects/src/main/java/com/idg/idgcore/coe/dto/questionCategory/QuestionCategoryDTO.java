package com.idg.idgcore.coe.dto.questionCategory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.util.List;

import static com.idg.idgcore.coe.common.Constants.QUESTION_CATEGORY;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionCategoryDTO extends CoreEngineBaseDTO {

    private String questionCategoryId;
    private String questionCategoryName;
    private String questionDisplay;
    private Boolean isShowQuestionCategoryName;
    private Boolean isEnableDocumentUpload;

    private List<QuestionCategoryDetailsDTO> questionCategoryDetails;

    @Override
    public String getTaskCode() {
        return QUESTION_CATEGORY;
    }

    @Override
    public void setTaskCode(String taskCode) {
        super.setTaskCode(QUESTION_CATEGORY);
    }

    @Override
    public String getTaskIdentifier() {
        return this.getQuestionCategoryId();
    }
}
