package com.idg.idgcore.coe.domain.service.bankparameter;

import com.idg.idgcore.coe.domain.entity.bankparameter.*;
import com.idg.idgcore.coe.domain.repository.bankparameter.IBankParameterRepository;
import com.idg.idgcore.coe.dto.bankparameter.*;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith (MockitoExtension.class)
class BankParameterDomainServiceTest {

    @Mock
    private IBankParameterRepository bankParameterRepository;

    @InjectMocks
    private BankParameterDomainService bankParameterDomainService;

    private BankParameterEntity bankParameterEntity;
    private BankParameterDTO bankParameterDTO;


    @BeforeEach
    void setUp() {
        bankParameterDTO=getBankParameterDTO ();
        bankParameterEntity=getBankParameterEntity();
    }

    @Test
    @DisplayName("Junit test for getBankParameters method ")
    void getBankParametersReturnBankParametersList() {
        given(bankParameterRepository.findAll()).willReturn(List.of(bankParameterEntity));
        List<BankParameterEntity> bankParameterEntityList = bankParameterDomainService.getBankParameters();
        assertThat(bankParameterEntityList).isNotNull();
        assertThat(bankParameterEntityList.size()).isEqualTo(1);
    }


    @Test
    @DisplayName("JUnit test for getBankParameters method for negative scenario")
    void getBankParametersEmptyBankParameterEntityList()
    {
        given(bankParameterRepository.findAll()).willReturn(Collections.emptyList());
        List<BankParameterEntity> bankParameterEntityList = bankParameterDomainService.getBankParameters();

        assertThat(bankParameterEntityList).isEmpty();
        assertThat(bankParameterEntityList.size()).isEqualTo(0);

    }


    @Test
    @DisplayName("JUnit test for getBankParameterById method")
    void getBankParameterByCodeReturnBankParameterEntityObject() {
        given(bankParameterRepository.findByBankCode("0002")).willReturn(bankParameterEntity);
        BankParameterEntity bankParameterEntity1 =bankParameterDomainService.getBankParameterByBankCode(bankParameterEntity.getBankCode());
        assertThat(bankParameterEntity1).isNotNull();
    }


