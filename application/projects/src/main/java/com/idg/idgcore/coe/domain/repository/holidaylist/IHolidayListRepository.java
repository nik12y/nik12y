package com.idg.idgcore.coe.domain.repository.holidaylist;

import com.idg.idgcore.coe.domain.entity.holidaylist.HolidayListEntity;
import com.idg.idgcore.coe.domain.entity.holidaylist.HolidayListEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHolidayListRepository extends JpaRepository<HolidayListEntity, HolidayListEntityKey> {
    HolidayListEntity findByHolidayListId (String holidayListId);
}
