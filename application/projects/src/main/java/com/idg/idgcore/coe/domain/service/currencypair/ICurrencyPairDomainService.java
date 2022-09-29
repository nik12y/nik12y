package com.idg.idgcore.coe.domain.service.currencypair;

import com.idg.idgcore.coe.domain.entity.currencypair.CurrencyPairEntity;
import com.idg.idgcore.coe.dto.currencypair.CurrencyPairDTO;

import java.util.List;

public interface ICurrencyPairDomainService {

    CurrencyPairEntity getConfigurationByCode(CurrencyPairDTO currencyPairDTO);

    List<CurrencyPairEntity> getCurrencyPairs();

    CurrencyPairEntity getCurrencyPairById(Integer pairId);

    void save(CurrencyPairDTO currencyPairDTO);

    CurrencyPairEntity getByCurrency1AndCurrency2AndEntityTypeAndEntityCode (String currency1, String currency2, String entityType, String entityCode);
}