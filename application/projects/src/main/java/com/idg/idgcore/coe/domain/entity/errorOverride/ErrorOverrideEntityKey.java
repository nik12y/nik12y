package com.idg.idgcore.coe.domain.entity.errorOverride;

import com.idg.idgcore.domain.*;
import lombok.*;

import javax.persistence.*;
import java.io.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table (name = "IDGC_COE_ERROR_OVERRIDE_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
public class ErrorOverrideEntityKey extends AbstractDomainKey
        implements Serializable
{
    @Id
    private String errorCode;

    @Override
    public String keyAsString () {
        return errorCode;
    }

}

