package com.idg.idgcore.app.dto;

import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static com.idg.idgcore.common.Constants.TaskCode.COUNTRY;

@ToString (callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties (ignoreUnknown = true)
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
    public String getTaskIdentifier () {
        return this.getCountryCode();
    }

}
