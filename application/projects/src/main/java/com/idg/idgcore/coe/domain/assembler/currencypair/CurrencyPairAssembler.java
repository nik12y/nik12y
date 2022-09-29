package com.idg.idgcore.coe.domain.assembler.currencypair;

import com.idg.idgcore.coe.domain.entity.currencypair.CurrencyPairEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.currencypair.CurrencyPairDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class CurrencyPairAssembler {

    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public CurrencyPairEntity convertDtoToEntity (CurrencyPairDTO currencyPairDTO) {
        CurrencyPairEntity currencyPairEntity = modelMapper.map(currencyPairDTO, CurrencyPairEntity.class);
        currencyPairEntity.setThroughCurrency(getCharValueFromBoolean(currencyPairDTO.getThroughCurrency()));
        currencyPairEntity.setQuotationMethods(getCharValueFromBoolean(currencyPairDTO.getQuotationMethods()));
        currencyPairEntity.setSpreadDefinition(getCharValueFromBoolean(currencyPairDTO.getSpreadDefinition()));
        return currencyPairEntity;
    }

    public CurrencyPairDTO convertEntityToDto (CurrencyPairEntity currencyPairEntity) {
            CurrencyPairDTO currencyPairDTO = modelMapper.map(currencyPairEntity,
                    CurrencyPairDTO.class);
            currencyPairDTO.setThroughCurrency(getBooleanValueFromChar(currencyPairEntity.getThroughCurrency()));
            currencyPairDTO.setQuotationMethods(getBooleanValueFromChar(currencyPairEntity.getQuotationMethods()));
            currencyPairDTO.setSpreadDefinition(getBooleanValueFromChar(currencyPairEntity.getSpreadDefinition()));
            return currencyPairDTO;
    }

    public CurrencyPairDTO setAuditFields (MutationEntity mutationEntity, CurrencyPairDTO currencyPairDTO) {
        currencyPairDTO.setAction(mutationEntity.getAction());
        currencyPairDTO.setAuthorized(mutationEntity.getAuthorized());
        currencyPairDTO.setRecordVersion(mutationEntity.getRecordVersion());
        currencyPairDTO.setStatus(mutationEntity.getStatus());
        currencyPairDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        currencyPairDTO.setCreatedBy(mutationEntity.getCreatedBy());
        currencyPairDTO.setCreationTime(mutationEntity.getCreationTime());
        currencyPairDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        currencyPairDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        currencyPairDTO.setTaskCode(mutationEntity.getTaskCode());
        currencyPairDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return currencyPairDTO;
    }

    public char getCharValueFromBoolean (boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar (Character value) {
        return value.equals(CHAR_Y);
    }

}