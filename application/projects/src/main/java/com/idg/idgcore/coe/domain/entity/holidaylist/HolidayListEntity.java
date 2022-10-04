package com.idg.idgcore.coe.domain.entity.holidaylist;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_CAL_HOLIDAY_LIST_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(HolidayListEntityKey.class)
public class HolidayListEntity extends AbstractAuditableDomainEntity
        implements Serializable {
    @Id
    @Column(name="holiday_list_id")
    private String holidayListId;

    @Column(name="holiday_list_name")
    private String holidayListName;

    @Column(name="holiday_list_description")
    private String holidayListDescription;

    @Column(name="is_adhoc_holidays")
    private char isAdhocHolidays;

    @Column(name="effective_date")
    private Date effectiveDate;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "holiday_list_id")
    private List<HolidayEntity> holidayEntity;


    @Column(name="life_cycle_id")
    private String lifeCycleId;

    @Column(name="reference_no")
    private String referenceNo;

    @Column(name="record_status")
    private String status;

    @Column(name="record_version")
    private Integer recordVersion;

    @Column(name="is_authorized")
    private String authorized;

    @Column(name="last_configuration_action")
    private String lastConfigurationAction;



}
