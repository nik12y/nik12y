package com.idg.idgcore.coe.endpoint.graphql.resolver.holidaylist;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.holidaylist.IHolidayListApplicationService;
import com.idg.idgcore.coe.dto.holidaylist.HolidayListDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HolidayListMutationResolver implements GraphQLMutationResolver{

    @Autowired
    private IHolidayListApplicationService holidayListApplicationService;

    public TransactionStatus processHolidayList(SessionContext sessionContext, HolidayListDTO holidayListDTO)
            throws FatalException, JsonProcessingException {
        return this.holidayListApplicationService.processHolidayList(sessionContext, holidayListDTO);
    }
}
