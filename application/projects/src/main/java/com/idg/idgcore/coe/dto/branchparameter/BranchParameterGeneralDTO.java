package com.idg.idgcore.coe.dto.branchparameter;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchParameterGeneralDTO {
    private String generalCurrencyCode;
    private String generalBranchParent;
    private String generalCountryCode;
    private String generalZonalOfficeCode;
    private String generalZonalOfficeName;
    private String generalRegionalOfficeCode;
    private String generalRegionalOfficeName;
    private String generalCutOffTime;

}
