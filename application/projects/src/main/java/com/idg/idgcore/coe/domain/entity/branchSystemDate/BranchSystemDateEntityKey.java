package com.idg.idgcore.coe.domain.entity.branchSystemDate;

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
@Table(name = "IDGC_COE_BRANCH_SYSTEM_DATE_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public class BranchSystemDateEntityKey extends AbstractDomainKey implements Serializable {

    @Id
    private String branchCode;

    @Override
    public String keyAsString() { return branchCode; }

}
