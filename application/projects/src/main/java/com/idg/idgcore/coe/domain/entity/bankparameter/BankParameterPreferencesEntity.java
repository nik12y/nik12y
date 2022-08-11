package com.idg.idgcore.coe.domain.entity.bankparameter;


import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BankParameterPreferencesEntity implements Serializable {
    @Column (name="week_begin_day")
    private String weekBeginDay;
    @Column (name="weekly_off1")
    private String weeklyOff1;
    @Column (name="weekly_off2")
    private String weeklyOff2;
    @Column (name="weekly_off3")
    private String weeklyOff3;
    @Column (name="financial_year_begin_month")
    private String financialYearBeginMonth;
}
