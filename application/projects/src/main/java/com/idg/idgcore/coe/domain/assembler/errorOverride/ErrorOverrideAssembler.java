package com.idg.idgcore.coe.domain.assembler.errorOverride;

import com.idg.idgcore.coe.domain.entity.errorOverride.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.dto.errorOverride.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class ErrorOverrideAssembler {
    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public ErrorOverrideEntity convertDtoToEntity (ErrorOverrideDTO errorOverrideDTO) {
        ErrorOverrideEntity errorOverrideEntity = modelMapper.map(errorOverrideDTO,
                ErrorOverrideEntity.class);
        errorOverrideEntity.setIsConfirmationRequired(
                getCharValueFromBoolean(errorOverrideDTO.getIsConfirmationRequired()));

        ErrorOverrideConversionsDTO errorOverrideConversionsDTO = errorOverrideDTO.getErrorOverrideConversions();
        ErrorOverrideLanguageDetailsDTO errorOverrideLanguageDetailsDTO = errorOverrideDTO.getErrorOverrideLanguageDetails();

        ErrorOverrideConversionsEntity errorOverrideConversions= modelMapper.map(errorOverrideConversionsDTO, ErrorOverrideConversionsEntity.class);
        ErrorOverrideLanguageDetailsEntity errorOverrideLanguageDetails= modelMapper.map(errorOverrideLanguageDetailsDTO, ErrorOverrideLanguageDetailsEntity.class);

        errorOverrideEntity.setErrorOverrideConversionsEntity(errorOverrideConversions);
        errorOverrideEntity.setErrorOverrideLanguageDetailsEntity(errorOverrideLanguageDetails);

        return errorOverrideEntity;
    }

    public ErrorOverrideDTO convertEntityToDto (ErrorOverrideEntity errorOverrideEntity) {
        ErrorOverrideDTO errorOverrideDTO = modelMapper.map(errorOverrideEntity,
                ErrorOverrideDTO.class);
        errorOverrideDTO.setIsConfirmationRequired(getBooleanValueFromChar(errorOverrideEntity.getIsConfirmationRequired()));

        ErrorOverrideConversionsEntity errorOverrideConversions = errorOverrideEntity.getErrorOverrideConversionsEntity();
        ErrorOverrideLanguageDetailsEntity errorOverrideLanguageDetails = errorOverrideEntity.getErrorOverrideLanguageDetailsEntity();

        ErrorOverrideConversionsDTO errorOverrideConversionsDTO= modelMapper.map(errorOverrideConversions, ErrorOverrideConversionsDTO.class);
        ErrorOverrideLanguageDetailsDTO errorOverrideLanguageDetailsDTO= modelMapper.map(errorOverrideLanguageDetails, ErrorOverrideLanguageDetailsDTO.class);

        errorOverrideDTO.setErrorOverrideConversions(errorOverrideConversionsDTO);
        errorOverrideDTO.setErrorOverrideLanguageDetails(errorOverrideLanguageDetailsDTO);

        return errorOverrideDTO;
    }

    public ErrorOverrideDTO setAuditFields (MutationEntity mutationEntity,
            ErrorOverrideDTO errorOverrideDTO) {
        errorOverrideDTO.setAction(mutationEntity.getAction());
        errorOverrideDTO.setAuthorized(mutationEntity.getAuthorized());
        errorOverrideDTO.setRecordVersion(mutationEntity.getRecordVersion());
        errorOverrideDTO.setStatus(mutationEntity.getStatus());
        errorOverrideDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        errorOverrideDTO.setCreatedBy(mutationEntity.getCreatedBy());
        errorOverrideDTO.setCreationTime(mutationEntity.getCreationTime());
        errorOverrideDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        errorOverrideDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        errorOverrideDTO.setTaskCode(mutationEntity.getTaskCode());
        errorOverrideDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return errorOverrideDTO;
    }

    public char getCharValueFromBoolean (boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar (Character value) {
        return value.equals(CHAR_Y);
    }

}
