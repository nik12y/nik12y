package com.idg.idgcore.coe.dto.holidaylist;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HolidayDTO extends CoreEngineBaseDTO {

    private Integer holidayId;

    private String holidayName;
    private String holidayType;
    private String holidayDate;
}
