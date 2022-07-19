package com.idg.idgcore.coe.dto.purpose;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.COUNTRY;
import static com.idg.idgcore.coe.common.Constants.PURPOSE;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurposeDTO extends CoreEngineBaseDTO {

    private String purposeCode;
    private String purposeName;
    private String purposeDescription;
    private String purposeType;

    @Override
    public String getTaskCode () {
        return PURPOSE;
    }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(COUNTRY);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getPurposeCode();
    }

}
