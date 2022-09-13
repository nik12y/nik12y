package com.idg.idgcore.coe.domain.assembler.currencyConfiguration;

import com.idg.idgcore.coe.domain.assembler.common.*;
import com.idg.idgcore.coe.domain.entity.currencyConfiguration.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.dto.currencyConfiguration.*;
import lombok.extern.slf4j.*;
import org.modelmapper.*;
import org.modelmapper.convention.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;

@Component
@Slf4j
public class CurrencyConfigurationAssembler {

    private static final String CLASS_NAME="CurrencyConfigurationAssembler.";
    private static final String ENTERED_STRING="Entered into ";
    private static final String EXIT_STRING="Exited from ";

    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public CurrencyCutOffRoundingDTO convertEntityToDto(Optional<CurrencyConfigurationCutOffRoundingEntity> currencyConfigCutOffRoundingEntity){

            log.info(ENTERED_STRING+CLASS_NAME+"convertEntityToDto() with CurrencyConfigCutOffRoundingEntity {}",currencyConfigCutOffRoundingEntity);

        CurrencyCutOffRoundingDTO currencyCutOffRoundingDTO = modelMapper.map(currencyConfigCutOffRoundingEntity, CurrencyCutOffRoundingDTO.class);
        currencyCutOffRoundingDTO.setEnableMt101Remit(
                AssemblerUtils.getBooleanValueFromChar(currencyConfigCutOffRoundingEntity.get().getEnableMt101Remit()));
        currencyCutOffRoundingDTO.setEnableMt103Stp(
                AssemblerUtils.getBooleanValueFromChar(currencyConfigCutOffRoundingEntity.get().getEnableMt103Stp()));
        currencyCutOffRoundingDTO.setIndexFlag(
                AssemblerUtils.getBooleanValueFromChar(currencyConfigCutOffRoundingEntity.get().getIndexFlag()));
        currencyCutOffRoundingDTO.setEnableMt202Cov(
                AssemblerUtils.getBooleanValueFromChar(currencyConfigCutOffRoundingEntity.get().getEnableMt202Cov()));
        currencyCutOffRoundingDTO.setClsCurrencyFlag(
                AssemblerUtils.getBooleanValueFromChar(currencyConfigCutOffRoundingEntity.get().getClsCurrencyFlag()));
        currencyCutOffRoundingDTO.setValidateTag50fFlag(
                AssemblerUtils.getBooleanValueFromChar(currencyConfigCutOffRoundingEntity.get().getValidateTag50fFlag()));
        currencyCutOffRoundingDTO.setEuroTransactionCurrency(
                AssemblerUtils.getBooleanValueFromChar(currencyConfigCutOffRoundingEntity.get().getEuroTransactionCurrency()));
        currencyCutOffRoundingDTO.setEuroCloseFlag(
                AssemblerUtils.getBooleanValueFromChar(currencyConfigCutOffRoundingEntity.get().getEuroTransactionCurrency()));

            log.info(EXIT_STRING+CLASS_NAME+"convertDtoToEntity() with following converted DTO {} CurrencyCutOffRoundingDTO :", currencyCutOffRoundingDTO);

       return currencyCutOffRoundingDTO;
    }

    public CurrencyConfigurationDTO currencyConfigConvertEntityToDto(Optional<CurrencyConfigurationEntity>  currencyConfigEntity){

            log.info(ENTERED_STRING+CLASS_NAME+"currencyConfigConvertEntityToDto() with CurrencyConfigEntity {}",currencyConfigEntity);

        CurrencyConfigurationDTO currencyConfigurationDTO = modelMapper.map(currencyConfigEntity, CurrencyConfigurationDTO.class);
        currencyConfigurationDTO.setBeforeFormattingFlag(
                AssemblerUtils.getBooleanValueFromChar(currencyConfigEntity.get().getBeforeFormattingFlag()));
        currencyConfigurationDTO.setAfterFormattingFlag(
                AssemblerUtils.getBooleanValueFromChar(currencyConfigEntity.get().getAfterFormattingFlag()));
        currencyConfigurationDTO.setIncludeSpaceFormattingFlag(
                AssemblerUtils.getBooleanValueFromChar(currencyConfigEntity.get().getIncludeSpaceFormattingFlag()));

            log.info(EXIT_STRING+CLASS_NAME+"currencyConfigConvertEntityToDto() with following converted DTO {} CurrencyConfigurationDTO :", currencyConfigurationDTO);

        return currencyConfigurationDTO;

    }

