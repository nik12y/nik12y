package com.idg.idgcore.coe.domain.assembler.currencyratetype;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.currencyratetype.CurrencyRateTypeEntity;
import com.idg.idgcore.coe.dto.currencyratetype.CurrencyRateTypeDTO;
import org.springframework.stereotype.Component;

@Component
public class CurrencyRateTypeAssembler extends Assembler<CurrencyRateTypeDTO, CurrencyRateTypeEntity> {

        @Override
        public Class getSpecificDTOClass() {
            return CurrencyRateTypeDTO.class;
        }

        @Override
        public Class getSpecificEntityClass() {
            return CurrencyRateTypeEntity.class;
        }

    }