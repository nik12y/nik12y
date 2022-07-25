package com.idg.idgcore.coe.dto.bankgroup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.GROUP_BANKING;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupBankingDTO extends CoreEngineBaseDTO {

    private String bankGroupCode;
    private String bankGroupName;

    @Override
    public String getTaskCode () {
        return GROUP_BANKING;
    }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(GROUP_BANKING);
    }
    @Override
    public String getTaskIdentifier () {
        return this.getBankGroupCode();
    }

}
