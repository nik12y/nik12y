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
public class BranchParameterAtmEntity implements Serializable {
    @Column (name="atm_branch")
    private String atmBranch;
    @Column (name="atm_institution_identity")
    private String atmInstitutionIdentity;
    @Column (name="atm_inter_branch_tran_code")
    private String atmInterBranchTranCode;
    @Column (name="atm_is_customer_fund_trf")
    private char atmIsCustomerFundTrf;
}
