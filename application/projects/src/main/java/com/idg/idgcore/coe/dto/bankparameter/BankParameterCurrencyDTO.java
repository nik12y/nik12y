package com.idg.idgcore.coe.dto.bankparameter;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankParameterCurrencyDTO {
    private String currencyCode;
    private String currencyName;
    private Boolean isDenominationTrackingRequired;
    private String currencyOfDenomination;
    private String denominationTrackingCurrency;

}
