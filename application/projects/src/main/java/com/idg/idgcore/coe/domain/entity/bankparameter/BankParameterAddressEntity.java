package com.idg.idgcore.coe.domain.entity.bankparameter;

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
public class BankParameterAddressEntity implements Serializable {
    @Column (name="bank_address1")
    private String bankAddress1;
    @Column (name="bank_address2")
    private String bankAddress2;
    @Column (name="bank_address3")
    private String bankAddress3;
    @Column (name="bank_address4")
    private String bankAddress4;
    @Column (name="country_code")
    private String countryCode;
    @Column (name="state_code")
    private String stateCode;
    @Column (name="city_code")
    private String cityCode;

}
