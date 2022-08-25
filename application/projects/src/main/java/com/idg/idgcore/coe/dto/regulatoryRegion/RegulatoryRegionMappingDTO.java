package com.idg.idgcore.coe.dto.regulatoryRegion;

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
public class RegulatoryRegionMappingDTO extends CoreEngineBaseDTO {

    private String demographicMappingCode;
  //  private String demographicMappingName;
}
