package com.idg.idgcore.coe.domain.entity.bankparameter;

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
public class BankParameterForOdLoanEntity implements Serializable {
    @Column (name="rule_id_od")
    private String ruleIdOd;
    @Column (name="rule_name_od")
    private String ruleNameOd;
    @Column (name="rule_id_loan")
    private String ruleIdLoan;
    @Column (name="rule_name_loan")
    private String ruleNameLoan;
}
