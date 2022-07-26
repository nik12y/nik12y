package com.idg.idgcore.coe.dto.capt;

import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static com.idg.idgcore.coe.common.Constants.CAPT;


//@Data
@ToString (callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties (ignoreUnknown = true)
public class CaptDTO extends CoreEngineBaseDTO {
    private String clearingPaymentTypeCode;
    private String clearingPaymentTypeName;
    private String networkType;
    private boolean compositeClearingOrPaymentCalendar;
    private String weeklyOff1;
    private String weeklyOff2;
    private String weeklyOff3;


    @Override
    public String getTaskCode () {
        return CAPT;
    }

    @Override
    public String getTaskIdentifier () {
        return this.getClearingPaymentTypeCode();
    }

}
