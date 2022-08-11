package com.idg.idgcore.coe.dto.bankparameter;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankParameterPreferencesDTO {
    private String weekBeginDay;
    private String weeklyOff1;
    private String weeklyOff2;
    private String weeklyOff3;
    private String financialYearBeginMonth;

}
