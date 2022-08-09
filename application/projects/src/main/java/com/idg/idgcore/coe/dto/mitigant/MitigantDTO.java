package com.idg.idgcore.coe.dto.mitigant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static com.idg.idgcore.coe.common.Constants.MITIGANT;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MitigantDTO extends CoreEngineBaseDTO {

    private String mitigantCode;
    private String mitigantCodeName;
    private String mitigantCodeDesc;
    private Boolean isAllowModification;
    private Boolean isActionable;

    public Boolean getIsAllowModification() {
        return isAllowModification;
    }

    public Boolean getIsActionable() {
        return isActionable;
    }

    private List<MitigantRiskCodeDTO> mitigantRiskCode;

    @Override
    public String getTaskCode() { return MITIGANT; }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(MITIGANT);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getMitigantCode();
    }



}
