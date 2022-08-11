package com.idg.idgcore.coe.dto.branchparameter;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchParameterAtmDTO {
    private String atmBranch;
    private String atmInstitutionIdentification;
    private String atmInterBranchTransactionCode;
    private Boolean atmIsCustomerFundTransfer;
}
