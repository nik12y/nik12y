package com.idg.idgcore.coe.domain.assembler.virtualentity;

import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.virtualentity.VirtualEntity;
import com.idg.idgcore.coe.dto.virtualentity.VirtualEntityDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class VirtualEntityAssembler {

    private ModelMapper modelMapper = new ModelMapper();

    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public VirtualEntity convertDtoToEntity(VirtualEntityDTO virtualEntityDTO) throws ParseException {

        VirtualEntity virtualEntity = modelMapper.map(virtualEntityDTO, VirtualEntity.class);
        virtualEntity.setIsDefault(getCharValueFromBoolean(virtualEntityDTO.getIsDefault()));
        virtualEntity.setEffectiveDate(formatter.parse(virtualEntityDTO.getEffectiveDate()));
        return virtualEntity;
    }

    public VirtualEntityDTO convertEntityToDto(VirtualEntity virtualEntity) {

        VirtualEntityDTO virtualEntityDTO = modelMapper.map(virtualEntity, VirtualEntityDTO.class);
        virtualEntityDTO.setIsDefault(getBooleanValueFromChar(virtualEntity.getIsDefault()));
        virtualEntityDTO.setEffectiveDate(formatter.format(virtualEntity.getEffectiveDate()));
        return virtualEntityDTO;
    }

    public VirtualEntityDTO setAuditFields (MutationEntity mutationEntity, VirtualEntityDTO virtualEntityDTO) {
        virtualEntityDTO.setAction(mutationEntity.getAction());
        virtualEntityDTO.setAuthorized(mutationEntity.getAuthorized());
        virtualEntityDTO.setRecordVersion(mutationEntity.getRecordVersion());
        virtualEntityDTO.setStatus(mutationEntity.getStatus());
        virtualEntityDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        virtualEntityDTO.setCreatedBy(mutationEntity.getCreatedBy());
        virtualEntityDTO.setCreationTime(mutationEntity.getCreationTime());
        virtualEntityDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        virtualEntityDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        virtualEntityDTO.setTaskCode(mutationEntity.getTaskCode());
        virtualEntityDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return virtualEntityDTO;
    }

    public char getCharValueFromBoolean (boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar (Character value) {
        return value.equals(CHAR_Y);
    }

}
