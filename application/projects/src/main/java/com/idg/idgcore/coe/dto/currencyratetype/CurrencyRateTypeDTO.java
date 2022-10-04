package com.idg.idgcore.coe.dto.currencyratetype;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.CURRENCY_RATE_TYPE;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrencyRateTypeDTO extends CoreEngineBaseDTO {

    private String currencyRateType;
    private String description;

    @Override
    public String getTaskCode () {
        return CURRENCY_RATE_TYPE;
    }

    @Override
    public String getTaskIdentifier () {
        return this.getCurrencyRateType();
    }

    public static Class getSpecificType() {
        return CurrencyRateTypeDTO.class;
    }

}
