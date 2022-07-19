package com.idg.idgcore.coe.dto.bank;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.BANK;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankDTO extends CoreEngineBaseDTO {

    private String bankCode;
    private String bankCodeRegulatory;
    private String bankName;

    @Override
    public String getTaskCode () {
        return BANK;
    }

    @Override
    public String getTaskIdentifier () {
        return this.getBankCode();
    }


}
