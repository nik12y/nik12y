package com.idg.idgcore.coe.dto.errorOverride;

import com.fasterxml.jackson.annotation.*;
import com.idg.idgcore.coe.dto.base.*;
import lombok.*;
import lombok.experimental.*;

import static com.idg.idgcore.coe.common.Constants.*;

@ToString (callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude (JsonInclude.Include.NON_NULL)
public class ErrorOverrideDTO extends CoreEngineBaseDTO {
    private String errorCode;
    private String errorMessage;
    private String branchCode;
    private String typeOfMessage;
    private Boolean isConfirmationRequired;
    private String functionCode;
    private String batchType;
    private Boolean isExcluded;
    private ErrorOverrideLanguageDetailsDTO errorOverrideLanguageDetails;
    private ErrorOverrideConversionsDTO errorOverrideConversions;


    @Override
    public String getTaskCode () {
        return ERROR_OVERRIDE;
    }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(ERROR_OVERRIDE);
    }

    @Override
    public String getTaskIdentifier () {
        return (this.getErrorCode() + FIELD_SEPARATOR + branchCode);
    }

}
