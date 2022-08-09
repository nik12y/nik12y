package com.idg.idgcore.coe.dto.questionCategory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionCategoryDetailsDTO extends CoreEngineBaseDTO {

    private String questionId;
    private String questionNature;
    private String parentQuestionId;
    private String displayCondition;
}
