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
@Table(name = "IDGC_COE_CITY_CODE_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public class CityEntityKey extends AbstractDomainKey
        implements Serializable {
    @Id
    private String cityCode;

    @Override
    public String keyAsString() {
        return cityCode;
    }
}
