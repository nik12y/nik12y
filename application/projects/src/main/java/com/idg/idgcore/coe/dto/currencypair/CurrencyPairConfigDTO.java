package com.idg.idgcore.coe.dto.currencypair;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static com.idg.idgcore.coe.common.Constants.CURRENCY_PAIR_CONFIG;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrencyPairConfigDTO extends CoreEngineBaseDTO {

//    private Integer pairConfigId;
    private Integer pairId;
    private String entityCode;

//    @Override
//    public String getTaskCode () {
//        return CURRENCY_PAIR_CONFIG;
//    }
//
//    @Override
//    public void setTaskCode(String taskCode)
//    {
//        super.setTaskCode(CURRENCY_PAIR_CONFIG);
//    }
//
//    @Override
//    public String getTaskIdentifier () {
//        return String.valueOf(this.getPairConfigId());
//    }
}
