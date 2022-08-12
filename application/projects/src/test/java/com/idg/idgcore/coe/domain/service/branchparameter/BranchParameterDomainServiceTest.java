package com.idg.idgcore.coe.domain.service.branchparameter;

import com.idg.idgcore.coe.domain.assembler.branchparameter.*;
import com.idg.idgcore.coe.domain.entity.branchparameter.*;
import com.idg.idgcore.coe.domain.repository.branchparameter.*;
import com.idg.idgcore.coe.dto.branchparameter.*;
import com.idg.idgcore.datatypes.exceptions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith (MockitoExtension.class)
class BranchParameterDomainServiceTest {
    @Mock
    private IBranchParameterRepository branchParameterRepository;
    @Mock
    private BranchParameterAssembler branchParameterAssembler;

    @InjectMocks
    private BranchParameterDomainService branchParameterDomainService;
    private BranchParameterEntity branchParameterEntity;
    private BranchParameterDTO branchParameterDTO;

    @BeforeEach
    void setUp() {
        branchParameterDTO=getBranchParameterDTO();
        branchParameterEntity=getBranchParameterEntity();
    }

    @Test
    @DisplayName("Junit test for getBranchParametersReturnBranchParametersList method ")
    void getBranchParametersReturnBranchParametersList() {
        given(branchParameterRepository.findAll()).willReturn(List.of(branchParameterEntity));
        List<BranchParameterEntity> bankParameterEntityList = branchParameterDomainService.getBranchParameters();
        assertThat(bankParameterEntityList).isNotNull();
        assertThat(bankParameterEntityList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("JUnit test for getBranchParametersEmptyBranchParameterEntityList method for negative scenario")
    void getBranchParametersEmptyBranchParameterEntityList()
    {
        given(branchParameterRepository.findAll()).willReturn(Collections.emptyList());
        List<BranchParameterEntity> branchParameterEntityList = branchParameterDomainService.getBranchParameters();

        assertThat(branchParameterEntityList).isEmpty();
        assertThat(branchParameterEntityList.size()).isEqualTo(0);

    }

    @Test
    @DisplayName("JUnit test for getBranchParameterByCodeReturnBranchParameterEntityObject method")
    void getBranchParameterByCodeReturnBranchParameterEntityObject() {
        given(branchParameterRepository.findByBranchCode("10001")).willReturn(branchParameterEntity);
        BranchParameterEntity branchParameterEntity1 =branchParameterDomainService.getBranchParameterByBranchCode(branchParameterEntity.getBranchCode());
        assertThat(branchParameterEntity1).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getBranchParameterByCodeReturnCatchBlock catch block method")
    void getBranchParameterByCodeReturnCatchBlock() {
        BranchParameterEntity branchParameterEntity1=null;

        assertThrows(Exception.class,()-> {
            BranchParameterEntity branchParameterEntity2 = branchParameterDomainService.getBranchParameterByBranchCode(branchParameterEntity1.getBranchCode());
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCodeTryBlock try block method")
    void getConfigurationByCodeTryBlock() {
        given(branchParameterRepository.findByBranchCode("10001")).willReturn(branchParameterEntity);
        BranchParameterEntity branchParameterByBranchParameterCountryCode = branchParameterDomainService.getConfigurationByCode(branchParameterDTO);
        assertThat(branchParameterByBranchParameterCountryCode).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCodeCatchBlock for Catch Block method")
    void getConfigurationByCodeCatchBlock() {
        BranchParameterDTO branchParameterDTO = null;
        assertThrows(BusinessException.class,()-> {
            BranchParameterEntity branchParameterByBranchParameterCountryCode = branchParameterDomainService.getConfigurationByCode(branchParameterDTO);
        });
    }

/*
    @Test
    @DisplayName("JUnit test for getSaveCodeCatchBlock for Catch Block method")
    void getSaveCodeCatchBlock() {
        //BranchParameterDTO branchParameterDTO = null;
        assertThrows(Exception.class,()-> {
            branchParameterDomainService.save(null);
        });
    }
*/


    /*@Test
    void getConfigurationByCode () {
    }

    @Test
    void getBranchParameters () {
    }

    @Test
    void getBranchParameterByBranchCode () {
    }

    @Test
    void save () {
    }*/

    private BranchParameterEntity getBranchParameterEntity () {
        BranchParameterEntity branchParameterEntity = new BranchParameterEntity();
        BranchParameterAddressEntity branchParameterAddressEntity = new BranchParameterAddressEntity();
        BranchParameterContactInfoEntity branchParameterContactInfoEntity = new BranchParameterContactInfoEntity();
        BranchParameterGeneralEntity branchParameterGeneralEntity = new BranchParameterGeneralEntity();
        BranchParameterClearingEntity branchParameterClearingEntity = new BranchParameterClearingEntity();
        BranchParameterAtmEntity branchParameterAtmEntity = new BranchParameterAtmEntity();

        BranchParameterTimezoneEntity branchParameterTimezoneEntity = new BranchParameterTimezoneEntity();
        BranchParameterGlobalInterdictEntity branchParameterGlobalInterdictEntity = new BranchParameterGlobalInterdictEntity();
        BranchParameterTranDuplicateEntity branchParameterTranDuplicateEntity = new BranchParameterTranDuplicateEntity();
        BranchParameterLocalCurrencyEntity branchParameterLocalCurrencyEntity = new BranchParameterLocalCurrencyEntity();
        BranchParameterMiscellaneousEntity branchParameterMiscellaneousEntity = new BranchParameterMiscellaneousEntity();

        branchParameterEntity.setBankCode("0002");
        branchParameterEntity.setBranchCode("10001");
        branchParameterEntity.setBranchName("Bandra East");
        branchParameterEntity.setBranchConciseName("SBI");
        branchParameterEntity.setIsBranchAvailableStatus(branchParameterAssembler.getCharValueFromBoolean(true));
        branchParameterEntity.setBankIdentifierCode("H1");
        branchParameterEntity.setBankCode("HO");

        branchParameterAddressEntity.setBranchAddress1("Kanakia Business Park 2");
        branchParameterAddressEntity.setBranchAddress2("JB Nagar 2");
        branchParameterAddressEntity.setBranchAddress3("Andheri East 2");
        branchParameterAddressEntity.setBranchAddress4("Mumbai 2");
        branchParameterAddressEntity.setCountryCode("IN");
        branchParameterAddressEntity.setStateCode("MH");
        branchParameterAddressEntity.setCityCode("MUM");

        branchParameterContactInfoEntity.setTelephoneNo("022-4156271");
        branchParameterContactInfoEntity.setFaxNo("022-4156271");
        branchParameterContactInfoEntity.setEmailId("abc@xyz.com");
        branchParameterContactInfoEntity.setBankWebsite("www.bob.com");

        branchParameterGeneralEntity.setGeneralCurrencyCode("INR");
        branchParameterGeneralEntity.setGeneralBranchParent("5002");
        branchParameterGeneralEntity.setGeneralCountryCode("IN");
        branchParameterGeneralEntity.setGeneralZonalOfficeCode("WI");
        branchParameterGeneralEntity.setGeneralZonalOfficeName("Western India");
        branchParameterGeneralEntity.setGeneralRegionalOfficeCode("MUM");
        branchParameterGeneralEntity.setGeneralRegionalOfficeName("Mumbai");
        branchParameterGeneralEntity.setGeneralCutOffTime("2");

        branchParameterClearingEntity.setClearingLocalPaymentBranch("Payment");
        branchParameterClearingEntity.setClearingCurrencyCode("INR");

        branchParameterAtmEntity.setAtmBranch("5001");
        branchParameterAtmEntity.setAtmInstitutionIdentity("SBI5");
        branchParameterAtmEntity.setAtmInterBranchTranCode("5001");
        branchParameterAtmEntity.setAtmIsCustomerFundTrf(branchParameterAssembler.getCharValueFromBoolean(true));

        branchParameterTimezoneEntity.setTimeZoneNoOfHours(0);
        branchParameterTimezoneEntity.setTimeZoneNoOfMinutes(3);
        branchParameterTimezoneEntity.setTimeZoneIsAhead(branchParameterAssembler.getCharValueFromBoolean(true));
        branchParameterTimezoneEntity.setTimeZoneLevel("IST (UTC+5:30)");

        branchParameterGlobalInterdictEntity.setGlobalInterdictFunctionId("FN01");

        branchParameterTranDuplicateEntity.setTranDupModuleCode("LN");
        branchParameterTranDuplicateEntity.setTranDupIsCheckReq(branchParameterAssembler.getCharValueFromBoolean(true));
        branchParameterTranDuplicateEntity.setTranDupNoOfDaysForCheck(1);

        branchParameterLocalCurrencyEntity.setLocalCurrencyMsgModCode("LN");
        branchParameterLocalCurrencyEntity.setLocalCurrencyMsgType("Suppress LCY Message");

        branchParameterMiscellaneousEntity.setMiscBranchIsDenoReq(branchParameterAssembler.getCharValueFromBoolean(true));
        branchParameterMiscellaneousEntity.setMiscBranchCurrencyDeno("INR");
        branchParameterMiscellaneousEntity.setMiscBranchWeekBeginDay("Monday");
        branchParameterMiscellaneousEntity.setMiscBranchWeeklyOff1("Saturday");
        branchParameterMiscellaneousEntity.setMiscBranchWeeklyOff2("Sunday");
        branchParameterMiscellaneousEntity.setMiscBranchFinYrBeginMth("April");

        branchParameterEntity.setBranchParameterAddressEntity(branchParameterAddressEntity);
        branchParameterEntity.setBranchParameterContactInfoEntity(branchParameterContactInfoEntity);
        branchParameterEntity.setBranchParameterGeneralEntity(branchParameterGeneralEntity);
        branchParameterEntity.setBranchParameterClearingEntity(branchParameterClearingEntity);
        branchParameterEntity.setBranchParameterAtmEntity(branchParameterAtmEntity);
        branchParameterEntity.setBranchParameterTimezoneEntity(branchParameterTimezoneEntity);
        branchParameterEntity.setBranchParameterGlobalInterdictEntity(branchParameterGlobalInterdictEntity);
        branchParameterEntity.setBranchParameterTranDuplicateEntity(branchParameterTranDuplicateEntity);
        branchParameterEntity.setBranchParameterLocalCurrencyEntity(branchParameterLocalCurrencyEntity);
        branchParameterEntity.setBranchParameterMiscellaneousEntity(branchParameterMiscellaneousEntity);
        branchParameterEntity.setAuthorized("Y");
        return branchParameterEntity;
    }

    private BranchParameterDTO getBranchParameterDTO () {
        BranchParameterDTO branchParameterDTO = new BranchParameterDTO();
        BranchParameterAddressDTO branchParameterAddress = new BranchParameterAddressDTO();
        BranchParameterContactInfoDTO branchParameterContactInfo = new BranchParameterContactInfoDTO();
        BranchParameterGeneralDTO branchParameterGeneral = new BranchParameterGeneralDTO();
        BranchParameterClearingDTO branchParameterClearing = new BranchParameterClearingDTO();
        BranchParameterAtmDTO branchParameterAtm = new BranchParameterAtmDTO();
        BranchParameterTimezoneDTO branchParameterTimezone = new BranchParameterTimezoneDTO();
        BranchParameterGlobalInterdictDTO branchParameterGlobalInterdict = new BranchParameterGlobalInterdictDTO();
        BranchParameterTranDuplicateDTO branchParameterTranDuplicate =  new BranchParameterTranDuplicateDTO();
        BranchParameterLocalCurrencyDTO branchParameterLocalCurrency = new BranchParameterLocalCurrencyDTO();
        BranchParameterMiscellaneousDTO branchParameterMiscellaneous = new BranchParameterMiscellaneousDTO();

        branchParameterDTO.setBankCode("0002");
        branchParameterDTO.setBranchCode("10001");
        branchParameterDTO.setBranchName("Bandra East");
        branchParameterDTO.setBranchConciseName("SBI");
        branchParameterDTO.setIsBranchAvailableStatus(true);
        branchParameterDTO.setBankIdentifierCode("H1");
        branchParameterDTO.setBankCode("HO");

        branchParameterAddress.setBranchAddress1("Kanakia Business Park 2");
        branchParameterAddress.setBranchAddress2("JB Nagar 2");
        branchParameterAddress.setBranchAddress3("Andheri East 2");
        branchParameterAddress.setBranchAddress4("Mumbai 2");
        branchParameterAddress.setCountry("IN");
        branchParameterAddress.setState("MH");
        branchParameterAddress.setCity("MUM");

        branchParameterContactInfo.setTelephoneNo("022-4156271");
        branchParameterContactInfo.setFaxNo("022-4156271");
        branchParameterContactInfo.setEmailId("abc@xyz.com");
        branchParameterContactInfo.setBankWebsite("www.bob.com");

        branchParameterGeneral.setGeneralCurrencyCode("INR");
        branchParameterGeneral.setGeneralBranchParent("5002");
        branchParameterGeneral.setGeneralCountryCode("IN");
        branchParameterGeneral.setGeneralZonalOfficeCode("WI");
        branchParameterGeneral.setGeneralZonalOfficeName("Western India");
        branchParameterGeneral.setGeneralRegionalOfficeCode("MUM");
        branchParameterGeneral.setGeneralRegionalOfficeName("Mumbai");
        branchParameterGeneral.setGeneralCutOffTime("2");

        branchParameterClearing.setClearingLocalPaymentBranch("Payment");
        branchParameterClearing.setClearingCurrencyCode("INR");

        branchParameterAtm.setAtmBranch("5001");
        branchParameterAtm.setAtmInstitutionIdentification("SBI5");
        branchParameterAtm.setAtmInterBranchTransactionCode("5001");
        branchParameterAtm.setAtmIsCustomerFundTransfer(true);

        branchParameterTimezone.setTimeZoneNoOfHours(0);
        branchParameterTimezone.setTimeZoneNoOfMinutes(3);
        branchParameterTimezone.setTimeZoneIsAhead(true);
        branchParameterTimezone.setTimeZoneLevel("IST (UTC+5:30)");

        branchParameterGlobalInterdict.setGlobalInterdictFunctionId("FN01");

        branchParameterTranDuplicate.setTranModuleCode("LN");
        branchParameterTranDuplicate.setTranDuplicationCheckRequired(true);
        branchParameterTranDuplicate.setTranNoOfDayForDuplicationCheck(1);

        branchParameterLocalCurrency.setLocalCurrencyMsgModCode("LN");
        branchParameterLocalCurrency.setLocalCurrencyMsgType("Suppress LCY Message");

        branchParameterMiscellaneous.setMiscDenominationTrackingRequired(true);
        branchParameterMiscellaneous.setMiscCurrencyOfDenomination("INR");
        branchParameterMiscellaneous.setMiscWeekBeginDay("Monday");
        branchParameterMiscellaneous.setMiscWeeklyOff1("Saturday");
        branchParameterMiscellaneous.setMiscWeeklyOff2("Sunday");
        branchParameterMiscellaneous.setMiscFinancialYearBeginMonth("April");

        branchParameterDTO.setAuthorized("Y");
        branchParameterDTO.setTaskIdentifier("10001");
        branchParameterDTO.setStatus("DELETED");
        branchParameterDTO.setRecordVersion(1);
        branchParameterDTO.setBranchParameterAddress(branchParameterAddress);
        branchParameterDTO.setBranchParameterContactInfo(branchParameterContactInfo);
        branchParameterDTO.setBranchParameterGeneral(branchParameterGeneral);
        branchParameterDTO.setBranchParameterClearing(branchParameterClearing);
        branchParameterDTO.setBranchParameterAtm(branchParameterAtm);
        branchParameterDTO.setBranchParameterTimezone(branchParameterTimezone);
        branchParameterDTO.setBranchParameterGlobalInterdict(branchParameterGlobalInterdict);
        branchParameterDTO.setBranchParameterTranDuplicate(branchParameterTranDuplicate);
        branchParameterDTO.setBranchParameterLocalCurrency(branchParameterLocalCurrency);
        branchParameterDTO.setBranchParameterMiscellaneous(branchParameterMiscellaneous);
        return branchParameterDTO;
    }

}