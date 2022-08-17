package com.idg.idgcore.coe.dto.riskcode;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.RISKCODE;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RiskCodeDTO extends CoreEngineBaseDTO {


    private String riskCode;
    private String riskCodeName;
    private String riskCodeDescription;
    private Boolean isAllowDetailsModified;
    private String riskMode;
    private String riskCategoryCode;



    @Override
    public String getTaskCode() {
        return RISKCODE;
    }

    @Override
    public void setTaskCode(String taskCode) {
        super.setTaskCode(RISKCODE);
    }

    @Override
    public String getTaskIdentifier() {
        return this.getRiskCode();
    }

}

