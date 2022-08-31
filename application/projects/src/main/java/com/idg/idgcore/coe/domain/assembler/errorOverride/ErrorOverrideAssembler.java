package com.idg.idgcore.coe.domain.assembler.errorOverride;

import com.idg.idgcore.coe.domain.entity.errorOverride.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.dto.errorOverride.*;
import org.modelmapper.*;
import org.modelmapper.convention.*;
import org.springframework.stereotype.*;

import javax.annotation.*;

import static com.idg.idgcore.coe.common.Constants.*;

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
        errorOverrideEntity.setIsExcluded(
                getCharValueFromBoolean(errorOverrideDTO.getIsExcluded()));
        /**
         Setting Conversions Details */
        ErrorOverrideConversionsDTO errorOverrideConversionsDTO = errorOverrideDTO.getErrorOverrideConversions();
        ErrorOverrideConversionsEntity errorOverrideConversions = modelMapper.map(
                errorOverrideConversionsDTO, ErrorOverrideConversionsEntity.class);
        /**
         Setting Language Details */
        ErrorOverrideLanguageDetailsDTO errorOverrideLanguageDetailsDTO = errorOverrideDTO.getErrorOverrideLanguageDetails();
        ErrorOverrideLanguageDetailsEntity errorOverrideLanguageDetails = modelMapper.map(
                errorOverrideLanguageDetailsDTO, ErrorOverrideLanguageDetailsEntity.class);
        errorOverrideEntity.setErrorOverrideConversionsEntity(errorOverrideConversions);
        errorOverrideEntity.setErrorOverrideLanguageDetailsEntity(errorOverrideLanguageDetails);
        return errorOverrideEntity;
    }

    public ErrorOverrideDTO convertEntityToDto (ErrorOverrideEntity errorOverrideEntity) {
        System.out.println("\n\n---convertEntityToDto---with--- "+errorOverrideEntity);
        ErrorOverrideDTO errorOverrideDTO = modelMapper.map(errorOverrideEntity,
                ErrorOverrideDTO.class);
        errorOverrideDTO.setIsConfirmationRequired(
                getBooleanValueFromChar(errorOverrideEntity.getIsConfirmationRequired()));
        errorOverrideDTO.setIsExcluded(getBooleanValueFromChar(errorOverrideEntity.getIsExcluded()));

        /**
         Setting Language Details */
        ErrorOverrideLanguageDetailsEntity errorOverrideLanguageDetails = errorOverrideEntity.getErrorOverrideLanguageDetailsEntity();
        ErrorOverrideLanguageDetailsDTO errorOverrideLanguageDetailsDTO = modelMapper.map(
                errorOverrideLanguageDetails, ErrorOverrideLanguageDetailsDTO.class);
        /**
         Setting Conversions Details */
        ErrorOverrideConversionsEntity errorOverrideConversions = errorOverrideEntity.getErrorOverrideConversionsEntity();
        ErrorOverrideConversionsDTO errorOverrideConversionsDTO = modelMapper.map(
                errorOverrideConversions, ErrorOverrideConversionsDTO.class);
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
