package com.idg.idgcore.coe.domain.entity.groupBanking;

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
@Entity
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public class GroupBankingEntityKey extends AbstractDomainKey implements Serializable {

    @Id
    private String bankGroupCode;

    @Override
    public String keyAsString() {
        return bankGroupCode;
    }
}
