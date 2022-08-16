package com.idg.idgcore.coe.domain.entity.city;

import com.idg.idgcore.domain.*;
import lombok.*;

import javax.persistence.*;
import java.io.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "IDGC_COE_CITY_CODE_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@IdClass (CityEntityKey.class)
public class CityEntity extends AbstractAuditableDomainEntity
        implements Serializable
{
    @Id
    private String cityCode;
    private String cityName;
    private String timeZone;
    private String countryCode;
    private String stateCode;

    private String lifeCycleId;
    private String referenceNo;
    @Column(name = "record_status")
    private String status;
    @Column(name = "record_version")
    private Integer recordVersion;
    @Column(name = "is_authorized")
    private String authorized;
    private String lastConfigurationAction;

}
