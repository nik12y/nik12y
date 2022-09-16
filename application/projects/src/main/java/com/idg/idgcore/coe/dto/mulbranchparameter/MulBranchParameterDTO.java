package com.idg.idgcore.coe.dto.mulbranchparameter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.currencypair.CurrencyPairConfigDTO;
import com.idg.idgcore.coe.dto.currencypair.CurrencyPairDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static com.idg.idgcore.coe.common.Constants.CURR_BRANCH_PARAM;
import static com.idg.idgcore.coe.common.Constants.FIELD_SEPARATOR;


@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MulBranchParameterDTO extends CoreEngineBaseDTO {

    private String currencyCode;
    private String entityCode;
    private Integer spotDays;
    private Integer generationOfPaymentMessage;
    private Integer generationOfReceiveMessages;


    @Override
    public String getTaskCode () {
        return CURR_BRANCH_PARAM;
    }

    @Override
    public void setTaskCode(String taskCode) {
        super.setTaskCode(CURR_BRANCH_PARAM);
    }

    @Override
    public String getTaskIdentifier () {
        return currencyCode + FIELD_SEPARATOR + entityCode;
    }

}
