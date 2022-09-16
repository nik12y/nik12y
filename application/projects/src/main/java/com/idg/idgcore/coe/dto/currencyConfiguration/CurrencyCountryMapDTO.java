package com.idg.idgcore.coe.dto.currencyConfiguration;

import com.fasterxml.jackson.annotation.*;
import com.idg.idgcore.coe.dto.base.*;
import lombok.*;

@ToString (callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@JsonInclude (JsonInclude.Include.NON_NULL)
public class CurrencyCountryMapDTO extends CoreEngineBaseDTO {

    private String currencyCode;
    private String currencyCountryCode;
    private String currencyCountryName;
}