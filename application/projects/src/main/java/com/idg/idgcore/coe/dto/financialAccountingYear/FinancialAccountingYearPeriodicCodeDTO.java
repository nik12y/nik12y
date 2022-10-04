package com.idg.idgcore.coe.dto.financialAccountingYear;

import com.idg.idgcore.coe.dto.base.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinancialAccountingYearPeriodicCodeDTO extends CoreEngineBaseDTO {
    private String bankCode;
    private String branchCode;
    private String financialAccountingYearCode;
    private String periodCode;
    private String startDateAccountingPeriod;
    private String endDateAccountingPeriod;
    private String periodCodeClosureStatus;
    private String financialYearClosureStatus;

    @Override
    public String getTaskCode() {
        return "FinancialAccountingYearPeriodicCodeDTO";
    }
}
