package com.idg.idgcore.coe.dto.bankparameter;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankParameterContactInfoDTO {
    private String telephoneNo;
    private String faxNo;
    private String emailId;
    private String bankWebsite;

}
