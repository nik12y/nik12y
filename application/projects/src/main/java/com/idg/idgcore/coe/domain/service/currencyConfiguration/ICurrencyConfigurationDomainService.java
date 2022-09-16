package com.idg.idgcore.coe.domain.service.currencyConfiguration;

import com.idg.idgcore.coe.domain.entity.currencyConfiguration.*;
import com.idg.idgcore.coe.dto.currencyConfiguration.*;

import java.util.*;

public interface ICurrencyConfigurationDomainService {

    void save (CurrencyConfigurationDTO currencyConfigurationDTO);

    public Optional<CurrencyConfigurationCutOffRoundingEntity> getCurrencyConfigCutOffRoundDetails(String currencyCode);
}
