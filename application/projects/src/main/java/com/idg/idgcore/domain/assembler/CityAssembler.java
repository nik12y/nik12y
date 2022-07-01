package com.idg.idgcore.domain.assembler;

import com.idg.idgcore.app.dto.CityDTO;
import com.idg.idgcore.domain.entity.CityEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.idg.idgcore.common.Constants.CHAR_N;
import static com.idg.idgcore.common.Constants.CHAR_Y;

@Component
public class CityAssembler {

    private ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public CityEntity convertDtoToEntity(CityDTO cityDTO) {
        CityEntity cityEntity = modelMapper.map(cityDTO, CityEntity.class);
        cityEntity.setCityCode(cityDTO.getCityCode());
        cityEntity.setCityName(cityDTO.getCityName());
        cityEntity.setTimeZone(cityDTO.getTimeZone());
        cityEntity.setCountryCode(cityDTO.getCountryCode());
        cityEntity.setStateCode(cityDTO.getStateCode());

        //Check why audit values are not updating
        return cityEntity;
    }

    public CityDTO convertEntityToDto(CityEntity cityEntity) {
        CityDTO cityDTO = modelMapper.map(cityEntity, CityDTO.class);
        cityDTO.setCityCode(cityEntity.getCityCode());
        cityDTO.setCityName(cityEntity.getCityName());
        cityDTO.setTimeZone(cityEntity.getTimeZone());
        cityDTO.setCountryCode(cityEntity.getCountryCode());
        cityDTO.setStateCode(cityEntity.getStateCode());

        return modelMapper.map(cityEntity, CityDTO.class);
    }

    public char getCharValueFromBoolean(boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar(Character value) {
        return value.equals(CHAR_Y);

    }
}