    public CurrencyConfigurationEntity convertDtoToEntity(
            CurrencyConfigurationDTO currencyConfigurationDTO) {

            log.info(ENTERED_STRING+CLASS_NAME+"convertDtoToEntity() with CurrencyConfigurationDTO {}",currencyConfigurationDTO);

        List<CurrencyCountryMapDTO> currencyCountryMapDTO = currencyConfigurationDTO.getCurrencyCountryMap();
        List<CurrencyCountryMapEntity> currencyCountryMapEntityList = new ArrayList<>();
        currencyCountryMapEntityList.addAll(currencyCountryMapDTO.stream().map(entity->{
            CurrencyCountryMapEntity currencyCountryMapEntity1 =null;
            currencyCountryMapEntity1 = modelMapper.map(entity, CurrencyCountryMapEntity.class);
            currencyCountryMapEntity1.setCountryCurrencyCode(currencyConfigurationDTO.getCurrencyCode());
            currencyCountryMapEntity1.setStatus(currencyConfigurationDTO.getStatus());
            currencyCountryMapEntity1.setRecordVersion(currencyConfigurationDTO.getRecordVersion());
            currencyCountryMapEntity1.setAuthorized(currencyConfigurationDTO.getAuthorized());
            currencyCountryMapEntity1.setLastConfigurationAction(currencyConfigurationDTO.getLastConfigurationAction());
            return currencyCountryMapEntity1;
        }).toList());

        List<CurrencyExtraFieldMapDTO> currencyExtraFieldMapDTO = currencyConfigurationDTO.getCurrencyExtraFieldMap();
        List<CurrencyExtraFieldMapEntity> currencyExtraFieldMapEntityList = new ArrayList<>();
        currencyExtraFieldMapEntityList.addAll(currencyExtraFieldMapDTO.stream().map(entity->{
            CurrencyExtraFieldMapEntity currencyExtraFieldMapEntity1 =null;
            currencyExtraFieldMapEntity1 = modelMapper.map(entity, CurrencyExtraFieldMapEntity.class);
            currencyExtraFieldMapEntity1.setCurrencyCode(currencyConfigurationDTO.getCurrencyCode());
            currencyExtraFieldMapEntity1.setStatus(currencyConfigurationDTO.getStatus());
            currencyExtraFieldMapEntity1.setRecordVersion(currencyConfigurationDTO.getRecordVersion());
            currencyExtraFieldMapEntity1.setAuthorized(currencyConfigurationDTO.getAuthorized());
            currencyExtraFieldMapEntity1.setLastConfigurationAction(currencyConfigurationDTO.getLastConfigurationAction());
            return currencyExtraFieldMapEntity1;
        }).toList());

        CurrencyCutOffRoundingDTO currencyCutOffRoundingDTO = currencyConfigurationDTO.getCurrencyCutOff();
        CurrencyConfigurationCutOffRoundingEntity currencyConfigurationCutOffRoundingEntity = modelMapper.map(
                currencyCutOffRoundingDTO, CurrencyConfigurationCutOffRoundingEntity.class);
        currencyConfigurationCutOffRoundingEntity.setEnableMt101Remit(AssemblerUtils.getCharValueFromBoolean(currencyCutOffRoundingDTO.isEnableMt101Remit()));
        currencyConfigurationCutOffRoundingEntity.setEnableMt103Stp(AssemblerUtils.getCharValueFromBoolean(currencyCutOffRoundingDTO.isEnableMt103Stp()));
        currencyConfigurationCutOffRoundingEntity.setIndexFlag(AssemblerUtils.getCharValueFromBoolean(currencyCutOffRoundingDTO.isIndexFlag()));
        currencyConfigurationCutOffRoundingEntity.setEnableMt202Cov(AssemblerUtils.getCharValueFromBoolean(currencyCutOffRoundingDTO.isEnableMt202Cov()));
        currencyConfigurationCutOffRoundingEntity.setClsCurrencyFlag(AssemblerUtils.getCharValueFromBoolean(currencyCutOffRoundingDTO.isClsCurrencyFlag()));
        currencyConfigurationCutOffRoundingEntity.setValidateTag50fFlag(AssemblerUtils.getCharValueFromBoolean(currencyCutOffRoundingDTO.isValidateTag50fFlag()));
        currencyConfigurationCutOffRoundingEntity.setEuroTransactionCurrency(AssemblerUtils.getCharValueFromBoolean(currencyCutOffRoundingDTO.isEuroTransactionCurrency()));
        currencyConfigurationCutOffRoundingEntity.setEuroCloseFlag(AssemblerUtils.getCharValueFromBoolean(currencyCutOffRoundingDTO.isEuroTransactionCurrency()));
        currencyConfigurationCutOffRoundingEntity.setStatus(currencyConfigurationDTO.getStatus());
        currencyConfigurationCutOffRoundingEntity.setRecordVersion(currencyConfigurationDTO.getRecordVersion());
        currencyConfigurationCutOffRoundingEntity.setAuthorized(currencyConfigurationDTO.getAuthorized());
        currencyConfigurationCutOffRoundingEntity.setLastConfigurationAction(
                currencyConfigurationDTO.getLastConfigurationAction());

        CurrencyConfigurationEntity currencyConfigurationEntity = modelMapper.map(
                currencyConfigurationDTO, CurrencyConfigurationEntity.class);
        currencyConfigurationEntity.setBeforeFormattingFlag(AssemblerUtils.getCharValueFromBoolean(
                currencyConfigurationDTO.isBeforeFormattingFlag()));
        currencyConfigurationEntity.setAfterFormattingFlag(AssemblerUtils.getCharValueFromBoolean(
                currencyConfigurationDTO.isAfterFormattingFlag()));
        currencyConfigurationEntity.setIncludeSpaceFormattingFlag(AssemblerUtils.getCharValueFromBoolean(
                currencyConfigurationDTO.isIncludeSpaceFormattingFlag()));
        currencyConfigurationEntity.setCurrencyCode(currencyConfigurationDTO.getCurrencyCode());
        currencyConfigurationEntity.setCurrencyConfigurationCutOffRoundingEntity(
                currencyConfigurationCutOffRoundingEntity);

        currencyConfigurationEntity.setCurrencyCountryMapEntity(currencyCountryMapEntityList);
        currencyConfigurationEntity.setCurrencyExtraFieldMapEntity(currencyExtraFieldMapEntityList);

            log.info(EXIT_STRING+CLASS_NAME+"convertDtoToEntity() with following converted entity {} CurrencyConfigurationEntity :", currencyConfigurationEntity);

        return currencyConfigurationEntity;
    }

