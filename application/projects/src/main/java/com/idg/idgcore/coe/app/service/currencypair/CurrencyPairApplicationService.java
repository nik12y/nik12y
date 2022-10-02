package com.idg.idgcore.coe.app.service.currencypair;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.common.Constants;
import com.idg.idgcore.coe.domain.assembler.currencypair.CurrencyPairAssembler;
import com.idg.idgcore.coe.domain.entity.currencypair.CurrencyPairEntity;
import com.idg.idgcore.coe.domain.service.currencypair.CurrencyPairDomainService;
import com.idg.idgcore.coe.dto.currencypair.CurrencyPairDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("currencyPairApplicationService")
public class CurrencyPairApplicationService extends GenericApplicationService<CurrencyPairDTO, CurrencyPairEntity, CurrencyPairDomainService, CurrencyPairAssembler> {

    protected CurrencyPairApplicationService() {
        super(Constants.CURRENCY_PAIR);
    }

    public String getTaskCode () {
        return CurrencyPairDTO.builder().build().getTaskCode();
    }

}