    @Test
    @DisplayName("JUnit test for getBankParameterById catch block method")
    void getBankParameterByCodeReturnCatchBolock() {
        BankParameterEntity bankParameterEntity1=null;

        assertThrows(Exception.class,()-> {
            BankParameterEntity bankParameterEntity2 = bankParameterDomainService.getBankParameterByBankCode(bankParameterEntity1.getBankCode());
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode try block method")
    void getConfigurationByCodeTryBlock() {
        given(bankParameterRepository.findByBankCode("0002")).willReturn(bankParameterEntity);
        BankParameterEntity bankParameterByCode = bankParameterDomainService.getConfigurationByCode(bankParameterDTO);
        assertThat(bankParameterByCode).isNotNull();
    }


    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    void getConfigurationByCodeCatchBlock() {
        BankParameterDTO bankParameterDTO = null;
        assertThrows(BusinessException.class,()-> {
            BankParameterEntity bankParameterByCode = bankParameterDomainService.getConfigurationByCode(bankParameterDTO);
        });
    }


    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    void getSaveCodeCatchBlock() {
        BankParameterDTO bankParameterDTO = null;
        assertThrows(Exception.class,()-> {
            bankParameterDomainService.save(bankParameterDTO);
        });
    }


    private BankParameterEntity getBankParameterEntity()
    {
        BankParameterEntity bankParameterEntity = new BankParameterEntity();
        BankParameterAddressEntity bankParameterAddressEntity = new BankParameterAddressEntity();
        BankParameterContactInfoEntity bankParameterContactInfoEntity = new BankParameterContactInfoEntity();
        BankParameterCurrencyEntity bankParameterCurrencyEntity = new BankParameterCurrencyEntity();
        BankParameterPreferencesEntity bankParameterPreferencesEntity = new BankParameterPreferencesEntity();
        BankParameterForOdLoanEntity bankParameterForOdLoanEntity = new BankParameterForOdLoanEntity();
        bankParameterEntity.setBankCode("0002");
        bankParameterEntity.setBankName("State Bank of India");
        bankParameterEntity.setBankCodeRegulatory("0002");
        bankParameterEntity.setBankConciseName("SBI");
        bankParameterEntity.setBankGroupCode("SBI");
        bankParameterAddressEntity.setBankAddress1("Kanakia Business Park 2");
        bankParameterAddressEntity.setBankAddress2("JB Nagar 2");
        bankParameterAddressEntity.setBankAddress3("Andheri East 2");
        bankParameterAddressEntity.setBankAddress4("Mumbai 2");
        bankParameterAddressEntity.setCountryCode("IN");
        bankParameterAddressEntity.setStateCode("MH");
        bankParameterAddressEntity.setCityCode("MUM");
        bankParameterContactInfoEntity.setTelephoneNo("022-4156271");
        bankParameterContactInfoEntity.setFaxNo("022-4156271");
        bankParameterContactInfoEntity.setEmailId("abc@xyz.com");
        bankParameterContactInfoEntity.setBankWebsite("www.bob.com");
        bankParameterCurrencyEntity.setCurrencyCode("INR");
        bankParameterCurrencyEntity.setCurrencyName("INDIAN RUPEES");
        //bankParameterCurrencyEntity.setIsDenominationTrackingRequired(false);
        bankParameterCurrencyEntity.setCurrencyDenomination("INR");
        bankParameterCurrencyEntity.setCurrencyDenominationTracking("INR");
        bankParameterPreferencesEntity.setWeekBeginDay("Monday");
        bankParameterPreferencesEntity.setWeeklyOff1("Saturday");
        bankParameterPreferencesEntity.setWeeklyOff2("Sunday");
        bankParameterPreferencesEntity.setWeeklyOff3("");
        bankParameterPreferencesEntity.setFinancialYearBeginMonth("April");
        bankParameterForOdLoanEntity.setRuleIdOd("OD_01");
        bankParameterForOdLoanEntity.setRuleNameOd("Regulated rule for Overdraft decision");
        bankParameterForOdLoanEntity.setRuleIdLoan("LN_01");
        bankParameterForOdLoanEntity.setRuleNameLoan("Regulated rule for Loan decision");
        bankParameterEntity.setBankParameterAddressEntity(bankParameterAddressEntity);
        bankParameterEntity.setBankParameterContactInfoEntity(bankParameterContactInfoEntity);
        bankParameterEntity.setBankParameterCurrencyEntity(bankParameterCurrencyEntity);
        bankParameterEntity.setBankParameterPreferencesEntity(bankParameterPreferencesEntity);
        bankParameterEntity.setBankParameterForOdLoanEntity(bankParameterForOdLoanEntity);
        bankParameterEntity.setAuthorized("Y");
        return bankParameterEntity;
    }

    private BankParameterDTO getBankParameterDTO()
    {
        BankParameterDTO bankParameterDTO = new BankParameterDTO();
        BankParameterAddressDTO bankParameterAddressDTO = new BankParameterAddressDTO();
        BankParameterContactInfoDTO bankParameterContactInfoDTO = new BankParameterContactInfoDTO();
        BankParameterCurrencyDTO bankParameterCurrencyDTO = new BankParameterCurrencyDTO();
        BankParameterPreferencesDTO bankParameterPreferencesDTO = new BankParameterPreferencesDTO();
        BankParameterForOdLoanDTO bankParameterForOdLoanDTO = new BankParameterForOdLoanDTO();
        bankParameterDTO.setBankCode("0002");
        bankParameterDTO.setBankName("State Bank of India");
        bankParameterDTO.setRegulatoryBankCode("0002");
        bankParameterDTO.setBankConciseName("SBI");
        bankParameterDTO.setGroupBankingCode("SBI");
        bankParameterAddressDTO.setBankAddress1("Kanakia Business Park 2");
        bankParameterAddressDTO.setBankAddress2("JB Nagar 2");
        bankParameterAddressDTO.setBankAddress3("Andheri East 2");
        bankParameterAddressDTO.setBankAddress4("Mumbai 2");
        bankParameterAddressDTO.setCountry("IN");
        bankParameterAddressDTO.setState("MH");
        bankParameterAddressDTO.setCity("MUM");
        bankParameterContactInfoDTO.setTelephoneNo("022-4156271");
        bankParameterContactInfoDTO.setFaxNo("022-4156271");
        bankParameterContactInfoDTO.setEmailId("abc@xyz.com");
        bankParameterContactInfoDTO.setBankWebsite("www.bob.com");
        bankParameterCurrencyDTO.setCurrencyCode("INR");
        bankParameterCurrencyDTO.setCurrencyName("INDIAN RUPEES");
        bankParameterCurrencyDTO.setIsDenominationTrackingRequired(false);
        bankParameterCurrencyDTO.setCurrencyOfDenomination("INR");
        bankParameterCurrencyDTO.setDenominationTrackingCurrency("INR");
        bankParameterPreferencesDTO.setWeekBeginDay("Monday");
        bankParameterPreferencesDTO.setWeeklyOff1("Saturday");
        bankParameterPreferencesDTO.setWeeklyOff2("Sunday");
        bankParameterPreferencesDTO.setWeeklyOff3("");
        bankParameterPreferencesDTO.setFinancialYearBeginMonth("April");
        bankParameterForOdLoanDTO.setRuleIdForOd("OD_01");
        bankParameterForOdLoanDTO.setRuleNameForOd("Regulated rule for Overdraft decision");
        bankParameterForOdLoanDTO.setRuleIdForLoan("LN_01");
        bankParameterForOdLoanDTO.setRuleNameForLoan("Regulated rule for Loan decision");
        bankParameterDTO.setAuthorized("Y");
        bankParameterDTO.setTaskCode("0002");
        bankParameterDTO.setStatus("DELETED");
        bankParameterDTO.setRecordVersion(1);
        bankParameterDTO.setBankParameterAddress(bankParameterAddressDTO);
        bankParameterDTO.setBankParameterContactInfo(bankParameterContactInfoDTO);
        bankParameterDTO.setBankParameterCurrency(bankParameterCurrencyDTO);
        bankParameterDTO.setBankParameterPreferences(bankParameterPreferencesDTO);
        bankParameterDTO.setBankParameterForOdLoan(bankParameterForOdLoanDTO);
        return bankParameterDTO;
    }

}