package com.idg.idgcore.coe.dto.financialAccountingYear;

import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinancialAccountingYearPeriodicCodeDTO {

    private Integer finAccYearPeriodCodesId;
    private String periodCode;
    private Date startDateAccountingPeriod;
    private Date endDateAccountingPeriod;

}
