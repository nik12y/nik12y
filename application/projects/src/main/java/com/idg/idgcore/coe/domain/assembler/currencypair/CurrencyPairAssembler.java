package com.idg.idgcore.coe.domain.assembler.currencypair;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.currencypair.CurrencyPairEntity;
import com.idg.idgcore.coe.dto.currencypair.CurrencyPairDTO;
import org.springframework.stereotype.Component;

@Component
public class CurrencyPairAssembler extends Assembler<CurrencyPairDTO, CurrencyPairEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return CurrencyPairDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return CurrencyPairEntity.class;
    }

    @Override
    public CurrencyPairEntity toEntity(CurrencyPairDTO currencyPairDTO) {
        CurrencyPairEntity currencyPairEntity = super.toEntity(currencyPairDTO);
        currencyPairEntity.setThroughCurrency(getCharValueFromBoolean(currencyPairDTO.getThroughCurrency()));
        currencyPairEntity.setQuotationMethods(getCharValueFromBoolean(currencyPairDTO.getQuotationMethods()));
        currencyPairEntity.setSpreadDefinition(getCharValueFromBoolean(currencyPairDTO.getSpreadDefinition()));
        return currencyPairEntity;
    }

    @Override
    public CurrencyPairDTO toDTO(CurrencyPairEntity currencyPairEntity) {
        CurrencyPairDTO currencyPairDTO = super.toDTO(currencyPairEntity);
        currencyPairDTO.setThroughCurrency(getBooleanValueFromChar(currencyPairEntity.getThroughCurrency()));
        currencyPairDTO.setQuotationMethods(getBooleanValueFromChar(currencyPairEntity.getQuotationMethods()));
        currencyPairDTO.setSpreadDefinition(getBooleanValueFromChar(currencyPairEntity.getSpreadDefinition()));
        return currencyPairDTO;
    }
}