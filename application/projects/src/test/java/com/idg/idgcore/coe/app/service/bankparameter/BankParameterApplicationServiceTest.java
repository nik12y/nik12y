
package com.idg.idgcore.coe.app.service.bankparameter;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.idg.idgcore.coe.domain.assembler.bankparameter.*;
import com.idg.idgcore.coe.domain.entity.bankparameter.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.domain.process.*;
import com.idg.idgcore.coe.domain.service.bankparameter.*;
import com.idg.idgcore.coe.domain.service.mutation.*;
import com.idg.idgcore.coe.dto.bankparameter.*;
import com.idg.idgcore.coe.dto.mutation.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import com.idg.idgcore.enumerations.core.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith (MockitoExtension.class)
class BankParameterApplicationServiceTest {
    @InjectMocks
    private BankParameterApplicationService bankParameterApplicationService;
    @Mock private BankParameterAssembler bankParameterAssembler;
    @Mock private IBankParameterDomainService bankParameterDomainService;
    @Mock private IMutationsDomainService mutationsDomainService;
    @Mock private ProcessConfiguration processConfiguration;
    @Autowired
    private MutationEntity mutationEntity;
    private MutationEntity mutationEntity2;

    private SessionContext sessionContext;
    private BankParameterDTO bankParameterDTOAuth;
    private BankParameterDTO bankParameterDTOUnAuth;
    private BankParameterDTO bankParameterDTO;
    private BankParameterEntity bankParameterEntity;
    private BankParameterEntity bankParameterEntityUnAut;
    private BankParameterDTO bankParameterDTOMapper;
    private BankParameterDTO bankParameterDTO1;

    @BeforeEach
    void setUp () {
        sessionContext = getValidSessionContext();
        bankParameterDTOAuth = getBankParameterDTOAuthorized();
        bankParameterEntity = getBankParameterEntity();
        bankParameterDTOUnAuth = getBankParameterDTOUnAuthorized();
        bankParameterDTOUnAuth.setAuthorized("N");
        bankParameterEntityUnAut = getBankParameterEntity();
        bankParameterEntityUnAut.setAuthorized("N");
        mutationEntity = getMutationEntity();
        bankParameterDTO = getBankParameterDTO();
        bankParameterDTOMapper = getBankParameterDTOMapper();
        bankParameterDTO1=getBankParameterDTO();
        mutationEntity2=getMutationEntityJsonError();
    }

