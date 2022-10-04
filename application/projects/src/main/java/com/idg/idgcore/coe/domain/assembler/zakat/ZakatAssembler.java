package com.idg.idgcore.coe.domain.assembler.zakat;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.zakat.ZakatEntity;
import com.idg.idgcore.coe.dto.zakat.ZakatDTO;
import com.idg.idgcore.coe.exception.Error;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
@Slf4j
public class ZakatAssembler extends Assembler<ZakatDTO, ZakatEntity> {
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Class getSpecificDTOClass() {
        return ZakatDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return ZakatEntity.class;
    }

    @Override
    public ZakatEntity toEntity(ZakatDTO zakatDTO) {
        ZakatEntity zakatEntity = super.toEntity(zakatDTO);
        try {
            zakatEntity.setStartDateOfRamadan(formatter.parse(zakatDTO.getStartDateOfRamadan()));
        } catch (ParseException e) {
            log.error("Error in parsing date in Zakat DTO: {} with year: {}",
                    zakatDTO.getStartDateOfRamadan(), zakatDTO.getZakatYear(), e);
            ExceptionUtil.handleException(Error.JSON_PARSING_ERROR);
        }
        return zakatEntity;
    }

    @Override
    public ZakatDTO toDTO(ZakatEntity zakatEntity) {
        ZakatDTO zakatDTO = super.toDTO(zakatEntity);
        zakatDTO.setStartDateOfRamadan(formatter.format(zakatEntity.getStartDateOfRamadan()));
        return zakatDTO;
    }

}
