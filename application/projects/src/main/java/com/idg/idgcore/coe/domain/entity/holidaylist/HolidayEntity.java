package com.idg.idgcore.coe.domain.entity.holidaylist;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_CAL_HOLIDAY_LIST")
@ToString
public class HolidayEntity extends AbstractAuditableDomainEntity
        implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="holiday_id")
    private Integer holidayId;

    @Column(name="holiday_name")
    private String holidayName;

    @Column(name="holiday_type")
    private String holidayType;

    @Column(name="holiday_date")
    private Date holidayDate;

    @Column(name="record_status")
    private String status;

    @Column(name="record_version")
    private Integer recordVersion;

    @Column(name="is_authorized")
    private String authorized;

    @Column(name="last_configuration_action")
    private String lastConfigurationAction;


}