    public CurrencyConfigurationDTO convertEntityToDtoCurrencyConfigDetails(
            Optional<CurrencyConfigurationEntity> currencyConfigEntity){

            log.info(ENTERED_STRING+CLASS_NAME+"convertEntityToDtoCurrencyConfigDetails() with CurrencyConfigEntity {}",currencyConfigEntity);

        List<CurrencyCountryMapDTO> currencyCountryMapDTOList = new ArrayList<>();
        List<CurrencyCountryMapEntity> currencyCountryMapEntity = currencyConfigEntity.get().getCurrencyCountryMapEntity();
        currencyCountryMapDTOList.addAll(currencyCountryMapEntity.stream().map(dto->{
            CurrencyCountryMapDTO currencyCountryMapDTO1=new CurrencyCountryMapDTO();
            currencyCountryMapDTO1.setCurrencyCode(dto.getCountryCurrencyCode());
            currencyCountryMapDTO1.setCurrencyCountryName(dto.getCurrencyCountryName());
            currencyCountryMapDTO1.setCurrencyCountryCode(dto.getCurrencyCountryCode());
            return currencyCountryMapDTO1;
        }).toList());

        List<CurrencyExtraFieldMapDTO> currencyExtraFieldMapDTOList = new ArrayList<>();
        List<CurrencyExtraFieldMapEntity> currencyExtraFieldMapEntity = currencyConfigEntity.get().getCurrencyExtraFieldMapEntity();
        currencyExtraFieldMapDTOList.addAll(currencyExtraFieldMapEntity.stream().map(dto->{
            CurrencyExtraFieldMapDTO currencyExtraFieldMapDTO1=new CurrencyExtraFieldMapDTO();
            currencyExtraFieldMapDTO1.setCurrencyCode(dto.getCurrencyCode());
            currencyExtraFieldMapDTO1.setExtraFieldName(dto.getExtraFieldName());
            currencyExtraFieldMapDTO1.setExtraFieldValue(dto.getExtraFieldValue());
            return currencyExtraFieldMapDTO1;
        }).toList());

        CurrencyConfigurationCutOffRoundingEntity currencyConfigurationCutOffRoundingEntity = currencyConfigEntity.get().getCurrencyConfigurationCutOffRoundingEntity();
        CurrencyCutOffRoundingDTO currencyCutOffRoundingDTO = modelMapper.map(
                currencyConfigurationCutOffRoundingEntity, CurrencyCutOffRoundingDTO.class);
        currencyCutOffRoundingDTO.setEnableMt101Remit(AssemblerUtils.getBooleanValueFromChar(
                currencyConfigurationCutOffRoundingEntity.getEnableMt101Remit()));
        currencyCutOffRoundingDTO.setEnableMt103Stp(AssemblerUtils.getBooleanValueFromChar(
                currencyConfigurationCutOffRoundingEntity.getEnableMt103Stp()));
        currencyCutOffRoundingDTO.setIndexFlag(AssemblerUtils.getBooleanValueFromChar(
                currencyConfigurationCutOffRoundingEntity.getIndexFlag()));
        currencyCutOffRoundingDTO.setEnableMt202Cov(AssemblerUtils.getBooleanValueFromChar(
                currencyConfigurationCutOffRoundingEntity.getEnableMt202Cov()));
        currencyCutOffRoundingDTO.setClsCurrencyFlag(AssemblerUtils.getBooleanValueFromChar(
                currencyConfigurationCutOffRoundingEntity.getClsCurrencyFlag()));
        currencyCutOffRoundingDTO.setValidateTag50fFlag(AssemblerUtils.getBooleanValueFromChar(
                currencyConfigurationCutOffRoundingEntity.getValidateTag50fFlag()));
        currencyCutOffRoundingDTO.setEuroTransactionCurrency(AssemblerUtils.getBooleanValueFromChar(
                currencyConfigurationCutOffRoundingEntity.getEuroTransactionCurrency()));
        currencyCutOffRoundingDTO.setEuroCloseFlag(AssemblerUtils.getBooleanValueFromChar(
                currencyConfigurationCutOffRoundingEntity.getEuroTransactionCurrency()));

        CurrencyConfigurationDTO currencyConfigurationDTO = modelMapper.map(currencyConfigEntity, CurrencyConfigurationDTO.class);
        currencyConfigurationDTO.setBeforeFormattingFlag(
                AssemblerUtils.getBooleanValueFromChar(currencyConfigEntity.get().getBeforeFormattingFlag()));
        currencyConfigurationDTO.setAfterFormattingFlag(
                AssemblerUtils.getBooleanValueFromChar(currencyConfigEntity.get().getAfterFormattingFlag()));
        currencyConfigurationDTO.setIncludeSpaceFormattingFlag(
                AssemblerUtils.getBooleanValueFromChar(currencyConfigEntity.get().getIncludeSpaceFormattingFlag()));
        currencyConfigurationDTO.setStatus(currencyConfigEntity.get().getStatus());
        currencyConfigurationDTO.setAction(currencyConfigEntity.get().getLastConfigurationAction());
        currencyConfigurationDTO.setRecordVersion(currencyConfigEntity.get().getRecordVersion());
        currencyConfigurationDTO.setCreatedBy(currencyConfigEntity.get().getCreatedBy());
        currencyConfigurationDTO.setCreationTime(currencyConfigEntity.get().getCreationTime());
        currencyConfigurationDTO.setLastUpdatedBy(currencyConfigEntity.get().getLastUpdatedBy());
        currencyConfigurationDTO.setLastUpdatedTime(currencyConfigEntity.get().getLastUpdatedTime());
        currencyConfigurationDTO.setCurrencyCountryMap(currencyCountryMapDTOList);
        currencyConfigurationDTO.setCurrencyExtraFieldMap(currencyExtraFieldMapDTOList);
        currencyConfigurationDTO.setCurrencyCutOff(currencyCutOffRoundingDTO);

            log.info(EXIT_STRING+CLASS_NAME+"convertEntityToDtoCurrencyConfigDetails() with following converted DTO {} CurrencyConfigurationDTO :", currencyConfigurationDTO);

        return currencyConfigurationDTO;
    }

