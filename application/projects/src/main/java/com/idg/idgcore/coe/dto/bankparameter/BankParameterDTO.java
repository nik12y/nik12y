package com.idg.idgcore.coe.dto.bankparameter;

import com.fasterxml.jackson.annotation.*;
import com.idg.idgcore.coe.dto.base.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.ToString;

import static com.idg.idgcore.coe.common.Constants.BANKPARAMETER;

@ToString (callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude (JsonInclude.Include.NON_NULL)
public class BankParameterDTO extends CoreEngineBaseDTO {
    private String bankCode;
    private String bankName;
    private String regulatoryBankCode;
    private String bankConciseName;
    private String groupBankingCode;
    private String lifeCycleId;
    private String referenceNo;

    private BankParameterAddressDTO bankParameterAddress;
    private BankParameterContactInfoDTO bankParameterContactInfo;
    private BankParameterCurrencyDTO bankParameterCurrency;
    private BankParameterPreferencesDTO bankParameterPreferences;
    private BankParameterForOdLoanDTO bankParameterForOdLoan;

    @Override
    public String getTaskCode () {
        return BANKPARAMETER;
    }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(BANKPARAMETER);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getBankCode();
    }

}
