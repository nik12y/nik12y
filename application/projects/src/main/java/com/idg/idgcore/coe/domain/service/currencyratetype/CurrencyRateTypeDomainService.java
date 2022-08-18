package com.idg.idgcore.coe.domain.service.currencyratetype;

import com.idg.idgcore.coe.domain.assembler.currencyratetype.CurrencyRateTypeAssembler;
import com.idg.idgcore.coe.domain.entity.currencyratetype.CurrencyRateTypeEntity;
import com.idg.idgcore.coe.domain.repository.currencyratetype.ICurrencyRateTypeRepository;
import com.idg.idgcore.coe.dto.currencyratetype.CurrencyRateTypeDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class CurrencyRateTypeDomainService implements ICurrencyRateTypeDomainService {

    @Autowired
    private ICurrencyRateTypeRepository currencyRateTypeRepository;

    @Autowired
    private CurrencyRateTypeAssembler currencyRateTypeAssembler;

    public CurrencyRateTypeEntity getConfigurationByCode(CurrencyRateTypeDTO currencyRateTypeDTO) {
        CurrencyRateTypeEntity currencyRateTypeEntity = null;
        try {
            currencyRateTypeEntity = this.currencyRateTypeRepository.findByCurrencyRateType(currencyRateTypeDTO.getCurrencyRateType());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return currencyRateTypeEntity;
    }

    public List<CurrencyRateTypeEntity> getCurrencyRateTypes() {
        return this.currencyRateTypeRepository.findAll();
    }

    public CurrencyRateTypeEntity  getCurrencyRateTypeByType(String currencyRateType) {
        CurrencyRateTypeEntity currencyRateTypeEntity = null;
        try {
            currencyRateTypeEntity = this.currencyRateTypeRepository.findByCurrencyRateType(currencyRateType);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return currencyRateTypeEntity;
    }

    public void save(CurrencyRateTypeDTO currencyRateTypeDTO) {
        try {
            CurrencyRateTypeEntity currencyRateTypeEntity = currencyRateTypeAssembler.convertDtoToEntity(currencyRateTypeDTO);
            this.currencyRateTypeRepository.save(currencyRateTypeEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}
