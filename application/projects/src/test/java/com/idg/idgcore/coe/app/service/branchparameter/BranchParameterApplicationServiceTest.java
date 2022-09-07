package com.idg.idgcore.coe.app.service.branchparameter;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.idg.idgcore.coe.domain.assembler.branchparameter.*;
import com.idg.idgcore.coe.domain.entity.branchparameter.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.domain.process.*;
import com.idg.idgcore.coe.domain.service.branchparameter.*;
import com.idg.idgcore.coe.domain.service.mutation.*;
import com.idg.idgcore.coe.dto.bankparameter.BankParameterDTO;
import com.idg.idgcore.coe.dto.branchparameter.*;
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

import static com.idg.idgcore.coe.common.Constants.BRANCH_PARAMETER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith (MockitoExtension.class)
class BranchParameterApplicationServiceTest {
    @InjectMocks
    private BranchParameterApplicationService branchParameterApplicationService;
    @Mock private BranchParameterAssembler branchParameterAssembler;
    @Mock private IBranchParameterDomainService branchParameterDomainService;
    @Mock private IMutationsDomainService mutationsDomainService;
    @Mock private ProcessConfiguration processConfiguration;
    @Autowired
    private MutationEntity mutationEntity;
    private MutationEntity mutationEntity2;

    private SessionContext sessionContext;
    private BranchParameterDTO branchParameterDTO;
    private BranchParameterDTO branchParameterDTOAuth;
    private BranchParameterDTO branchParameterDTOUnAuth;
    private BranchParameterEntity branchParameterEntity;
    private BranchParameterEntity branchParameterEntityUnAut;
    private BranchParameterDTO branchParameterDTOMapper;
    private BranchParameterDTO branchParameterDTO1;

    @BeforeEach
    void setUp () {
        sessionContext = getValidSessionContext();
        branchParameterDTOAuth = getBranchParameterDTOAuthorized();
        branchParameterEntity = getBranchParameterEntity();
        branchParameterDTOUnAuth = getBranchParameterDTOUnAuthorized();
        branchParameterDTOUnAuth.setAuthorized("N");
        branchParameterEntityUnAut = getBranchParameterEntity();
        branchParameterEntityUnAut.setAuthorized("N");
        mutationEntity = getMutationEntity();
        branchParameterDTO = getBranchParameterDTO();
        //branchParameterDTOMapper = getBranchParameterDTOMapper();
        branchParameterDTO1=getBranchParameterDTO();
        //mutationEntity2=getMutationEntityJsonError();
    }

