package com.idg.idgcore.coe.domain.service.currencyAmountInWord;

import com.idg.idgcore.coe.domain.entity.currencyAmountInWord.*;
import com.idg.idgcore.coe.dto.currencyAmountInWord.*;

import java.util.*;

public interface ICurrencyAmountInWordDomainService {

    void save (CurrencyAmountInWordDTO currencyAmountInWordDTO);

    public Optional<CurrencyAmountInWordEntity> getCurrencyAmountInWordsDetails(String currencyCode);
}
