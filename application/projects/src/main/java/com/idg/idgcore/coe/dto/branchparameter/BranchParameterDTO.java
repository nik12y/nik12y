package com.idg.idgcore.coe.dto.branchparameter;

import com.fasterxml.jackson.annotation.*;
import com.idg.idgcore.coe.dto.base.*;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static com.idg.idgcore.coe.common.Constants.BRANCH_PARAMETER;

@ToString (callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude (JsonInclude.Include.NON_NULL)
public class BranchParameterDTO extends CoreEngineBaseDTO {
    private String branchCode;
    private String branchName;
    private String bankCode;
    private String branchConciseName;
    private Boolean isBranchAvailableStatus;
    private String bankIdentifierCode;
    private String branchType;

    private BranchParameterAddressDTO branchParameterAddress;
    private BranchParameterContactInfoDTO branchParameterContactInfo;
    private BranchParameterGeneralDTO branchParameterGeneral;
    private BranchParameterClearingDTO branchParameterClearing;
    private BranchParameterAtmDTO branchParameterAtm;
    private BranchParameterTimezoneDTO branchParameterTimezone;
    private BranchParameterGlobalInterdictDTO branchParameterGlobalInterdict;
    private BranchParameterTranDuplicateDTO branchParameterTranDuplicate;
    private BranchParameterLocalCurrencyDTO branchParameterLocalCurrency;
    private BranchParameterMiscellaneousDTO branchParameterMiscellaneous;

    @Override
    public String getTaskCode () {
        return BRANCH_PARAMETER;
    }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(BRANCH_PARAMETER);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getBranchCode();
    }

}
