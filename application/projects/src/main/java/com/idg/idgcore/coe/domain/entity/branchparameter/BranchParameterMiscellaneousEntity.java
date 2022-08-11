package com.idg.idgcore.coe.domain.entity.branchparameter;

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
public class BranchParameterMiscellaneousEntity implements Serializable {
    @Column (name="misc_branch_is_deno_req")
    private char miscBranchIsDenoReq;
    @Column (name="misc_branch_currency_deno")
    private String miscBranchCurrencyDeno;
    @Column (name="misc_branch_week_begin_day")
    private String miscBranchWeekBeginDay;
    @Column (name="misc_branch_weekly_off1")
    private String miscBranchWeeklyOff1;
    @Column (name="misc_branch_weekly_off2")
    private String miscBranchWeeklyOff2;
    @Column (name="misc_branch_fin_yr_begin_mth")
    private String miscBranchFinYrBeginMth;
}
