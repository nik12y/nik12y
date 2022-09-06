package com.idg.idgcore.coe.app.service.iban;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.idg.idgcore.coe.domain.assembler.iban.*;
import com.idg.idgcore.coe.domain.entity.iban.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.domain.process.*;
import com.idg.idgcore.coe.domain.service.iban.*;
import com.idg.idgcore.coe.domain.service.mutation.*;
import com.idg.idgcore.coe.dto.iban.*;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith (MockitoExtension.class)
class IbanApplicationServiceTest {
    @InjectMocks
    private IbanApplicationService ibanApplicationService;
    @Mock private IbanAssembler ibanAssembler;
    @Mock private IIbanDomainService ibanDomainService;
    @Mock private IMutationsDomainService mutationsDomainService;
    @Mock private ProcessConfiguration processConfiguration;
    @Autowired
    private MutationEntity mutationEntity;
    private MutationEntity mutationEntity2;

    private SessionContext sessionContext;
    private IbanDTO ibanDTO;
    private IbanDTO ibanDTOAuth;
    private IbanDTO ibanDTOUnAuth;
    private IbanEntity ibanEntity;
    private IbanEntity ibanEntityUnAut;
    private IbanDTO ibanDTOMapper;
    private IbanDTO ibanDTO1;

    @BeforeEach
    void setUp () {
        sessionContext = getValidSessionContext();
        ibanDTOAuth = getIbanDTOAuthorized();
        ibanEntity = getIbanEntity();
        ibanDTOUnAuth = getIbanDTOUnAuthorized();
        ibanDTOUnAuth.setAuthorized("N");
        ibanEntityUnAut = getIbanEntity();
        ibanEntityUnAut.setAuthorized("N");
        mutationEntity = getMutationEntity();
        ibanDTO = getIbanDTO();
        //ibanDTOMapper = getIbanDTOMapper();
        ibanDTO1=getIbanDTO();
        //mutationEntity2=getMutationEntityJsonError();
    }

