package com.idg.idgcore.coe.domain.assembler.virtualentity;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.virtualentity.VirtualEntity;
import com.idg.idgcore.coe.dto.virtualentity.VirtualEntityDTO;
import com.idg.idgcore.coe.exception.Error;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
@Slf4j
public class VirtualEntityAssembler extends Assembler<VirtualEntityDTO, VirtualEntity> {

    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Class getSpecificDTOClass() {
        return VirtualEntityDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return VirtualEntity.class;
    }

    @Override
    public VirtualEntity toEntity(VirtualEntityDTO virtualEntityDTO) {

        VirtualEntity virtualEntity = modelMapper.map(virtualEntityDTO, VirtualEntity.class);
        virtualEntity.setIsDefault(getCharValueFromBoolean(virtualEntityDTO.getIsDefault()));
        try {
            virtualEntity.setEffectiveDate(formatter.parse(virtualEntityDTO.getEffectiveDate()));
        } catch (ParseException e) {
            log.error("ParseException in processing [{}]", virtualEntityDTO.getEffectiveDate(), e);
            ExceptionUtil.handleException(Error.JSON_PARSING_ERROR);
        }
        return virtualEntity;
    }

    @Override
    public VirtualEntityDTO toDTO(VirtualEntity virtualEntity) {
        VirtualEntityDTO virtualEntityDTO = modelMapper.map(virtualEntity, VirtualEntityDTO.class);
        virtualEntityDTO.setIsDefault(getBooleanValueFromChar(virtualEntity.getIsDefault()));
        virtualEntityDTO.setEffectiveDate(formatter.format(virtualEntity.getEffectiveDate()));
        return virtualEntityDTO;
    }

}
