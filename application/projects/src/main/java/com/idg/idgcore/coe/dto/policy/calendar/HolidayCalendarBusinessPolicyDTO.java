package com.idg.idgcore.coe.dto.policy.calendar;

import com.idg.idgcore.coe.dto.holidaylist.HolidayListDTO;
import com.idg.idgcore.dto.policy.AbstractBusinessPolicyDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@SuperBuilder
public class HolidayCalendarBusinessPolicyDTO extends AbstractBusinessPolicyDTO {

    private HolidayListDTO holidayListDTO;
}
