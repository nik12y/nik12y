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
        given(repository.findByBankCode("BNP")).willReturn(financialAccountingYearEntity);
        FinancialAccountingYearEntity financialAccountingYearEntity1 =
                domainService.getFinancialAccountingYearByCode(
                        financialAccountingYearEntity.getBankCode());
        assertThat(financialAccountingYearEntity1).isNotNull();
    }

    @Test
    @DisplayName ("JUnit test for getFinancialAccountingYearById catch block method")
    void getFinancialAccountingYearByCodeReturnCatchBlock () {
        FinancialAccountingYearEntity financialAccountingYearEntity1 = null;
        assertThrows(Exception.class, () -> {
            FinancialAccountingYearEntity financialAccountingYearEntity2 =
                    domainService.getFinancialAccountingYearByCode(
                            financialAccountingYearEntity1.getBankCode());
        });
    }

    @Test
    @DisplayName ("JUnit test for getConfigurationByCode try block method")
    void getConfigurationByCodeTryBlock () {
        given(repository.findByBankCode("BNP")).willReturn(financialAccountingYearEntity);
        FinancialAccountingYearEntity financialAccountingYearByCode = domainService.getConfigurationByCode(
                financialAccountingYearDTO);
        assertThat(financialAccountingYearByCode).isNotNull();
    }

/*
    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    void getConfigurationByCodeCatchBlock() {
        FinancialAccountingYearDTO financialAccountingYearDTO = null;
        assertThrows(BusinessException.class,()-> {
            FinancialAccountingYearEntity financialAccountingYearByCode = financialAccountingYearDomainService.getConfigurationByCode(financialAccountingYearDTO);
        });
    }*/

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
        FinancialAccountingYearPeriodicCodeEntity entity1 = new FinancialAccountingYearPeriodicCodeEntity(
                1L, 2021L, "JAN", getDate("2022-01-01"), getDate("2022-12-31"));
        FinancialAccountingYearPeriodicCodeEntity entity2 = new FinancialAccountingYearPeriodicCodeEntity();
        entity2.setPeriodCode("Feb");
        entity2.setFinAccYearPeriodCodesId(2L);
        entity2.setFinancialAccountingYearId(2021L);
        entity2.setStartDateAccountingPeriod(getDate("2022-02-01"));
        entity2.setEndDateAccountingPeriod(getDate("2022-02-28"));
        FinancialAccountingYearPeriodicCodeEntity entity3 = new FinancialAccountingYearPeriodicCodeEntity(
                1L, 2021L, "JAN", getDate("2022-02-28"), getDate("2022-02-28"));
        List<FinancialAccountingYearPeriodicCodeEntity> periodicCodeEntityList = new ArrayList<FinancialAccountingYearPeriodicCodeEntity>();
        periodicCodeEntityList.add(entity1);
        periodicCodeEntityList.add(entity2);
        periodicCodeEntityList.add(entity3);
        FinancialAccountingYearEntity financialAccountingYearEntity =
                new FinancialAccountingYearEntity();
        financialAccountingYearEntity.setBankCode("BNP");
        financialAccountingYearEntity.setFinancialAccountingYearId(1L);
        financialAccountingYearEntity.setBranchCode("CBB");
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
        Date sDate = getDate("2022-01-01");
        Date eDate = getDate("2022-12-31");
        FinancialAccountingYearPeriodicCodeDTO dto1 = new FinancialAccountingYearPeriodicCodeDTO(
                1L, "JAN", sDate, eDate);
        FinancialAccountingYearPeriodicCodeDTO dto2 = new FinancialAccountingYearPeriodicCodeDTO();
        dto2.setPeriodCode("FEB");
        dto2.setFinAccYearPeriodCodesId(1L);
        dto2.setStartDateAccountingPeriod(sDate);
        dto2.setEndDateAccountingPeriod(eDate);
        FinancialAccountingYearPeriodicCodeDTO dto3 = new FinancialAccountingYearPeriodicCodeDTO(
                1L, "FC", sDate, eDate);
        financialAccountingYearPeriodicCodeList.add(dto1);
        financialAccountingYearPeriodicCodeList.add(dto2);
        financialAccountingYearPeriodicCodeList.add(dto3);
        FinancialAccountingYearDTO financialAccountingYearDTO =
                new FinancialAccountingYearDTO();
        financialAccountingYearDTO.setBankCode("BNP");
        financialAccountingYearDTO.setFinancialAccountingYearId(1L);
        financialAccountingYearDTO.setBranchCode("CBB");
        financialAccountingYearDTO.setStartDate(getDate("2022-01-01"));
        financialAccountingYearDTO.setEndDate(getDate("2022-02-28"));
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
        financialAccountingYearDTO.setBankCode("BNP");
        return financialAccountingYearDTO;
    }

}