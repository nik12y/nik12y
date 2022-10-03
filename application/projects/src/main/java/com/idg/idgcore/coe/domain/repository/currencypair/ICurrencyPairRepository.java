package com.idg.idgcore.coe.domain.repository.currencypair;

import com.idg.idgcore.coe.domain.entity.currencypair.CurrencyPairEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICurrencyPairRepository extends JpaRepository<CurrencyPairEntity, Integer> {

    CurrencyPairEntity getByCurrency1AndCurrency2AndEntityTypeAndEntityCode (String currency1, String currency2, String entityType, String entityCode);
}
