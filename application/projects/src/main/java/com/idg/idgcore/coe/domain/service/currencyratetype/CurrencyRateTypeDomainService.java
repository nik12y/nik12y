package com.idg.idgcore.coe.domain.service.currencyratetype;

import com.idg.idgcore.coe.domain.assembler.currencyratetype.CurrencyRateTypeAssembler;
import com.idg.idgcore.coe.domain.entity.currencyratetype.CurrencyRateTypeEntity;
import com.idg.idgcore.coe.domain.repository.currencyratetype.ICurrencyRateTypeRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.currencyratetype.CurrencyRateTypeDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class CurrencyRateTypeDomainService extends DomainService<CurrencyRateTypeDTO,CurrencyRateTypeEntity> {

    @Autowired
    private ICurrencyRateTypeRepository currencyRateTypeRepository;

    @Autowired
    private CurrencyRateTypeAssembler currencyRateTypeAssembler;

    @Override
    public CurrencyRateTypeEntity getEntityByIdentifier(String  currencyRateType) {
        CurrencyRateTypeEntity currencyRateTypeEntity = null;
        try {
            currencyRateTypeEntity= this.currencyRateTypeRepository.findByCurrencyRateType(currencyRateType);
        } catch (Exception e) {
            log.error("Exception in getEntityByIdentifier",e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return currencyRateTypeEntity;
    }

    @Override
    public List<CurrencyRateTypeEntity> getAllEntities() {
        return this.currencyRateTypeRepository.findAll();
    }

    public void save(CurrencyRateTypeDTO currencyRateTypeDTO) {
        try {
            CurrencyRateTypeEntity currencyRateTypeEntity= currencyRateTypeAssembler.toEntity(currencyRateTypeDTO);
            this.currencyRateTypeRepository.save(currencyRateTypeEntity);
        } catch (Exception e) {
            log.error("Exception in Save",e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

