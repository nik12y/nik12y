package com.idg.idgcore.coe.domain.entity.branchparameter;

import com.idg.idgcore.domain.AbstractDomainKey;
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
@Table (name = "IDGC_COE_BRANCH_PARAMETERS_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
public class BranchParameterEntityKey extends AbstractDomainKey
        implements Serializable
{
    @Id
    @Column (name="branch_code")
    private String branchCode;

    @Override
    public String keyAsString() {
        return branchCode;
    }
}
