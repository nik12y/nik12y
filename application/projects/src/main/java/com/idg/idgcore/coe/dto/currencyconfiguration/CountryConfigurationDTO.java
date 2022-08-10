package com.idg.idgcore.coe.dto.currencyconfiguration;

import com.idg.idgcore.coe.domain.entity.currencyconfiguration.*;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
public class CountryConfigurationDTO {

    private String currencyCode;
    private String countryName;
    private String countryCode;

    public CountryConfigurationDTO(CountryDetailsEntity countryConfigurationEntity){
        this.currencyCode = countryConfigurationEntity.getCurrencyCode();
        this.countryName = countryConfigurationEntity.getCountryName();
        this.countryCode = countryConfigurationEntity.getCountryCode();
    }
}
