package com.idg.idgcore.coe.domain.entity.reason;

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
@Table (name = "IDGC_COE_REASON_CODE_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
public class ReasonEntityKey extends AbstractDomainKey
        implements Serializable
{
    @Id
    private String primaryReasonCode;


    @Override
    public String keyAsString() {
        return primaryReasonCode;
    }
}
