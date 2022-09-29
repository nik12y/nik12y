package com.idg.idgcore.coe.dto.iban;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IbanBbanDTO {
    private Integer bankIdentifierPosition;
    private Integer bankIdentifierLength;
    private Integer branchIdentifierPosition;
    private Integer branchIdentifierLength;
    private Integer accountNumberPosition;
    private Integer accountNumberLength;
}
