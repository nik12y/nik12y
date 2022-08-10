package com.idg.idgcore.coe.domain.assembler.currencyconfiguration;

import com.idg.idgcore.coe.domain.entity.currencyconfiguration.*;
import com.idg.idgcore.coe.dto.currencyconfiguration.*;
import org.modelmapper.*;
import org.modelmapper.convention.*;
import org.springframework.stereotype.*;

import javax.annotation.*;

import static com.idg.idgcore.coe.common.Constants.*;

@Component
public class CurrencyConfigurationAssembler {

    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public CurrenciesDTO convertEntityToDto (CurrenciesEntity countryEntity) {
        CurrenciesDTO currenciesDTO = modelMapper.map(countryEntity, CurrenciesDTO.class);
        currenciesDTO.setCurrencyCode(countryEntity.getCurrencyCode());
        currenciesDTO.setCurrencyName(countryEntity.getCurrencyName());
        currenciesDTO.setCountryCode(countryEntity.getCountryCode());
        currenciesDTO.setCountryName(countryEntity.getCountryName());
        currenciesDTO.setIsoCode(countryEntity.getIsoNumericCode());
        currenciesDTO.setLocale(countryEntity.getLocale());
        return currenciesDTO;
    }

    public WeekDaysDTO convertEntityToDto (WeekDaysDetailsEntity weekDaysEntity) {
        WeekDaysDTO weekDaysDTO = modelMapper.map(weekDaysEntity, WeekDaysDTO.class);
        weekDaysDTO.setWeekDays(weekDaysDTO.getWeekDays());
        weekDaysDTO.setAbbreviation(weekDaysDTO.getAbbreviation());
        return weekDaysDTO;
    }

    public DayDivisorDTO convertEntityToDto (DayDivisorDetailsEntity dayDivisorEntity) {
        DayDivisorDTO dayDivisorDTO = modelMapper.map(dayDivisorEntity, DayDivisorDTO.class);
        dayDivisorDTO.setSrNo(dayDivisorDTO.getSrNo());
        dayDivisorDTO.setInterestMethod(dayDivisorDTO.getInterestMethod());
        return dayDivisorDTO;
    }

    public RoundingRuleDTO convertEntityToDto (RoundingRulesDetailsEntity roundingRulesEntity) {
        RoundingRuleDTO roundingRuleDTO = modelMapper.map(roundingRulesEntity, RoundingRuleDTO.class);
        roundingRuleDTO.setRoundingRule(roundingRulesEntity.getRoundingRule());
        roundingRuleDTO.setRoundingRuleInstructionClass(roundingRuleDTO.getRoundingRuleInstructionClass());
        return roundingRuleDTO;
    }

    public char getCharValueFromBoolean (boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar (Character value) {
        return value.equals(CHAR_Y);
    }
}
