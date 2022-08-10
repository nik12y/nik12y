package com.idg.idgcore.coe.domain.assembler.financialAccountingYear;

import com.idg.idgcore.coe.domain.entity.financialAccountingYear.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.dto.financialAccountingYear.*;
import org.modelmapper.*;
import org.modelmapper.convention.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;


@Component
public class FinancialAccountingYearAssembler {
    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public FinancialAccountingYearEntity convertDtoToEntity (FinancialAccountingYearDTO inputDTO) {
        List<FinancialAccountingYearPeriodicCodeDTO> periodDTOList = inputDTO.getFinancialAccountingYearPeriodicCode();
        List<FinancialAccountingYearPeriodicCodeEntity> periodEntityList = new ArrayList<>();
        periodEntityList.addAll(periodDTOList.stream().map(entity -> {
            return  modelMapper.map(entity, FinancialAccountingYearPeriodicCodeEntity.class);
        }).collect(Collectors.toList()));
        FinancialAccountingYearEntity outEntity = modelMapper.map(inputDTO,
                FinancialAccountingYearEntity.class);
        outEntity.setFinancialAccountingYearPeriodicCode(periodEntityList);
        return outEntity;
    }

    public FinancialAccountingYearDTO convertEntityToDto (FinancialAccountingYearEntity inEntity) {
        List<FinancialAccountingYearPeriodicCodeDTO> periodDTOList = new ArrayList<>();
        List<FinancialAccountingYearPeriodicCodeEntity> periodEntityList = inEntity.getFinancialAccountingYearPeriodicCode();
        periodDTOList.addAll(periodEntityList.stream().map(dto -> {
            FinancialAccountingYearPeriodicCodeDTO periodDTO = new FinancialAccountingYearPeriodicCodeDTO();
            periodDTO.setPeriodCode(dto.getPeriodCode());
            periodDTO.setStartDateAccountingPeriod(dto.getStartDateAccountingPeriod());
            periodDTO.setEndDateAccountingPeriod(dto.getEndDateAccountingPeriod());
            return periodDTO;
        }).collect(Collectors.toList()));

        FinancialAccountingYearDTO outDTO = modelMapper.map(inEntity,
                FinancialAccountingYearDTO.class);
        outDTO.setFinancialAccountingYearPeriodicCode(periodDTOList);
        return outDTO;
    }

    public FinancialAccountingYearDTO setAuditFields (MutationEntity mutationEntity,
            FinancialAccountingYearDTO dto) {
        dto.setAction(mutationEntity.getAction());
        dto.setAuthorized(mutationEntity.getAuthorized());
        dto.setRecordVersion(mutationEntity.getRecordVersion());
        dto.setStatus(mutationEntity.getStatus());
        dto.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        dto.setCreatedBy(mutationEntity.getCreatedBy());
        dto.setCreationTime(mutationEntity.getCreationTime());
        dto.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        dto.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        dto.setTaskCode(mutationEntity.getTaskCode());
        dto.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return dto;
    }


}
