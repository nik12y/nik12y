package com.idg.idgcore.coe.dto.bankidentifier;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.BANK_IDENTIFIER;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankIdentifierDTO extends CoreEngineBaseDTO {

    private String bankIdentifierCode;
    private String bankIdentifierCodeName;
    private String bankAddress1;
    private String bankAddress2;
    private String bankAddress3;
    private String bankAddress4;
    private String internalAddress;

    @Override
    public String getTaskCode () {
        return BANK_IDENTIFIER;
    }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(BANK_IDENTIFIER);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getBankIdentifierCode();
    }

}
