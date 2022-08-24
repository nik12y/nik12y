package com.idg.idgcore.coe.dto.financialAccountingYear;

import com.fasterxml.jackson.annotation.*;
import com.idg.idgcore.coe.dto.base.*;
import lombok.*;
import lombok.experimental.*;

import java.util.*;

import static com.idg.idgcore.coe.common.Constants.*;

@ToString (callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude (JsonInclude.Include.NON_NULL)
public class FinancialAccountingYearDTO extends CoreEngineBaseDTO {
    private String bankCode;
    private String branchCode;
    private String financialAccountingYearCode;
    private String startDate;
    private String endDate;
    private String financialAccountingYearName;
    private String periodCodeFrequency;
    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;
    private List<FinancialAccountingYearPeriodicCodeDTO> financialAccountingYearPeriodicCode;

    @Override
    public String getTaskCode () {
        return FINANCIAL_ACCOUNTING_YEAR;
    }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(FINANCIAL_ACCOUNTING_YEAR);
    }

    @Override
    public String getTaskIdentifier () {
        return bankCode + FIELD_SEPARATOR + branchCode + FIELD_SEPARATOR
                + financialAccountingYearCode;
    }

}