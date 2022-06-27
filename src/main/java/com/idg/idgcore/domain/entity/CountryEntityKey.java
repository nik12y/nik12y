package com.idg.idgcore.domain.entity;

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
@Table (name = "IDGC_COE_CONFG_COUNTRY_B")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
public class CountryEntityKey extends AbstractDomainKey
        implements Serializable
{
    @Id
    private String countryCode;


    @Override
    public String keyAsString() {
        return countryCode;
    }
}
