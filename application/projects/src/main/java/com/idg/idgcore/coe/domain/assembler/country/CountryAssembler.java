package com.idg.idgcore.coe.domain.assembler.country;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.country.CountryEntity;
import com.idg.idgcore.coe.dto.country.CountryDTO;
import org.springframework.stereotype.Component;

@Component
public class CountryAssembler extends Assembler<CountryDTO, CountryEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return CountryDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return CountryEntity.class;
    }

    @Override
    public CountryEntity toEntity (CountryDTO countryDTO) {
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

    @Override
    public CountryDTO toDTO (CountryEntity countryEntity) {
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

}
