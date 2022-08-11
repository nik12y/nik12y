package com.idg.idgcore.coe.dto.currencyconfiguration;

import com.idg.idgcore.coe.domain.entity.currencyconfiguration.*;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
public class CurrenciesDTO {

    private String currencyCode;

    private String currencyName;

    private short isoCode;

    private String countryName;

    private String countryCode;

    private String locale;

    public CurrenciesDTO(CurrenciesEntity currenciesEntity){
        this.currencyCode = currenciesEntity.getCurrencyCode();
        this.currencyName = currenciesEntity.getCurrencyName();
        this.isoCode = currenciesEntity.getIsoNumericCode();
        this.countryName = currenciesEntity.getCountryName();
        this.countryCode = currenciesEntity.getCountryCode();
        this.locale = currenciesEntity.getLocale();

    }

}
