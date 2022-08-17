package com.idg.idgcore.coe.domain.entity.riskcategory;

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
@Table(name = "IDGC_COE_RISK_CATEGORY_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public class RiskCategoryEntityKey extends AbstractDomainKey
        implements Serializable {
    @Id
    private String riskCategoryCode;


    @Override
    public String keyAsString() {
        return riskCategoryCode;
    }
}