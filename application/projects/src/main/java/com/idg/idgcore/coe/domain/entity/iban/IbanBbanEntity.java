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
    @Column (name="bank_identifier_position")
    private Integer bankIdentifierPosition;
    @Column (name="bank_identifier_length")
    private String bankIdentifierLength;
    @Column (name="branch_identifier_position")
    private String branchIdentifierPosition;
    @Column (name="branch_identifier_length")
    private String branchIdentifierLength;
    @Column (name="account_number_position")
    private Integer accountNumberPosition;
    @Column (name="account_number_length")
    private Integer accountNumberLength;
}
