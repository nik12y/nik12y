package com.idg.idgcore.coe.dto.currencyconfiguration;

import com.idg.idgcore.coe.domain.entity.currencyconfiguration.*;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
public class DayDivisorDTO {

    private int srNo;
    private String interestMethod;

    public DayDivisorDTO(DayDivisorDetailsEntity dayDivisorEntity){
        this.srNo = dayDivisorEntity.getSrNo();
        this.interestMethod = String.valueOf(dayDivisorEntity.getInterestMethod());
    }
}
