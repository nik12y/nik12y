package com.idg.idgcore.coe.domain.repository.currencyAmountInWord;

import com.idg.idgcore.coe.domain.entity.currencyAmountInWord.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface ICurrencyAmountInWordRepository extends JpaRepository<CurrencyAmountInWordEntity,String> {

    CurrencyAmountInWordEntity findByCurrencyCode(String currencyCode);
}
