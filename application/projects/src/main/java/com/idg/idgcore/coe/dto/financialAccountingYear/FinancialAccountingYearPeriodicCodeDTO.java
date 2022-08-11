package com.idg.idgcore.coe.dto.financialAccountingYear;

import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinancialAccountingYearPeriodicCodeDTO {

    private Long finAccYearPeriodCodesId;
    private String periodCode;
    private String startDateAccountingPeriod;
    private String endDateAccountingPeriod;

}
