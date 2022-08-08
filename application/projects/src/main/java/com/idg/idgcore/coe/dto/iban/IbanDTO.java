package com.idg.idgcore.coe.dto.iban;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.*;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import static com.idg.idgcore.coe.common.Constants.IBAN;

@ToString (callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude (JsonInclude.Include.NON_NULL)
public class IbanDTO extends CoreEngineBaseDTO {
    private String ibanCountryCode;
    private Integer ibanCountryPosition;
    private Integer ibanCountryCodeLength;
    private Integer ibanCheckDigitPosition;
    private Integer ibanCheckDigitLength;
    private String ibanNationalIdLength;
    private Integer ibanTotalLength;
    private IbanBbanDTO ibanBban;



    @Override
    public String getTaskCode () {
        return IBAN;
    }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(IBAN);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getIbanCountryCode();
    }

}
