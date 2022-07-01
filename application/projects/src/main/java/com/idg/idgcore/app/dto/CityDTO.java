package com.idg.idgcore.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.common.Constants.TaskCode.CITY;

@ToString (callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties (ignoreUnknown = true)
public class CityDTO extends CoreEngineBaseDTO {

    private String cityCode;
    private String cityName;
    private String timeZone;
    private String countryCode;
    private String stateCode;

    @Override
    public String getTaskCode () {
        return CITY;
    }

    @Override
    public String getTaskIdentifier () {
        return this.getCityCode();
    }

}
