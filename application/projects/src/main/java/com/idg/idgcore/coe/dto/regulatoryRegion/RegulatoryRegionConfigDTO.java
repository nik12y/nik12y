package com.idg.idgcore.coe.dto.regulatoryRegion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static com.idg.idgcore.coe.common.Constants.REGULATORY_SERVICE;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegulatoryRegionConfigDTO extends CoreEngineBaseDTO {

    private String regulatoryRegionCode;
    private String regulatoryRegionName;
    private String regulatoryRegionDescription;
    private String regionEffectiveDate;
    private String regionGroupCode;
    private String purpose;

    private List<RegulatoryRegionMappingDTO> regulatoryRegionMapping;
    @Override
    public String getTaskCode() {
        return REGULATORY_SERVICE;
    }

    @Override
    public void setTaskCode(String taskCode) {
        super.setTaskCode(REGULATORY_SERVICE);
    }

    @Override
    public String getTaskIdentifier() {
        return this.getRegulatoryRegionCode();
    }
}
