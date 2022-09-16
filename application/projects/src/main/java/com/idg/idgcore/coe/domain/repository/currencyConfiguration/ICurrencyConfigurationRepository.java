package com.idg.idgcore.coe.domain.repository.currencyConfiguration;

import com.idg.idgcore.coe.domain.entity.currencyConfiguration.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface ICurrencyConfigurationRepository extends JpaRepository<CurrencyConfigurationEntity,String> {

}
