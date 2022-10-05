package com.idg.idgcore.coe.domain.entity.virtualentity;

import com.idg.idgcore.domain.AbstractDomainKey;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public class VirtualEntityKey extends AbstractDomainKey implements Serializable {

    @Id
    private String entityCode;

    @Id
    private String parentEntityCode;

    @Override
    public String keyAsString() {
        return entityCode+parentEntityCode;
    }

}
