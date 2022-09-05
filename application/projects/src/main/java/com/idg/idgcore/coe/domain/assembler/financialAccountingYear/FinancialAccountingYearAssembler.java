package com.idg.idgcore.coe.domain.assembler.financialAccountingYear;

import com.idg.idgcore.coe.domain.entity.financialAccountingYear.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.dto.financialAccountingYear.*;
import org.modelmapper.*;
import org.modelmapper.convention.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.text.*;
import java.time.*;
import java.time.temporal.*;
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
        FinancialAccountingYearEntity outEntity = modelMapper.map(inputDTO,
                FinancialAccountingYearEntity.class);
        List<FinancialAccountingYearPeriodicCodeDTO> periodDTOList = inputDTO.getFinancialAccountingYearPeriodicCode();
        List<FinancialAccountingYearPeriodicCodeEntity> periodEntityList = new ArrayList<>();
        periodEntityList.addAll(periodDTOList.stream().map(entity -> {
            FinancialAccountingYearPeriodicCodeEntity map = modelMapper.map(entity,
                    FinancialAccountingYearPeriodicCodeEntity.class);
            map.setBankCode(inputDTO.getBankCode());
            map.setBranchCode(inputDTO.getBranchCode());
            map.setFinancialAccountingYearCode(inputDTO.getFinancialAccountingYearCode());
            map.setPeriodCodeClosureStatus("Open");
            map.setFinancialYearClosureStatus("Open");
            map.setStatus(outEntity.getStatus());
            map.setAuthorized(outEntity.getAuthorized());
            map.setRecordVersion(outEntity.getRecordVersion());
            map.setLastConfigurationAction(outEntity.getLastConfigurationAction());
            return map;
        }).toList());
        outEntity.setFinancialAccountingYearPeriodicCode(periodEntityList);
        return outEntity;
    }

    public FinancialAccountingYearDTO convertEntityToDto (FinancialAccountingYearEntity inEntity) {
        if (inEntity != null) {
            FinancialAccountingYearDTO outDTO = modelMapper.map(inEntity,
                    FinancialAccountingYearDTO.class);
            List<FinancialAccountingYearPeriodicCodeDTO> periodDTOList = new ArrayList<>();
            List<FinancialAccountingYearPeriodicCodeEntity> periodEntityList = inEntity.getFinancialAccountingYearPeriodicCode();
            periodDTOList.addAll(periodEntityList.stream().map(dto -> {
                FinancialAccountingYearPeriodicCodeDTO periodDTO = new FinancialAccountingYearPeriodicCodeDTO();
                periodDTO.setPeriodCode(dto.getPeriodCode());
                periodDTO.setStartDateAccountingPeriod(
                        formatter.format(dto.getStartDateAccountingPeriod()));
                periodDTO.setEndDateAccountingPeriod(
                        formatter.format(dto.getEndDateAccountingPeriod()));
                periodDTO.setPeriodCodeClosureStatus(dto.getPeriodCodeClosureStatus());
                periodDTO.setFinancialYearClosureStatus(dto.getFinancialYearClosureStatus());
                periodDTO.setStatus(outDTO.getStatus());
                periodDTO.setAuthorized(outDTO.getAuthorized());
                periodDTO.setRecordVersion(outDTO.getRecordVersion());
                periodDTO.setAction(outDTO.getAction());
                periodDTO.setLastConfigurationAction(outDTO.getLastConfigurationAction());
                return periodDTO;
            }).toList());
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

    public FinancialAccountingYearProcessDTO getDateAndPeriodCode (
            FinancialAccountingYearEntity inEntity,
            Date inputDate) {
        String periodCode = null;
        String startDateAccountingPeriod = null;
        String endDateAccountingPeriod = null;
        String periodCodeClosureStatus = null;
        String financialYearClosureStatus = null;
        if (inEntity != null) {
            List<FinancialAccountingYearPeriodicCodeDTO> periodDTOList = new ArrayList<>();
            List<FinancialAccountingYearPeriodicCodeEntity> periodEntityList = inEntity.getFinancialAccountingYearPeriodicCode();
            periodDTOList.addAll(periodEntityList.stream().map(dto -> {
                FinancialAccountingYearPeriodicCodeDTO periodDTO = null;
                if ((inputDate.equals(dto.getStartDateAccountingPeriod()) || inputDate.equals(
                        dto.getEndDateAccountingPeriod())) ||
                        (inputDate.after(dto.getStartDateAccountingPeriod()) && inputDate.before(
                                dto.getEndDateAccountingPeriod()))) {
                    periodDTO = new FinancialAccountingYearPeriodicCodeDTO();
                    periodDTO.setPeriodCode(dto.getPeriodCode());
                    periodDTO.setStartDateAccountingPeriod(
                            formatter.format(dto.getStartDateAccountingPeriod()));
                    periodDTO.setEndDateAccountingPeriod(
                            formatter.format(dto.getEndDateAccountingPeriod()));
                }
                return periodDTO;
            }).toList());
            periodDTOList.removeIf(Objects::isNull);
            /**
             * TO get period code details for fetched Financial Accounting year */
            for (FinancialAccountingYearPeriodicCodeDTO dtos : periodDTOList) {
                //prints the elements of the List
                periodCode = dtos.getPeriodCode();
                startDateAccountingPeriod = dtos.getStartDateAccountingPeriod();
                endDateAccountingPeriod = dtos.getEndDateAccountingPeriod();
                periodCodeClosureStatus = dtos.getPeriodCodeClosureStatus();
                financialYearClosureStatus = dtos.getFinancialYearClosureStatus();
            }
            /**
             * TO get number of days for fetched Financial Accounting year */
            LocalDate dateBefore = LocalDate.parse(formatter.format(inEntity.getStartDate()));
            LocalDate dateAfter = LocalDate.parse(formatter.format(inEntity.getEndDate()));
            //calculating number of days in between
            long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
            FinancialAccountingYearProcessDTO outDTO = modelMapper.map(inEntity,
                    FinancialAccountingYearProcessDTO.class);
            outDTO.setStartDate(formatter.format(inEntity.getStartDate()));
            outDTO.setEndDate(formatter.format(inEntity.getEndDate()));
            outDTO.setPeriodCode(periodCode);
            outDTO.setStartDateAccountingPeriod(startDateAccountingPeriod);
            outDTO.setEndDateAccountingPeriod(endDateAccountingPeriod);
            outDTO.setPeriodCodeClosureStatus(periodCodeClosureStatus);
            outDTO.setFinancialYearClosureStatus(financialYearClosureStatus);
            outDTO.setBranchDate(formatter.format(inputDate));
            outDTO.setNumberOfDays(noOfDaysBetween + 1);
            return outDTO;
        }
        else {
            return null;
        }
    }

    public FinancialAccountingYearDTO getDtoFromEntity (
            FinancialAccountingYearEntity inEntity,
            Date inputDate) {
        if (inEntity != null) {
            List<FinancialAccountingYearPeriodicCodeDTO> periodDTOList = new ArrayList<>();
            List<FinancialAccountingYearPeriodicCodeEntity> periodEntityList = inEntity.getFinancialAccountingYearPeriodicCode();
            periodDTOList.addAll(periodEntityList.stream().map(dto -> {
                FinancialAccountingYearPeriodicCodeDTO periodDTO = null;
                if ((inputDate.equals(dto.getStartDateAccountingPeriod()) || inputDate.equals(
                        dto.getEndDateAccountingPeriod())) ||
                        (inputDate.after(dto.getStartDateAccountingPeriod()) && inputDate.before(
                                dto.getEndDateAccountingPeriod()))) {
                    periodDTO = new FinancialAccountingYearPeriodicCodeDTO();
                    periodDTO.setPeriodCode(dto.getPeriodCode());
                    periodDTO.setStartDateAccountingPeriod(
                            formatter.format(dto.getStartDateAccountingPeriod()));
                    periodDTO.setEndDateAccountingPeriod(
                            formatter.format(dto.getEndDateAccountingPeriod()));
                }
                return periodDTO;
            }).toList());
            periodDTOList.removeIf(Objects::isNull);
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

}
