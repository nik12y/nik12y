package com.idg.idgcore.coe.domain.entity.capt;

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
@Table (name = "IDGC_COE_MODULE_CODE_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
public class CaptEntityKey extends AbstractDomainKey
        implements Serializable
{
    @Id
    private String clearingPaymentTypeCode;


    @Override
    public String keyAsString() {
        return clearingPaymentTypeCode;
    }
}
