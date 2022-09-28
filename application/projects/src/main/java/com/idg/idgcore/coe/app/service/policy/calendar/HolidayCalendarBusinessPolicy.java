package com.idg.idgcore.coe.app.service.policy.calendar;

import com.idg.idgcore.coe.dto.holidaylist.HolidayDTO;
import com.idg.idgcore.coe.dto.policy.calendar.HolidayCalendarBusinessPolicyDTO;
import com.idg.idgcore.datatypes.exceptions.BusinessValidationError;
import com.idg.idgcore.domain.policy.AbstractBusinessPolicy;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class HolidayCalendarBusinessPolicy extends AbstractBusinessPolicy {

    private HolidayCalendarBusinessPolicyDTO holidayCalendarBusinessPolicyDTO;

    public HolidayCalendarBusinessPolicy(){
        super("HOLIDAY_POLICY");
    }

    public HolidayCalendarBusinessPolicy(HolidayCalendarBusinessPolicyDTO holidayCalendarBusinessPolicyDTO)
    {
        this();
        this.holidayCalendarBusinessPolicyDTO=holidayCalendarBusinessPolicyDTO;
    }
    @Override
    protected void validatePolicy() {
        // Logic to check date
        validateDate();

    }

    private void validateDate()
    {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date();
        String date2= sdformat.format(date1);
        if (!(date2.compareTo(holidayCalendarBusinessPolicyDTO.getHolidayListDTO().getEffectiveDate()) < 0))
        {
            addBusinessValidationError(new BusinessValidationError("DE001","Validation Failed For Calendar Date "));
        }
        List<HolidayDTO> holidayDTO = holidayCalendarBusinessPolicyDTO.getHolidayListDTO().getHoliday();


        for(HolidayDTO holidayDTO1 : holidayDTO){
            LocalDate effectiveDate= LocalDate.parse(holidayCalendarBusinessPolicyDTO.getHolidayListDTO().getEffectiveDate());
            LocalDate holidayDate=LocalDate.parse(holidayDTO1.getHolidayDate());
            if(effectiveDate.isAfter(holidayDate))
                {
                    addBusinessValidationError(new BusinessValidationError("DE002","Validation Failed For Holiday Date "));
                }
            }
    }

}
