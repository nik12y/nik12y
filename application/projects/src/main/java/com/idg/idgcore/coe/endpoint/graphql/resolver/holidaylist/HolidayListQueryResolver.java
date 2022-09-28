package com.idg.idgcore.coe.endpoint.graphql.resolver.holidaylist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.holidaylist.IHolidayListApplicationService;
import com.idg.idgcore.coe.dto.holidaylist.HolidayListDTO;
import com.idg.idgcore.coe.dto.holidaylist.HolidayListResponse;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class HolidayListQueryResolver implements GraphQLQueryResolver{


    @Autowired
    private IHolidayListApplicationService holidayListApplicationService;

    public HolidayListResponse getHolidayListByID(SessionContext sessionContext, HolidayListDTO holidayListDTO)
            throws FatalException, JsonProcessingException {
        return this.holidayListApplicationService.getHolidayListById(sessionContext, holidayListDTO);
    }

    public HolidayListResponse getHolidayLists(SessionContext sessionContext)
            throws FatalException {
        return this.holidayListApplicationService.getHolidayLists(sessionContext);
    }

    public HolidayListResponse searchHolidayList (SessionContext sessionContext, HolidayListDTO holidayListDTO) throws FatalException {
        return this.holidayListApplicationService.searchHolidayList(sessionContext, holidayListDTO);
    }


}
