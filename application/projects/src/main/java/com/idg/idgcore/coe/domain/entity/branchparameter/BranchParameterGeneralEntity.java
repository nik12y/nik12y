package com.idg.idgcore.coe.domain.entity.branchparameter;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BranchParameterGeneralEntity implements Serializable {
    @Column (name="general_currency_code")
    private String generalCurrencyCode;
    @Column (name="general_branch_parent")
    private String generalBranchParent;
    @Column (name="general_country_code")
    private String generalCountryCode;
    @Column (name="general_zonal_office_code")
    private String generalZonalOfficeCode;
    @Column (name="general_zonal_office_name")
    private String generalZonalOfficeName;
    @Column (name="general_regional_office_code")
    private String generalRegionalOfficeCode;
    @Column (name="general_regional_office_name")
    private String generalRegionalOfficeName;
    @Column (name="general_cut_off_time")
    private String generalCutOffTime;
}
