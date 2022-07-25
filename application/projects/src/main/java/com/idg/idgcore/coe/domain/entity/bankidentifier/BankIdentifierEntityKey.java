package com.idg.idgcore.coe.domain.entity.bankidentifier;

import com.idg.idgcore.domain.AbstractDomainKey;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

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
public class BankIdentifierEntityKey  extends AbstractDomainKey
        implements Serializable {

    @Id
    private String bankIdentifierCode;

    @Override
    public String keyAsString() {
        return bankIdentifierCode;
    }
}
