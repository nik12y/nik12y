package com.idg.idgcore.coe.dto.riskcategory;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.RISKCATEGORY;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RiskCategoryDTO extends CoreEngineBaseDTO {


    private String riskCategoryCode;
    private String riskCategoryName;
    private String riskCategoryDescription;


    @Override
    public String getTaskCode() {
        return RISKCATEGORY;
    }

    @Override
    public void setTaskCode(String taskCode) {
        super.setTaskCode(RISKCATEGORY);
    }

    @Override
    public String getTaskIdentifier() {
        return this.getRiskCategoryCode();
    }

}

