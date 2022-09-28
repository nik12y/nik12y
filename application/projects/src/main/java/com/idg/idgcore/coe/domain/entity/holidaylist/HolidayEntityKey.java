package com.idg.idgcore.coe.domain.entity.holidaylist;


import com.idg.idgcore.domain.AbstractDomainKey;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public class HolidayEntityKey extends AbstractDomainKey
        implements Serializable {

    @Id
    @Column(name="holiday_id")
    private Integer holidayId;

    @Id
    @Column(name="holiday_list_id")
    private String holidayListId;


    @Override
    public String keyAsString() {
        return holidayId + holidayListId;
    }
}
