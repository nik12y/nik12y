package com.idg.idgcore.coe.domain.entity.riskcode;

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
@Table(name = "IDGC_COE_RISK_CODE_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public class RiskCodeEntityKey extends AbstractDomainKey
        implements Serializable {
    @Id
    private String riskCode;


    @Override
    public String keyAsString() {
        return riskCode;
    }
}