package com.idg.idgcore.coe.domain.entity.city;

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
@Table(name = "IDGC_COE_CITY_CODE_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public class CityEntityKey extends AbstractDomainKey
        implements Serializable {
    @Id
    private String cityCode; //CITY - "PN",   XYZ - "PN"

    @Override
    public String keyAsString() {
        return cityCode;
    }
}