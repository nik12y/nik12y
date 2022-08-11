package com.idg.idgcore.coe.dto.bankparameter;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankParameterAddressDTO  {
    private String bankAddress1;
    private String bankAddress2;
    private String bankAddress3;
    private String bankAddress4;
    private String country;
    private String state;
    private String city;


}
