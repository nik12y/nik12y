package com.idg.idgcore.coe.domain.entity.purging;

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
@Table(name = "IDGC_COE_PURGING_POLICY_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public class PurgingEntityKey extends AbstractDomainKey implements Serializable {

    @Id
    private String moduleCode;


    @Override
    public String keyAsString() { return moduleCode; }
}
