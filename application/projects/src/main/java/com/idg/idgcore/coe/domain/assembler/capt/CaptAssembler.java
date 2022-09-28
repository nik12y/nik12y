package com.idg.idgcore.coe.domain.assembler.capt;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.capt.CaptEntity;
import com.idg.idgcore.coe.dto.capt.CaptDTO;
import org.springframework.stereotype.Component;

@Component
public class CaptAssembler extends Assembler<CaptDTO, CaptEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return CaptDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return CaptEntity.class;
    }

    @Override
    public CaptEntity toEntity (CaptDTO captDTO) {
        CaptEntity captEntity = modelMapper.map(captDTO, CaptEntity.class);
        captEntity.setIsClearingPaymentCalender(getCharValueFromBoolean(captDTO.isCompositeClearingOrPaymentCalendar()));
        return captEntity;
    }

    @Override
    public CaptDTO toDTO (CaptEntity captEntity) {
        CaptDTO captDTO = modelMapper.map(captEntity, CaptDTO.class);
        captDTO.setCompositeClearingOrPaymentCalendar(getBooleanValueFromChar(captEntity.getIsClearingPaymentCalender()));
        return captDTO;
    }

}