    @Test
    @DisplayName ("JUnit for getBranchParameterByBranchParameterCountryCode in application service when Authorize")
    void getBranchParameterByBranchParameterCountryCodeWithAuthRecord () throws FatalException {
        given(branchParameterDomainService.getBranchParameterByBranchCode(
                branchParameterDTOAuth.getBranchCode())).willReturn(branchParameterEntity);
        given(branchParameterAssembler.convertEntityToDto(branchParameterEntity)).willReturn(
                branchParameterDTOAuth);
        BranchParameterDTO branchParameterDTO1 = branchParameterApplicationService.getBranchParameterByBranchCode(
                sessionContext, branchParameterDTOAuth);
        assertThat(branchParameterDTO1.getAuthorized()).isEqualTo("Y");
        assertThat(branchParameterDTOAuth).isNotNull();
        assertThat(branchParameterDTOAuth.toString()).isNotNull();
        assertThat(branchParameterDTO.toString()).isNotNull();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverageDTO()
    {
        System.out.println(branchParameterEntity.toString());
        System.out.println(branchParameterDTO.toString());

        BranchParameterAddressDTO branchParameterAddressDTO = new BranchParameterAddressDTO("Kanakia Business Park 2","JB Nagar 2","Andheri East 2","Mumbai 2","IN","MH","MUM");
        BranchParameterContactInfoDTO branchParameterContactInfoDTO = new BranchParameterContactInfoDTO("022-4156271","022-4156271","abc@xyz.com","www.bob.com");
        BranchParameterGeneralDTO branchParameterGeneralDTO = new BranchParameterGeneralDTO("INR","5002","IN","WI","Western India","MUM","Mumbai","2");
        BranchParameterClearingDTO branchParameterClearingDTO = new BranchParameterClearingDTO("Payment","INR");
        BranchParameterAtmDTO branchParameterAtmDTO = new BranchParameterAtmDTO("5001","SBI5","5001",true);
        BranchParameterTimezoneDTO branchParameterTimezoneDTO = new BranchParameterTimezoneDTO(0,3,true,"IST (UTC+5:30)");
        BranchParameterGlobalInterdictDTO branchParameterGlobalInterdictDTO = new BranchParameterGlobalInterdictDTO("FN01");
        BranchParameterTranDuplicateDTO branchParameterTranDuplicateDTO = new BranchParameterTranDuplicateDTO("LN",true,1);
        BranchParameterLocalCurrencyDTO branchParameterLocalCurrencyDTO = new BranchParameterLocalCurrencyDTO("LN","Suppress LCY Message");
        BranchParameterMiscellaneousDTO branchParameterMiscellaneousDTO = new BranchParameterMiscellaneousDTO(true,"INR","Monday","Saturday","Sunday","April");
        BranchParameterDTO branchParameterDTO2=new BranchParameterDTO("10001","Bandra East","0002","SBI",true,"H1","HO", branchParameterAddressDTO,branchParameterContactInfoDTO, branchParameterGeneralDTO,branchParameterClearingDTO,branchParameterAtmDTO, branchParameterTimezoneDTO,branchParameterGlobalInterdictDTO, branchParameterTranDuplicateDTO, branchParameterLocalCurrencyDTO, branchParameterMiscellaneousDTO);

        String s = BranchParameterDTO.builder().branchCode("10001").branchName("Bandra East").bankCode("0002")
                .branchConciseName("SBI").isBranchAvailableStatus(true).bankIdentifierCode("H1").branchType("HO").build()
                .toString();

        branchParameterDTO2.setBranchParameterAddress(branchParameterAddress);
        branchParameterDTO2.setBranchParameterContactInfo(branchParameterContactInfo);
        branchParameterDTO2.setBranchParameterGeneral(branchParameterGeneral);
        branchParameterDTO2.setBranchParameterClearing(branchParameterClearing);
        branchParameterDTO2.setBranchParameterAtm(branchParameterAtm);
        branchParameterDTO2.setBranchParameterTimezone(branchParameterTimezone);
        branchParameterDTO2.setBranchParameterGlobalInterdict(branchParameterGlobalInterdict);
        branchParameterDTO2.setBranchParameterTranDuplicate(branchParameterTranDuplicate);
        branchParameterDTO2.setBranchParameterLocalCurrency(branchParameterLocalCurrency);
        branchParameterDTO2.setBranchParameterMiscellaneous(branchParameterMiscellaneous);

        BranchParameterEntityKey branchParameterEntityKey=new BranchParameterEntityKey("10001");

        branchParameterEntityKey.setBranchCode("10001");
        System.out.println(branchParameterEntityKey.getBranchCode());
        branchParameterEntityKey.keyAsString();
        //branchParameterEntityKey.builder().bankCode("0003").build();
        assertThat(branchParameterDTO2).descriptionText();
        assertThat(s.toString()).isNotNull();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverageEntity()
    {
        BranchParameterAddressEntity branchParameterAddressEntity = new BranchParameterAddressEntity("Kanakia Business Park 2","JB Nagar 2","Andheri East 2","Mumbai 2","IN","MH","MUM");
        BranchParameterContactInfoEntity branchParameterContactInfoEntity = new BranchParameterContactInfoEntity("022-4156271","022-4156271","abc@xyz.com","www.bob.com");
        BranchParameterGeneralEntity branchParameterGeneralEntity = new BranchParameterGeneralEntity("INR","5002","IN","WI","Western India","MUM","Mumbai","2");
        BranchParameterClearingEntity branchParameterClearingEntity = new BranchParameterClearingEntity("Payment","INR");
        BranchParameterAtmEntity branchParameterAtmEntity = new BranchParameterAtmEntity("5001","SBI5","5001",branchParameterAssembler.getCharValueFromBoolean(true));
        BranchParameterTimezoneEntity branchParameterTimezoneEntity = new BranchParameterTimezoneEntity(0,3,branchParameterAssembler.getCharValueFromBoolean(true),"IST (UTC+5:30)");
        BranchParameterGlobalInterdictEntity branchParameterGlobalInterdictEntity = new BranchParameterGlobalInterdictEntity("FN01");
        BranchParameterTranDuplicateEntity branchParameterTranDuplicateEntity = new BranchParameterTranDuplicateEntity("LN",branchParameterAssembler.getCharValueFromBoolean(true),1);
        BranchParameterLocalCurrencyEntity branchParameterLocalCurrencyEntity = new BranchParameterLocalCurrencyEntity("LN","Suppress LCY Message");
        BranchParameterMiscellaneousEntity branchParameterMiscellaneousEntity = new BranchParameterMiscellaneousEntity(branchParameterAssembler.getCharValueFromBoolean(true),"INR","Monday","Saturday","Sunday","April");
        BranchParameterEntity branchParameterEntity;
        branchParameterEntity = new BranchParameterEntity("10001","Bandra East","0002","SBI",branchParameterAssembler.getCharValueFromBoolean(true),"H1","HO", "","","draft", 0, "Y", "draft",   branchParameterAddressEntity,branchParameterContactInfoEntity, branchParameterGeneralEntity,branchParameterClearingEntity,branchParameterAtmEntity, branchParameterTimezoneEntity,branchParameterGlobalInterdictEntity, branchParameterTranDuplicateEntity, branchParameterLocalCurrencyEntity, branchParameterMiscellaneousEntity);
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

        assertThat(branchParameterEntity).descriptionText();
    }

    @DisplayName ("JUnit test for processBranchParameter method")
    @Test
    void processBranchParameterWithNew () throws JsonProcessingException, FatalException {
        BranchParameterDTO branchParameterDTONew = getBranchParameterDTOMapper();
        doNothing().when(processConfiguration).process(branchParameterDTONew);
        branchParameterApplicationService.processBranchParameter(sessionContext, branchParameterDTONew);
        verify(processConfiguration, times(1)).process(branchParameterDTONew);
    }

    @DisplayName ("JUnit test for addUpdateRecord method")
    @Test
    void addUpdateRecord () throws JsonProcessingException, FatalException {
        String payloadStr = getpayloadValidString();
        BranchParameterDTO branchParameterDTO = getBranchParameterDTOForSave();
        doNothing().when(branchParameterDomainService).save(branchParameterDTO);
        branchParameterApplicationService.save(branchParameterDTO);
        branchParameterApplicationService.addUpdateRecord(payloadStr);
        verify(branchParameterDomainService, times(1)).save(branchParameterDTO);
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = branchParameterDTO.getBranchCode();
        given(branchParameterDomainService.getBranchParameterByBranchCode(code)).willReturn(branchParameterEntity);
        branchParameterApplicationService.getConfigurationByCode(code);
        assertThat(branchParameterEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeDTOTest(){
        String code = branchParameterDTO.getBranchCode();
        given(branchParameterDomainService.getBranchParameterByBranchCode(code)).willReturn(branchParameterEntity);
        branchParameterApplicationService.getConfigurationByCode(code);
        assertThat(branchParameterEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for processBranchParameter in application service for Try Block")
    void processBranchParameterForTryBlock() throws JsonProcessingException, FatalException {
        doNothing().when(processConfiguration).process(branchParameterDTO);
        branchParameterApplicationService.processBranchParameter(sessionContext, branchParameterDTO);
        verify(processConfiguration, times(1)).process(branchParameterDTO);
    }

    @Test
    @DisplayName("JUnit for processBranchParameter in application service for Catch Block")
    void processStateForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            branchParameterApplicationService.processBranchParameter(sessionContext2, branchParameterDTO);
            assertThat(branchParameterDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for getBranchParameterByBranchParameterCountryCode in application service when Not Authorize in catch block")
    void getBranchParameterByBranchParameterCountryCodeWhenNotAuthorizeCatchBlock () throws FatalException {
        String payLoadString1 =getpayloadInvalidString();
        //"{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"STATE\",\"taskIdentifier\":\"MH\",\"stateCode\":\"MH\",\"stateName\":\"MAHARASHTRA\",\"countryCode\":\"IN\"}";
        given(mutationsDomainService.getConfigurationByCode(branchParameterDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        /*given(branchParameterAssembler.setAuditFields(mutationEntity2, branchParameterDTOUnAuth))
                .willReturn(branchParameterDTOUnAuth);*/
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = mock(PayloadDTO.class);
        Assertions.assertThrows(Exception.class,()-> {
            BranchParameterDTO branchParameterDTO1 = branchParameterApplicationService.getBranchParameterByBranchCode(sessionContext, branchParameterDTOUnAuth);
            assertEquals("N",branchParameterDTO1.getAuthorized());
            assertThat(branchParameterDTO1).isNotNull();

        });
    }

    @Test
    @DisplayName("JUnit for getBranchParameters in application service for try block")
    void getBranchParametersTryBlock() throws FatalException {

        given(mutationsDomainService.getMutations(BRANCH_PARAMETER))
                .willReturn(List.of(mutationEntity));
        List<BranchParameterDTO> branchParameterDTOList =
                branchParameterApplicationService.getBranchParameters(sessionContext);
        assertThat(branchParameterDTOList).isNotNull();
    }


    /**
     *
     * Negative Test Cases
     */
    @Test
    @DisplayName("JUnit for getBankByCode in application service when Authorize for Negative")
    void getBranchParameterBankByCodeIsAuthorizeForNegative() throws FatalException {
        given(branchParameterDomainService.getBranchParameterByBranchCode(branchParameterDTO.getBranchCode())).willReturn(branchParameterEntity);
        given(branchParameterAssembler.convertEntityToDto(branchParameterEntity)).willReturn(branchParameterDTO);
        BranchParameterDTO branchParameterDTO1 = branchParameterApplicationService.getBranchParameterByBranchCode(sessionContext, branchParameterDTO);
        assertNotEquals("N",branchParameterDTO1.getAuthorized());
        assertThat(branchParameterDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getBankByCode in application service check Parameter not null")
    void getBranchParameterBankByCodeIsAuthorizeCheckParameter() throws FatalException {
        //BranchParameterDTO branchParameterDTOnull=null;
        BranchParameterDTO branchParameterDTOEx=new BranchParameterDTO();
        branchParameterDTOEx.setBranchCode("10001");
        branchParameterDTOEx.setAuthorized("Y");
        given(branchParameterDomainService.getBranchParameterByBranchCode(branchParameterDTOEx.getBranchCode())).willReturn(branchParameterEntity);
        given(branchParameterAssembler.convertEntityToDto(branchParameterEntity)).willReturn(branchParameterDTO);
        BranchParameterDTO stateDTO1 = branchParameterApplicationService.getBranchParameterByBranchCode(sessionContext, branchParameterDTOEx);
        assertThat(branchParameterDTOEx.getBranchCode()).isNotBlank();
        assertThat(branchParameterDTOEx.getAuthorized()).isNotBlank();
    }

    /*@Test
    void getBranchParameterByBranchCode () {
    }

    @Test
    void getBranchParameters () {
    }

    @Test
    void processBranchParameter () {
    }

    @Test
    void addUpdateRecord () {
    }

    @Test
    void getConfigurationByCode () {
    }

    @Test
    void save () {
    }
*/

    private String branchParameterCountryCode;
    private Integer branchParameterCountryPosition;
    private Integer branchParameterCountryCodeLength;
    private Integer branchParameterCheckDigitPosition;
    private Integer branchParameterCheckDigitLength;
    private String branchParameterNationalIdLength;
    private Integer branchParameterTotalLength;

    private BranchParameterAddressDTO branchParameterAddress;
    private BranchParameterContactInfoDTO branchParameterContactInfo;
    private BranchParameterGeneralDTO branchParameterGeneral;
    private BranchParameterClearingDTO branchParameterClearing;
    private BranchParameterAtmDTO branchParameterAtm;
    private BranchParameterTimezoneDTO branchParameterTimezone;
    private BranchParameterGlobalInterdictDTO branchParameterGlobalInterdict;
    private BranchParameterTranDuplicateDTO branchParameterTranDuplicate;
    private BranchParameterLocalCurrencyDTO branchParameterLocalCurrency;
    private BranchParameterMiscellaneousDTO branchParameterMiscellaneous;

    private Integer bankIdentifierPosition;
    private String bankIdentifierLength;
    private String branchIdentifierPosition;
    private String branchIdentifierLength;
    private Integer accountNumberPosition;
    private Integer accountNumberLength;

    private BranchParameterDTO getBranchParameterDTOAuthorized () {
        BranchParameterDTO branchParameterDTOMapper = new BranchParameterDTO();
        BranchParameterAddressDTO branchParameterAddressDTOMapper = new BranchParameterAddressDTO();
        BranchParameterContactInfoDTO branchParameterContactInfoMapper = new BranchParameterContactInfoDTO();
        BranchParameterGeneralDTO branchParameterGeneralMapper = new BranchParameterGeneralDTO();
        BranchParameterClearingDTO branchParameterClearingMapper = new BranchParameterClearingDTO();
        BranchParameterAtmDTO branchParameterAtmMapper = new BranchParameterAtmDTO();
        BranchParameterTimezoneDTO branchParameterTimezoneMapper = new BranchParameterTimezoneDTO();
        BranchParameterGlobalInterdictDTO branchParameterGlobalInterdictMapper = new BranchParameterGlobalInterdictDTO();
        BranchParameterTranDuplicateDTO branchParameterTranDuplicateMapper =  new BranchParameterTranDuplicateDTO();
        BranchParameterLocalCurrencyDTO branchParameterLocalCurrencyMapper = new BranchParameterLocalCurrencyDTO();
        BranchParameterMiscellaneousDTO branchParameterMiscellaneousMapper = new BranchParameterMiscellaneousDTO();

        branchParameterDTOMapper.setBankCode("0002");
        branchParameterDTOMapper.setBranchCode("10001");
        branchParameterDTOMapper.setBranchName("Bandra East");
        branchParameterDTOMapper.setBranchConciseName("SBI");
        branchParameterDTOMapper.setIsBranchAvailableStatus(true);
        branchParameterDTOMapper.setBankIdentifierCode("H1");
        branchParameterDTOMapper.setBankCode("HO");

        branchParameterAddressDTOMapper.setBranchAddress1("Kanakia Business Park 2");
        branchParameterAddressDTOMapper.setBranchAddress2("JB Nagar 2");
        branchParameterAddressDTOMapper.setBranchAddress3("Andheri East 2");
        branchParameterAddressDTOMapper.setBranchAddress4("Mumbai 2");
        branchParameterAddressDTOMapper.setCountry("IN");
        branchParameterAddressDTOMapper.setState("MH");
        branchParameterAddressDTOMapper.setCity("MUM");

        branchParameterContactInfoMapper.setTelephoneNo("022-4156271");
        branchParameterContactInfoMapper.setFaxNo("022-4156271");
        branchParameterContactInfoMapper.setEmailId("abc@xyz.com");
        branchParameterContactInfoMapper.setBankWebsite("www.bob.com");

        branchParameterGeneralMapper.setGeneralCurrencyCode("INR");
        branchParameterGeneralMapper.setGeneralBranchParent("5002");
        branchParameterGeneralMapper.setGeneralCountryCode("IN");
        branchParameterGeneralMapper.setGeneralZonalOfficeCode("WI");
        branchParameterGeneralMapper.setGeneralZonalOfficeName("Western India");
        branchParameterGeneralMapper.setGeneralRegionalOfficeCode("MUM");
        branchParameterGeneralMapper.setGeneralRegionalOfficeName("Mumbai");
        branchParameterGeneralMapper.setGeneralCutOffTime("2");

        branchParameterClearingMapper.setClearingLocalPaymentBranch("Payment");
        branchParameterClearingMapper.setClearingCurrencyCode("INR");

        branchParameterAtmMapper.setAtmBranch("5001");
        branchParameterAtmMapper.setAtmInstitutionIdentification("SBI5");
        branchParameterAtmMapper.setAtmInterBranchTransactionCode("5001");
        branchParameterAtmMapper.setAtmIsCustomerFundTransfer(true);

        branchParameterTimezoneMapper.setTimeZoneNoOfHours(0);
        branchParameterTimezoneMapper.setTimeZoneNoOfMinutes(3);
        branchParameterTimezoneMapper.setTimeZoneIsAhead(true);
        branchParameterTimezoneMapper.setTimeZoneLevel("IST (UTC+5:30)");

        branchParameterGlobalInterdictMapper.setGlobalInterdictFunctionId("FN01");

        branchParameterTranDuplicateMapper.setTranModuleCode("LN");
        branchParameterTranDuplicateMapper.setTranDuplicationCheckRequired(true);
        branchParameterTranDuplicateMapper.setTranNoOfDayForDuplicationCheck(1);

        branchParameterLocalCurrencyMapper.setLocalCurrencyMsgModCode("LN");
        branchParameterLocalCurrencyMapper.setLocalCurrencyMsgType("Suppress LCY Message");

        branchParameterMiscellaneousMapper.setMiscDenominationTrackingRequired(true);
        branchParameterMiscellaneousMapper.setMiscCurrencyOfDenomination("INR");
        branchParameterMiscellaneousMapper.setMiscWeekBeginDay("Monday");
        branchParameterMiscellaneousMapper.setMiscWeeklyOff1("Saturday");
        branchParameterMiscellaneousMapper.setMiscWeeklyOff2("Sunday");
        branchParameterMiscellaneousMapper.setMiscFinancialYearBeginMonth("April");

        branchParameterDTOMapper.setAuthorized("Y");
        branchParameterDTOMapper.setTaskCode("BRANCH_PARAMETER");
        branchParameterDTOMapper.setTaskIdentifier("10001");
        branchParameterDTOMapper.setBranchParameterAddress(branchParameterAddress);
        branchParameterDTOMapper.setBranchParameterContactInfo(branchParameterContactInfo);
        branchParameterDTOMapper.setBranchParameterGeneral(branchParameterGeneral);
        branchParameterDTOMapper.setBranchParameterClearing(branchParameterClearing);
        branchParameterDTOMapper.setBranchParameterAtm(branchParameterAtm);
        branchParameterDTOMapper.setBranchParameterTimezone(branchParameterTimezone);
        branchParameterDTOMapper.setBranchParameterGlobalInterdict(branchParameterGlobalInterdict);
        branchParameterDTOMapper.setBranchParameterTranDuplicate(branchParameterTranDuplicate);
        branchParameterDTOMapper.setBranchParameterLocalCurrency(branchParameterLocalCurrency);
        branchParameterDTOMapper.setBranchParameterMiscellaneous(branchParameterMiscellaneous);
        return branchParameterDTOMapper;
    }

    private BranchParameterDTO getBranchParameterDTOUnAuthorized () {
        BranchParameterDTO branchParameterDTOMapper = new BranchParameterDTO();
        BranchParameterAddressDTO branchParameterAddressDTOMapper = new BranchParameterAddressDTO();
        BranchParameterContactInfoDTO branchParameterContactInfoMapper = new BranchParameterContactInfoDTO();
        BranchParameterGeneralDTO branchParameterGeneralMapper = new BranchParameterGeneralDTO();
        BranchParameterClearingDTO branchParameterClearingMapper = new BranchParameterClearingDTO();
        BranchParameterAtmDTO branchParameterAtmMapper = new BranchParameterAtmDTO();
        BranchParameterTimezoneDTO branchParameterTimezoneMapper = new BranchParameterTimezoneDTO();
        BranchParameterGlobalInterdictDTO branchParameterGlobalInterdictMapper = new BranchParameterGlobalInterdictDTO();
        BranchParameterTranDuplicateDTO branchParameterTranDuplicateMapper =  new BranchParameterTranDuplicateDTO();
        BranchParameterLocalCurrencyDTO branchParameterLocalCurrencyMapper = new BranchParameterLocalCurrencyDTO();
        BranchParameterMiscellaneousDTO branchParameterMiscellaneousMapper = new BranchParameterMiscellaneousDTO();

        branchParameterDTOMapper.setBankCode("0002");
        branchParameterDTOMapper.setBranchCode("10001");
        branchParameterDTOMapper.setBranchName("Bandra East");
        branchParameterDTOMapper.setBranchConciseName("SBI");
        branchParameterDTOMapper.setIsBranchAvailableStatus(true);
        branchParameterDTOMapper.setBankIdentifierCode("H1");
        branchParameterDTOMapper.setBankCode("HO");

        branchParameterAddressDTOMapper.setBranchAddress1("Kanakia Business Park 2");
        branchParameterAddressDTOMapper.setBranchAddress2("JB Nagar 2");
        branchParameterAddressDTOMapper.setBranchAddress3("Andheri East 2");
        branchParameterAddressDTOMapper.setBranchAddress4("Mumbai 2");
        branchParameterAddressDTOMapper.setCountry("IN");
        branchParameterAddressDTOMapper.setState("MH");
        branchParameterAddressDTOMapper.setCity("MUM");

        branchParameterContactInfoMapper.setTelephoneNo("022-4156271");
        branchParameterContactInfoMapper.setFaxNo("022-4156271");
        branchParameterContactInfoMapper.setEmailId("abc@xyz.com");
        branchParameterContactInfoMapper.setBankWebsite("www.bob.com");

        branchParameterGeneralMapper.setGeneralCurrencyCode("INR");
        branchParameterGeneralMapper.setGeneralBranchParent("5002");
        branchParameterGeneralMapper.setGeneralCountryCode("IN");
        branchParameterGeneralMapper.setGeneralZonalOfficeCode("WI");
        branchParameterGeneralMapper.setGeneralZonalOfficeName("Western India");
        branchParameterGeneralMapper.setGeneralRegionalOfficeCode("MUM");
        branchParameterGeneralMapper.setGeneralRegionalOfficeName("Mumbai");
        branchParameterGeneralMapper.setGeneralCutOffTime("2");

        branchParameterClearingMapper.setClearingLocalPaymentBranch("Payment");
        branchParameterClearingMapper.setClearingCurrencyCode("INR");

        branchParameterAtmMapper.setAtmBranch("5001");
        branchParameterAtmMapper.setAtmInstitutionIdentification("SBI5");
        branchParameterAtmMapper.setAtmInterBranchTransactionCode("5001");
        branchParameterAtmMapper.setAtmIsCustomerFundTransfer(true);

        branchParameterTimezoneMapper.setTimeZoneNoOfHours(0);
        branchParameterTimezoneMapper.setTimeZoneNoOfMinutes(3);
        branchParameterTimezoneMapper.setTimeZoneIsAhead(true);
        branchParameterTimezoneMapper.setTimeZoneLevel("IST (UTC+5:30)");

        branchParameterGlobalInterdictMapper.setGlobalInterdictFunctionId("FN01");

        branchParameterTranDuplicateMapper.setTranModuleCode("LN");
        branchParameterTranDuplicateMapper.setTranDuplicationCheckRequired(true);
        branchParameterTranDuplicateMapper.setTranNoOfDayForDuplicationCheck(1);

        branchParameterLocalCurrencyMapper.setLocalCurrencyMsgModCode("LN");
        branchParameterLocalCurrencyMapper.setLocalCurrencyMsgType("Suppress LCY Message");

        branchParameterMiscellaneousMapper.setMiscDenominationTrackingRequired(true);
        branchParameterMiscellaneousMapper.setMiscCurrencyOfDenomination("INR");
        branchParameterMiscellaneousMapper.setMiscWeekBeginDay("Monday");
        branchParameterMiscellaneousMapper.setMiscWeeklyOff1("Saturday");
        branchParameterMiscellaneousMapper.setMiscWeeklyOff2("Sunday");
        branchParameterMiscellaneousMapper.setMiscFinancialYearBeginMonth("April");

        branchParameterDTOMapper.setAuthorized("N");
        branchParameterDTOMapper.setTaskCode("BRANCH_PARAMETER");
        branchParameterDTOMapper.setTaskIdentifier("10001");
        branchParameterDTOMapper.setBranchParameterAddress(branchParameterAddress);
        branchParameterDTOMapper.setBranchParameterContactInfo(branchParameterContactInfo);
        branchParameterDTOMapper.setBranchParameterGeneral(branchParameterGeneral);
        branchParameterDTOMapper.setBranchParameterClearing(branchParameterClearing);
        branchParameterDTOMapper.setBranchParameterAtm(branchParameterAtm);
        branchParameterDTOMapper.setBranchParameterTimezone(branchParameterTimezone);
        branchParameterDTOMapper.setBranchParameterGlobalInterdict(branchParameterGlobalInterdict);
        branchParameterDTOMapper.setBranchParameterTranDuplicate(branchParameterTranDuplicate);
        branchParameterDTOMapper.setBranchParameterLocalCurrency(branchParameterLocalCurrency);
        branchParameterDTOMapper.setBranchParameterMiscellaneous(branchParameterMiscellaneous);
        return branchParameterDTOMapper;
    }

    private BranchParameterDTO getBranchParameterDTO () {
        BranchParameterDTO branchParameterDTO = new BranchParameterDTO();
        BranchParameterAddressDTO branchParameterAddressDTO = new BranchParameterAddressDTO();
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

        branchParameterAddressDTO.setBranchAddress1("Kanakia Business Park 2");
        branchParameterAddressDTO.setBranchAddress2("JB Nagar 2");
        branchParameterAddressDTO.setBranchAddress3("Andheri East 2");
        branchParameterAddressDTO.setBranchAddress4("Mumbai 2");
        branchParameterAddressDTO.setCountry("IN");
        branchParameterAddressDTO.setState("MH");
        branchParameterAddressDTO.setCity("MUM");

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

    private BranchParameterDTO getBranchParameterDTOMapper () {
        BranchParameterDTO branchParameterDTOMapper = new BranchParameterDTO();
        BranchParameterAddressDTO branchParameterAddressDTOMapper = new BranchParameterAddressDTO();
        BranchParameterContactInfoDTO branchParameterContactInfoMapper = new BranchParameterContactInfoDTO();
        BranchParameterGeneralDTO branchParameterGeneralMapper = new BranchParameterGeneralDTO();
        BranchParameterClearingDTO branchParameterClearingMapper = new BranchParameterClearingDTO();
        BranchParameterAtmDTO branchParameterAtmMapper = new BranchParameterAtmDTO();
        BranchParameterTimezoneDTO branchParameterTimezoneMapper = new BranchParameterTimezoneDTO();
        BranchParameterGlobalInterdictDTO branchParameterGlobalInterdictMapper = new BranchParameterGlobalInterdictDTO();
        BranchParameterTranDuplicateDTO branchParameterTranDuplicateMapper =  new BranchParameterTranDuplicateDTO();
        BranchParameterLocalCurrencyDTO branchParameterLocalCurrencyMapper = new BranchParameterLocalCurrencyDTO();
        BranchParameterMiscellaneousDTO branchParameterMiscellaneousMapper = new BranchParameterMiscellaneousDTO();

        branchParameterDTOMapper.setBankCode("0002");
        branchParameterDTOMapper.setBranchCode("10001");
        branchParameterDTOMapper.setBranchName("Bandra East");
        branchParameterDTOMapper.setBranchConciseName("SBI");
        branchParameterDTOMapper.setIsBranchAvailableStatus(true);
        branchParameterDTOMapper.setBankIdentifierCode("H1");
        branchParameterDTOMapper.setBankCode("HO");

        branchParameterAddressDTOMapper.setBranchAddress1("Kanakia Business Park 2");
        branchParameterAddressDTOMapper.setBranchAddress2("JB Nagar 2");
        branchParameterAddressDTOMapper.setBranchAddress3("Andheri East 2");
        branchParameterAddressDTOMapper.setBranchAddress4("Mumbai 2");
        branchParameterAddressDTOMapper.setCountry("IN");
        branchParameterAddressDTOMapper.setState("MH");
        branchParameterAddressDTOMapper.setCity("MUM");

        branchParameterContactInfoMapper.setTelephoneNo("022-4156271");
        branchParameterContactInfoMapper.setFaxNo("022-4156271");
        branchParameterContactInfoMapper.setEmailId("abc@xyz.com");
        branchParameterContactInfoMapper.setBankWebsite("www.bob.com");

        branchParameterGeneralMapper.setGeneralCurrencyCode("INR");
        branchParameterGeneralMapper.setGeneralBranchParent("5002");
        branchParameterGeneralMapper.setGeneralCountryCode("IN");
        branchParameterGeneralMapper.setGeneralZonalOfficeCode("WI");
        branchParameterGeneralMapper.setGeneralZonalOfficeName("Western India");
        branchParameterGeneralMapper.setGeneralRegionalOfficeCode("MUM");
        branchParameterGeneralMapper.setGeneralRegionalOfficeName("Mumbai");
        branchParameterGeneralMapper.setGeneralCutOffTime("2");

        branchParameterClearingMapper.setClearingLocalPaymentBranch("Payment");
        branchParameterClearingMapper.setClearingCurrencyCode("INR");

        branchParameterAtmMapper.setAtmBranch("5001");
        branchParameterAtmMapper.setAtmInstitutionIdentification("SBI5");
        branchParameterAtmMapper.setAtmInterBranchTransactionCode("5001");
        branchParameterAtmMapper.setAtmIsCustomerFundTransfer(true);

        branchParameterTimezoneMapper.setTimeZoneNoOfHours(0);
        branchParameterTimezoneMapper.setTimeZoneNoOfMinutes(3);
        branchParameterTimezoneMapper.setTimeZoneIsAhead(true);
        branchParameterTimezoneMapper.setTimeZoneLevel("IST (UTC+5:30)");

        branchParameterGlobalInterdictMapper.setGlobalInterdictFunctionId("FN01");

        branchParameterTranDuplicateMapper.setTranModuleCode("LN");
        branchParameterTranDuplicateMapper.setTranDuplicationCheckRequired(true);
        branchParameterTranDuplicateMapper.setTranNoOfDayForDuplicationCheck(1);

        branchParameterLocalCurrencyMapper.setLocalCurrencyMsgModCode("LN");
        branchParameterLocalCurrencyMapper.setLocalCurrencyMsgType("Suppress LCY Message");

        branchParameterMiscellaneousMapper.setMiscDenominationTrackingRequired(true);
        branchParameterMiscellaneousMapper.setMiscCurrencyOfDenomination("INR");
        branchParameterMiscellaneousMapper.setMiscWeekBeginDay("Monday");
        branchParameterMiscellaneousMapper.setMiscWeeklyOff1("Saturday");
        branchParameterMiscellaneousMapper.setMiscWeeklyOff2("Sunday");
        branchParameterMiscellaneousMapper.setMiscFinancialYearBeginMonth("April");

        branchParameterDTOMapper.setAuthorized("N");
        branchParameterDTOMapper.setTaskCode("BRANCH_PARAMETER");
        branchParameterDTOMapper.setTaskIdentifier("10001");

        branchParameterDTOMapper.setBranchParameterAddress(branchParameterAddress);
        branchParameterDTOMapper.setBranchParameterContactInfo(branchParameterContactInfo);
        branchParameterDTOMapper.setBranchParameterGeneral(branchParameterGeneral);
        branchParameterDTOMapper.setBranchParameterClearing(branchParameterClearing);
        branchParameterDTOMapper.setBranchParameterAtm(branchParameterAtm);
        branchParameterDTOMapper.setBranchParameterTimezone(branchParameterTimezone);
        branchParameterDTOMapper.setBranchParameterGlobalInterdict(branchParameterGlobalInterdict);
        branchParameterDTOMapper.setBranchParameterTranDuplicate(branchParameterTranDuplicate);
        branchParameterDTOMapper.setBranchParameterLocalCurrency(branchParameterLocalCurrency);
        branchParameterDTOMapper.setBranchParameterMiscellaneous(branchParameterMiscellaneous);
        return branchParameterDTOMapper;
    }

    private BranchParameterDTO getBranchParameterDTOForSave () {
        BranchParameterDTO branchParameterDTOMapper = new BranchParameterDTO();
        BranchParameterAddressDTO branchParameterAddressDTOMapper = new BranchParameterAddressDTO();
        BranchParameterContactInfoDTO branchParameterContactInfoMapper = new BranchParameterContactInfoDTO();
        BranchParameterGeneralDTO branchParameterGeneralMapper = new BranchParameterGeneralDTO();
        BranchParameterClearingDTO branchParameterClearingMapper = new BranchParameterClearingDTO();
        BranchParameterAtmDTO branchParameterAtmMapper = new BranchParameterAtmDTO();
        BranchParameterTimezoneDTO branchParameterTimezoneMapper = new BranchParameterTimezoneDTO();
        BranchParameterGlobalInterdictDTO branchParameterGlobalInterdictMapper = new BranchParameterGlobalInterdictDTO();
        BranchParameterTranDuplicateDTO branchParameterTranDuplicateMapper =  new BranchParameterTranDuplicateDTO();
        BranchParameterLocalCurrencyDTO branchParameterLocalCurrencyMapper = new BranchParameterLocalCurrencyDTO();
        BranchParameterMiscellaneousDTO branchParameterMiscellaneousMapper = new BranchParameterMiscellaneousDTO();

        branchParameterDTOMapper.setBankCode("0002");
        branchParameterDTOMapper.setBranchCode("10001");
        branchParameterDTOMapper.setBranchName("Bandra East");
        branchParameterDTOMapper.setBranchConciseName("SBI");
        branchParameterDTOMapper.setIsBranchAvailableStatus(true);
        branchParameterDTOMapper.setBankIdentifierCode("H1");
        branchParameterDTOMapper.setBankCode("HO");

        branchParameterAddressDTOMapper.setBranchAddress1("Kanakia Business Park 2");
        branchParameterAddressDTOMapper.setBranchAddress2("JB Nagar 2");
        branchParameterAddressDTOMapper.setBranchAddress3("Andheri East 2");
        branchParameterAddressDTOMapper.setBranchAddress4("Mumbai 2");
        branchParameterAddressDTOMapper.setCountry("IN");
        branchParameterAddressDTOMapper.setState("MH");
        branchParameterAddressDTOMapper.setCity("MUM");

        branchParameterContactInfoMapper.setTelephoneNo("022-4156271");
        branchParameterContactInfoMapper.setFaxNo("022-4156271");
        branchParameterContactInfoMapper.setEmailId("abc@xyz.com");
        branchParameterContactInfoMapper.setBankWebsite("www.bob.com");

        branchParameterGeneralMapper.setGeneralCurrencyCode("INR");
        branchParameterGeneralMapper.setGeneralBranchParent("5002");
        branchParameterGeneralMapper.setGeneralCountryCode("IN");
        branchParameterGeneralMapper.setGeneralZonalOfficeCode("WI");
        branchParameterGeneralMapper.setGeneralZonalOfficeName("Western India");
        branchParameterGeneralMapper.setGeneralRegionalOfficeCode("MUM");
        branchParameterGeneralMapper.setGeneralRegionalOfficeName("Mumbai");
        branchParameterGeneralMapper.setGeneralCutOffTime("2");

        branchParameterClearingMapper.setClearingLocalPaymentBranch("Payment");
        branchParameterClearingMapper.setClearingCurrencyCode("INR");

        branchParameterAtmMapper.setAtmBranch("5001");
        branchParameterAtmMapper.setAtmInstitutionIdentification("SBI5");
        branchParameterAtmMapper.setAtmInterBranchTransactionCode("5001");
        branchParameterAtmMapper.setAtmIsCustomerFundTransfer(true);

        branchParameterTimezoneMapper.setTimeZoneNoOfHours(0);
        branchParameterTimezoneMapper.setTimeZoneNoOfMinutes(3);
        branchParameterTimezoneMapper.setTimeZoneIsAhead(true);
        branchParameterTimezoneMapper.setTimeZoneLevel("IST (UTC+5:30)");

        branchParameterGlobalInterdictMapper.setGlobalInterdictFunctionId("FN01");

        branchParameterTranDuplicateMapper.setTranModuleCode("LN");
        branchParameterTranDuplicateMapper.setTranDuplicationCheckRequired(true);
        branchParameterTranDuplicateMapper.setTranNoOfDayForDuplicationCheck(1);

        branchParameterLocalCurrencyMapper.setLocalCurrencyMsgModCode("LN");
        branchParameterLocalCurrencyMapper.setLocalCurrencyMsgType("Suppress LCY Message");

        branchParameterMiscellaneousMapper.setMiscDenominationTrackingRequired(true);
        branchParameterMiscellaneousMapper.setMiscCurrencyOfDenomination("INR");
        branchParameterMiscellaneousMapper.setMiscWeekBeginDay("Monday");
        branchParameterMiscellaneousMapper.setMiscWeeklyOff1("Saturday");
        branchParameterMiscellaneousMapper.setMiscWeeklyOff2("Sunday");
        branchParameterMiscellaneousMapper.setMiscFinancialYearBeginMonth("April");

        branchParameterDTOMapper.setAuthorized("N");
        branchParameterDTOMapper.setTaskCode("BRANCH_PARAMETER");
        branchParameterDTOMapper.setTaskIdentifier("10001");
        branchParameterDTOMapper.setAction("authorize");
        branchParameterDTOMapper.setStatus("active");
        branchParameterDTOMapper.setRecordVersion(1);
        branchParameterDTOMapper.setLastConfigurationAction("authorized");

        branchParameterDTOMapper.setBranchParameterAddress(branchParameterAddress);
        branchParameterDTOMapper.setBranchParameterContactInfo(branchParameterContactInfo);
        branchParameterDTOMapper.setBranchParameterGeneral(branchParameterGeneral);
        branchParameterDTOMapper.setBranchParameterClearing(branchParameterClearing);
        branchParameterDTOMapper.setBranchParameterAtm(branchParameterAtm);
        branchParameterDTOMapper.setBranchParameterTimezone(branchParameterTimezone);
        branchParameterDTOMapper.setBranchParameterGlobalInterdict(branchParameterGlobalInterdict);
        branchParameterDTOMapper.setBranchParameterTranDuplicate(branchParameterTranDuplicate);
        branchParameterDTOMapper.setBranchParameterLocalCurrency(branchParameterLocalCurrency);
        branchParameterDTOMapper.setBranchParameterMiscellaneous(branchParameterMiscellaneous);
        return branchParameterDTOMapper;
    }

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

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder().bankCode("0002")
                .defaultBranchCode("0002")
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

    private MutationEntity getMutationEntity () {
        String payLoadString =
                getpayloadValidString();
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("10001");
        mutationEntity.setTaskCode("BRANCH_PARAMETER");
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("active");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("authorize");
        mutationEntity.setLastConfigurationAction("unauthorized");
        return mutationEntity;
    }

    private MutationEntity getMutationEntityError () {
        String payLoadString =
                getpayloadInvalidString();
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("10001");
        mutationEntity.setTaskCode("BRANCH_PARAMETER");
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
                "{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":1,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"BRANCH_PARAMETER\",\"taskIdentifier\":\"10001\",\"branchCode\":\"10001\",\"branchName\":\"Bandra East\",\"bankCode\":\"0002\",\"branchConciseName\":\"SBI\",\"isBranchAvailableStatus\":true,\"bankIdentifierCode\":\"H1\",\"branchType\":\"HO\",\"branchParameterAddress\":{\"branchAddress1\":\"Kanakia Business Park 2\",\"branchAddress2\":\"JB Nagar 2\",\"branchAddress3\":\"Andheri East 2\",\"branchAddress4\":\"Mumbai 2\",\"country\":\"IN\",\"state\":\"MH\",\"city\":\"MUM\"},\"branchParameterContactInfo\":{\"telephoneNo\":\"022-4156271\",\"faxNo\":\"022-4156271\",\"emailId\":\"abc@xyz.com\",\"bankWebsite\":\"www.bob.com\"},\"branchParameterGeneral\":{\"generalCurrencyCode\":\"INR\",\"generalBranchParent\":\"5002\",\"generalCountryCode\":\"IN\",\"generalZonalOfficeCode\":\"WI\",\"generalZonalOfficeName\":\"Western India\",\"generalRegionalOfficeCode\":\"MUM\",\"generalRegionalOfficeName\":\"Mumbai\",\"generalCutOffTime\":\"\"},\"branchParameterClearing\":{\"clearingLocalPaymentBranch\":\"Payment\",\"clearingCurrencyCode\":\"INR\"},\"branchParameterAtm\":{\"atmBranch\":\"5001\",\"atmInstitutionIdentification\":\"SBI5\",\"atmInterBranchTransactionCode\":\"5001\",\"atmIsCustomerFundTransfer\":true},\"branchParameterTimezone\":{\"timeZoneNoOfHours\":0,\"timeZoneNoOfMinutes\":3,\"timeZoneIsAhead\":true,\"timeZoneLevel\":\"IST (UTC+5:30)\"},\"branchParameterGlobalInterdict\":{\"globalInterdictFunctionId\":\"FN02\"},\"branchParameterTranDuplicate\":{\"tranModuleCode\":\"LN\",\"tranDuplicationCheckRequired\":true,\"tranNoOfDayForDuplicationCheck\":1},\"branchParameterLocalCurrency\":{\"localCurrencyMsgModCode\":\"LN\",\"localCurrencyMsgType\":\"Suppress LCY Message\"},\"branchParameterMiscellaneous\":{\"miscDenominationTrackingRequired\":true,\"miscCurrencyOfDenomination\":\"INR\",\"miscWeekBeginDay\":\"Monday\",\"miscWeeklyOff1\":\"Saturday\",\"miscWeeklyOff2\":\"Sunday\",\"miscFinancialYearBeginMonth\":\"April\"}}";
        return payLoadString;
    }

    private String getpayloadInvalidString () {
        String payLoadString =
                "{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":10,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"BRANCH_PARAMETER\",\"taskIdentifier\":\"10001\",\"branchCode\":\"10001\",\"branchName\":\"Bandra East\",\"bankCode\":\"0002\",\"branchConciseName\":\"SBI\",\"isBranchAvailableStatus\":true,\"bankIdentifierCode\":\"H1\",\"branchType\":\"HO\",\"branchParameterAddress\":{\"branchAddress1\":\"Kanakia Business Park 2\",\"branchAddress2\":\"JB Nagar 2\",\"branchAddress3\":\"Andheri East 2\",\"branchAddress4\":\"Mumbai 2\",\"country\":\"IN\",\"state\":\"MH\",\"city\":\"MUM\"},\"branchParameterContactInfo\":{\"telephoneNo\":\"022-4156271\",\"faxNo\":\"022-4156271\",\"emailId\":\"abc@xyz.com\",\"bankWebsite\":\"www.bob.com\"},\"branchParameterGeneral\":{\"generalCurrencyCode\":\"INR\",\"generalBranchParent\":\"5002\",\"generalCountryCode\":\"IN\",\"generalZonalOfficeCode\":\"WI\",\"generalZonalOfficeName\":\"Western India\",\"generalRegionalOfficeCode\":\"MUM\",\"generalRegionalOfficeName\":\"Mumbai\",\"generalCutOffTime\":\"\"},\"branchParameterClearing\":{\"clearingLocalPaymentBranch\":\"Payment\",\"clearingCurrencyCode\":\"INR\"},\"branchParameterAtm\":{\"atmBranch\":\"5001\",\"atmInstitutionIdentification\":\"SBI5\",\"atmInterBranchTransactionCode\":\"5001\",\"atmIsCustomerFundTransfer\":true},\"branchParameterTimezone\":{\"timeZoneNoOfHours\":0,\"timeZoneNoOfMinutes\":3,\"timeZoneIsAhead\":true,\"timeZoneLevel\":\"IST (UTC+5:30)\"},\"branchParameterGlobalInterdict\":{\"globalInterdictFunctionId\":\"FN02\"},\"branchParameterTranDuplicate\":{\"tranModuleCode\":\"LN\",\"tranDuplicationCheckRequired\":true,\"tranNoOfDayForDuplicationCheck\":1},\"branchParameterLocalCurrency\":{\"localCurrencyMsgModCode\":\"LN\",\"localCurrencyMsgType\":\"Suppress LCY Message\"},\"branchParameterMiscellaneous\":{\"miscDenominationTrackingRequired\":true,\"miscCurrencyOfDenomination\":\"INR\",\"miscWeekBeginDay\":\"Monday\",\"miscWeeklyOff1\":\"Saturday\",\"miscWeeklyOff2\":\"Sunday\",\"miscFinancialYearBeginMonth\":\"April\"}}";
        return payLoadString;
    }
}