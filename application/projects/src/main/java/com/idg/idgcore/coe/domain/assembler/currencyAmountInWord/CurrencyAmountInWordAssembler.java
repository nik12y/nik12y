package com.idg.idgcore.coe.domain.assembler.currencyAmountInWord;

import com.idg.idgcore.coe.domain.assembler.common.*;
import com.idg.idgcore.coe.domain.assembler.generic.*;
import com.idg.idgcore.coe.domain.entity.currencyAmountInWord.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.dto.currencyAmountInWord.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@Slf4j
public class CurrencyAmountInWordAssembler extends Assembler<CurrencyAmountInWordDTO, CurrencyAmountInWordEntity> {

    private static final String CLASS_NAME="CurrencyAmountInWordAssembler.";
    private static final String ENTERED_STRING="Entered into ";
    private static final String EXIT_STRING="Exited from ";

    @Override
    public Class getSpecificDTOClass() {
        return CurrencyAmountInWordDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return CurrencyAmountInWordEntity.class;
    }

    @Override
    public CurrencyAmountInWordEntity toEntity(CurrencyAmountInWordDTO currencyAmountInWordDTO) {
        log.info(ENTERED_STRING+CLASS_NAME+"toEntity() with CurrencyAmountInWordDTO{}:", currencyAmountInWordDTO);
        CurrencyAmountInWordEntity currencyAmountInWordEntity = super.toEntity(currencyAmountInWordDTO);
        currencyAmountInWordEntity.setFraction(getCharValueFromBoolean(currencyAmountInWordDTO.isFraction()));
        log.info(EXIT_STRING+CLASS_NAME+"toEntity() with converted CurrencyAmountInWordEntity {} :", currencyAmountInWordEntity);
        return currencyAmountInWordEntity;
    }

    @Override
    public CurrencyAmountInWordDTO toDTO(CurrencyAmountInWordEntity currencyAmountInWordEntity) {
        log.info(ENTERED_STRING+CLASS_NAME+"toDTO() with CurrencyAmountInWordEntity{}:", currencyAmountInWordEntity);
        CurrencyAmountInWordDTO currencyAmountInWordDTO = super.toDTO(currencyAmountInWordEntity);
        currencyAmountInWordDTO.setFraction(getBooleanValueFromChar(currencyAmountInWordEntity.getFraction()));
        currencyAmountInWordDTO.setAction(currencyAmountInWordEntity.getLastConfigurationAction());
        log.info(EXIT_STRING+CLASS_NAME+"toDTO() with converted CurrencyAmountInWordDTO {} :", currencyAmountInWordDTO);
        return currencyAmountInWordDTO;
    }

    public CurrencyAmountInWordDTO convertEntityToDto(
            Optional<CurrencyAmountInWordEntity> currencyAmountInWordsEntity){

        log.info(ENTERED_STRING+CLASS_NAME+"convertEntityToDto() with CurrencyAmountInWordsEntity {}",currencyAmountInWordsEntity);

        CurrencyAmountInWordDTO currencyAmountInWordDTO = modelMapper.map(currencyAmountInWordsEntity, CurrencyAmountInWordDTO.class);
        currencyAmountInWordDTO.setFraction(
                AssemblerUtils.getBooleanValueFromChar(currencyAmountInWordsEntity.get().getFraction()));
        currencyAmountInWordDTO.setAction(currencyAmountInWordsEntity.get().getLastConfigurationAction());
        log.info(EXIT_STRING+CLASS_NAME+"convertDtoToEntity() with following converted DTO {} CurrencyAmountInWordDTO :", currencyAmountInWordDTO);

        return currencyAmountInWordDTO;
    }

    public CurrencyAmountInWordDTO setAuditFields (MutationEntity mutationEntity, CurrencyAmountInWordDTO currencyAmountInWordDTO) {

        log.info(ENTERED_STRING+CLASS_NAME+"setAuditFields() with MutationEntity {} and CurrencyAmountInWordDTO {} ",mutationEntity,currencyAmountInWordDTO);

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

        log.info(EXIT_STRING+CLASS_NAME+"setAuditFields() with following massaged DTO CurrencyAmountInWordDTO :", currencyAmountInWordDTO);

        return currencyAmountInWordDTO;
    }

}
