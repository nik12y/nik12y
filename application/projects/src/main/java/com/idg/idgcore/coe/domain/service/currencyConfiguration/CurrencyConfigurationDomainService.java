package com.idg.idgcore.coe.domain.service.currencyConfiguration;

import com.idg.idgcore.coe.domain.assembler.currencyConfiguration.*;
import com.idg.idgcore.coe.domain.entity.currencyConfiguration.*;
import com.idg.idgcore.coe.domain.repository.currencyConfiguration.*;
import com.idg.idgcore.coe.dto.currencyConfiguration.*;
import com.idg.idgcore.coe.exception.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

import static com.idg.idgcore.coe.exception.Error.*;

@Slf4j
@Service
public class CurrencyConfigurationDomainService implements ICurrencyConfigurationDomainService{

    private static final String CLASS_NAME="CurrencyConfigurationDomainService.";
    private static final String ENTERED_STRING="Entered into ";
    private static final String EXIT_STRING="Exited from ";


    @Autowired
    private ICurrencyConfigurationDetailsRepository iCurrencyConfigurationDetailsRepository;

    @Autowired
    private CurrencyConfigurationAssembler currencyConfigurationAssembler;

    @Autowired
    private ICurrencyCutOffRepository icurrencyCutOffRepository;

    @Autowired
    private ICurrencyConfigurationRepository icurrencyConfigurationRepository;


    public void save (CurrencyConfigurationDTO currencyConfigurationDTO) {

        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"save() with CurrencyConfigDTO{}",
                    currencyConfigurationDTO);
        }

        try{
            CurrencyConfigurationEntity currencyConfigurationEntity = currencyConfigurationAssembler.convertDtoToEntity(
                    currencyConfigurationDTO);
            if (log.isInfoEnabled()) {
                log.info(EXIT_STRING+CLASS_NAME+"save()");
            }
            this.iCurrencyConfigurationDetailsRepository.save(currencyConfigurationEntity);
        }
        catch (Exception e) {
            log.error("Exception in "+CLASS_NAME+"Save()",e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }

    public Optional<CurrencyConfigurationCutOffRoundingEntity> getCurrencyConfigCutOffRoundDetails(String currencyCode) {
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"getCurrencyConfigCutOffRoundDetails() with Currency code{}", currencyCode);
        }
        Optional<CurrencyConfigurationCutOffRoundingEntity> currencyConfigCutOffRoundingEntity = this.icurrencyCutOffRepository.findById(currencyCode);
        if (log.isInfoEnabled()) {
            log.info(EXIT_STRING+CLASS_NAME+"getCurrencyConfigCutOffRoundDetails()");
        }
        return currencyConfigCutOffRoundingEntity;
    }

    public Optional<CurrencyConfigurationEntity> getCurrencyConfigDetails(String currencyCode) {
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"getCurrencyConfigDetails() with Currency code{}", currencyCode);
        }
        Optional<CurrencyConfigurationEntity>currencyConfigEntity = this.icurrencyConfigurationRepository.findById(currencyCode);
        if (log.isInfoEnabled()) {
            log.info(EXIT_STRING+CLASS_NAME+"getCurrencyConfigDetails()");
        }
        return currencyConfigEntity;
    }


}
