package com.idg.idgcore.coe.domain.service.currencyratetype;

import com.idg.idgcore.coe.domain.entity.currencyratetype.CurrencyRateTypeEntity;
import com.idg.idgcore.coe.dto.currencyratetype.CurrencyRateTypeDTO;

import java.util.List;

public interface ICurrencyRateTypeDomainService {
    CurrencyRateTypeEntity getConfigurationByCode(CurrencyRateTypeDTO currencyRateTypeDTO);
    List<CurrencyRateTypeEntity> getCurrencyRateTypes();
    CurrencyRateTypeEntity getCurrencyRateTypeByType(String currencyRateType);
    void save(CurrencyRateTypeDTO currencyRateTypeDTO);
}
