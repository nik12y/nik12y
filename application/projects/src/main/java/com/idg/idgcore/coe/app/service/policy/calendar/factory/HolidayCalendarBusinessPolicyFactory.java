package com.idg.idgcore.coe.app.service.policy.calendar.factory;

import com.idg.idgcore.coe.app.service.policy.calendar.HolidayCalendarBusinessPolicy;
import com.idg.idgcore.coe.common.CalendarBusinessPolicyConstants;
import com.idg.idgcore.coe.dto.policy.calendar.HolidayCalendarBusinessPolicyDTO;
import com.idg.idgcore.domain.policy.AbstractBusinessPolicy;
import org.springframework.stereotype.Component;

@Component
public class HolidayCalendarBusinessPolicyFactory {

    // Implement singleton here
    //

    private static HolidayCalendarBusinessPolicyFactory holidayCalendarBusinessPolicyFactory = null;

    private HolidayCalendarBusinessPolicyFactory() {

    }

    public AbstractBusinessPolicy getPolicyInstance(String policyID, HolidayCalendarBusinessPolicyDTO holidayCalendarBusinessPolicyDTO) {
        switch (policyID) {
            case CalendarBusinessPolicyConstants.HOLIDAY_CALENDAR_BUSINESS_POLICY:
                return new HolidayCalendarBusinessPolicy(holidayCalendarBusinessPolicyDTO);
            default:
                throw new RuntimeException("Business Policy Not defined ");
        }

    }

    public HolidayCalendarBusinessPolicyFactory getInstance() {
        if (holidayCalendarBusinessPolicyFactory == null) {
            synchronized (HolidayCalendarBusinessPolicyFactory.class) {
                holidayCalendarBusinessPolicyFactory = new HolidayCalendarBusinessPolicyFactory();
//                return holidayCalendarBusinessPolicyFactory;
            }
        }
        return holidayCalendarBusinessPolicyFactory;
    }
}
