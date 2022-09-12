package com.idg.idgcore.coe.domain.assembler.currencyAmountInWord;

import com.idg.idgcore.coe.domain.entity.currencyAmountInWord.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.dto.currencyAmountInWord.*;
import lombok.extern.slf4j.*;
import org.modelmapper.*;
import org.modelmapper.convention.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;

import static com.idg.idgcore.coe.common.Constants.*;

@Component
@Slf4j
public class CurrencyAmountInWordAssembler {

    private static final String CLASS_NAME="CurrencyAmountInWordAssembler.";
    private static final String ENTERED_STRING="Entered into ";
    private static final String EXIT_STRING="Exited from ";

    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public CurrencyAmountInWordEntity convertDtoToEntity(
            CurrencyAmountInWordDTO currencyAmountInWordDTO) {
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"convertDtoToEntity() with CurrencyAmountInWordDTO {}",currencyAmountInWordDTO);
        }
        CurrencyAmountInWordEntity currencyAmountInWordEntity = modelMapper.map(
                currencyAmountInWordDTO, CurrencyAmountInWordEntity.class);
        currencyAmountInWordEntity.setFraction(getCharValueFromBoolean(currencyAmountInWordDTO.isFraction()));
        if (log.isInfoEnabled()) {
            log.info(EXIT_STRING+CLASS_NAME+"convertDtoToEntity() with following converted entity {} CurrencyAmountInWordEntity :", currencyAmountInWordEntity);
        }
        return currencyAmountInWordEntity;
    }

    public CurrencyAmountInWordDTO convertEntityToDto(
            Optional<CurrencyAmountInWordEntity> currencyAmountInWordsEntity){
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"convertEntityToDto() with CurrencyAmountInWordsEntity {}",currencyAmountInWordsEntity);
        }
        CurrencyAmountInWordDTO currencyAmountInWordDTO = modelMapper.map(currencyAmountInWordsEntity, CurrencyAmountInWordDTO.class);
        currencyAmountInWordDTO.setFraction(getBooleanValueFromChar(currencyAmountInWordsEntity.get().getFraction()));
        currencyAmountInWordDTO.setStatus(currencyAmountInWordsEntity.get().getStatus());
        currencyAmountInWordDTO.setAction(currencyAmountInWordsEntity.get().getLastConfigurationAction());
        currencyAmountInWordDTO.setRecordVersion(currencyAmountInWordsEntity.get().getRecordVersion());
        currencyAmountInWordDTO.setCreatedBy(currencyAmountInWordsEntity.get().getCreatedBy());
        currencyAmountInWordDTO.setCreationTime(currencyAmountInWordsEntity.get().getCreationTime());
        currencyAmountInWordDTO.setLastUpdatedBy(currencyAmountInWordsEntity.get().getLastUpdatedBy());
        currencyAmountInWordDTO.setLastUpdatedTime(currencyAmountInWordsEntity.get().getLastUpdatedTime());
        if (log.isInfoEnabled()) {
            log.info(EXIT_STRING+CLASS_NAME+"convertDtoToEntity() with following converted DTO {} CurrencyAmountInWordDTO :", currencyAmountInWordDTO);
        }
        return currencyAmountInWordDTO;
    }

    public CurrencyAmountInWordDTO setAuditFields (MutationEntity mutationEntity, CurrencyAmountInWordDTO currencyAmountInWordDTO) {
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"setAuditFields() with MutationEntity {} and CurrencyAmountInWordDTO {} ",mutationEntity,currencyAmountInWordDTO);
        }
        currencyAmountInWordDTO.setAction(mutationEntity.getAction());
        currencyAmountInWordDTO.setAuthorized(mutationEntity.getAuthorized());
        currencyAmountInWordDTO.setRecordVersion(mutationEntity.getRecordVersion());
        currencyAmountInWordDTO.setStatus(mutationEntity.getStatus());
        currencyAmountInWordDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        currencyAmountInWordDTO.setCreatedBy(mutationEntity.getCreatedBy());
        currencyAmountInWordDTO.setCreationTime(mutationEntity.getCreationTime());
        currencyAmountInWordDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        currencyAmountInWordDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        currencyAmountInWordDTO.setTaskCode(mutationEntity.getTaskCode());
        currencyAmountInWordDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        if (log.isInfoEnabled()) {
            log.info(EXIT_STRING+CLASS_NAME+"setAuditFields() with following massaged DTO CurrencyAmountInWordDTO :", currencyAmountInWordDTO);
        }
        return currencyAmountInWordDTO;
    }

    public char getCharValueFromBoolean (boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar (Character value) {
        return value.equals(CHAR_Y);
    }
}