    @Test
    @DisplayName ("JUnit for getIbanByIbanCountryCode in application service when Authorize")
    void getIbanByIbanCountryCodeWithAuthRecord () throws FatalException {
        given(ibanDomainService.getIbanByIbanCountryCode(
                ibanDTOAuth.getIbanCountryCode())).willReturn(ibanEntity);
        given(ibanAssembler.convertEntityToDto(ibanEntity)).willReturn(
                ibanDTOAuth);
        IbanDTO ibanDTO1 = ibanApplicationService.getIbanByIbanCountryCode(
                sessionContext, ibanDTOAuth);
        assertThat(ibanDTO1.getAuthorized()).isEqualTo("Y");
        assertThat(ibanDTOAuth).isNotNull();
        assertThat(ibanDTOAuth.toString()).isNotNull();
        assertThat(ibanDTO.toString()).isNotNull();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverageDTO()
    {
        System.out.println(ibanEntity.toString());
        System.out.println(ibanDTO.toString());

        IbanBbanDTO ibanBbanDTO = new IbanBbanDTO(1,1,1,0,5,4);
        IbanDTO ibanDTO2=new IbanDTO("US",1,1,1,1,0,20,ibanBbanDTO);

        String s = IbanDTO.builder().ibanCountryCode("US").ibanCountryPosition(1)
                .ibanCountryCodeLength(1).ibanCheckDigitPosition(1).ibanCheckDigitLength(1)
                .ibanNationalIdLength(0).ibanTotalLength(20).build()
                .toString();

        ibanDTO2.setIbanBban(ibanBbanDTO);
        IbanEntityKey ibanEntityKey=new IbanEntityKey("US");


        ibanEntityKey.setIbanCountryCode("US");
        System.out.println(ibanEntityKey.getIbanCountryCode());
        ibanEntityKey.keyAsString();
        //ibanEntityKey.builder().bankCode("0003").build();
        assertThat(ibanDTO2).descriptionText();
        assertThat(s.toString()).isNotNull();
    }


    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverageEntity()
    {
        IbanBbanEntity ibanBbanEntity = new IbanBbanEntity(1,1,1,0,5,4);
        IbanEntity ibanEntity;
        ibanEntity = new IbanEntity("US", 1, 1, 1, 1, 0,20,"","", "draft",0,"Y","draft",ibanBbanEntity);
        ibanEntity.setIbanBbanEntity(ibanBbanEntity);
        assertThat(ibanEntity).descriptionText();
    }

    @DisplayName ("JUnit test for processIban method")
    @Test
    void processIbanWithNew () throws JsonProcessingException, FatalException {
        IbanDTO ibanDTONew = getIbanDTOMapper();
        doNothing().when(processConfiguration).process(ibanDTONew);
        ibanApplicationService.processIban(sessionContext, ibanDTONew);
        verify(processConfiguration, times(1)).process(ibanDTONew);
    }

    @DisplayName ("JUnit test for addUpdateRecord method")
    @Test
    void addUpdateRecord () throws JsonProcessingException, FatalException {
        String payloadStr = getpayloadValidString();
        IbanDTO ibanDTO = getIbanDTOForSave();
        doNothing().when(ibanDomainService).save(ibanDTO);
        ibanApplicationService.save(ibanDTO);
        ibanApplicationService.addUpdateRecord(payloadStr);
        verify(ibanDomainService, times(1)).save(ibanDTO);
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = ibanDTO.getIbanCountryCode();
        given(ibanDomainService.getIbanByIbanCountryCode(code)).willReturn(ibanEntity);
        ibanApplicationService.getConfigurationByCode(code);
        assertThat(ibanEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeDTOTest(){
        String code = ibanDTO.getIbanCountryCode();
        given(ibanDomainService.getIbanByIbanCountryCode(code)).willReturn(ibanEntity);
        ibanApplicationService.getConfigurationByCode(code);
        assertThat(ibanEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for processIban in application service for Try Block")
    void processIbanForTryBlock() throws JsonProcessingException, FatalException {
        doNothing().when(processConfiguration).process(ibanDTO);
        ibanApplicationService.processIban(sessionContext, ibanDTO);
        verify(processConfiguration, times(1)).process(ibanDTO);
    }

    @Test
    @DisplayName("JUnit for processIban in application service for Catch Block")
    void processStateForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            ibanApplicationService.processIban(sessionContext2, ibanDTO);
            assertThat(ibanDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for getIbanByIbanCountryCode in application service when Not Authorize in catch block")
    void getIbanByIbanCountryCodeWhenNotAuthorizeCatchBlock () throws FatalException {
        String payLoadString1 =getpayloadInvalidString();
        //"{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"STATE\",\"taskIdentifier\":\"MH\",\"stateCode\":\"MH\",\"stateName\":\"MAHARASHTRA\",\"countryCode\":\"IN\"}";
        given(mutationsDomainService.getConfigurationByCode(ibanDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        /*given(ibanAssembler.setAuditFields(mutationEntity2, ibanDTOUnAuth))
                .willReturn(ibanDTOUnAuth);*/
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = mock(PayloadDTO.class);
        Assertions.assertThrows(Exception.class,()-> {
            IbanDTO ibanDTO1 = ibanApplicationService.getIbanByIbanCountryCode(sessionContext, ibanDTOUnAuth);
            assertEquals("N",ibanDTO1.getAuthorized());
            assertThat(ibanDTO1).isNotNull();

        });
    }
    /**
     *
     * Negative Test Cases
     */
    @Test
    @DisplayName("JUnit for getBankByCode in application service when Authorize for Negative")
    void getIbanBankByCodeIsAuthorizeForNegative() throws FatalException {
        given(ibanDomainService.getIbanByIbanCountryCode(ibanDTO.getIbanCountryCode())).willReturn(ibanEntity);
        given(ibanAssembler.convertEntityToDto(ibanEntity)).willReturn(ibanDTO);
        IbanDTO ibanDTO1 = ibanApplicationService.getIbanByIbanCountryCode(sessionContext, ibanDTO);
        assertNotEquals("N",ibanDTO1.getAuthorized());
        assertThat(ibanDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getBankByCode in application service check Parameter not null")
    void getIbanBankByCodeIsAuthorizeCheckParameter() throws FatalException {
        //IbanDTO ibanDTOnull=null;
        IbanDTO ibanDTOEx=new IbanDTO();
        ibanDTOEx.setIbanCountryCode("US");
        ibanDTOEx.setAuthorized("Y");
        given(ibanDomainService.getIbanByIbanCountryCode(ibanDTOEx.getIbanCountryCode())).willReturn(ibanEntity);
        given(ibanAssembler.convertEntityToDto(ibanEntity)).willReturn(ibanDTO);
        IbanDTO stateDTO1 = ibanApplicationService.getIbanByIbanCountryCode(sessionContext, ibanDTOEx);
        assertThat(ibanDTOEx.getIbanCountryCode()).isNotBlank();
        assertThat(ibanDTOEx.getAuthorized()).isNotBlank();
    }



    /*@Test
    void getIbanByIbanCountryCode () {
    }

    @Test
    void getIbans () {
    }

    @Test
    void processIban () {
    }

    @Test
    void addUpdateRecord () {
    }

    @Test
    void getConfigurationByCode () {
    }

    @Test
    void save () {
    }*/


    private String ibanCountryCode;
    private Integer ibanCountryPosition;
    private Integer ibanCountryCodeLength;
    private Integer ibanCheckDigitPosition;
    private Integer ibanCheckDigitLength;
    private String ibanNationalIdLength;
    private Integer ibanTotalLength;
    private IbanBbanDTO ibanBban;

    private Integer bankIdentifierPosition;
    private String bankIdentifierLength;
    private String branchIdentifierPosition;
    private String branchIdentifierLength;
    private Integer accountNumberPosition;
    private Integer accountNumberLength;


    private IbanDTO getIbanDTOAuthorized () {
        IbanDTO ibanDTOMapper = new IbanDTO();
        IbanBbanDTO ibanBbanDTOMapper = new IbanBbanDTO();

        ibanDTOMapper.setIbanCountryCode("US");
        ibanDTOMapper.setIbanCountryPosition(1);
        ibanDTOMapper.setIbanCountryCodeLength(1);
        ibanDTOMapper.setIbanCheckDigitPosition(1);
        ibanDTOMapper.setIbanCheckDigitLength(1);
        ibanDTOMapper.setIbanNationalIdLength(0);
        ibanDTOMapper.setIbanTotalLength(20);

        ibanBbanDTOMapper.setBankIdentifierPosition(1);
        ibanBbanDTOMapper.setBankIdentifierLength(1);
        ibanBbanDTOMapper.setBranchIdentifierPosition(1);
        ibanBbanDTOMapper.setBankIdentifierLength(0);
        ibanBbanDTOMapper.setAccountNumberPosition(5);
        ibanBbanDTOMapper.setAccountNumberLength(4);

        ibanDTOMapper.setAuthorized("Y");
        ibanDTOMapper.setTaskCode("IBAN");
        ibanDTOMapper.setTaskIdentifier("US");
        ibanDTOMapper.setIbanBban(ibanBbanDTOMapper);
        return ibanDTOMapper;
    }

    private IbanDTO getIbanDTOUnAuthorized () {
        IbanDTO ibanDTOMapper = new IbanDTO();
        IbanBbanDTO ibanBbanDTOMapper = new IbanBbanDTO();

        ibanDTOMapper.setIbanCountryCode("IN");
        ibanDTOMapper.setIbanCountryPosition(1);
        ibanDTOMapper.setIbanCountryCodeLength(1);
        ibanDTOMapper.setIbanCheckDigitPosition(1);
        ibanDTOMapper.setIbanCheckDigitLength(1);
        ibanDTOMapper.setIbanNationalIdLength(0);
        ibanDTOMapper.setIbanTotalLength(20);

        ibanBbanDTOMapper.setBankIdentifierPosition(1);
        ibanBbanDTOMapper.setBankIdentifierLength(1);
        ibanBbanDTOMapper.setBranchIdentifierPosition(1);
        ibanBbanDTOMapper.setBankIdentifierLength(0);
        ibanBbanDTOMapper.setAccountNumberPosition(5);
        ibanBbanDTOMapper.setAccountNumberLength(4);

        ibanDTOMapper.setAuthorized("N");
        ibanDTOMapper.setIbanBban(ibanBbanDTOMapper);
        return ibanDTOMapper;
    }


    private IbanDTO getIbanDTO () {
        IbanDTO ibanDTO = new IbanDTO();
        IbanBbanDTO ibanBbanDTO = new IbanBbanDTO();

        ibanDTO.setIbanCountryCode("US");
        ibanDTO.setIbanCountryPosition(1);
        ibanDTO.setIbanCountryCodeLength(1);
        ibanDTO.setIbanCheckDigitPosition(1);
        ibanDTO.setIbanCheckDigitLength(1);
        ibanDTO.setIbanNationalIdLength(0);
        ibanDTO.setIbanTotalLength(20);

        ibanBbanDTO.setBankIdentifierPosition(1);
        ibanBbanDTO.setBankIdentifierLength(1);
        ibanBbanDTO.setBranchIdentifierPosition(1);
        ibanBbanDTO.setBankIdentifierLength(0);
        ibanBbanDTO.setAccountNumberPosition(5);
        ibanBbanDTO.setAccountNumberLength(4);

        ibanDTO.setAuthorized("Y");
        ibanDTO.setTaskCode("US");
        ibanDTO.setStatus("DELETED");
        ibanDTO.setRecordVersion(1);
        ibanDTO.setIbanBban(ibanBbanDTO);
        return ibanDTO;
    }

    private IbanDTO getIbanDTOMapper () {
        IbanDTO ibanDTOMapper = new IbanDTO();
        IbanBbanDTO ibanBbanDTOMapper = new IbanBbanDTO();

        ibanDTO.setIbanCountryCode("US");
        ibanDTO.setIbanCountryPosition(1);
        ibanDTO.setIbanCountryCodeLength(1);
        ibanDTO.setIbanCheckDigitPosition(1);
        ibanDTO.setIbanCheckDigitLength(1);
        ibanDTO.setIbanNationalIdLength(0);
        ibanDTO.setIbanTotalLength(20);

        ibanBbanDTOMapper.setBankIdentifierPosition(1);
        ibanBbanDTOMapper.setBankIdentifierLength(1);
        ibanBbanDTOMapper.setBranchIdentifierPosition(1);
        ibanBbanDTOMapper.setBankIdentifierLength(0);
        ibanBbanDTOMapper.setAccountNumberPosition(5);
        ibanBbanDTOMapper.setAccountNumberLength(4);

        ibanDTOMapper.setAuthorized("N");
        ibanDTOMapper.setTaskCode("IBAN");
        ibanDTOMapper.setTaskIdentifier("US");
        ibanDTOMapper.setIbanBban(ibanBbanDTOMapper);

        return ibanDTOMapper;
    }

    private IbanDTO getIbanDTOForSave () {
        IbanDTO ibanDTOMapper = new IbanDTO();
        IbanBbanDTO ibanBbanDTOMapper = new IbanBbanDTO();

        ibanDTOMapper.setIbanCountryCode("US");
        ibanDTOMapper.setIbanCountryPosition(1);
        ibanDTOMapper.setIbanCountryCodeLength(1);
        ibanDTOMapper.setIbanCheckDigitPosition(1);
        ibanDTOMapper.setIbanCheckDigitLength(1);
        ibanDTOMapper.setIbanNationalIdLength(0);
        ibanDTOMapper.setIbanTotalLength(20);

        ibanBbanDTOMapper.setBankIdentifierPosition(1);
        ibanBbanDTOMapper.setBankIdentifierLength(1);
        ibanBbanDTOMapper.setBranchIdentifierPosition(1);
        ibanBbanDTOMapper.setBankIdentifierLength(0);
        ibanBbanDTOMapper.setAccountNumberPosition(5);
        ibanBbanDTOMapper.setAccountNumberLength(4);

        ibanDTOMapper.setTaskCode("IBAN");
        ibanDTOMapper.setTaskIdentifier("US");
        ibanDTOMapper.setAction("authorize");
        ibanDTOMapper.setStatus("active");
        ibanDTOMapper.setRecordVersion(1);
        ibanDTOMapper.setAuthorized("N");
        ibanDTOMapper.setLastConfigurationAction("authorized");
        ibanDTOMapper.setTaskCode("IBAN");
        ibanDTOMapper.setTaskIdentifier("US");
        ibanDTOMapper.setIbanBban(ibanBbanDTOMapper);
        return ibanDTOMapper;
    }

    private IbanEntity getIbanEntity () {
        IbanEntity ibanEntity = new IbanEntity();
        IbanBbanEntity ibanBbanEntity = new IbanBbanEntity();

        ibanEntity.setIbanCountryCode("US");
        ibanEntity.setIbanCountryPosition(1);
        ibanEntity.setIbanCountryCodeLength(1);
        ibanEntity.setIbanCheckDigitPosition(1);
        ibanEntity.setIbanCheckDigitLength(1);
        ibanEntity.setIbanNationalIdLength(0);
        ibanEntity.setIbanTotalLength(20);

        ibanBbanEntity.setBankIdentifierPosition(1);
        ibanBbanEntity.setBankIdentifierLength(1);
        ibanBbanEntity.setBranchIdentifierPosition(1);
        ibanBbanEntity.setBankIdentifierLength(0);
        ibanBbanEntity.setAccountNumberPosition(5);
        ibanBbanEntity.setAccountNumberLength(4);

        ibanEntity.setIbanBbanEntity(ibanBbanEntity);
        ibanEntity.setAuthorized("Y");
        return ibanEntity;
    }

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder().bankCode("0002").defaultBranchCode("0002")
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
        mutationEntity.setTaskIdentifier("US");
        mutationEntity.setTaskCode("IBAN");
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
        mutationEntity.setTaskIdentifier("US");
        mutationEntity.setTaskCode("IBAN");
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
                "{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":0,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"IBAN\",\"taskIdentifier\":\"US\",\"ibanCountryCode\":\"US\",\"ibanCountryPosition\":1,\"ibanCountryCodeLength\":1,\"ibanCheckDigitPosition\":1,\"ibanCheckDigitLength\":1,\"ibanNationalIdLength\":\"IBAN0001\",\"ibanTotalLength\":20,\"ibanBban\":{\"bankIdentifierPosition\":1,\"bankIdentifierLength\":\"IBAN000001\",\"branchIdentifierPosition\":\"AM\",\"branchIdentifierLength\":\"IB01\",\"accountNumberPosition\":5,\"accountNumberLength\":4}}";
        return payLoadString;
    }

    private String getpayloadInvalidString () {
        String payLoadString =
                "{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":10,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"IBAN\",\"taskIdentifier\":\"US\",\"ibanCountryCode\":\"US\",\"ibanCountryPosition\":1,\"ibanCountryCodeLength\":1,\"ibanCheckDigitPosition\":1,\"ibanCheckDigitLength\":1,\"ibanNationalIdLength\":\"IBAN0001\",\"ibanTotalLength\":20,\"ibanBban\":{\"bankIdentifierPosition\":1,\"bankIdentifierLength\":\"IBAN000001\",\"branchIdentifierPosition\":\"AM\",\"branchIdentifierLength\":\"IB01\",\"accountNumberPosition\":5,\"accountNumberLength\":4}}";
        return payLoadString;
    }

}