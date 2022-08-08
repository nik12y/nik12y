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
public class BankParameterCurrencyEntity implements Serializable {
    @Column (name="currency_code")
    private String currencyCode;
    @Column (name="currency_name")
    private String currencyName;
    @Column (name="is_denomination_tracking_required")
    private char isDenominationTrackingRequired;
    @Column (name="currency_denomination")
    private String currencyDenomination;
    @Column (name="currency_denomination_tracking")
    private String currencyDenominationTracking;
}
