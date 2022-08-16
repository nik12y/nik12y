package com.idg.idgcore.coe.dto.errorOverride;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ErrorOverrideConversionsDTO {
    private String branchCode;
    private String functionCodeOverride;
    private String newErrorCode;

}