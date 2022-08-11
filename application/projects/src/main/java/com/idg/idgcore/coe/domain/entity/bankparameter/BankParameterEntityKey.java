package com.idg.idgcore.coe.domain.entity.bankparameter;

import com.idg.idgcore.domain.*;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "IDGC_COE_BANK_PARAMETERS_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
public class BankParameterEntityKey extends AbstractDomainKey
        implements Serializable
{
    @Id
    @Column (name="bank_code")
    private String bankCode;


    @Override
    public String keyAsString() {
        return bankCode;
    }
}
