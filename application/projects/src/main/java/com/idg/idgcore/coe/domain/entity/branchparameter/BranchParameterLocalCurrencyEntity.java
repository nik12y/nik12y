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
public class BranchParameterLocalCurrencyEntity implements Serializable {
    @Column (name="local_currency_msg_mod_code")
    private String localCurrencyMsgModCode;
    @Column (name="local_currency_msg_type")
    private String localCurrencyMsgType;

}
