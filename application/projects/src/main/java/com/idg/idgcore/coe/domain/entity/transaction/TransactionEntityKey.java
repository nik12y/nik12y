package com.idg.idgcore.coe.domain.entity.transaction;

import com.idg.idgcore.domain.AbstractDomainKey;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table (name = "IDGC_COE_TRAN_CODE_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
public class TransactionEntityKey extends AbstractDomainKey
        implements Serializable
{
    @Id
    private String transactionCode;


    @Override
    public String keyAsString() {
        return transactionCode;
    }
}
