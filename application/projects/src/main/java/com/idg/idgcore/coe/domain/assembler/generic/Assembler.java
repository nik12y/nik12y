package com.idg.idgcore.coe.domain.assembler.generic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Component
@Slf4j
public abstract class Assembler<
        T_DTO extends CoreEngineBaseDTO,
        T_ENTITY extends AbstractAuditableDomainEntity>
{

    protected ModelMapper modelMapper = new ModelMapper();

    public abstract Class getSpecificDTOClass();
    public abstract Class getSpecificEntityClass();

    public T_DTO
    payloadToDTO(String payload) {
        ObjectMapper objectMapper = new ObjectMapper();
        Object readValue = null;
        try {
            readValue = objectMapper.readValue(payload, getSpecificDTOClass());
        } catch (JsonProcessingException e) {
            log.error("Parsing Exception in reading value [{}] in the class with name: [{}]",
                    payload, getSpecificDTOClass().getName(), e);
            ExceptionUtil.handleException(JSON_PARSING_ERROR);
        }
        return (T_DTO) readValue;
    }

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public T_ENTITY toEntity(T_DTO valDTO) {
        Object obj = modelMapper.map(valDTO, getSpecificEntityClass());
        return (T_ENTITY) obj;
    }

    public T_DTO toDTO(T_ENTITY entity) {
        Object obj = modelMapper.map(entity, getSpecificDTOClass());
        return (T_DTO) obj;
    }

    public void setAuditFields (MutationEntity mutationEntity, CoreEngineBaseDTO valDTO) {
        valDTO.setAction(mutationEntity.getAction());
        valDTO.setAuthorized(mutationEntity.getAuthorized());
        valDTO.setRecordVersion(mutationEntity.getRecordVersion());
        valDTO.setStatus(mutationEntity.getStatus());
        valDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        valDTO.setCreatedBy(mutationEntity.getCreatedBy());
        valDTO.setCreationTime(mutationEntity.getCreationTime());
        valDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        valDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        valDTO.setTaskCode(mutationEntity.getTaskCode());
        valDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
    }

    public char getCharValueFromBoolean (boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar (Character value) {
        return value.equals(CHAR_Y);
    }

}
