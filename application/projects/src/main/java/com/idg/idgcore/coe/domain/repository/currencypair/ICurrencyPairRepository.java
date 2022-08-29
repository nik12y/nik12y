package com.idg.idgcore.coe.domain.repository.currencypair;

import com.idg.idgcore.coe.domain.entity.currencypair.CurrencyPairEntity;
import com.idg.idgcore.coe.domain.entity.currencypair.CurrencyPairEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICurrencyPairRepository extends JpaRepository<CurrencyPairEntity, CurrencyPairEntityKey> {

    CurrencyPairEntity findByPairId(Integer pairId);
}
