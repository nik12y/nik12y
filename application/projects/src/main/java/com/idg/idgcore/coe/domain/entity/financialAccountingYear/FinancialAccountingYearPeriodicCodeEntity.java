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
//@Inheritance (strategy = InheritanceType.JOINED)
@ToString
//@IdClass (FinancialAccountingYearPeriodicCodeKey.class)
public class FinancialAccountingYearPeriodicCodeEntity extends AbstractAuditableDomainEntity
        implements Serializable
{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "fin_acc_year_period_codes_id")
    private Integer finAccYearPeriodCodesId;
    @Column (name = "financial_accounting_year_id")
    private Integer financialAccountingYearId;
    private String periodCode;
    private Date startDateAccountingPeriod;
    private Date endDateAccountingPeriod;


}



