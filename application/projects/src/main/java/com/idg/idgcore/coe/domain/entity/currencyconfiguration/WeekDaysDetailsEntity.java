package com.idg.idgcore.coe.domain.entity.currencyconfiguration;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "week_days_meta_info")
public class WeekDaysDetailsEntity {

    @Id
    @Column(name = "week_days")
    private String weekDays;

    @Column(name = "abbreviation")
    private String abbreviation;
}
