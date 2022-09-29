package com.idg.idgcore.coe.dto.currencyratetype;

import com.idg.idgcore.coe.domain.entity.currencyratetype.CurrencyRateTypeEntity;
import com.idg.idgcore.coe.domain.entity.currencyratetype.CurrencyRateTypeEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICurrencyRateTypeRepository extends JpaRepository<CurrencyRateTypeEntity, CurrencyRateTypeEntityKey> {
    CurrencyRateTypeEntity  findByCurrencyRateType(String currencyRateType);
}
