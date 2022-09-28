package com.idg.idgcore.coe.app.service.holidaylist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.holidaylist.HolidayListDTO;
import com.idg.idgcore.coe.dto.holidaylist.HolidayListResponse;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

public interface IHolidayListApplicationService extends IBaseApplicationService {

    TransactionStatus processHolidayList (SessionContext sessionContext, HolidayListDTO dto) throws FatalException,
            JsonProcessingException;
    void save (HolidayListDTO holidayListDTO);
    HolidayListResponse getHolidayListById(SessionContext sessionContext, HolidayListDTO holidayListDTO)
            throws FatalException, JsonProcessingException;
    HolidayListResponse getHolidayLists (SessionContext sessionContext)
            throws FatalException;
    HolidayListResponse searchHolidayList(SessionContext sessionContext, HolidayListDTO holidayListDTO)throws FatalException;
}
