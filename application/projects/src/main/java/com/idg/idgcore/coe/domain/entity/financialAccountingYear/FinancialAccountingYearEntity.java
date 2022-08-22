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
@Table (name = "IDGC_COE_FINANCIAL_ACCOUNTING_YEAR_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@IdClass (FinancialAccountingYearEntityKey.class)
public class FinancialAccountingYearEntity extends AbstractAuditableDomainEntity
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
    private Date startDate;
    private Date endDate;
    @Column (name = "financial_accounting_year_desc")
    private String financialAccountingYearName;
    private String periodCodeFrequency;
    private String lifeCycleId;
    private String referenceNo;
    @Column (name = "record_status")
    private String status;
    @Column (name = "record_version")
    private Integer recordVersion;
    @Column (name = "is_authorized")
    private String authorized;
    private String lastConfigurationAction;
    @OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumns ({
            @JoinColumn (name = "bank_code", referencedColumnName = "bank_code"),
            @JoinColumn (name = "branch_code", referencedColumnName = "branch_code"),
            @JoinColumn (name = "financial_accounting_year_code", referencedColumnName = "financial_accounting_year_code")
    })
    private List<FinancialAccountingYearPeriodicCodeEntity> financialAccountingYearPeriodicCode;

}
