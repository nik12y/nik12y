package com.idg.idgcore.coe.domain.entity.iban;

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
@Table (name = "IDGC_COE_IBAN_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
public class IbanEntityKey extends AbstractDomainKey
        implements Serializable
{
    @Id
    @Column (name="iban_country_code")
    private String ibanCountryCode;


    @Override
    public String keyAsString() {
        return ibanCountryCode;
    }
}
