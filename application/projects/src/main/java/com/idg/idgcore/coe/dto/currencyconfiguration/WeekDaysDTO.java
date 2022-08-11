package com.idg.idgcore.coe.dto.currencyconfiguration;

import com.idg.idgcore.coe.domain.entity.currencyconfiguration.*;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
public class WeekDaysDTO {

    private String weekDays;

    private String abbreviation;

    public WeekDaysDTO(WeekDaysDetailsEntity weekDaysEntity){
        this.weekDays = weekDaysEntity.getWeekDays();
        this.abbreviation = weekDaysEntity.getAbbreviation();
    }
}
