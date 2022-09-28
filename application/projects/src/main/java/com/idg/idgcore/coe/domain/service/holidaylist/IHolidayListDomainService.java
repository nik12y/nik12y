package com.idg.idgcore.coe.domain.service.holidaylist;

import com.idg.idgcore.coe.domain.entity.holidaylist.HolidayListEntity;
import com.idg.idgcore.coe.dto.holidaylist.HolidayListDTO;

import java.util.List;

public interface IHolidayListDomainService {

    HolidayListEntity getConfigurationByCode (HolidayListDTO holidayListDTO);
    List<HolidayListEntity> getHolidayLists();
    HolidayListEntity getHolidayListById (String holidayListId);
    void save (HolidayListDTO holidayListDTO);
}
