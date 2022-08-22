package com.idg.idgcore.coe.domain.assembler.financialAccountingYear;

import com.idg.idgcore.coe.domain.entity.financialAccountingYear.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.dto.financialAccountingYear.*;
import org.modelmapper.*;
import org.modelmapper.convention.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.text.*;
import java.util.*;
import java.util.stream.*;

@Component
public class FinancialAccountingYearAssembler {
    private final ModelMapper modelMapper = new ModelMapper();
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @PostConstruct
    private void setMapperConfig () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public FinancialAccountingYearEntity convertDtoToEntity (FinancialAccountingYearDTO inputDTO) {
        List<FinancialAccountingYearPeriodicCodeDTO> periodDTOList = inputDTO.getFinancialAccountingYearPeriodicCode();
        List<FinancialAccountingYearPeriodicCodeEntity> periodEntityList = new ArrayList<>();
        periodEntityList.addAll(periodDTOList.stream().map(entity -> {
            FinancialAccountingYearPeriodicCodeEntity map = modelMapper.map(entity,
                    FinancialAccountingYearPeriodicCodeEntity.class);
            map.setBankCode(inputDTO.getBankCode());
            map.setBranchCode(inputDTO.getBranchCode());
            map.setFinancialAccountingYearCode(inputDTO.getFinancialAccountingYearCode());
            return map;
        }).collect(Collectors.toList()));
        FinancialAccountingYearEntity outEntity = modelMapper.map(inputDTO,
                FinancialAccountingYearEntity.class);
        outEntity.setFinancialAccountingYearPeriodicCode(periodEntityList);
        return outEntity;
    }

    public FinancialAccountingYearDTO convertEntityToDto (FinancialAccountingYearEntity inEntity) {
        if (inEntity != null) {
            List<FinancialAccountingYearPeriodicCodeDTO> periodDTOList = new ArrayList<>();
            List<FinancialAccountingYearPeriodicCodeEntity> periodEntityList = inEntity.getFinancialAccountingYearPeriodicCode();
            periodDTOList.addAll(periodEntityList.stream().map(dto -> {
                FinancialAccountingYearPeriodicCodeDTO periodDTO = new FinancialAccountingYearPeriodicCodeDTO();
                periodDTO.setPeriodCode(dto.getPeriodCode());
                periodDTO.setStartDateAccountingPeriod(
                        formatter.format(dto.getStartDateAccountingPeriod()));
                periodDTO.setEndDateAccountingPeriod(
                        formatter.format(dto.getEndDateAccountingPeriod()));
                return periodDTO;
            }).collect(Collectors.toList()));
            FinancialAccountingYearDTO outDTO = modelMapper.map(inEntity,
                    FinancialAccountingYearDTO.class);
            outDTO.setStartDate(formatter.format(inEntity.getStartDate()));
            outDTO.setEndDate(formatter.format(inEntity.getEndDate()));
            outDTO.setFinancialAccountingYearPeriodicCode(periodDTOList);
            return outDTO;
        }
        else {

            return null;
        }
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
