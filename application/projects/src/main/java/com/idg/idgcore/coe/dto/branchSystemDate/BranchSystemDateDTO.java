package com.idg.idgcore.coe.dto.branchSystemDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.BRANCH_SYSTEM_DATE;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BranchSystemDateDTO extends CoreEngineBaseDTO {

    private String branchCode;
    private String currentWorkingDate;
    private String previousWorkingDate;
    private String nextWorkingDate;

    @Override
    public String getTaskCode () {
        return BRANCH_SYSTEM_DATE;
    }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(BRANCH_SYSTEM_DATE);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getBranchCode();
    }
}
