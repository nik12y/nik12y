package com.idg.idgcore.coe.domain.util;

import com.idg.idgcore.coe.dto.financialAccountingYear.*;

import java.time.*;
import java.time.format.*;
import java.time.temporal.*;
import java.util.*;

import static com.idg.idgcore.coe.common.Constants.BI_MONTHLY;
import static com.idg.idgcore.coe.common.Constants.DATE_FORMATTER;
import static com.idg.idgcore.coe.common.Constants.FNC;
import static com.idg.idgcore.coe.common.Constants.HALF_YEARLY;
import static com.idg.idgcore.coe.common.Constants.MONTHLY;
import static com.idg.idgcore.coe.common.Constants.QUARTERLY;
import static com.idg.idgcore.coe.common.Constants.YEARLY;

public class FinancialAccountingYearUtil {
    public List<FinancialAccountingYearPeriodicCodeDTO> getMonthlyPeriodCodeDetails (String strDate
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        LocalDate localDate = LocalDate.parse(strDate, formatter);
        List periodicCodeDTOSList = new ArrayList<FinancialAccountingYearPeriodicCodeDTO>();
        try {
            int dayOfMonth = 0;
            int toadd = 0;
            int count = 0;
            String periodCode = String.valueOf(localDate.getMonth());
            String periodCodeStartDate = localDate.toString();
            String periodCodeEndDate = null;
            for (int i = 1; i <= 12; i++) {
                FinancialAccountingYearPeriodicCodeDTO financialAccountingYearPeriodicCodeDTO = new FinancialAccountingYearPeriodicCodeDTO();
                periodCode = String.valueOf(localDate.getMonth());
                periodCodeStartDate = localDate.toString();
                dayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
                toadd = dayOfMonth - 1;
                periodCodeEndDate = localDate.plusDays(toadd).toString();
                localDate = localDate.plusDays((dayOfMonth));
                /**
                 */
                financialAccountingYearPeriodicCodeDTO.setPeriodCode(periodCode);
                financialAccountingYearPeriodicCodeDTO.setStartDateAccountingPeriod(
                        periodCodeStartDate);
                financialAccountingYearPeriodicCodeDTO.setEndDateAccountingPeriod(
                        periodCodeEndDate);
                periodicCodeDTOSList.add(financialAccountingYearPeriodicCodeDTO);
            }
            /**
             * Setting the Financial Closure periodCode
             */
            FinancialAccountingYearPeriodicCodeDTO periodCodeFNC = new FinancialAccountingYearPeriodicCodeDTO();
            periodCodeFNC.setPeriodCode(FNC);
            periodCodeFNC.setStartDateAccountingPeriod(periodCodeEndDate);
            periodCodeFNC.setEndDateAccountingPeriod(periodCodeEndDate);
            periodicCodeDTOSList.add(periodCodeFNC);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return periodicCodeDTOSList;
    }

    public List<FinancialAccountingYearPeriodicCodeDTO> getHalfYearlyPeriodCodeDetails (
            String strDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(strDate, formatter);
        List periodicCodeDTOSList = new ArrayList<FinancialAccountingYearPeriodicCodeDTO>();
        try {
            int dayOfMonth = 0;
            int toadd = 0;
            int count = 0;
            String periodCode = String.valueOf(localDate.getMonth());
            String periodCodeStartDate = localDate.toString();
            String periodCodeEndDate = null;
            for (int i = 1; i <= 12; i++) {
                FinancialAccountingYearPeriodicCodeDTO financialAccountingYearPeriodicCodeDTO = new FinancialAccountingYearPeriodicCodeDTO();
                /**
                 * */
                if (count == 5) {
                    dayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
                    periodCodeEndDate = localDate.plusDays(dayOfMonth - 1).toString();
                    /**
                     */
                    financialAccountingYearPeriodicCodeDTO.setPeriodCode(periodCode);
                    financialAccountingYearPeriodicCodeDTO.setStartDateAccountingPeriod(
                            periodCodeStartDate);
                    financialAccountingYearPeriodicCodeDTO.setEndDateAccountingPeriod(
                            periodCodeEndDate);
                    periodicCodeDTOSList.add(financialAccountingYearPeriodicCodeDTO);
                    /**
                     * */
                    localDate = localDate.plusDays((dayOfMonth));
                    periodCode = String.valueOf(localDate.getMonth());
                    count = -1;
                    periodCodeStartDate = localDate.toString();
                }
                else {
                    dayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
                    periodCodeEndDate = localDate.plusDays(dayOfMonth - 1).toString();
                    localDate = localDate.plusDays((dayOfMonth));
                }
                count++;
            }
            /**
             * Setting the Financial Closure periodCode
             */
            FinancialAccountingYearPeriodicCodeDTO periodCodeFNC = new FinancialAccountingYearPeriodicCodeDTO();
            periodCodeFNC.setPeriodCode(FNC);
            periodCodeFNC.setStartDateAccountingPeriod(periodCodeEndDate);
            periodCodeFNC.setEndDateAccountingPeriod(periodCodeEndDate);
            periodicCodeDTOSList.add(periodCodeFNC);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return periodicCodeDTOSList;
    }

    public List<FinancialAccountingYearPeriodicCodeDTO> getBiMonthlyPeriodCodeDetails (
            String strDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(strDate, formatter);
        List<FinancialAccountingYearPeriodicCodeDTO> periodicCodeDTOSList = new ArrayList<>();
        try {
            int dayOfMonth = 0;
            int count = 0;
            String periodCode = String.valueOf(localDate.getMonth());
            String periodCodeStartDate = localDate.toString();
            String periodCodeEndDate = null;
            for (int i = 1; i <= 12; i++) {
                FinancialAccountingYearPeriodicCodeDTO financialAccountingYearPeriodicCodeDTO = new FinancialAccountingYearPeriodicCodeDTO();
                /**
                 * */
                if (count == 1) {
                    dayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
                    periodCodeEndDate = localDate.plusDays(dayOfMonth - 1).toString();
                    financialAccountingYearPeriodicCodeDTO.setPeriodCode(periodCode);
                    financialAccountingYearPeriodicCodeDTO.setStartDateAccountingPeriod(
                            periodCodeStartDate);
                    financialAccountingYearPeriodicCodeDTO.setEndDateAccountingPeriod(
                            periodCodeEndDate);
                    periodicCodeDTOSList.add(financialAccountingYearPeriodicCodeDTO);
                    /**
                     * */
                    localDate = localDate.plusDays((dayOfMonth));
                    periodCode = String.valueOf(localDate.getMonth());
                    count = -1;
                    periodCodeStartDate = localDate.toString();
                }
                else {
                    dayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
                    periodCodeEndDate = localDate.plusDays(dayOfMonth - 1).toString();
                    localDate = localDate.plusDays((dayOfMonth));
                }
                count++;
            }
            /**
             * Setting the Financial Closure periodCode
             */
            FinancialAccountingYearPeriodicCodeDTO periodCodeFNC = new FinancialAccountingYearPeriodicCodeDTO();
            periodCodeFNC.setPeriodCode(FNC);
            periodCodeFNC.setStartDateAccountingPeriod(periodCodeEndDate);
            periodCodeFNC.setEndDateAccountingPeriod(periodCodeEndDate);
            periodicCodeDTOSList.add(periodCodeFNC);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return periodicCodeDTOSList;
    }

    public List<FinancialAccountingYearPeriodicCodeDTO> getQuarterlyPeriodCodeDetails (
            String strDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(strDate, formatter);
        List periodicCodeDTOSList = new ArrayList<FinancialAccountingYearPeriodicCodeDTO>();
        try {
            int dayOfMonth = 0;
            int count = 0;
            String periodCode = String.valueOf(localDate.getMonth());
            String periodCodeStartDate = localDate.toString();
            String periodCodeEndDate = null;
            for (int i = 1; i <= 12; i++) {
                FinancialAccountingYearPeriodicCodeDTO financialAccountingYearPeriodicCodeDTO = new FinancialAccountingYearPeriodicCodeDTO();

                if (count == 2) {
                    //                            periodCodeEndDate = localDate.plusDays(toadd - 1).toString();
                    dayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
                    periodCodeEndDate = localDate.plusDays(dayOfMonth - 1).toString();

                    financialAccountingYearPeriodicCodeDTO.setPeriodCode(periodCode);
                    financialAccountingYearPeriodicCodeDTO.setStartDateAccountingPeriod(
                            periodCodeStartDate);
                    financialAccountingYearPeriodicCodeDTO.setEndDateAccountingPeriod(
                            periodCodeEndDate);
                    periodicCodeDTOSList.add(financialAccountingYearPeriodicCodeDTO);
                    /**
                     * */
                    localDate = localDate.plusDays((dayOfMonth));
                    periodCode = String.valueOf(localDate.getMonth());
                    count = -1;
                    periodCodeStartDate = localDate.toString();
                }
                else {
                    dayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
                    periodCodeEndDate = localDate.plusDays(dayOfMonth - 1).toString();
                    localDate = localDate.plusDays((dayOfMonth));
                }
                count++;
            }
            FinancialAccountingYearPeriodicCodeDTO periodCodeFNC = new FinancialAccountingYearPeriodicCodeDTO();
            periodCodeFNC.setPeriodCode(FNC);
            periodCodeFNC.setStartDateAccountingPeriod(periodCodeEndDate);
            periodCodeFNC.setEndDateAccountingPeriod(periodCodeEndDate);
            periodicCodeDTOSList.add(periodCodeFNC);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return periodicCodeDTOSList;
    }

    private List<FinancialAccountingYearPeriodicCodeDTO> getYearlyPeriodCodeDetails (
            String startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(startDate, formatter);
        List<FinancialAccountingYearPeriodicCodeDTO> periodicCodeDTOSList = new ArrayList<>();
        try {
            int dayOfMonth = 0;
            String periodCode = String.valueOf(localDate.getMonth());
            String periodCodeStartDate = localDate.toString();
            String periodCodeEndDate = null;
            FinancialAccountingYearPeriodicCodeDTO financialAccountingYearPeriodicCodeDTO = new FinancialAccountingYearPeriodicCodeDTO();
            for (int i = 1; i <= 12; i++) {
                dayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
                periodCodeEndDate = localDate.plusDays(dayOfMonth - 1).toString();
                localDate = localDate.plusDays((dayOfMonth));
            }
            financialAccountingYearPeriodicCodeDTO.setPeriodCode(periodCode);
            financialAccountingYearPeriodicCodeDTO.setStartDateAccountingPeriod(
                    periodCodeStartDate);
            financialAccountingYearPeriodicCodeDTO.setEndDateAccountingPeriod(
                    periodCodeEndDate);
            periodicCodeDTOSList.add(financialAccountingYearPeriodicCodeDTO);
            FinancialAccountingYearPeriodicCodeDTO periodCodeFNC = new FinancialAccountingYearPeriodicCodeDTO();
            periodCodeFNC.setPeriodCode(FNC);
            periodCodeFNC.setStartDateAccountingPeriod(periodCodeEndDate);
            periodCodeFNC.setEndDateAccountingPeriod(periodCodeEndDate);
            periodicCodeDTOSList.add(periodCodeFNC);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return periodicCodeDTOSList;
    }

    public FinancialAccountingYearDTO getPeriodCodeDetails (FinancialAccountingYearDTO dto) {
        dto.getPeriodCodeFrequency();
        List<FinancialAccountingYearPeriodicCodeDTO> periodCodeDetails = null;
        if (MONTHLY.equals(dto.getPeriodCodeFrequency())) {
            periodCodeDetails = getMonthlyPeriodCodeDetails(dto.getStartDate());
        }
        else if (BI_MONTHLY.equals(dto.getPeriodCodeFrequency())) {
            periodCodeDetails = getBiMonthlyPeriodCodeDetails(dto.getStartDate());
        }
        else if (QUARTERLY.equals(dto.getPeriodCodeFrequency())) {
            periodCodeDetails = getQuarterlyPeriodCodeDetails(dto.getStartDate());
        }
        else if (HALF_YEARLY.equals(dto.getPeriodCodeFrequency())) {
            periodCodeDetails = getHalfYearlyPeriodCodeDetails(dto.getStartDate());
        }
        else if (YEARLY.equals(dto.getPeriodCodeFrequency())) {
            periodCodeDetails = getYearlyPeriodCodeDetails(dto.getStartDate());
        }
        FinancialAccountingYearDTO responseDto = dto;
        responseDto.setFinancialAccountingYearPeriodicCode(periodCodeDetails);
        return responseDto;
    }

}
