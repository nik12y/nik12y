package com.idg.idgcore.coe.dto.mitigant;

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
public class MitigantRiskCodeDTO extends CoreEngineBaseDTO {

    private String riskCode;
    private String riskName;

}
