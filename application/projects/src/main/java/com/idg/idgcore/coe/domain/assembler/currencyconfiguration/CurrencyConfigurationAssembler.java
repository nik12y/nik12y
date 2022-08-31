package com.idg.idgcore.coe.domain.assembler.currencyconfiguration;

import com.idg.idgcore.coe.domain.entity.currencyconfiguration.*;
import com.idg.idgcore.coe.dto.currencyconfiguration.*;
import lombok.extern.slf4j.*;
import org.modelmapper.*;
import org.modelmapper.convention.*;
import org.springframework.stereotype.*;

import javax.annotation.*;

@Slf4j
@Component
public class CurrencyConfigurationAssembler {

    private static final String CLASS_NAME="CurrencyConfigurationAssembler.";
    private static final String ENTERED_STRING="Entered into ";
    private static final String EXIT_STRING="Exited from ";

    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public CurrencyConfigurationEntity convertDtoToEntity (CurrencyDetailsDTO currencyDetailsDTO) {
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"convertEntityToDto() with CurrencyDetailsDTO{}",currencyDetailsDTO);
        }
        CurrencyConfigurationEntity currencyConfigListEntity = modelMapper.map(currencyDetailsDTO, CurrencyConfigurationEntity.class);
        if (log.isInfoEnabled()) {
            log.info(EXIT_STRING+CLASS_NAME+"convertEntityToDto() with following response weekDaysDTO {}",currencyConfigListEntity);
        }
        return currencyConfigListEntity;
    }

    public CurrencyDetailsDTO convertEntityToDto (CurrencyConfigurationDetailsEntity currencyConfigurationDetailsEntity) {
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"convertEntityToDto() with CurrencyConfigurationDetailsEntity{}",currencyConfigurationDetailsEntity);
        }
        CurrencyDetailsDTO currencyDetailsDTO = modelMapper.map(currencyConfigurationDetailsEntity, CurrencyDetailsDTO.class);
        currencyDetailsDTO.setDays(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().getDays());
        currencyDetailsDTO.setHours(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().getHours());
        currencyDetailsDTO.setMinutes(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().getMinutes());
        currencyDetailsDTO.setEnableMt101Remit(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().isEnableMt101Remit());
        currencyDetailsDTO.setEnableMt103Stp(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().isEnableMt103Stp());
        currencyDetailsDTO.setEnableMt101Remit(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().isEnableMt101Remit());
        currencyDetailsDTO.setIndexFlag(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().isIndexFlag());
        currencyDetailsDTO.setEnableMt202Cov(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().isEnableMt202Cov());
        currencyDetailsDTO.setClsCurrencyFlag(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().isClsCurrencyFlag());
        currencyDetailsDTO.setValidateTag50fFlag(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().isValidateTag50fFlag());
        currencyDetailsDTO.setPreferredHoliday1(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().getPreferredHoliday1());
        currencyDetailsDTO.setPreferredHoliday2(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().getPreferredHoliday2());
        currencyDetailsDTO.setPreferredHoliday3(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().getPreferredHoliday3());
        currencyDetailsDTO.setRoundingRule(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().getRoundingRule());
        currencyDetailsDTO.setRoundingUnit(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().getRoundingUnit());
        currencyDetailsDTO.setAmountFormatMaskPtt(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().getAmountFormatMaskPtt());
        currencyDetailsDTO.setEuroTransactionCurrency(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().isEuroTransactionCurrency());
        currencyDetailsDTO.setInLegCurrency(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().getInLegCurrency());
        currencyDetailsDTO.setOutLegCurrency(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().getOutLegCurrency());
        currencyDetailsDTO.setEuroCloseFlag(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().isEuroCloseFlag());
        currencyDetailsDTO.setCreditExchangeLimit(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().getCreditExchangeLimit());
        currencyDetailsDTO.setDebitExchangeLimit(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().getDebitExchangeLimit());
        currencyDetailsDTO.setExtraFieldName(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().getExtraFieldName());
        currencyDetailsDTO.setExtraFiledvalue(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().getExtraFiledvalue());
        currencyDetailsDTO.setCurrencyCountryCode(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().getCurrencyCountryCode());
        currencyDetailsDTO.setCurrencyCountryName(currencyConfigurationDetailsEntity.getCurrencyCutOffRoundingRuleEntity().getCurrencyCountryName());

        if (log.isInfoEnabled()) {
            log.info(EXIT_STRING+CLASS_NAME+"convertEntityToDto() with following response currencyDetailsDTO {}",currencyDetailsDTO);
        }
        return currencyDetailsDTO;

    }

}
