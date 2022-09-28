package com.idg.idgcore.coe.dto.currencyConfiguration;

import com.fasterxml.jackson.annotation.*;
import com.idg.idgcore.coe.dto.base.*;
import lombok.*;

@ToString (callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@JsonInclude (JsonInclude.Include.NON_NULL)
public class CurrencyExtraFieldMapDTO extends CoreEngineBaseDTO {

    private String currencyCode;
    private String extraFieldName;
    private String extraFieldValue;

    @Override
    public String getTaskCode() {
        return "CurrencyExtraFieldMapDTO";
    }
}
