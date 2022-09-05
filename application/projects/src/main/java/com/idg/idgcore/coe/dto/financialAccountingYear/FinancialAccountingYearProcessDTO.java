package com.idg.idgcore.coe.dto.financialAccountingYear;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinancialAccountingYearProcessDTO {
    private String bankCode;
    private String branchCode;
    private String financialAccountingYearCode;
    private String startDate;
    private String endDate;
    private String financialAccountingYearName;
    private String periodCodeFrequency;

    private String periodCode;
    private String startDateAccountingPeriod;
    private String endDateAccountingPeriod;
    private String periodCodeClosureStatus;
    private String financialYearClosureStatus;

    private Long numberOfDays;
    private String branchDate;

}
