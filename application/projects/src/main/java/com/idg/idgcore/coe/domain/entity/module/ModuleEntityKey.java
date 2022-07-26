package com.idg.idgcore.coe.domain.entity.module;

import com.idg.idgcore.domain.AbstractDomainKey;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;
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
public class ModuleEntityKey extends AbstractDomainKey
        implements Serializable
{
    @Id
    private String moduleCode;


    @Override
    public String keyAsString() {
        return moduleCode;
    }
}
