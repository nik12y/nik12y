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
@Table(name = "idgc_mcy_cnfg_branch_parameter")
@Inheritance(strategy = InheritanceType.JOINED)

public class MulBranchParameterEntityKey extends AbstractDomainKey
        implements Serializable{

    @Id
    private String currencyCode;
    @Id
    private String entityCode;
    @Id
    private String entityType;

    @Override
    public String keyAsString () {
        return currencyCode+entityCode+entityType;
    }

}

