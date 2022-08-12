package com.idg.idgcore.coe.dto.appvertype;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static com.idg.idgcore.coe.common.Constants.APP_VERIFICATION_TYPE;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppVerTypeDTO extends CoreEngineBaseDTO {

    private String verificationTypeId;
    private String verificationTypeName;
    private String verificationTypeDesc;
    private boolean isViewableToCustomer;
    private boolean isAlertToBeSentOnCompl;
    private boolean isExternal;
    private boolean isDocumentRequired;

    private List<DocumentsDTO> documents;

    public boolean getIsViewableToCustomer() {
        return isViewableToCustomer;
    }

    public boolean getIsAlertToBeSentOnCompl() {
        return isAlertToBeSentOnCompl;
    }

    public boolean getIsExternal() {
        return isExternal;
    }

    public boolean getIsDocumentRequired() {
        return isDocumentRequired;
    }

    @Override
    public String getTaskCode () {
        return APP_VERIFICATION_TYPE;
    }

    @Override
    public void setTaskCode(String taskCode)
    {
        super.setTaskCode(APP_VERIFICATION_TYPE);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getVerificationTypeId();
    }
}
