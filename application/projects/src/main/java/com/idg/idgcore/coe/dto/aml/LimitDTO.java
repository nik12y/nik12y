package com.idg.idgcore.coe.dto.aml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class LimitDTO {

    private String limitCode;
    private Float limitAmount;
    private String limitCurrency;
}
