package com.idg.idgcore.coe.domain.entity.mulbranchparameter;

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
@Table(name = "IDGC_MCY_BRANCH_PARAMETER_CONFIG")
@Inheritance(strategy = InheritanceType.JOINED)

public class MulBranchParameterEntityKey extends AbstractDomainKey
        implements Serializable{

    @Id
    @Column(name= "currency_code")
    private String currencyCode;

    @Id
    @Column(name= "entity_code")
    private String entityCode;

    @Override
    public String keyAsString () {
        return currencyCode + entityCode;
    }

}