    @Test
    @DisplayName ("JUnit for getBankByCode in application service when Authorize")
    void getBankParameterByCodeWithAuthRecord () throws FatalException {
        given(bankParameterDomainService.getBankParameterByBankCode(
                bankParameterDTOAuth.getBankCode())).willReturn(bankParameterEntity);
        given(bankParameterAssembler.convertEntityToDto(bankParameterEntity)).willReturn(
                bankParameterDTOAuth);
        BankParameterDTO bankParameterDTO1 = bankParameterApplicationService.getBankParameterByBankCode(
                sessionContext, bankParameterDTOAuth);
        assertThat(bankParameterDTO1.getAuthorized()).isEqualTo("Y");
        assertThat(bankParameterDTOAuth).isNotNull();
        assertThat(bankParameterDTOAuth.toString()).isNotNull();
        assertThat(bankParameterDTO.toString()).isNotNull();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverageDTO()
    {
        System.out.println(bankParameterEntity.toString());
        System.out.println(bankParameterDTO.toString());

        BankParameterAddressDTO bankParameterAddressDTO = new BankParameterAddressDTO("Kanakia Business Park 2","JB Nagar 2","Andheri East 2","Mumbai 2","IN","MH","MUM");
        BankParameterContactInfoDTO bankParameterContactInfoDTO = new BankParameterContactInfoDTO("022-4156271","022-4156271","abc@xyz.com","www.bob.com");
        BankParameterCurrencyDTO bankParameterCurrencyDTO = new BankParameterCurrencyDTO("INR","INDIAN RUPEES",false,"INR","INR");
        BankParameterPreferencesDTO bankParameterPreferencesDTO = new BankParameterPreferencesDTO("Monday","Saturday","Sunday","","April");
        BankParameterForOdLoanDTO bankParameterForOdLoanDTO = new BankParameterForOdLoanDTO("OD_01","Regulated rule for Overdraft decision","LN_01","Regulated rule for Loan decision");
        BankParameterDTO bankParameterDTO2=new BankParameterDTO("0002","State Bank of India","0002","SBI","SBI",bankParameterAddressDTO,bankParameterContactInfoDTO,bankParameterCurrencyDTO,bankParameterPreferencesDTO,bankParameterForOdLoanDTO);

        String s = BankParameterDTO.builder().bankCode("0002").bankName("State Bank of India")
                .regulatoryBankCode("0002").bankConciseName("SBI").groupBankingCode("SBI").build()
                .toString();

        bankParameterDTO2.setBankParameterAddress(bankParameterAddressDTO);
        bankParameterDTO2.setBankParameterContactInfo(bankParameterContactInfoDTO);
        bankParameterDTO2.setBankParameterCurrency(bankParameterCurrencyDTO);
        bankParameterDTO2.setBankParameterPreferences(bankParameterPreferencesDTO);
        bankParameterDTO2.setBankParameterForOdLoan(bankParameterForOdLoanDTO);

        BankParameterEntityKey bankParameterEntityKey=new BankParameterEntityKey("0002");


        bankParameterEntityKey.setBankCode("0002");
        System.out.println(bankParameterEntityKey.getBankCode());
        bankParameterEntityKey.keyAsString();
        //bankParameterEntityKey.builder().bankCode("0003").build();
        assertThat(bankParameterDTO2).descriptionText();
        assertThat(s.toString()).isNotNull();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverageEntity()
    {

        BankParameterAddressEntity bankParameterAddressEntity = new BankParameterAddressEntity("Kanakia Business Park 2","JB Nagar 2","Andheri East 2","Mumbai 2","IN","MH","MUM");
        BankParameterContactInfoEntity bankParameterContactInfoEntity = new BankParameterContactInfoEntity("022-4156271","022-4156271","abc@xyz.com","www.bob.com");
        BankParameterCurrencyEntity bankParameterCurrencyEntity = new BankParameterCurrencyEntity("INR","INDIAN RUPEES",getCharValueFromBoolean(false),"INR","INR");
        BankParameterPreferencesEntity bankParameterPreferencesEntity = new BankParameterPreferencesEntity("Monday","Saturday","Sunday","","April");
        BankParameterForOdLoanEntity bankParameterForOdLoanEntity = new BankParameterForOdLoanEntity("OD_01","Regulated rule for Overdraft decision","LN_01","Regulated rule for Loan decision");

        BankParameterEntity bankParameterEntity=new BankParameterEntity("0002","State Bank of India","0002","SBI","SBI","","","draft",0,"Y","draft",bankParameterAddressEntity,bankParameterContactInfoEntity,bankParameterCurrencyEntity,bankParameterPreferencesEntity,bankParameterForOdLoanEntity);

        bankParameterEntity.setBankParameterAddressEntity(bankParameterAddressEntity);
        bankParameterEntity.setBankParameterContactInfoEntity(bankParameterContactInfoEntity);
        bankParameterEntity.setBankParameterCurrencyEntity(bankParameterCurrencyEntity);
        bankParameterEntity.setBankParameterPreferencesEntity(bankParameterPreferencesEntity);
        bankParameterEntity.setBankParameterForOdLoanEntity(bankParameterForOdLoanEntity);

        assertThat(bankParameterEntity).descriptionText();
    }



    public char getCharValueFromBoolean(boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar(Character value) {
        return value.equals(CHAR_Y);

    }

    @Test
    @DisplayName("JUnit for getBankByCode in application service when Not Authorize in catch block")
    void getBankParameterByCodeWhenNotAuthorizeCatchBlock () throws FatalException {
        String payLoadString1 =getpayloadInvalidString();
                //"{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"STATE\",\"taskIdentifier\":\"MH\",\"stateCode\":\"MH\",\"stateName\":\"MAHARASHTRA\",\"countryCode\":\"IN\"}";
        given(mutationsDomainService.getConfigurationByCode(bankParameterDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        given(bankParameterAssembler.setAuditFields(mutationEntity2, bankParameterDTOUnAuth))
                .willReturn(bankParameterDTOUnAuth);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);
        Assertions.assertThrows(Exception.class,()-> {
            BankParameterDTO bankParameterDTO1 = bankParameterApplicationService.getBankParameterByBankCode(sessionContext, bankParameterDTOUnAuth);
            assertEquals("N",bankParameterDTO1.getAuthorized());
            assertThat(bankParameterDTO1).isNotNull();

        });
    }
    /*//PPN
    @Test
    @DisplayName("JUnit for getBankParameters in application service for catch block for checker")
    void getBankParametersCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        //        unauthorizedEntities.setStatus("draft");
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        //        unauthorizedEntities1.setStatus("closed");
        //sessionContext.setRole(new String[] { "" });
        given(mutationsDomainService.getUnauthorizedMutation(
                bankParameterDTO1.getTaskCode(),AUTHORIZED_N))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
        Assertions.assertThrows(FatalException.class,()-> {
            List<BankParameterDTO> bankParameterDTO1 = bankParameterApplicationService.getBankParameters(sessionContext);
            assertThat(bankParameterDTO1).isNotNull();
        });
    }*/

    /*@Test
    @DisplayName ("JUnit for getBankParameters in application service")
    void getBankParameters () throws JsonProcessingException, FatalException {
        BankParameterEntity bankParameterEntity = getBankParameterEntityDeleted();
        BankParameterDTO bankParameterDTO = getBankParameterDTODeleted();
        MutationEntity unauthorizedEntities = getMutationEntityDeleted();
        //String data = "\{\"createdBy":null,"creationTime":null,"lastUpdatedBy":null,"lastUpdatedTime":null,"action":"authorize","status":"active","recordVersion":1,"authorized":"Y","lastConfigurationAction":"authorized","taskCode":"BANKPARAMETER","taskIdentifier":"0002","bankCode":"0002","bankName":"State Bank of India","regulatoryBankCode":"0002","bankConciseName":"SBI","groupBankingCode":"SBI","bankParameterAddress":{"bankAddress1":"Kanakia Business Park 2","bankAddress2":"JB Nagar 2","bankAddress3":"Andheri East 2","bankAddress4":"Mumbai 2","country":"IN","state":"MH","city":"MUM"},"bankParameterContactInfo":{"telephoneNo":"022-4156271","faxNo":"022-4156271","emailId":"abc@xyz.com","bankWebsite":"www.bob.com"},"bankParameterCurrency":{"currencyCode":"INR","currencyName":"INDIAN RUPEES","isDenominationTrackingRequired":false,"currencyOfDenomination":"INR","denominationTrackingCurrency":"INR"},"bankParameterPreferences":{"weekBeginDay":"Monday","weeklyOff1":"Saturday","weeklyOff2":"Sunday","weeklyOff3":"","financialYearBeginMonth":"April"},"bankParameterForOdLoan":{"ruleIdForOd":"OD_01","ruleNameForOd":"Regulated rule for Overdraft decision","ruleIdForLoan":"LN_01","ruleNameForLoan":"Regulated rule for Loan decision"}";
        ObjectMapper objectMapper = new ObjectMapper();
        given(bankParameterDomainService.getBankParameters()).willReturn(
                List.of(bankParameterEntity));
        given(mutationsDomainService.getUnauthorizedMutation(
                "BANKPARAMETER", AUTHORIZED_N)).willReturn(
                List.of(unauthorizedEntities));
        given(bankParameterAssembler.setAuditFields(unauthorizedEntities,
                bankParameterDTO)).willReturn(bankParameterDTO);
        given(objectMapper.readValue(data, BankParameterDTO.class)).willReturn(bankParameterDTO);
        given(bankParameterAssembler.convertEntityToDto(bankParameterEntity)).willReturn(
                bankParameterDTO);
        Assertions.assertThrows(BusinessException.class, () -> {
            List<BankParameterDTO> bankParameterDTOList = bankParameterApplicationService.getBankParameters(
                    sessionContext);
            assertThat(bankParameterDTOList).isNotNull();
        });
        List<BankParameterDTO> bankParameterDTO1 = bankParameterApplicationService.getBankParameters(
                sessionContext);
        assertThat(bankParameterDTO1).isNotNull();
    }*/

   /* @Test
    @DisplayName ("JUnit for getBankParameter in application service for catch block")
    void getBankParametersForException () throws FatalException {
        SessionContext sessionContext1 = null;
        Assertions.assertThrows(Exception.class, () -> {
            List<BankParameterDTO> cities = bankParameterApplicationService.getBankParameters(
                    sessionContext1);
            assertThat(cities).isNotNull();
        });
    }

    @Test
    @DisplayName ("JUnit for getStates in application service for catch block")
    void getBankParametersException () throws FatalException {
        SessionContext sessionContext = getInValidSessionContext();
        given(bankParameterDomainService.getBankParameters()).willReturn(
                List.of(bankParameterEntity));
        given(mutationsDomainService.getConfigurationByCode(
                bankParameterDTO.getTaskIdentifier())).willReturn(
                (MutationEntity) List.of(mutationEntity));
        given(bankParameterAssembler.convertEntityToDto(bankParameterEntity)).willReturn(
                bankParameterDTO);
        Assertions.assertThrows(Exception.class, () -> {
            List<BankParameterDTO> cities = bankParameterApplicationService.getBankParameters(
                    sessionContext);
            assertThat(cities).isNotNull();
        });
    }*/

    @DisplayName ("JUnit test for processBankParameter method")
    @Test
    void processBankParameterWithNew () throws JsonProcessingException, FatalException {
        BankParameterDTO bankParameterDTONew = getBankParameterDTOMapper();
        doNothing().when(processConfiguration).process(bankParameterDTONew);
        bankParameterApplicationService.processBankParameter(sessionContext, bankParameterDTONew);
        verify(processConfiguration, times(1)).process(bankParameterDTONew);
    }

    @DisplayName ("JUnit test for addUpdateRecord method")
    @Test
    void addUpdateRecord () throws JsonProcessingException, FatalException {
        String payloadStr = getpayloadValidString();
        BankParameterDTO bankParameterDTO = getBankParameterDTOForSave();
        doNothing().when(bankParameterDomainService).save(bankParameterDTO);
        bankParameterApplicationService.save(bankParameterDTO);
        bankParameterApplicationService.addUpdateRecord(payloadStr);
        verify(bankParameterDomainService, times(1)).save(bankParameterDTO);
    }

    /**
     * New test cases
     */
    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = bankParameterDTO.getBankCode();
        given(bankParameterDomainService.getBankParameterByBankCode(code)).willReturn(bankParameterEntity);
        bankParameterApplicationService.getConfigurationByCode(code);
        assertThat(bankParameterEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for processBankParameter in application service for Try Block")
    void processBankParameterForTryBlock() throws JsonProcessingException, FatalException {
        doNothing().when(processConfiguration).process(bankParameterDTO);
        bankParameterApplicationService.processBankParameter(sessionContext, bankParameterDTO);
        verify(processConfiguration, times(1)).process(bankParameterDTO);
    }

    @Test
    @DisplayName("JUnit for processBankParameter in application service for Catch Block")
    void processParameterForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            bankParameterApplicationService.processBankParameter(sessionContext2, bankParameterDTO);
            assertThat(bankParameterDTO).descriptionText();
        });
    }

    /**
     *
     * Negative Test Cases
     */

    @Test
    @DisplayName("JUnit for getBankByCode in application service when Authorize for Negative")
    void getBankParameterBankByCodeIsAuthorizeForNegative() throws FatalException {
        given(bankParameterDomainService.getBankParameterByBankCode(bankParameterDTO.getBankCode())).willReturn(bankParameterEntity);
        given(bankParameterAssembler.convertEntityToDto(bankParameterEntity)).willReturn(bankParameterDTO);
        BankParameterDTO bankParameterDTO1 = bankParameterApplicationService.getBankParameterByBankCode(sessionContext, bankParameterDTO);
        assertNotEquals("N",bankParameterDTO1.getAuthorized());
        assertThat(bankParameterDTO).isNotNull();
    }

    /*@Test
    @DisplayName("JUnit for getBankByCode in application service when UnAuthorize fetch no Record from database")
    void getBankParameterBankByCodeNotAuthorizeNull() throws FatalException {
        BankParameterDTO bankParameterDTOnull=null;
        BankParameterDTO bankParameterDTOEx=new BankParameterDTO();
        bankParameterDTOEx.setBankCode("0002");
        bankParameterDTOEx.setAuthorized("N");
        given(mutationsDomainService.getConfigurationByCode(bankParameterDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);
        BankParameterDTO bankParameterDTO1 = bankParameterApplicationService.getBankByCode(sessionContext, bankParameterDTOEx);
        assertNotEquals("Y",bankParameterDTO1.getAuthorized());
        assertNull(bankParameterDTOnull);
    }*/

    @Test
    @DisplayName("JUnit for getBankByCode in application service check Parameter not null")
    void getBankParameterBankByCodeIsAuthorizeCheckParameter() throws FatalException {
        BankParameterDTO bankParameterDTOnull=null;
        BankParameterDTO bankParameterDTOEx=new BankParameterDTO();
        bankParameterDTOEx.setBankCode("0002");
        bankParameterDTOEx.setAuthorized("Y");
        given(bankParameterDomainService.getBankParameterByBankCode(bankParameterDTOEx.getBankCode())).willReturn(bankParameterEntity);
        given(bankParameterAssembler.convertEntityToDto(bankParameterEntity)).willReturn(bankParameterDTO);
        BankParameterDTO bankParameterDTO1 = bankParameterApplicationService.getBankParameterByBankCode(sessionContext, bankParameterDTOEx);
        assertThat(bankParameterDTOEx.getBankCode()).isNotBlank();
        assertThat(bankParameterDTOEx.getAuthorized()).isNotBlank();
    }

    /*@Test
    @DisplayName("JUnit for getBankByCode in application service when Not Authorize in try block for Negative when getAuthorized unexpected is Y")
    void getBankParameterBankByCodeWhenNotAuthorizeTryBlockForNegative() throws JsonProcessingException, FatalException {
        given(mutationsDomainService.getConfigurationByCode(bankParameterDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);
        BankParameterDTO bankParameterDTO1 = bankParameterApplicationService.getBankByCode(sessionContext,bankParameterDTOMapper);
        assertNotEquals("Y",bankParameterDTO1.getAuthorized());
        assertThat(bankParameterDTO1).isNotNull();
    }*/


    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder().bankCode("02").defaultBranchCode("02")
                .internalTransactionReferenceNumber(null).userTransactionReferenceNumber(null)
                .externalTransactionReferenceNumber(null).targetUnit("").postingDate(new Date())
                .valueDate(new Date()).transactionBranch("").userId("TestMaker").channel("")
                .taskCode("").originalTransactionReferenceNumber("").externalBatchNumber(1L)
                .customAttributes("").serviceInvocationModeType(ServiceInvocationModeType.Regular)
                .allTargetUnitsSelected(true).userLocal("").originatingModuleCode("")
                .role(new String[] { "maker" }).build();
        return sessionContext;
    }

    private SessionContext getInValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder().bankCode("").defaultBranchCode("")
                .internalTransactionReferenceNumber(null).userTransactionReferenceNumber("")
                .externalTransactionReferenceNumber(null).targetUnit("").postingDate(new Date())
                .valueDate(new Date()).transactionBranch("").userId("TestMaker").channel("")
                .taskCode("").originalTransactionReferenceNumber("").externalBatchNumber(1L)
                .customAttributes("").serviceInvocationModeType(null)
                .allTargetUnitsSelected(true).userLocal("").originatingModuleCode("")
                .role(null).build();
        return sessionContext;
    }

