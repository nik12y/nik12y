package com.idg.idgcore.coe.domain.entity.city;

import com.fasterxml.jackson.annotation.*;
import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Entity
@Table(name = "IDGC_COE_CITY_CODE_CNFG")

@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(CityEntityKey.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityEntity extends AbstractAuditableDomainEntity
        implements Serializable {
    @Id
    private String cityCode;
    private String cityName;
    private String timeZone;

    @Column(name = "country_code", updatable = false)
    private String countryCode;
    private String stateCode;

    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;

}
