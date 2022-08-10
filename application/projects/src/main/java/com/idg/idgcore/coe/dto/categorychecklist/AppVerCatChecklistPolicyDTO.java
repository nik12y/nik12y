package com.idg.idgcore.coe.dto.categorychecklist;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.CHECKLIST;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class AppVerCatChecklistPolicyDTO extends CoreEngineBaseDTO {

    private String appVerChecklistPolicyId;
    private String appVerChecklistPolicyDesc;
    private String domainId;
    private String domainCategoryId;
    private String eventId;
    private String effectiveDate;
    private String entity;
    private String ruleId;


//    private List<AppVerChecklistPolicyCategoryDTO> appVerChecklistPolicyCategory;


    @Override
    public String getTaskCode () {
        return CHECKLIST;
    }

    @Override
    public void setTaskCode(String taskCode)
    {
        super.setTaskCode(CHECKLIST);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getAppVerChecklistPolicyId();
    }
}
