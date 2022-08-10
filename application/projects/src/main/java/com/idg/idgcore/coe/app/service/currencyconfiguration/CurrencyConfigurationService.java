package com.idg.idgcore.coe.app.service.currencyconfiguration;

import com.idg.idgcore.coe.domain.assembler.currencyconfiguration.*;
import com.idg.idgcore.coe.domain.repository.currencyconfiguration.*;
import com.idg.idgcore.coe.dto.currencyconfiguration.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
public class CurrencyConfigurationService implements ICurrencyConfigurationService {

    @Autowired
    private ICurrenciesRepository currenciesRepository;

    @Autowired
    private IWeekDaysRepository weekDaysRepository;

    @Autowired
    private IDayDivisorRepository dayDivisorRepository;

    @Autowired
    private IRoundingRuleRepository roundingRuleRepository;
    @Autowired
    private ICurrencyConfigurationRepository currencyConfigurationRepository;

    @Autowired
    private CurrencyConfigurationAssembler currencyConfigurationAssembler;

    public List<CurrenciesDTO> getAllCurrencyList(SessionContext sessionContext) throws
            FatalException {
        List<CurrenciesDTO> currenciesList = null;
        try {
            currenciesList= new ArrayList<>();
            currenciesList.addAll(currenciesRepository.findAll().stream()
                    .map(entity -> currencyConfigurationAssembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
        }catch (Exception exception) {
            throw new FatalException("Exception in getAllCurrencyList()"+ exception);
        }
        return currenciesList;
    }

    public List<WeekDaysDTO> getWeekAllDays(SessionContext sessionContext) throws FatalException{

        List<WeekDaysDTO> weekDaysList = null;
        try {
            weekDaysList = new ArrayList<>();
            weekDaysList.addAll(weekDaysRepository.findAll().stream()
                    .map(entity -> currencyConfigurationAssembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
        }catch (Exception exception) {
            throw new FatalException("Exception in getWeekAllDays()"+ exception);
        }
        return weekDaysList;
    }

    public List<DayDivisorDTO> getDayDivisor(SessionContext sessionContext) throws FatalException{
        List<DayDivisorDTO> dayDivisorList = null;
        try {
            dayDivisorList = new ArrayList<>();
            dayDivisorList.addAll(dayDivisorRepository.findAll().stream()
                    .map(entity ->currencyConfigurationAssembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
        }catch (Exception exception){
            throw new FatalException("Exception in getDayDivisor()"+ exception);
        }
        return dayDivisorList;
    }

    public List<RoundingRuleDTO> getRoundingRules(SessionContext sessionContext) throws FatalException{
        List<RoundingRuleDTO> roundingRuleList = null;

        try {
            roundingRuleList = new ArrayList<>();
            roundingRuleList.addAll(roundingRuleRepository.findAll().stream()
                    .map(entity -> currencyConfigurationAssembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
        }catch (Exception exception){
            throw new FatalException("Exception in getRoundingRules()"+ exception);
        }
        return roundingRuleList;
    }

    public CurrencyDetailsDTO getCurrencyDetails(SessionContext sessionContext,String currencyCode) throws FatalException{
        CurrencyDetailsDTO currencyDetailsDTO = null;
        try {
            if(currencyConfigurationRepository.findById(currencyCode).isPresent()){
                currencyDetailsDTO = new CurrencyDetailsDTO(currencyConfigurationRepository.findById(currencyCode).get());
            }
        }catch (Exception exception){
            throw new FatalException("Exception in getCurrencyDetails()"+ exception);
        }
        return currencyDetailsDTO;
    }

}
