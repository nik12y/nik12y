package com.idg.idgcore.coe.domain.service.currencypair;

import com.idg.idgcore.coe.domain.assembler.currencypair.CurrencyPairAssembler;
import com.idg.idgcore.coe.domain.entity.currencypair.CurrencyPairEntity;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.currencypair.CurrencyPairDTO;
import com.idg.idgcore.coe.dto.currencypair.ICurrencyPairRepository;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.common.Constants.FIELD_SEPARATOR;
import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class CurrencyPairDomainService extends DomainService<CurrencyPairDTO, CurrencyPairEntity> {

    @Autowired
    private ICurrencyPairRepository currencyPairRepository;

    @Autowired
    private CurrencyPairAssembler currencyPairAssembler;

    @Override
    public CurrencyPairEntity getEntityByIdentifier(String identifier) {
        CurrencyPairEntity currencyPairEntity = null;
        try{
            String[] fields = identifier.split(FIELD_SEPARATOR);
            if (fields.length == 4) {
                currencyPairEntity = currencyPairRepository.getByCurrency1AndCurrency2AndEntityTypeAndEntityCode(
                        fields[0], fields[1], fields[2], fields[3]);
            }
        }
        catch (Exception e) {
            log.error("Exception in getEntityByIdentifier",e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return currencyPairEntity;
    }

    @Override
    public List<CurrencyPairEntity> getAllEntities() {
        return this.currencyPairRepository.findAll();
    }

    public void save(CurrencyPairDTO valDTO) {
        try {
            CurrencyPairEntity existingEntity = currencyPairRepository.getByCurrency1AndCurrency2AndEntityTypeAndEntityCode(valDTO.getCurrency1(),
                    valDTO.getCurrency2(), valDTO.getEntityType(), valDTO.getEntityCode());
            if(existingEntity != null) {
                valDTO.setPairId(existingEntity.getPairId());
            }
            CurrencyPairEntity currencyPairEntity = currencyPairAssembler.toEntity(valDTO);
            this.currencyPairRepository.save(currencyPairEntity);
        }
          catch (Exception e) {
              log.error("Exception in Save of currency pair with Curr1: [{}]," +
                      "curr2: [{}], entityType: [{}], entityCode: [{}]",valDTO.getCurrency1(),
                      valDTO.getCurrency2(), valDTO.getEntityType(), valDTO.getEntityCode(), e);
              ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}