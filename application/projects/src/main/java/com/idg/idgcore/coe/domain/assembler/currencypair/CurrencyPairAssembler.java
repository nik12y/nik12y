package com.idg.idgcore.coe.domain.assembler.currencypair;

import com.idg.idgcore.coe.domain.entity.currencypair.CurrencyPairConfigEntity;
import com.idg.idgcore.coe.domain.entity.currencypair.CurrencyPairEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.currencypair.CurrencyPairConfigDTO;
import com.idg.idgcore.coe.dto.currencypair.CurrencyPairDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
//import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class CurrencyPairAssembler {

    private ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

//    public CurrencyPairDTO convertEntityToDto (CurrencyPairEntity currencyPairEntity) {
//        CurrencyPairDTO currencyPairDTO = modelMapper.map(currencyPairEntity, CurrencyPairDTO.class);
//        currencyPairDTO.setPairId(currencyPairEntity.getPairId());
//        currencyPairDTO.setCurrency1(currencyPairEntity.getCurrency1());
//        currencyPairDTO.setCurrency1Description(currencyPairEntity.getCurrency1Description());
//        currencyPairDTO.setCurrency2(currencyPairEntity.getCurrency2());
//        currencyPairDTO.setCurrency2Description(currencyPairEntity.getCurrency2Description());
//        currencyPairDTO.setEntityCodeType(currencyPairEntity.getEntityCodeType());
//        currencyPairDTO.setCountryCode(currencyPairEntity.getCountryCode());
//        currencyPairDTO.setThroughCurrency(currencyPairEntity.getThroughCurrency());
//        currencyPairDTO.setThroughCurrencyCode(currencyPairEntity.getThroughCurrencyCode());
//        currencyPairDTO.setThroughCurrencyDescription(currencyPairEntity.getThroughCurrencyDescription());
//        currencyPairDTO.setNoofunits(currencyPairEntity.getNoofunits());
//        currencyPairDTO.setPointMultiplier(currencyPairEntity.getPointMultiplier());
//        currencyPairDTO.setQuotationMethods(currencyPairEntity.getQuotationMethods());
//        currencyPairDTO.setSpreadDefinition(currencyPairEntity.getSpreadDefinition());
//        return currencyPairDTO;
//    }
//
//    public CurrencyPairEntity convertDtoToEntity(CurrencyPairDTO currencyPairDTO) {
//        List<VirtualConfigTypeDTO> virtualConfigTypeDTO = currencyPairDTO.getVirtualConfigType();
//        List<VirtualConfigTypeEntity> virtualConfigTypeEntityList = new ArrayList<>();
//        virtualConfigTypeEntityList.addAll(virtualConfigTypeDTO.stream().map(entity->{
//            VirtualConfigTypeEntity virtualConfigTypeEntity1=null;
//            virtualConfigTypeEntity1 = modelMapper.map(entity, VirtualConfigTypeEntity.class);
//            return virtualConfigTypeEntity1;
//        }).collect(Collectors.toList()));
//
//        CurrencyPairEntity currencyPairEntity = modelMapper.map(currencyPairDTO, CurrencyPairEntity.class);
//        currencyPairEntity.setVirtualConfigTypeEntity((VirtualConfigTypeEntity) virtualConfigTypeEntityList);
//        return currencyPairEntity;
//    }

    public CurrencyPairEntity convertDtoToEntity(CurrencyPairDTO currencyPairDTO) {

        List<CurrencyPairConfigDTO> currencyPairConfigDTOList = currencyPairDTO.getCurrencyPairConfigDTOList();

        Type listType = new TypeToken<List<CurrencyPairConfigEntity>>(){}.getType();
        List<CurrencyPairConfigEntity> currencyPairConfigEntityList = modelMapper.map(currencyPairConfigDTOList,listType);

        CurrencyPairEntity currencyPairEntity = modelMapper.map(currencyPairDTO, CurrencyPairEntity.class);
        currencyPairEntity.setPairId(currencyPairDTO.getPairId());
        currencyPairEntity.setCurrency1(currencyPairDTO.getCurrency1());
        currencyPairEntity.setCurrency1Description(currencyPairDTO.getCurrency1Description());
        currencyPairEntity.setCurrency2(currencyPairDTO.getCurrency2());
        currencyPairEntity.setCurrency2Description(currencyPairDTO.getCurrency2Description());
        currencyPairEntity.setEntityCodeType(currencyPairDTO.getEntityCodeType());
        currencyPairEntity.setCountryCode(currencyPairDTO.getCountryCode());
        currencyPairEntity.setThroughCurrency(getCharValueFromBoolean(currencyPairDTO.getThroughCurrency()));
        currencyPairEntity.setThroughCurrencyCode(currencyPairDTO.getThroughCurrencyCode());
        currencyPairEntity.setThroughCurrencyDescription(currencyPairDTO.getThroughCurrencyDescription());
        currencyPairEntity.setNoofunits(currencyPairDTO.getNoofunits());
        currencyPairEntity.setPointMultiplier(currencyPairDTO.getPointMultiplier());
        currencyPairEntity.setQuotationMethods(getCharValueFromBoolean(currencyPairDTO.getQuotationMethods()));
        currencyPairEntity.setSpreadDefinition(getCharValueFromBoolean(currencyPairDTO.getSpreadDefinition()));
        currencyPairEntity.setCurrencyPairConfigEntityList(currencyPairConfigEntityList);
        return currencyPairEntity;
    }

    public CurrencyPairDTO convertEntityToDto(CurrencyPairEntity currencyPairEntity) {

        List<CurrencyPairConfigEntity> currencyPairConfigEntityList = currencyPairEntity.getCurrencyPairConfigEntityList();

        Type listType = new TypeToken<List<CurrencyPairConfigDTO>>(){}.getType();
        List<CurrencyPairConfigDTO> currencyPairConfigDTOList = modelMapper.map(currencyPairConfigEntityList,listType);

        CurrencyPairDTO currencyPairDTO = modelMapper.map(currencyPairEntity, CurrencyPairDTO.class);
        currencyPairDTO.setPairId(currencyPairEntity.getPairId());
        currencyPairDTO.setCurrency1(currencyPairEntity.getCurrency1());
        currencyPairDTO.setCurrency1Description(currencyPairEntity.getCurrency1Description());
        currencyPairDTO.setCurrency2(currencyPairEntity.getCurrency2());
        currencyPairDTO.setCurrency2Description(currencyPairEntity.getCurrency2Description());
        currencyPairDTO.setEntityCodeType(currencyPairEntity.getEntityCodeType());
        currencyPairDTO.setCountryCode(currencyPairEntity.getCountryCode());
        currencyPairDTO.setThroughCurrency(getBooleanValueFromChar(currencyPairEntity.getThroughCurrency()));
        currencyPairDTO.setThroughCurrencyCode(currencyPairEntity.getThroughCurrencyCode());
        currencyPairDTO.setThroughCurrencyDescription(currencyPairEntity.getThroughCurrencyDescription());
        currencyPairDTO.setNoofunits(currencyPairEntity.getNoofunits());
        currencyPairDTO.setPointMultiplier(currencyPairEntity.getPointMultiplier());
        currencyPairDTO.setQuotationMethods(getBooleanValueFromChar(currencyPairEntity.getQuotationMethods()));
        currencyPairDTO.setSpreadDefinition(getBooleanValueFromChar(currencyPairEntity.getSpreadDefinition()));
        currencyPairDTO.setCurrencyPairConfigDTOList(currencyPairConfigDTOList);
        return currencyPairDTO;
    }

    public CurrencyPairDTO setAuditFields (MutationEntity mutationEntity, CurrencyPairDTO currencyPairDTO) {
        currencyPairDTO.setAction(mutationEntity.getAction());
        currencyPairDTO.setAuthorized(mutationEntity.getAuthorized());
        currencyPairDTO.setRecordVersion(mutationEntity.getRecordVersion());
        currencyPairDTO.setStatus(mutationEntity.getStatus());
        currencyPairDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        currencyPairDTO.setCreatedBy(mutationEntity.getCreatedBy());
        currencyPairDTO.setCreationTime(mutationEntity.getCreationTime());
        currencyPairDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        currencyPairDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        currencyPairDTO.setTaskCode(mutationEntity.getTaskCode());
        currencyPairDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return currencyPairDTO;
    }

    public char getCharValueFromBoolean (boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar (Character value) {
        return value.equals(CHAR_Y);
    }

}