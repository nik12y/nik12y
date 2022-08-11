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
public class BranchParameterTranDuplicateEntity implements Serializable {
    @Column (name="tran_dup_module_code")
    private String tranDupModuleCode;
    @Column (name="tran_dup_is_check_req")
    private char tranDupIsCheckReq;
    @Column (name="tran_dup_no_of_days_for_check")
    private Integer tranDupNoOfDaysForCheck;
}
