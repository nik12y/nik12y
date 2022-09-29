package com.idg.idgcore.coe.app.service.currencyratetype;
import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.common.Constants;
import com.idg.idgcore.coe.domain.assembler.currencyratetype.CurrencyRateTypeAssembler;
import com.idg.idgcore.coe.domain.entity.currencyratetype.CurrencyRateTypeEntity;
import com.idg.idgcore.coe.domain.service.currencyratetype.CurrencyRateTypeDomainService;
import com.idg.idgcore.coe.dto.currencyratetype.CurrencyRateTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("currencyRateTypeApplicationService")
public class CurrencyRateTypeApplicationService extends GenericApplicationService<CurrencyRateTypeDTO,CurrencyRateTypeEntity, CurrencyRateTypeDomainService,CurrencyRateTypeAssembler> {
    CurrencyRateTypeApplicationService() {
        super(Constants.CURRENCY_RATE_TYPE);
    }

    public String getTaskCode() {
        return CurrencyRateTypeDTO.builder().build().getTaskCode();
    }
}
