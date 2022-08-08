package com.idg.idgcore.coe.domain.entity.iban;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class IbanBbanEntity implements Serializable {
    private Integer bankIdentifierPosition;
    private String bankIdentifierLength;
    private String branchIdentifierPosition;
    private String branchIdentifierLength;
    private Integer accountNumberPosition;
    private Integer accountNumberLength;

}
