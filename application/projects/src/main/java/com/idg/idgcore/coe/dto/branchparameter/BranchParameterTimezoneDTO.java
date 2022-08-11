package com.idg.idgcore.coe.dto.branchparameter;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchParameterTimezoneDTO {
    private Integer timeZoneNoOfHours;
    private Integer timeZoneNoOfMinutes;
    private Boolean timeZoneIsAhead;
    private String timeZoneLevel;
}
