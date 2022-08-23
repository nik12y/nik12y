package com.idg.idgcore.coe.domain.assembler.capt;

import com.idg.idgcore.coe.domain.entity.capt.CaptEntity;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.dto.capt.CaptDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class CaptAssembler {
    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public CaptEntity convertDtoToEntity (CaptDTO captDTO) {
        CaptEntity captEntity = modelMapper.map(captDTO, CaptEntity.class);
        captEntity.setClearingPaymentTypeCode(captDTO.getClearingPaymentTypeCode());
        captEntity.setClearingPaymentTypeName(captDTO.getClearingPaymentTypeName());
        captEntity.setIsClearingPaymentCalender(getCharValueFromBoolean(captDTO.isCompositeClearingOrPaymentCalendar()));
        captEntity.setWeeklyOff1(captDTO.getWeeklyOff1());
        captEntity.setWeeklyOff2(captDTO.getWeeklyOff2());
        captEntity.setWeeklyOff3(captDTO.getWeeklyOff3());
        return captEntity;
    }

    public CaptDTO convertEntityToDto (CaptEntity captEntity) {
        CaptDTO captDTO = modelMapper.map(captEntity, CaptDTO.class);
        captDTO.setClearingPaymentTypeCode(captEntity.getClearingPaymentTypeCode());
        captDTO.setClearingPaymentTypeName(captEntity.getClearingPaymentTypeName());
        captDTO.setCompositeClearingOrPaymentCalendar(getBooleanValueFromChar(captEntity.getIsClearingPaymentCalender()));
        captDTO.setWeeklyOff1(captEntity.getWeeklyOff1());
        captDTO.setWeeklyOff2(captEntity.getWeeklyOff2());
        captDTO.setWeeklyOff3(captEntity.getWeeklyOff3());
        return captDTO;
    }

    public CaptDTO setAuditFields (MutationEntity  mutationEntity, CaptDTO captDTO) {
        captDTO.setAction(mutationEntity.getAction());
        captDTO.setAuthorized(mutationEntity.getAuthorized());
        captDTO.setRecordVersion(mutationEntity.getRecordVersion());
        captDTO.setStatus(mutationEntity.getStatus());
        captDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        captDTO.setCreatedBy(mutationEntity.getCreatedBy());
        captDTO.setCreationTime(mutationEntity.getCreationTime());
        captDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        captDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        captDTO.setTaskCode(mutationEntity.getTaskCode());
        captDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return captDTO;
    }

    public char getCharValueFromBoolean (boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar (Character value) {
        return value.equals(CHAR_Y);
    }

}
