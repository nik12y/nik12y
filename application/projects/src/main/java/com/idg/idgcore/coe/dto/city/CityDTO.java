package com.idg.idgcore.coe.dto.city;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idg.idgcore.coe.dto.base.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import static com.idg.idgcore.coe.common.Constants.CITY;

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

    public static Class<? extends CoreEngineBaseDTO> getSpecificType() {
        return CityDTO.class;
    }

}
