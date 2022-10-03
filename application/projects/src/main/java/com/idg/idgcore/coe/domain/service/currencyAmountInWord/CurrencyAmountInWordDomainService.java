package com.idg.idgcore.coe.domain.service.currencyAmountInWord;

import com.idg.idgcore.coe.domain.assembler.currencyAmountInWord.*;
import com.idg.idgcore.coe.domain.entity.currencyAmountInWord.*;
import com.idg.idgcore.coe.domain.repository.currencyAmountInWord.*;
import com.idg.idgcore.coe.domain.service.generic.*;
import com.idg.idgcore.coe.dto.currencyAmountInWord.*;
import com.idg.idgcore.coe.exception.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

import static com.idg.idgcore.coe.exception.Error.*;

@Slf4j
@Service
public class CurrencyAmountInWordDomainService extends DomainService<CurrencyAmountInWordDTO, CurrencyAmountInWordEntity> {

    private static final String CLASS_NAME="CurrencyAmountInWordDomainService.";
    private static final String ENTERED_STRING="Entered into ";
    private static final String EXIT_STRING="Exited from ";

    @Autowired
    private ICurrencyAmountInWordRepository icurrencyAmountInWordRepository;

    @Autowired
    private CurrencyAmountInWordAssembler currencyAmountInWordAssembler;

    @Override
    public CurrencyAmountInWordEntity getEntityByIdentifier(String productCategory) {
        log.info(ENTERED_STRING+CLASS_NAME+"getEntityByIdentifier() with productCategory:", productCategory);
        CurrencyAmountInWordEntity currencyAmountInWordEntity = null;
        try {
            currencyAmountInWordEntity = this.icurrencyAmountInWordRepository.findByCurrencyCode(productCategory);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        log.info(EXIT_STRING+CLASS_NAME+"getEntityByIdentifier() with CurrencyAmountInWordEntity{} response :", currencyAmountInWordEntity);
        return currencyAmountInWordEntity;
    }

    public Optional<CurrencyAmountInWordEntity> getCurrencyAmountInWordsDetails(String currencyCode) {

        log.info(ENTERED_STRING+CLASS_NAME+"getCurrencyAmountInWordsDetails() with Currency code{}", currencyCode);

        Optional<CurrencyAmountInWordEntity> currencyAmountInWordsEntity = this.icurrencyAmountInWordRepository.findById(currencyCode);

        log.info(EXIT_STRING+CLASS_NAME+"getCurrencyAmountInWordsDetails()");

        return currencyAmountInWordsEntity;
    }

    @Override
    public List<CurrencyAmountInWordEntity> getAllEntities() {
        log.info(ENTERED_STRING+CLASS_NAME+"getAllEntities()");
        log.info(EXIT_STRING+CLASS_NAME+"getAllEntities() with CurrencyAmountInWordEntity list response :");
        return this.icurrencyAmountInWordRepository.findAll();
    }

    public void save (CurrencyAmountInWordDTO currencyAmountInWordDTO) {
        log.info(ENTERED_STRING+CLASS_NAME+"save() with CurrencyAmountInWordDTO{} :", currencyAmountInWordDTO);
        try {
            CurrencyAmountInWordEntity currencyAmountInWordEntity = currencyAmountInWordAssembler.toEntity(currencyAmountInWordDTO);
            log.info(EXIT_STRING+CLASS_NAME+"save()");
            this.icurrencyAmountInWordRepository.save(currencyAmountInWordEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }


}
