package com.idg.idgcore.coe.domain.service.financialAccountingYear;

import com.idg.idgcore.coe.domain.entity.financialAccountingYear.*;
import com.idg.idgcore.coe.domain.repository.financialAccountingYear.*;
import com.idg.idgcore.coe.dto.financialAccountingYear.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.text.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith (MockitoExtension.class)
class FinancialAccountingYearDomainServiceTest {
    @Mock
    private IFinancialAccountingYearRepository repository;
    @InjectMocks
    private FinancialAccountingYearDomainService domainService;
    private FinancialAccountingYearEntity financialAccountingYearEntity;
    private FinancialAccountingYearDTO financialAccountingYearDTO;

    @BeforeEach
    void setUp () {
        financialAccountingYearDTO = getFinancialAccountingYearDTO();
        financialAccountingYearEntity = getFinancialAccountingYearEntity();
    }

    @Test
    @DisplayName ("Junit test for getCities method ")
    void getFinancialAccountingYears () {
        given(repository.findAll()).willReturn(List.of(financialAccountingYearEntity));
        List<FinancialAccountingYearEntity> financialAccountingYearEntityList = domainService.getFinancialAccountingYears();
        assertThat(financialAccountingYearEntityList).isNotNull();
        assertThat(financialAccountingYearEntityList).hasSize(1);
    }

    @Test
    @DisplayName ("JUnit test for getCities method for negative scenario")
    void getFinancialAccountingYearEmptyFinancialAccountingYearEntityList () {
        given(repository.findAll()).willReturn(Collections.emptyList());
        List<FinancialAccountingYearEntity> financialAccountingYearEntityList = domainService.getFinancialAccountingYears();
        assertThat(financialAccountingYearEntityList).isEmpty();
        assertThat(financialAccountingYearEntityList).hasSize(0);
    }

    @Test
    @DisplayName ("JUnit test for getFinancialAccountingYearById method")
    void getFinancialAccountingYearByCodeReturnFinancialAccountingYearEntityObject () {
        given(repository.getByBankCodeAndBranchCodeAndFinancialAccountingYearCode("ICI", "BRV",
                "FY2022")).willReturn(financialAccountingYearEntity);
        FinancialAccountingYearEntity financialAccountingYearEntity1 =
                domainService.getByBankCodeAndBranchCodeAndFinancialAccountingYearCode(
                        financialAccountingYearEntity.getBankCode(),
                        financialAccountingYearEntity.getBranchCode()
                        , financialAccountingYearEntity.getFinancialAccountingYearCode());
        assertThat(financialAccountingYearEntity1).isNotNull();
    }

    @Test
    @DisplayName ("JUnit test for getFinancialAccountingYearById catch block method")
    void getFinancialAccountingYearByCodeReturnCatchBlock () {
        FinancialAccountingYearEntity financialAccountingYearEntity1 = null;
        assertThrows(Exception.class, () -> {
            FinancialAccountingYearEntity financialAccountingYearEntity2 =
                    domainService.getByBankCodeAndBranchCodeAndFinancialAccountingYearCode(
                            financialAccountingYearEntity1.getBankCode(),
                            financialAccountingYearEntity1.getBranchCode(),
                            financialAccountingYearEntity1.getFinancialAccountingYearCode());
        });
    }

    @Test
    @DisplayName ("JUnit test for getConfigurationByCode try block method")
    void getConfigurationByCodeTryBlock () {
        given(repository.getByBankCodeAndBranchCodeAndFinancialAccountingYearCode("ICI","BRV","FY2022")).willReturn(financialAccountingYearEntity);
        FinancialAccountingYearEntity financialAccountingYearByCode = domainService.getByBankCodeAndBranchCodeAndFinancialAccountingYearCode(
                financialAccountingYearDTO.getBankCode(),
                financialAccountingYearDTO.getBranchCode(),
                financialAccountingYearDTO.getFinancialAccountingYearCode());
        assertThat(financialAccountingYearByCode).isNotNull();
    }

    @Test
    @DisplayName ("JUnit test for getConfigurationByCode for Catch Block method")
    void getConfigurationByCodeCatchBlock () {
        FinancialAccountingYearDTO financialAccountingYearDTO = null;
        assertThrows(Exception.class, () -> {
            FinancialAccountingYearEntity financialAccountingYearByCode =
                    domainService.getByBankCodeAndBranchCodeAndFinancialAccountingYearCode(
                            financialAccountingYearDTO.getBankCode(),
                            financialAccountingYearDTO.getBranchCode(),
                            financialAccountingYearDTO.getFinancialAccountingYearCode());
        });
    }

    @Test
    @DisplayName ("JUnit test for getConfigurationByCode for Catch Block method")
    void getSaveCodeCatchBlock () {
        FinancialAccountingYearDTO financialAccountingYearDTO = null;
        assertThrows(Exception.class, () -> {
            domainService.save(financialAccountingYearDTO);
        });
    }