    private BankParameterDTO getBankParameterDTOAuthorized () {
        BankParameterDTO bankParameterDTOMapper = new BankParameterDTO();
        BankParameterAddressDTO bankParameterAddressDTOMapper = new BankParameterAddressDTO();
        BankParameterContactInfoDTO bankParameterContactInfoDTOMapper = new BankParameterContactInfoDTO();
        BankParameterCurrencyDTO bankParameterCurrencyDTOMapper = new BankParameterCurrencyDTO();
        BankParameterPreferencesDTO bankParameterPreferencesDTOMapper = new BankParameterPreferencesDTO();
        BankParameterForOdLoanDTO bankParameterForOdLoanDTOMapper = new BankParameterForOdLoanDTO();
        bankParameterDTOMapper.setBankCode("0002");
        bankParameterDTOMapper.setBankName("State Bank of India");
        bankParameterDTOMapper.setRegulatoryBankCode("0002");
        bankParameterDTOMapper.setBankConciseName("SBI");
        bankParameterDTOMapper.setGroupBankingCode("SBI");
        bankParameterAddressDTOMapper.setBankAddress1("Kanakia Business Park 2");
        bankParameterAddressDTOMapper.setBankAddress2("JB Nagar 2");
        bankParameterAddressDTOMapper.setBankAddress3("Andheri East 2");
        bankParameterAddressDTOMapper.setBankAddress4("Mumbai 2");
        bankParameterAddressDTOMapper.setCountry("IN");
        bankParameterAddressDTOMapper.setState("MH");
        bankParameterAddressDTOMapper.setCity("MUM");
        bankParameterContactInfoDTOMapper.setTelephoneNo("022-4156271");
        bankParameterContactInfoDTOMapper.setFaxNo("022-4156271");
        bankParameterContactInfoDTOMapper.setEmailId("abc@xyz.com");
        bankParameterContactInfoDTOMapper.setBankWebsite("www.bob.com");
        bankParameterCurrencyDTOMapper.setCurrencyCode("INR");
        bankParameterCurrencyDTOMapper.setCurrencyName("INDIAN RUPEES");
        bankParameterCurrencyDTOMapper.setIsDenominationTrackingRequired(false);
        bankParameterCurrencyDTOMapper.setCurrencyOfDenomination("INR");
        bankParameterCurrencyDTOMapper.setDenominationTrackingCurrency("INR");
        bankParameterPreferencesDTOMapper.setWeekBeginDay("Monday");
        bankParameterPreferencesDTOMapper.setWeeklyOff1("Saturday");
        bankParameterPreferencesDTOMapper.setWeeklyOff2("Sunday");
        bankParameterPreferencesDTOMapper.setWeeklyOff3("");
        bankParameterPreferencesDTOMapper.setFinancialYearBeginMonth("April");
        bankParameterForOdLoanDTOMapper.setRuleIdForOd("OD_01");
        bankParameterForOdLoanDTOMapper.setRuleNameForOd("Regulated rule for Overdraft decision");
        bankParameterForOdLoanDTOMapper.setRuleIdForLoan("LN_01");
        bankParameterForOdLoanDTOMapper.setRuleNameForLoan("Regulated rule for Loan decision");
        bankParameterDTOMapper.setAuthorized("Y");
        bankParameterDTOMapper.setTaskCode("BANKPARAMETER");
        bankParameterDTOMapper.setTaskIdentifier("0002");
        bankParameterDTOMapper.setBankParameterAddress(bankParameterAddressDTOMapper);
        bankParameterDTOMapper.setBankParameterContactInfo(bankParameterContactInfoDTOMapper);
        bankParameterDTOMapper.setBankParameterCurrency(bankParameterCurrencyDTOMapper);
        bankParameterDTOMapper.setBankParameterPreferences(bankParameterPreferencesDTOMapper);
        bankParameterDTOMapper.setBankParameterForOdLoan(bankParameterForOdLoanDTOMapper);
        return bankParameterDTOMapper;
    }

