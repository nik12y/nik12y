package com.idg.idgcore.coe.dto.reason;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.REASON;

@ToString (callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude (JsonInclude.Include.NON_NULL)
public class ReasonDTO extends CoreEngineBaseDTO {
    private String primaryReasonCode;
    private String primaryReasonDescription;
    private boolean primaryAccountBlock;
    private boolean primaryAccountUnblock ;
    private boolean primaryAccountClosure;
    private boolean primaryRequestForAccountClosure;
    private boolean primaryStopPayment;
    private boolean primaryStopPaymentRevoke;
    private String secondaryReasonCode;
    private String secondaryReasonDescription;
    private boolean secondaryAccountBlock;
    private boolean secondaryAccountUnblock ;
    private boolean secondaryAccountClosure;
    private boolean secondaryRequestForAccountClosure;
    private boolean secondaryStopPayment;
    private boolean secondaryStopPaymentRevoke;
    private String applicableCategories;
    private String documentRequiredIfAny;

    @Override
    public String getTaskCode () {
        return REASON;
    }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(REASON);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getPrimaryReasonCode();
    }
}
