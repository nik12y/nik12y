package com.idg.idgcore.coe.dto.currencyconfiguration;

import com.idg.idgcore.coe.domain.entity.currencyconfiguration.*;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
public class CurrencyConfigurationDTO {

    private String currencyCode;
    private String currencyName;
    private short isoNumericCode;

    public CurrencyConfigurationDTO(CurrencyConfigurationDetailsEntity currencyConfigurationEntity){
        this.currencyCode = currencyConfigurationEntity.getCurrencyCode();
        this.currencyName = currencyConfigurationEntity.getCurrencyName();
        this.isoNumericCode = currencyConfigurationEntity.getIsoNumericCode();
    }
}
