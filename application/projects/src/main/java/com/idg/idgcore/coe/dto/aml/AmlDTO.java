package com.idg.idgcore.coe.dto.aml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.AML;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AmlDTO  extends CoreEngineBaseDTO {

    private String productCategory;
    private String productDescription;
    private boolean isProductType;
    private String productLimitCurrency;
    private String exchangeRateCode;

    private String exchangeRateType;
    private String debitCreditIndicator;

    private LimitDTO limit;

    public String getProductCategory() {
        return productCategory;
    }

    public boolean getIsProductType() {
        return isProductType;
    }

    @Override
    public String getTaskCode () {
        return AML;
    }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(AML);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getProductCategory();
    }
}
