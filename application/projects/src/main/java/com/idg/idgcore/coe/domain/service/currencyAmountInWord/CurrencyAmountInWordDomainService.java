package com.idg.idgcore.coe.domain.service.currencyAmountInWord;

import com.idg.idgcore.coe.domain.assembler.currencyAmountInWord.*;
import com.idg.idgcore.coe.domain.entity.currencyAmountInWord.*;
import com.idg.idgcore.coe.domain.repository.currencyAmountInWord.*;
import com.idg.idgcore.coe.dto.currencyAmountInWord.*;
import com.idg.idgcore.coe.exception.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

import static com.idg.idgcore.coe.exception.Error.*;

@Slf4j
@Service
public class CurrencyAmountInWordDomainService implements ICurrencyAmountInWordDomainService {

    @Autowired
    CurrencyAmountInWordAssembler currencyAmountInWordAssembler;

    @Autowired
    ICurrencyAmountInWordRepository iCurrencyAmountInWordRepository;

    private static final String CLASS_NAME="CurrencyAmountInWordDomainService.";
    private static final String ENTERED_STRING="Entered into ";
    private static final String EXIT_STRING="Exited from ";

    public void save (CurrencyAmountInWordDTO currencyAmountInWordDTO) {

        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"save() with CurrencyAmountInWordsDTO{}",
                    currencyAmountInWordDTO);
        }

        try{
            CurrencyAmountInWordEntity currencyAmountInWordEntity = currencyAmountInWordAssembler.convertDtoToEntity(
                    currencyAmountInWordDTO);
            if (log.isInfoEnabled()) {
                log.info(EXIT_STRING+CLASS_NAME+"save()");
            }
            this.iCurrencyAmountInWordRepository.save(currencyAmountInWordEntity);
        }
        catch (Exception e) {
            log.error("Exception in "+CLASS_NAME+"Save()",e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }

    public Optional<CurrencyAmountInWordEntity> getCurrencyAmountInWordsDetails(String currencyCode) {
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"getCurrencyAmountInWordsDetails() with Currency code{}", currencyCode);
        }
        Optional<CurrencyAmountInWordEntity> currencyAmountInWordsEntity = this.iCurrencyAmountInWordRepository.findById(currencyCode);
        if (log.isInfoEnabled()) {
            log.info(EXIT_STRING+CLASS_NAME+"getCurrencyAmountInWordsDetails()");
        }
        return currencyAmountInWordsEntity;
    }
}
