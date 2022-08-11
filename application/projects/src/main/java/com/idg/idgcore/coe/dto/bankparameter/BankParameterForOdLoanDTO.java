package com.idg.idgcore.coe.dto.bankparameter;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankParameterForOdLoanDTO {
    private String ruleIdForOd;
    private String ruleNameForOd;
    private String ruleIdForLoan;
    private String ruleNameForLoan;

}
