package com.idg.idgcore.coe.dto.state;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.STATE;


@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude (JsonInclude.Include.NON_NULL)
public class StateDTO extends CoreEngineBaseDTO {

    private String stateCode;
    private String stateName;
    private String countryCode;

    @Override
    public String getTaskCode () {
        return STATE;
    }

    @Override
    public void setTaskCode(String taskCode)
    {
        super.setTaskCode(STATE);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getStateCode();
    }

}
