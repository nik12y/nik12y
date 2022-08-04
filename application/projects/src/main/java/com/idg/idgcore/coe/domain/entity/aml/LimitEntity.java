package com.idg.idgcore.coe.domain.entity.aml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class LimitEntity {

    private String limitCode;
    private Float limitAmount;
    private String limitCurrency;

}