    public CurrencyConfigurationDTO setAuditFields (MutationEntity mutationEntity, CurrencyConfigurationDTO currencyConfigurationDTO) {

            log.info(ENTERED_STRING+CLASS_NAME+"setAuditFields() with MutationEntity {} and CurrencyConfigurationDTO {} ",mutationEntity,currencyConfigurationDTO);

        currencyConfigurationDTO.setAction(mutationEntity.getAction());
        currencyConfigurationDTO.setAuthorized(mutationEntity.getAuthorized());
        currencyConfigurationDTO.setRecordVersion(mutationEntity.getRecordVersion());
        currencyConfigurationDTO.setStatus(mutationEntity.getStatus());
        currencyConfigurationDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        currencyConfigurationDTO.setCreatedBy(mutationEntity.getCreatedBy());
        currencyConfigurationDTO.setCreationTime(mutationEntity.getCreationTime());
        currencyConfigurationDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        currencyConfigurationDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        currencyConfigurationDTO.setTaskCode(mutationEntity.getTaskCode());
        currencyConfigurationDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());

            log.info(EXIT_STRING+CLASS_NAME+"setAuditFields() with following massaged DTO CurrencyConfigurationDTO :", currencyConfigurationDTO);

        return currencyConfigurationDTO;
    }

}
