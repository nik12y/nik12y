package com.idg.idgcore.coe.dto.purgingpolicy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.PURGING_POLICY;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurgingDTO extends CoreEngineBaseDTO {

    private String moduleCode;
    private String tranMaintenanceStatus;
    private int retentionPeriod;

    @Override
    public String getTaskCode () {
        return PURGING_POLICY;
    }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(PURGING_POLICY);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getModuleCode();
    }
}
