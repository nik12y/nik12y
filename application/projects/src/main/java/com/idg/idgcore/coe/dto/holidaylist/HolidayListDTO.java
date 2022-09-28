package com.idg.idgcore.coe.dto.holidaylist;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static com.idg.idgcore.coe.common.Constants.HOLIDAY;


@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HolidayListDTO extends CoreEngineBaseDTO {

    private String holidayListId;
    private String holidayListName;
    private String holidayListDescription;
    private boolean isAdhocHolidays;
    private String effectiveDate;


    private List<HolidayDTO> holiday;

    public boolean getIsAdhocHolidays() {
        return isAdhocHolidays;
    }


    @Override
    public String getTaskCode () {
        return HOLIDAY;
    }

    @Override
    public void setTaskCode(String taskCode)
    {
        super.setTaskCode(HOLIDAY);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getHolidayListId();
    }
}
