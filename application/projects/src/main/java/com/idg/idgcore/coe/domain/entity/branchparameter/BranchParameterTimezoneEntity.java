package com.idg.idgcore.coe.domain.entity.branchparameter;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BranchParameterTimezoneEntity implements Serializable {
    @Column (name="time_zone_no_of_hours")
    private Integer timeZoneNoOfHours;
    @Column (name="time_zone_no_of_minutes")
    private Integer timeZoneNoOfMinutes;
    @Column (name="time_zone_is_ahead")
    private char timeZoneIsAhead;
    @Column (name="time_zone_level")
    private String timeZoneLevel;
}