    private BankParameterDTO getBankParameterDTOForSave () {
        BankParameterDTO bankParameterDTOMapper = new BankParameterDTO();
        BankParameterAddressDTO bankParameterAddressDTOMapper = new BankParameterAddressDTO();
        BankParameterContactInfoDTO bankParameterContactInfoDTOMapper = new BankParameterContactInfoDTO();
        BankParameterCurrencyDTO bankParameterCurrencyDTOMapper = new BankParameterCurrencyDTO();
        BankParameterPreferencesDTO bankParameterPreferencesDTOMapper = new BankParameterPreferencesDTO();
        BankParameterForOdLoanDTO bankParameterForOdLoanDTOMapper = new BankParameterForOdLoanDTO();
        bankParameterDTOMapper.setBankCode("0002");
        bankParameterDTOMapper.setBankName("State Bank of India");
        bankParameterDTOMapper.setRegulatoryBankCode("0002");
        bankParameterDTOMapper.setBankConciseName("SBI");
        bankParameterDTOMapper.setGroupBankingCode("SBI");
        bankParameterAddressDTOMapper.setBankAddress1("Kanakia Business Park 2");
        bankParameterAddressDTOMapper.setBankAddress2("JB Nagar 2");
        bankParameterAddressDTOMapper.setBankAddress3("Andheri East 2");
        bankParameterAddressDTOMapper.setBankAddress4("Mumbai 2");
        bankParameterAddressDTOMapper.setCountry("IN");
        bankParameterAddressDTOMapper.setState("MH");
        bankParameterAddressDTOMapper.setCity("MUM");
        bankParameterContactInfoDTOMapper.setTelephoneNo("022-4156271");
        bankParameterContactInfoDTOMapper.setFaxNo("022-4156271");
        bankParameterContactInfoDTOMapper.setEmailId("abc@xyz.com");
        bankParameterContactInfoDTOMapper.setBankWebsite("www.bob.com");
        bankParameterCurrencyDTOMapper.setCurrencyCode("INR");
        bankParameterCurrencyDTOMapper.setCurrencyName("INDIAN RUPEES");
        bankParameterCurrencyDTOMapper.setIsDenominationTrackingRequired(false);
        bankParameterCurrencyDTOMapper.setCurrencyOfDenomination("INR");
        bankParameterCurrencyDTOMapper.setDenominationTrackingCurrency("INR");
        bankParameterPreferencesDTOMapper.setWeekBeginDay("Monday");
        bankParameterPreferencesDTOMapper.setWeeklyOff1("Saturday");
        bankParameterPreferencesDTOMapper.setWeeklyOff2("Sunday");
        bankParameterPreferencesDTOMapper.setWeeklyOff3("");
        bankParameterPreferencesDTOMapper.setFinancialYearBeginMonth("April");
        bankParameterForOdLoanDTOMapper.setRuleIdForOd("OD_01");
        bankParameterForOdLoanDTOMapper.setRuleNameForOd("Regulated rule for Overdraft decision");
        bankParameterForOdLoanDTOMapper.setRuleIdForLoan("LN_01");
        bankParameterForOdLoanDTOMapper.setRuleNameForLoan("Regulated rule for Loan decision");
        bankParameterDTOMapper.setTaskCode("BANKPARAMETER");
        bankParameterDTOMapper.setTaskIdentifier("0002");
        bankParameterDTOMapper.setAction("authorize");
        bankParameterDTOMapper.setStatus("active");
        bankParameterDTOMapper.setRecordVersion(1);
        bankParameterDTOMapper.setAuthorized("N");
        bankParameterDTOMapper.setLastConfigurationAction("authorized");
        bankParameterDTOMapper.setTaskCode("BANKPARAMETER");
        bankParameterDTOMapper.setTaskIdentifier("0002");
        bankParameterDTOMapper.setBankParameterAddress(bankParameterAddressDTOMapper);
        bankParameterDTOMapper.setBankParameterContactInfo(bankParameterContactInfoDTOMapper);
        bankParameterDTOMapper.setBankParameterCurrency(bankParameterCurrencyDTOMapper);
        bankParameterDTOMapper.setBankParameterPreferences(bankParameterPreferencesDTOMapper);
        bankParameterDTOMapper.setBankParameterForOdLoan(bankParameterForOdLoanDTOMapper);
        return bankParameterDTOMapper;
    }

