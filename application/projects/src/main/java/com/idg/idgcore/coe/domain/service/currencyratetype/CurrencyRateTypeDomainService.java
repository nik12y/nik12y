package com.idg.idgcore.coe.domain.service.currencyratetype;

import com.idg.idgcore.coe.domain.assembler.currencyratetype.CurrencyRateTypeAssembler;
import com.idg.idgcore.coe.domain.entity.currencyratetype.CurrencyRateTypeEntity;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.currencyratetype.ICurrencyRateTypeRepository;
import com.idg.idgcore.coe.dto.currencyratetype.CurrencyRateTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class CurrencyRateTypeDomainService extends DomainService<CurrencyRateTypeDTO,CurrencyRateTypeEntity> {

    @Autowired
    private ICurrencyRateTypeRepository currencyRateTypeRepository;

    @Autowired
    private CurrencyRateTypeAssembler currencyRateTypeAssembler;

    @Override
    public CurrencyRateTypeEntity getEntityByIdentifier(String identifier) {
        return getCurrencyRateTypeByType(identifier);
    }
    @Override
    public List<CurrencyRateTypeEntity> getAllEntities() {
        return getCurrencyRateTypes();
    }

    public void save(CurrencyRateTypeDTO currencyRateTypeDTO)
    {
        CurrencyRateTypeEntity currencyRateTypeEntity= currencyRateTypeAssembler.toEntity(currencyRateTypeDTO);
        this.currencyRateTypeRepository.save(currencyRateTypeEntity);

    }

    public CurrencyRateTypeEntity getCurrencyRateTypeByType(String currencyRateType )
    {
        return this.currencyRateTypeRepository.findByCurrencyRateType(currencyRateType);
    }

    public List<CurrencyRateTypeEntity> getCurrencyRateTypes () {
        return this.currencyRateTypeRepository.findAll();
    }

}
