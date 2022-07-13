package com.idg.idgcore.coe.dto.country;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.*;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.COUNTRY;

@ToString (callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude (JsonInclude.Include.NON_NULL)
public class CountryDTO extends CoreEngineBaseDTO {
    private String countryCode;
    private String countryName;
    private Integer numericCountryCode;
    private String alternateCountryCode;
    private String regionCode;
    private String limitCurrency;
    private Float overallLimit;
    private boolean ibanRequired;
    private boolean euMember;
    private boolean clearingCodeBicPlus;
    private boolean generateMt205;
    private boolean defaultClearingNetwork;

    @Override
    public String getTaskCode () {
        return COUNTRY;
    }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(COUNTRY);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getCountryCode();
    }
}