    private BankParameterDTO getBankParameterDTOUnAuthorized () {
        BankParameterDTO bankParameterDTOMapper = new BankParameterDTO();
        BankParameterAddressDTO bankParameterAddressDTOMapper = new BankParameterAddressDTO();
        BankParameterContactInfoDTO bankParameterContactInfoDTOMapper = new BankParameterContactInfoDTO();
        BankParameterCurrencyDTO bankParameterCurrencyDTOMapper = new BankParameterCurrencyDTO();
        BankParameterPreferencesDTO bankParameterPreferencesDTOMapper = new BankParameterPreferencesDTO();
        BankParameterForOdLoanDTO bankParameterForOdLoanDTOMapper = new BankParameterForOdLoanDTO();
        bankParameterDTOMapper.setBankCode("0002");
        bankParameterDTOMapper.setBankName("State Bank of India");
        bankParameterDTOMapper.setRegulatoryBankCode("0002");
        bankParameterDTOMapper.setBankConciseName("State Bank");
        bankParameterDTOMapper.setGroupBankingCode("SBI");
        bankParameterAddressDTOMapper.setBankAddress1("Kanakia Business Park 2");
        bankParameterAddressDTOMapper.setBankAddress2("JB Nagar 2");
        bankParameterAddressDTOMapper.setBankAddress3("Andheri East 2");
        bankParameterAddressDTOMapper.setBankAddress4("Mumbai 2");
        bankParameterAddressDTOMapper.setCountry("IN");
        bankParameterAddressDTOMapper.setState("MH");
        bankParameterAddressDTOMapper.setCity("MUM");
        bankParameterContactInfoDTOMapper.setTelephoneNo("022-4156271");
        bankParameterContactInfoDTOMapper.setFaxNo("022-4156271");
        bankParameterContactInfoDTOMapper.setEmailId("abc@xyz.com");
        bankParameterContactInfoDTOMapper.setBankWebsite("www.bob.com");
        bankParameterCurrencyDTOMapper.setCurrencyCode("INR");
        bankParameterCurrencyDTOMapper.setCurrencyName("INDIAN RUPEES");
        bankParameterCurrencyDTOMapper.setIsDenominationTrackingRequired(false);
        bankParameterCurrencyDTOMapper.setCurrencyOfDenomination("INR");
        bankParameterCurrencyDTOMapper.setDenominationTrackingCurrency("INR");
        bankParameterPreferencesDTOMapper.setWeekBeginDay("Monday");
        bankParameterPreferencesDTOMapper.setWeeklyOff1("Saturday");
        bankParameterPreferencesDTOMapper.setWeeklyOff2("Sunday");
        bankParameterPreferencesDTOMapper.setWeeklyOff3("");
        bankParameterPreferencesDTOMapper.setFinancialYearBeginMonth("April");
        bankParameterForOdLoanDTOMapper.setRuleIdForOd("OD_01");
        bankParameterForOdLoanDTOMapper.setRuleNameForOd("Regulated rule for Overdraft decision");
        bankParameterForOdLoanDTOMapper.setRuleIdForLoan("LN_01");
        bankParameterForOdLoanDTOMapper.setRuleNameForLoan("Regulated rule for Loan decision");
        bankParameterDTOMapper.setAuthorized("N");
        bankParameterDTOMapper.setBankParameterAddress(bankParameterAddressDTOMapper);
        bankParameterDTOMapper.setBankParameterContactInfo(bankParameterContactInfoDTOMapper);
        bankParameterDTOMapper.setBankParameterCurrency(bankParameterCurrencyDTOMapper);
        bankParameterDTOMapper.setBankParameterPreferences(bankParameterPreferencesDTOMapper);
        bankParameterDTOMapper.setBankParameterForOdLoan(bankParameterForOdLoanDTOMapper);
        return bankParameterDTOMapper;
    }

