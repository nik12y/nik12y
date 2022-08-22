package com.idg.idgcore.coe.domain.entity.financialAccountingYear;

import com.idg.idgcore.domain.*;
import lombok.*;

import javax.persistence.*;
import java.io.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table (name = "IDGC_COE_FINANCIAL_ACCOUNTING_YEAR_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
public class FinancialAccountingYearEntityKey extends AbstractDomainKey
        implements Serializable
{
    @Id
    @Column (name = "bank_code")
    private String bankCode;
    @Id
    @Column (name = "branch_code")
    private String branchCode;
    @Id
    @Column (name = "financial_accounting_year_code")
    private String financialAccountingYearCode;

    @Override
    public String keyAsString () {
        return bankCode + branchCode + financialAccountingYearCode;
    }

}

