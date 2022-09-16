package com.idg.idgcore.coe.dto.currencyConfiguration;

import com.fasterxml.jackson.annotation.*;
import com.idg.idgcore.coe.dto.base.*;
import lombok.*;
import lombok.experimental.*;

import java.util.*;

import static com.idg.idgcore.coe.common.Constants.*;

@ToString (callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude (JsonInclude.Include.NON_NULL)
public class CurrencyConfigurationDTO extends CoreEngineBaseDTO {

    private String currencyCode;
    private String currencyName;
    private short isoNumericCode;
    private String countryCode;
    private String countryName;
    private String locale;
    private String alternateCurrencyCode;
    private String currencySymbol;
    private String currencyDescription;
    private String currencyCategory;
    private short decimals;
    private String dayDivisor;
    private short spotDays;
    private short fxNettingDays;
    private short settlementDays;
    private String calenderId;
    private boolean beforeFormattingFlag;
    private boolean afterFormattingFlag;
    private boolean includeSpaceFormattingFlag;
    private CurrencyCutOffRoundingDTO CurrencyCutOff;
    private List<CurrencyCountryMapDTO> currencyCountryMap;
    private List<CurrencyExtraFieldMapDTO> currencyExtraFieldMap;



    @Override
    public String getTaskCode () {
        return CURRENCY;
    }

    @Override
    public void setTaskCode(String taskCode)
    {
        super.setTaskCode(CURRENCY);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getCurrencyCode();
    }

}