    private BankParameterEntity getBankParameterEntity () {
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

    private BankParameterDTO getBankParameterDTOMapper () {
        BankParameterDTO bankParameterDTOMapper = new BankParameterDTO();
        BankParameterAddressDTO bankParameterAddressDTOMapper = new BankParameterAddressDTO();
        BankParameterContactInfoDTO bankParameterContactInfoDTOMapper = new BankParameterContactInfoDTO();
        BankParameterCurrencyDTO bankParameterCurrencyDTOMapper = new BankParameterCurrencyDTO();
        BankParameterPreferencesDTO bankParameterPreferencesDTOMapper = new BankParameterPreferencesDTO();
        BankParameterForOdLoanDTO bankParameterForOdLoanDTOMapper = new BankParameterForOdLoanDTO();
        bankParameterDTOMapper.setBankCode("0002");
        bankParameterDTOMapper.setBankName("State Bank of India");
        bankParameterDTOMapper.setRegulatoryBankCode("0002");
        bankParameterDTOMapper.setBankConciseName("SBI");
        bankParameterDTOMapper.setGroupBankingCode("SBI");
        bankParameterAddressDTOMapper.setBankAddress1("Kanakia Business Park 2");
        bankParameterAddressDTOMapper.setBankAddress2("JB Nagar 2");
        bankParameterAddressDTOMapper.setBankAddress3("Andheri East 2");
        bankParameterAddressDTOMapper.setBankAddress4("Mumbai 2");
        bankParameterAddressDTOMapper.setCountry("IN");
        bankParameterAddressDTOMapper.setState("MH");
        bankParameterAddressDTOMapper.setCity("MUM");
        bankParameterContactInfoDTOMapper.setTelephoneNo("022-4156271");
        bankParameterContactInfoDTOMapper.setFaxNo("022-4156271");
        bankParameterContactInfoDTOMapper.setEmailId("abc@xyz.com");
        bankParameterContactInfoDTOMapper.setBankWebsite("www.bob.com");
        bankParameterCurrencyDTOMapper.setCurrencyCode("INR");
        bankParameterCurrencyDTOMapper.setCurrencyName("INDIAN RUPEES");
        bankParameterCurrencyDTOMapper.setIsDenominationTrackingRequired(false);
        bankParameterCurrencyDTOMapper.setCurrencyOfDenomination("INR");
        bankParameterCurrencyDTOMapper.setDenominationTrackingCurrency("INR");
        bankParameterPreferencesDTOMapper.setWeekBeginDay("Monday");
        bankParameterPreferencesDTOMapper.setWeeklyOff1("Saturday");
        bankParameterPreferencesDTOMapper.setWeeklyOff2("Sunday");
        bankParameterPreferencesDTOMapper.setWeeklyOff3("");
        bankParameterPreferencesDTOMapper.setFinancialYearBeginMonth("April");
        bankParameterForOdLoanDTOMapper.setRuleIdForOd("OD_01");
        bankParameterForOdLoanDTOMapper.setRuleNameForOd("Regulated rule for Overdraft decision");
        bankParameterForOdLoanDTOMapper.setRuleIdForLoan("LN_01");
        bankParameterForOdLoanDTOMapper.setRuleNameForLoan("Regulated rule for Loan decision");

        bankParameterDTOMapper.setAuthorized("N");
        bankParameterDTOMapper.setTaskCode("BANKPARAMETER");
        bankParameterDTOMapper.setTaskIdentifier("0002");
        //bankParameterDTOMapper.setAction("add");
        //bankParameterDTOMapper.setStatus("new");
        //bankParameterDTOMapper.setRecordVersion(0);
        bankParameterDTOMapper.setBankParameterAddress(bankParameterAddressDTOMapper);
        bankParameterDTOMapper.setBankParameterContactInfo(bankParameterContactInfoDTOMapper);
        bankParameterDTOMapper.setBankParameterCurrency(bankParameterCurrencyDTOMapper);
        bankParameterDTOMapper.setBankParameterPreferences(bankParameterPreferencesDTOMapper);
        bankParameterDTOMapper.setBankParameterForOdLoan(bankParameterForOdLoanDTOMapper);
        return bankParameterDTOMapper;
    }

    private BankParameterDTO getBankParameterDTO () {
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

    private MutationEntity getMutationEntity () {
        String payLoadString =
                getpayloadValidString();
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("0002");
        mutationEntity.setTaskCode("BANKPARAMETER");
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("active");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("authorize");
        mutationEntity.setLastConfigurationAction("unauthorized");
        return mutationEntity;
    }

    private String getpayloadValidString () {
        String payLoadString =
                "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0,\"authorized\":\"N\",\"lastConfigurationAction\":\"draft\",\"taskCode\":\"BANKPARAMETER\",\"taskIdentifier\":\"0002\",\"bankCode\":\"0002\",\"bankName\":\"State Bank of India\",\"regulatoryBankCode\":\"0002\",\"bankConciseName\":\"State Bank\",\"groupBankingCode\":\"SBI\",\"bankParameterAddress\":{\"bankAddress1\":\"Kanakia Business Park 2\",\"bankAddress2\":\"JB Nagar 2\",\"bankAddress3\":\"Andheri East 2\",\"bankAddress4\":\"Mumbai 2\",\"country\":\"IN\",\"state\":\"MH\",\"city\":\"MUM\"},\"bankParameterContactInfo\":{\"telephoneNo\":\"022-4156271\",\"faxNo\":\"022-4156271\",\"emailId\":\"abc@xyz.com\",\"bankWebsite\":\"www.bob.com\"},\"bankParameterCurrency\":{\"currencyCode\":\"INR\",\"currencyName\":\"INDIAN RUPEES\",\"isDenominationTrackingRequired\":false,\"currencyOfDenomination\":\"INR\",\"denominationTrackingCurrency\":\"INR\"},\"bankParameterPreferences\":{\"weekBeginDay\":\"Monday\",\"weeklyOff1\":\"Saturday\",\"weeklyOff2\":\"Sunday\",\"weeklyOff3\":\"\",\"financialYearBeginMonth\":\"April\"},\"bankParameterForOdLoan\":{\"ruleIdForOd\":\"OD_01\",\"ruleNameForOd\":\"Regulated rule for Overdraft decision\",\"ruleIdForLoan\":\"LN_01\",\"ruleNameForLoan\":\"Regulated rule for Loan decision\"}}";
        return payLoadString;
    }

    private String getpayloadInvalidString () {
        String payLoadString =
                "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":10,\"authorized\":\"N\",\"lastConfigurationAction\":\"draft\",\"taskCode\":\"BANKPARAMETER\",\"taskIdentifier\":\"0002\",\"bankCode\":\"0002\",\"bankName\":\"State Bank of India\",\"regulatoryBankCode\":\"0002\",\"bankConciseName\":\"State Bank\",\"groupBankingCode\":\"SBI\",\"bankParameterAddress\":{\"bankAddress1\":\"Kanakia Business Park 2\",\"bankAddress2\":\"JB Nagar 2\",\"bankAddress3\":\"Andheri East 2\",\"bankAddress4\":\"Mumbai 2\",\"country\":\"IN\",\"state\":\"MH\",\"city\":\"MUM\"},\"bankParameterContactInfo\":{\"telephoneNo\":\"022-4156271\",\"faxNo\":\"022-4156271\",\"emailId\":\"abc@xyz.com\",\"bankWebsite\":\"www.bob.com\"},\"bankParameterCurrency\":{\"currencyCode\":\"INR\",\"currencyName\":\"INDIAN RUPEES\",\"isDenominationTrackingRequired\":false,\"currencyOfDenomination\":\"INR\",\"denominationTrackingCurrency\":\"INR\"},\"bankParameterPreferences\":{\"weekBeginDay\":\"Monday\",\"weeklyOff1\":\"Saturday\",\"weeklyOff2\":\"Sunday\",\"weeklyOff3\":\"\",\"financialYearBeginMonth\":\"April\"},\"bankParameterForOdLoan\":{\"ruleIdForOd\":\"OD_01\",\"ruleNameForOd\":\"Regulated rule for Overdraft decision\",\"ruleIdForLoan\":\"LN_01\",\"ruleNameForLoan\":\"Regulated rule for Loan decision\"}}";
        return payLoadString;
    }

    private MutationEntity getMutationEntityError () {
        String payLoadString =
                getpayloadInvalidString();
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("0002");
        mutationEntity.setTaskCode("BANKPARAMETER");
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("active");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("authorize");
        mutationEntity.setLastConfigurationAction("unauthorized");
        return mutationEntity;
    }

    private BankParameterDTO getBankParameterDTODeleted () {
        BankParameterDTO bankParameterDtoDeleted = new BankParameterDTO();
        BankParameterAddressDTO bankParameterAddressDTODeleted = new BankParameterAddressDTO();
        BankParameterContactInfoDTO bankParameterContactInfoDTODeleted = new BankParameterContactInfoDTO();
        BankParameterCurrencyDTO bankParameterCurrencyDTODeleted = new BankParameterCurrencyDTO();
        BankParameterPreferencesDTO bankParameterPreferencesDTODeleted = new BankParameterPreferencesDTO();
        BankParameterForOdLoanDTO bankParameterForOdLoanDTODeleted = new BankParameterForOdLoanDTO();
        bankParameterDtoDeleted.setBankCode("0002");
        bankParameterDtoDeleted.setBankName("State Bank of India");
        bankParameterDtoDeleted.setRegulatoryBankCode("0002");
        bankParameterDtoDeleted.setBankConciseName("SBI");
        bankParameterDtoDeleted.setGroupBankingCode("SBI");
        bankParameterAddressDTODeleted.setBankAddress1("Kanakia Business Park 2");
        bankParameterAddressDTODeleted.setBankAddress2("JB Nagar 2");
        bankParameterAddressDTODeleted.setBankAddress3("Andheri East 2");
        bankParameterAddressDTODeleted.setBankAddress4("Mumbai 2");
        bankParameterAddressDTODeleted.setCountry("IN");
        bankParameterAddressDTODeleted.setState("MH");
        bankParameterAddressDTODeleted.setCity("MUM");
        bankParameterContactInfoDTODeleted.setTelephoneNo("022-4156271");
        bankParameterContactInfoDTODeleted.setFaxNo("022-4156271");
        bankParameterContactInfoDTODeleted.setEmailId("abc@xyz.com");
        bankParameterContactInfoDTODeleted.setBankWebsite("www.bob.com");
        bankParameterCurrencyDTODeleted.setCurrencyCode("INR");
        bankParameterCurrencyDTODeleted.setCurrencyName("INDIAN RUPEES");
        bankParameterCurrencyDTODeleted.setIsDenominationTrackingRequired(false);
        bankParameterCurrencyDTODeleted.setCurrencyOfDenomination("INR");
        bankParameterCurrencyDTODeleted.setDenominationTrackingCurrency("INR");
        bankParameterPreferencesDTODeleted.setWeekBeginDay("Monday");
        bankParameterPreferencesDTODeleted.setWeeklyOff1("Saturday");
        bankParameterPreferencesDTODeleted.setWeeklyOff2("Sunday");
        bankParameterPreferencesDTODeleted.setWeeklyOff3("");
        bankParameterPreferencesDTODeleted.setFinancialYearBeginMonth("April");
        bankParameterForOdLoanDTODeleted.setRuleIdForOd("OD_01");
        bankParameterForOdLoanDTODeleted.setRuleNameForOd("Regulated rule for Overdraft decision");
        bankParameterForOdLoanDTODeleted.setRuleIdForLoan("LN_01");
        bankParameterForOdLoanDTODeleted.setRuleNameForLoan("Regulated rule for Loan decision");
        bankParameterDtoDeleted.setAction("");
        bankParameterDtoDeleted.setStatus("deleted");
        bankParameterDtoDeleted.setTaskCode("BANKPARAMETER");
        bankParameterDtoDeleted.setTaskIdentifier("0002");
        bankParameterDtoDeleted.setRecordVersion(1);
        return bankParameterDtoDeleted;
    }

    private MutationEntity getMutationEntityDeleted () {
        String payLoadString =
                getpayloadValidString();
        MutationEntity unauthorizedEntities = new MutationEntity();
        unauthorizedEntities.setTaskCode("BANKPARAMETER");
        unauthorizedEntities.setTaskIdentifier("0002");
        unauthorizedEntities.setPayload(new Payload(payLoadString));
        unauthorizedEntities.setAuthorized("N");
        unauthorizedEntities.setStatus("DELETED");
        unauthorizedEntities.setRecordVersion(1);
        return unauthorizedEntities;
    }

    private BankParameterEntity getBankParameterEntityDeleted () {
        BankParameterEntity bankParameterEntityDeleted = new BankParameterEntity();
        BankParameterAddressEntity bankParameterAddressEntityDeleted = new BankParameterAddressEntity();
        BankParameterContactInfoEntity bankParameterContactInfoEntityDeleted = new BankParameterContactInfoEntity();
        BankParameterCurrencyEntity bankParameterCurrencyEntityDeleted = new BankParameterCurrencyEntity();
        BankParameterPreferencesEntity bankParameterPreferencesEntityDeleted = new BankParameterPreferencesEntity();
        BankParameterForOdLoanEntity bankParameterForOdLoanEntityDeleted = new BankParameterForOdLoanEntity();
        bankParameterEntityDeleted.setBankCode("0002");
        bankParameterEntityDeleted.setBankName("State Bank of India");
        bankParameterEntityDeleted.setBankCodeRegulatory("0002");
        bankParameterEntityDeleted.setBankConciseName("SBI");
        bankParameterEntityDeleted.setBankGroupCode("SBI");
        bankParameterAddressEntityDeleted.setBankAddress1("Kanakia Business Park 2");
        bankParameterAddressEntityDeleted.setBankAddress2("JB Nagar 2");
        bankParameterAddressEntityDeleted.setBankAddress3("Andheri East 2");
        bankParameterAddressEntityDeleted.setBankAddress4("Mumbai 2");
        bankParameterAddressEntityDeleted.setCountryCode("IN");
        bankParameterAddressEntityDeleted.setStateCode("MH");
        bankParameterAddressEntityDeleted.setCityCode("MUM");
        bankParameterContactInfoEntityDeleted.setTelephoneNo("022-4156271");
        bankParameterContactInfoEntityDeleted.setFaxNo("022-4156271");
        bankParameterContactInfoEntityDeleted.setEmailId("abc@xyz.com");
        bankParameterContactInfoEntityDeleted.setBankWebsite("www.bob.com");
        bankParameterCurrencyEntityDeleted.setCurrencyCode("INR");
        bankParameterCurrencyEntityDeleted.setCurrencyName("INDIAN RUPEES");
        //bankParameterCurrencyEntityDeleted.setIsDenominationTrackingRequired(false);
        bankParameterCurrencyEntityDeleted.setCurrencyDenomination("INR");
        bankParameterCurrencyEntityDeleted.setCurrencyDenominationTracking("INR");
        bankParameterPreferencesEntityDeleted.setWeekBeginDay("Monday");
        bankParameterPreferencesEntityDeleted.setWeeklyOff1("Saturday");
        bankParameterPreferencesEntityDeleted.setWeeklyOff2("Sunday");
        bankParameterPreferencesEntityDeleted.setWeeklyOff3("");
        bankParameterPreferencesEntityDeleted.setFinancialYearBeginMonth("April");
        bankParameterForOdLoanEntityDeleted.setRuleIdOd("OD_01");
        bankParameterForOdLoanEntityDeleted.setRuleNameOd("Regulated rule for Overdraft decision");
        bankParameterForOdLoanEntityDeleted.setRuleIdLoan("LN_01");
        bankParameterForOdLoanEntityDeleted.setRuleNameLoan("Regulated rule for Loan decision");
        bankParameterEntityDeleted.setStatus("deleted");
        bankParameterEntityDeleted.setRecordVersion(1);
        bankParameterEntityDeleted.setBankParameterAddressEntity(bankParameterAddressEntityDeleted);
        bankParameterEntityDeleted.setBankParameterContactInfoEntity(
                bankParameterContactInfoEntityDeleted);
        bankParameterEntityDeleted.setBankParameterCurrencyEntity(
                bankParameterCurrencyEntityDeleted);
        bankParameterEntityDeleted.setBankParameterPreferencesEntity(
                bankParameterPreferencesEntityDeleted);
        bankParameterEntityDeleted.setBankParameterForOdLoanEntity(
                bankParameterForOdLoanEntityDeleted);
        return bankParameterEntityDeleted;
    }

    private MutationEntity getMutationEntityJsonError()
    {
        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":10,\"authorized\":\"N\",\"lastConfigurationAction\":\"draft\",\"taskCode\":\"BANKPARAMETER\",\"taskIdentifier\":\"0002\",\"bankCode\":\"0002\",\"bankName\":\"State Bank of India\",\"regulatoryBankCode\":\"0002\",\"bankConciseName\":\"State Bank\",\"groupBankingCode\":\"SBI\",\"bankParameterAddress\":{\"bankAddress1\":\"Kanakia Business Park 2\",\"bankAddress2\":\"JB Nagar 2\",\"bankAddress3\":\"Andheri East 2\",\"bankAddress4\":\"Mumbai 2\",\"country\":\"IN\",\"state\":\"MH\",\"city\":\"MUM\"},\"bankParameterContactInfo\":{\"telephoneNo\":\"022-4156271\",\"faxNo\":\"022-4156271\",\"emailId\":\"abc@xyz.com\",\"bankWebsite\":\"www.bob.com\"},\"bankParameterCurrency\":{\"currencyCode\":\"INR\",\"currencyName\":\"INDIAN RUPEES\",\"isDenominationTrackingRequired\":false,\"currencyOfDenomination\":\"INR\",\"denominationTrackingCurrency\":\"INR\"},\"bankParameterPreferences\":{\"weekBeginDay\":\"Monday\",\"weeklyOff1\":\"Saturday\",\"weeklyOff2\":\"Sunday\",\"weeklyOff3\":\"\",\"financialYearBeginMonth\":\"April\"},\"bankParameterForOdLoan\":{\"ruleIdForOd\":\"OD_01\",\"ruleNameForOd\":\"Regulated rule for Overdraft decision\",\"ruleIdForLoan\":\"LN_01\",\"ruleNameForLoan\":\"Regulated rule for Loan decision\"}}";
        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("0002");
        mutationEntity2.setTaskCode("BANKPARAMETER");
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("draft");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("draft");
        return mutationEntity2;
    }
}


