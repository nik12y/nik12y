package com.idg.idgcore.coe.domain.repository.currencyconfiguration;

import com.idg.idgcore.coe.domain.entity.currencyconfiguration.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface ICurrencyConfigurationRepository extends JpaRepository<CurrencyConfigurationDetailsEntity,String> {
}