    private FinancialAccountingYearEntity getFinancialAccountingYearEntity () {
        List<FinancialAccountingYearPeriodicCodeEntity> financialAccountingYearPeriodicCodeList = new ArrayList<>();
        FinancialAccountingYearEntity financialAccountingYearEntity =
                new FinancialAccountingYearEntity();
        financialAccountingYearEntity.setBankCode("ICI");
        financialAccountingYearEntity.setBranchCode("BRV");
        financialAccountingYearEntity.setStartDate(getDate("2022-01-01"));
        financialAccountingYearEntity.setEndDate(getDate("2022-02-28"));
        financialAccountingYearEntity.setFinancialAccountingYearCode("FY2022");
        financialAccountingYearEntity.setFinancialAccountingYearName(
                "Financial Accounting Year FY 2022");
        financialAccountingYearEntity.setPeriodCodeFrequency("Half-Yearly");
        financialAccountingYearEntity.setStatus("new");
        financialAccountingYearEntity.setRecordVersion(1);
        financialAccountingYearEntity.setAuthorized("Y");
        financialAccountingYearEntity.setLastConfigurationAction("new");
        /**
         * setting child records*/
        FinancialAccountingYearPeriodicCodeEntity entity1 = new FinancialAccountingYearPeriodicCodeEntity(
                "ICI", "BRV", "FY2022", "Q1", getDate("2022-01-01"), getDate("2022-12-31"),"Open","Open","new", 1, "Y", "");
        FinancialAccountingYearPeriodicCodeEntity entity2 = new FinancialAccountingYearPeriodicCodeEntity();
        entity2.setPeriodCode("Q2");
        entity2.setBankCode("ICI");
        entity2.setBranchCode("BRV");
        entity2.setFinancialAccountingYearCode("FY2022");
        entity2.setPeriodCode("Q2");
        entity2.setStartDateAccountingPeriod(getDate("2022-02-01"));
        entity2.setEndDateAccountingPeriod(getDate("2022-02-28"));
        FinancialAccountingYearPeriodicCodeEntity entity3 = new FinancialAccountingYearPeriodicCodeEntity(
                "ICI", "BRV", "FY2022", "FC", getDate("2022-02-28"), getDate("2022-02-28"),"Open","Open","new", 1, "Y", "");
        List<FinancialAccountingYearPeriodicCodeEntity> periodicCodeEntityList = new ArrayList<FinancialAccountingYearPeriodicCodeEntity>();
        periodicCodeEntityList.add(entity1);
        periodicCodeEntityList.add(entity2);
        periodicCodeEntityList.add(entity3);
        financialAccountingYearEntity.setFinancialAccountingYearPeriodicCode(
                financialAccountingYearPeriodicCodeList);
        return financialAccountingYearEntity;
    }

    private Date getDate (String s) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(s);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private FinancialAccountingYearDTO getFinancialAccountingYearDTO () {
        List<FinancialAccountingYearPeriodicCodeDTO> financialAccountingYearPeriodicCodeList = new ArrayList<>();
        String sDate = "2022-01-01";
        String eDate = "2022-12-31";
        FinancialAccountingYearPeriodicCodeDTO dto1 = new FinancialAccountingYearPeriodicCodeDTO(
                "ICI", "BRV", "FY2022", "Q1", sDate, eDate,"Open","Open");
        FinancialAccountingYearPeriodicCodeDTO dto2 = new FinancialAccountingYearPeriodicCodeDTO();
        dto2.setBankCode("ICI");
        dto2.setBranchCode("BRV");
        dto2.setFinancialAccountingYearCode("FY2022");
        dto2.setPeriodCode("FEB");
        dto2.setStartDateAccountingPeriod(sDate);
        dto2.setEndDateAccountingPeriod(eDate);
        FinancialAccountingYearPeriodicCodeDTO dto3 = new FinancialAccountingYearPeriodicCodeDTO(
                "ICI", "BRV", "FY2022", "FC", sDate, eDate,"Open","Open");
        financialAccountingYearPeriodicCodeList.add(dto1);
        financialAccountingYearPeriodicCodeList.add(dto2);
        financialAccountingYearPeriodicCodeList.add(dto3);
        FinancialAccountingYearDTO financialAccountingYearDTO =
                new FinancialAccountingYearDTO();
        financialAccountingYearDTO.setBankCode("ICI");
        financialAccountingYearDTO.setBranchCode("BRV");
        financialAccountingYearDTO.setStartDate(sDate);
        financialAccountingYearDTO.setEndDate(eDate);
        financialAccountingYearDTO.setFinancialAccountingYearCode("FY2022");
        financialAccountingYearDTO.setFinancialAccountingYearName(
                "Financial Accounting Year FY 2022");
        financialAccountingYearDTO.setPeriodCodeFrequency("Half-Yearly");
        financialAccountingYearDTO.setStatus("new");
        financialAccountingYearDTO.setRecordVersion(1);
        financialAccountingYearDTO.setAuthorized("Y");
        financialAccountingYearDTO.setLastConfigurationAction("new");
        financialAccountingYearDTO.setFinancialAccountingYearPeriodicCode(
                financialAccountingYearPeriodicCodeList);
        financialAccountingYearDTO.setAuthorized("Y");
        financialAccountingYearDTO.setBankCode("ICI");
        return financialAccountingYearDTO;
    }

}