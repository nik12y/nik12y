package com.idg.idgcore.coe.dto.currencyAmountInWord;

import com.fasterxml.jackson.annotation.*;
import com.idg.idgcore.coe.dto.base.*;
import lombok.*;
import lombok.experimental.*;

import static com.idg.idgcore.coe.common.Constants.*;

@ToString (callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude (JsonInclude.Include.NON_NULL)
public class CurrencyAmountInWordDTO extends CoreEngineBaseDTO {

    private String currencyCode;
    private String languageCode;
    private String languageName;
    private String localeCode;
    private String localeName;
    private String beforeDecimalVerbose;
    private String afterDecimalVerbose;
    private String suffixText;
    private String prefixTextWithCurrency;
    private boolean fraction;
    private String textBetween;
    private int amountInFigures;
    private String amountInWords;


    @Override
    public String getTaskCode () {
        return AMOUNT_IN_WORDS;
    }

    @Override
    public void setTaskCode(String taskCode)
    {
        super.setTaskCode(AMOUNT_IN_WORDS);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getCurrencyCode();
    }
}
