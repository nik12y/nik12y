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
@ToString
public class FinancialAccountingYearEntity extends AbstractAuditableDomainEntity
        implements Serializable
{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer financialAccountingYearId;
    private String bankCode;
    private String branchCode;
    private Date startDate;
    private Date endDate;
    private String financialAccountingYearCode;
    private String financialAccountingYearName;
    private String periodCodeFrequency;
    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;

    @OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn (name = "financial_accounting_year_id")//financialAccountingYearId
    private List<FinancialAccountingYearPeriodicCodeEntity> financialAccountingYearPeriodicCodes;
}
