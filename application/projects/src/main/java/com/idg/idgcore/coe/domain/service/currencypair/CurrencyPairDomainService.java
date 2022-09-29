package com.idg.idgcore.coe.domain.service.currencypair;

import com.idg.idgcore.coe.domain.assembler.currencypair.CurrencyPairAssembler;
import com.idg.idgcore.coe.domain.entity.currencypair.CurrencyPairEntity;
import com.idg.idgcore.coe.domain.repository.currencypair.ICurrencyPairRepository;
import com.idg.idgcore.coe.dto.currencypair.CurrencyPairDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class CurrencyPairDomainService implements ICurrencyPairDomainService{

    @Autowired
    private ICurrencyPairRepository currencyPairRepository;

    @Autowired
    private CurrencyPairAssembler currencyPairAssembler;

    @Override
    public CurrencyPairEntity getConfigurationByCode (CurrencyPairDTO currencyPairDTO) {
        CurrencyPairEntity currencyPairEntity = null;
        try{
            currencyPairEntity = this.currencyPairRepository.findByPairId(currencyPairDTO.getPairId());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return currencyPairEntity;
    }

    @Override
    public List<CurrencyPairEntity> getCurrencyPairs(){
        return this.currencyPairRepository.findAll();
    }

    @Override
    public CurrencyPairEntity getCurrencyPairById(Integer pairId) {
        CurrencyPairEntity currencyPairEntity = null;
        try{
            currencyPairEntity = this.currencyPairRepository.findByPairId(pairId);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return currencyPairEntity;
    }

        public CurrencyPairEntity getByCurrency1AndCurrency2AndEntityTypeAndEntityCode (
            String currency1, String currency2, String entityType, String entityCode) {
        CurrencyPairEntity currencyPairEntity = null;
        try {
            currencyPairEntity = this.currencyPairRepository.getByCurrency1AndCurrency2AndEntityTypeAndEntityCode(
                    currency1, currency2, entityType, entityCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return currencyPairEntity;
    }

    @Override
    public void save(CurrencyPairDTO currencyPairDTO) {
        try {
            CurrencyPairEntity currencyPairEntity = this.currencyPairAssembler.convertDtoToEntity(currencyPairDTO);
            this.currencyPairRepository.save(currencyPairEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}