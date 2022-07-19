package com.idg.idgcore.coe.domain.entity.state;

import com.idg.idgcore.domain.AbstractDomainKey;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.ToString;


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
public class StateEntityKey extends AbstractDomainKey
        implements Serializable {
    @Id
    private String stateCode;

    @Override
    public String keyAsString() {
        return stateCode;
    }
}