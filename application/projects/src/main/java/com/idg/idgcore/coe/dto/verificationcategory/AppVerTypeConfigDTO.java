package com.idg.idgcore.coe.dto.verificationcategory;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.TYPE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class AppVerTypeConfigDTO extends CoreEngineBaseDTO {

    private String appVerificationTypeId;
    private boolean isViewToCustomer;
    private String nature;


    public boolean getIsViewToCustomer() {
        return isViewToCustomer;
    }

}

