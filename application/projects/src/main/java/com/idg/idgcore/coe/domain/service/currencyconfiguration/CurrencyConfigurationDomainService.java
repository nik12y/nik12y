package com.idg.idgcore.coe.domain.service.currencyconfiguration;

import com.idg.idgcore.coe.domain.assembler.currencyconfiguration.*;
import com.idg.idgcore.coe.domain.entity.currencyconfiguration.*;
import com.idg.idgcore.coe.domain.repository.currencyconfiguration.*;
import com.idg.idgcore.coe.dto.currencyconfiguration.*;
import com.idg.idgcore.coe.exception.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import static com.idg.idgcore.coe.exception.Error.*;

@Slf4j
@Service
public class CurrencyConfigurationDomainService implements ICurrencyConfigurationDomainService{

    private static final String CLASS_NAME="CurrencyConfigurationService.";
    private static final String ENTERED_STRING="Entered into ";
    private static final String EXIT_STRING="Exited from ";

    @Autowired
    private ICurrencyRepository iCurrencyRepository;

    @Autowired
    private CurrencyConfigurationAssembler currencyConfigurationAssembler;

    public void save (CurrencyDetailsDTO currencyDetailsDTO) {

        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"save() with CurrencyDetailsDTO{}",currencyDetailsDTO);
        }

        try{
            CurrencyConfigurationEntity currencyConfigListEntity = currencyConfigurationAssembler.convertDtoToEntity(currencyDetailsDTO);
            if (log.isInfoEnabled()) {
                log.info(EXIT_STRING+CLASS_NAME+"save()");
            }
            this.iCurrencyRepository.save(currencyConfigListEntity);
        }
        catch (Exception e) {
            log.error("Exception in "+CLASS_NAME+"Save()",e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}
