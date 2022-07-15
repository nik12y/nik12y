package com.idg.idgcore.coe.domain.assembler.country;

import com.idg.idgcore.coe.domain.entity.country.CountryEntity;
import com.idg.idgcore.coe.dto.country.CountryDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class CountryAssembler {
    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public CountryEntity convertDtoToEntity (CountryDTO countryDTO) {
        CountryEntity countryEntity = modelMapper.map(countryDTO, CountryEntity.class);
        countryEntity.setCountryCodeAlternate(countryDTO.getAlternateCountryCode());
        countryEntity.setCountryCodeMachine(countryDTO.getNumericCountryCode());
        countryEntity.setCurrencyLimit(countryDTO.getLimitCurrency());
        countryEntity.setIsBicCode(getCharValueFromBoolean(countryDTO.isClearingCodeBicPlus()));
        countryEntity.setIsClearingNetwork(
                getCharValueFromBoolean(countryDTO.isDefaultClearingNetwork()));
        countryEntity.setIsEuMember(getCharValueFromBoolean(countryDTO.isEuMember()));
        countryEntity.setIsIban(getCharValueFromBoolean(countryDTO.isIbanRequired()));
        countryEntity.setIsMtGenerate(getCharValueFromBoolean(countryDTO.isGenerateMt205()));
        return countryEntity;
    }

    public CountryDTO convertEntityToDto (CountryEntity countryEntity) {
        CountryDTO countryDTO = modelMapper.map(countryEntity, CountryDTO.class);
        countryDTO.setAlternateCountryCode(countryEntity.getCountryCodeAlternate());
        countryDTO.setNumericCountryCode(countryEntity.getCountryCodeMachine());
        countryDTO.setLimitCurrency(countryEntity.getCurrencyLimit());
        countryDTO.setClearingCodeBicPlus(getBooleanValueFromChar(countryEntity.getIsBicCode()));
        countryDTO.setDefaultClearingNetwork(
                getBooleanValueFromChar(countryEntity.getIsClearingNetwork()));
        countryDTO.setEuMember(getBooleanValueFromChar(countryEntity.getIsEuMember()));
        countryDTO.setIbanRequired(getBooleanValueFromChar(countryEntity.getIsIban()));
        countryDTO.setGenerateMt205(getBooleanValueFromChar(countryEntity.getIsMtGenerate()));
        return countryDTO;
    }

    public char getCharValueFromBoolean (boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar (Character value) {
        return value.equals(CHAR_Y);
    }

}
