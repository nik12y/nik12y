package com.idg.idgcore.coe.domain.entity.financialAccountingYear;

import com.idg.idgcore.domain.*;
import lombok.*;

import javax.persistence.*;
import java.io.*;
import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "IDGC_COE_FIN_ACC_YEAR_PERIOD_CODES_CNFG")
@ToString
@IdClass (FinancialAccountingYearPeriodicCodeEntityKey.class)
public class FinancialAccountingYearPeriodicCodeEntity extends AbstractAuditableDomainEntity
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
    @Id
    private String periodCode;
    private Date startDateAccountingPeriod;
    private Date endDateAccountingPeriod;

    @Column (name = "period_code_closure_status")
    private String periodCodeClosureStatus;
    @Column (name = "financial_year_closure_status")
    private String financialYearClosureStatus;

    @Column (name = "record_status")
    private String status;
    @Column (name = "record_version")
    private Integer recordVersion;
    @Column (name = "is_authorized")
    private String authorized;
    private String lastConfigurationAction;
}



