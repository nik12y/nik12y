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
public class BranchParameterClearingEntity implements Serializable {
    @Column (name="clearing_local_payment_branch")
    private String clearingLocalPaymentBranch;
    @Column (name="clearing_currency_code")
    private String clearingCurrencyCode;
}
