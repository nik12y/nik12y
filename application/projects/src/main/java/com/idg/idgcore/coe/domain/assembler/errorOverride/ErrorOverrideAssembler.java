package com.idg.idgcore.coe.domain.assembler.errorOverride;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.errorOverride.ErrorOverrideConversionsEntity;
import com.idg.idgcore.coe.domain.entity.errorOverride.ErrorOverrideEntity;
import com.idg.idgcore.coe.domain.entity.errorOverride.ErrorOverrideLanguageDetailsEntity;
import com.idg.idgcore.coe.dto.errorOverride.ErrorOverrideConversionsDTO;
import com.idg.idgcore.coe.dto.errorOverride.ErrorOverrideDTO;
import com.idg.idgcore.coe.dto.errorOverride.ErrorOverrideLanguageDetailsDTO;
import org.springframework.stereotype.Component;

@Component
public class ErrorOverrideAssembler extends Assembler<ErrorOverrideDTO, ErrorOverrideEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return ErrorOverrideDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return ErrorOverrideEntity.class;
    }

    @Override
    public ErrorOverrideEntity toEntity(ErrorOverrideDTO errorOverrideDTO) {
        ErrorOverrideEntity errorOverrideEntity = super.toEntity(errorOverrideDTO);
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

    @Override
    public ErrorOverrideDTO toDTO(ErrorOverrideEntity errorOverrideEntity) {
        ErrorOverrideDTO errorOverrideDTO = super.toDTO(errorOverrideEntity);
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

}
