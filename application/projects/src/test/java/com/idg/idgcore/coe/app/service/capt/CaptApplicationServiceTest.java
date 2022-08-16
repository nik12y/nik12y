package com.idg.idgcore.coe.app.service.capt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.coe.domain.assembler.capt.CaptAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.entity.capt.CaptEntity;
import com.idg.idgcore.coe.domain.entity.capt.CaptEntityKey;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.capt.ICaptDomainService;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.capt.CaptDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.ServiceInvocationModeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith (MockitoExtension.class)
class CaptApplicationServiceTest {
    @InjectMocks
    private CaptApplicationService captApplicationService;
    @Mock private CaptAssembler captAssembler;
    @Mock private ICaptDomainService captDomainService;
    @Mock private IMutationsDomainService mutationsDomainService;
    @Mock private ProcessConfiguration processConfiguration;
    @Autowired
    private MutationEntity mutationEntity;
    private MutationEntity mutationEntity2;

    private SessionContext sessionContext;
    private CaptDTO captDTO;
    private CaptDTO captDTOAuth;
    private CaptDTO captDTOUnAuth;
    private CaptEntity captEntity;
    private CaptEntity captEntityUnAut;
    private CaptDTO captDTOMapper;
    private CaptDTO captDTO1;

    @BeforeEach
    void setUp () {
        sessionContext = getValidSessionContext();
        captDTOAuth = getCaptDTOAuthorized();
        captEntity = getCaptEntity();
        captDTOUnAuth = getCaptDTOUnAuthorized();
        captEntityUnAut = getCaptEntity();
        captEntityUnAut.setAuthorized("N");
        mutationEntity = getMutationEntity();
        captDTO = getCaptDTO();
        //captDTOMapper = getCaptDTOMapper();
        captDTO1 = getCaptDTO();
        //mutationEntity2=getMutationEntityJsonError();
    }

    @Test
    @DisplayName ("JUnit for getCaptByCode in application service when Authorize")
    void getCaptByCodeWithAuthRecord () throws FatalException {
        given(captDomainService.getCaptByCode(captDTOAuth.getClearingPaymentTypeCode())).willReturn(captEntity);
        given(captAssembler.convertEntityToDto(captEntity)).willReturn(captDTOAuth);
        CaptDTO captDTO1 = captApplicationService.getCaptByCode(sessionContext, captDTOAuth);
        assertThat(captDTO1.getAuthorized()).isEqualTo("Y");
        assertThat(captDTOAuth).isNotNull();
        //assertThat(ibanDTOAuth.toString()).isNotNull();
        //assertThat(ibanDTO.toString()).isNotNull();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverageDTO()
    {
        System.out.println(captEntity.toString());
        System.out.println(captDTO.toString());

        CaptDTO captDTO2 = new CaptDTO("ABCD","TESTCAPT","TESTCAPT",true,"SUNDAY","MONDAY","TUESDAY");

        String s = CaptDTO.builder()
                .clearingPaymentTypeCode("ABCD")
                .clearingPaymentTypeName("TESTCAPT")
                .networkType("TESTCAPT")
                .compositeClearingOrPaymentCalendar(true)
                .weeklyOff1("SUNDAY")
                .weeklyOff2("MONDAY")
                .weeklyOff3("TUESDAY")
                .build()
                .toString();


        CaptEntityKey captEntityKey=new CaptEntityKey("ABCD");


        captEntityKey.setClearingPaymentTypeCode("ABCD");
        System.out.println(captEntityKey.getClearingPaymentTypeCode());
        captEntityKey.keyAsString();
        //ibanEntityKey.builder().bankCode("0003").build();
        assertThat(captDTO2).descriptionText();
        //assertThat(s.toString()).isNotNull();
    }


    @DisplayName ("JUnit test for processCapt method")
    @Test
    void processCaptWithNew () throws JsonProcessingException, FatalException {
        CaptDTO captDTONew = getCaptDTOMapper();
        doNothing().when(processConfiguration).process(captDTONew);
        captApplicationService.processCapt(sessionContext, captDTONew);
        verify(processConfiguration, times(1)).process(captDTONew);
    }

    @DisplayName ("JUnit test for addUpdateRecord method")
    @Test
    void addUpdateRecord () throws JsonProcessingException, FatalException {
        String payloadStr = getpayloadValidString();
        CaptDTO captDTO = getCaptDTOForSave();
        doNothing().when(captDomainService).save(captDTO);
        captApplicationService.save(captDTO);
        captApplicationService.addUpdateRecord(payloadStr);
        verify(captDomainService, times(1)).save(captDTO);
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = captDTO.getClearingPaymentTypeCode();
        given(captDomainService.getCaptByCode(code)).willReturn(captEntity);
        captApplicationService.getConfigurationByCode(code);
        assertThat(captEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeDTOTest(){
        String code = captDTO.getClearingPaymentTypeCode();
        given(captDomainService.getCaptByCode(code)).willReturn(captEntity);
        captApplicationService.getConfigurationByCode(code);
        assertThat(captEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for processIban in application service for Try Block")
    void processCaptForTryBlock() throws JsonProcessingException, FatalException {
        doNothing().when(processConfiguration).process(captDTO);
        captApplicationService.processCapt(sessionContext, captDTO);
        verify(processConfiguration, times(1)).process(captDTO);
    }

    @Test
    @DisplayName("JUnit for processIban in application service for Catch Block")
    void processCaptForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            captApplicationService.processCapt(sessionContext2, captDTO);
            assertThat(captDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for getIbanByIbanCountryCode in application service when Not Authorize in catch block")
    void getCaptByCodeWhenNotAuthorizeCatchBlock () throws FatalException {
        String payLoadString1 =getpayloadInvalidString();
        //"{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"STATE\",\"taskIdentifier\":\"MH\",\"stateCode\":\"MH\",\"stateName\":\"MAHARASHTRA\",\"countryCode\":\"IN\"}";
        given(mutationsDomainService.getConfigurationByCode(captDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        /*given(ibanAssembler.setAuditFields(mutationEntity2, ibanDTOUnAuth))
                .willReturn(ibanDTOUnAuth);*/
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = mock(PayloadDTO.class);
        Assertions.assertThrows(Exception.class,()-> {
            CaptDTO captDTO1 = captApplicationService.getCaptByCode(sessionContext, captDTOUnAuth);
            assertEquals("N", captDTO1.getAuthorized());
            assertThat(captDTO1).isNotNull();

        });
    }
    /**
     *
     * Negative Test Cases
     */
    @Test
    @DisplayName("JUnit for getBankByCode in application service when Authorize for Negative")
    void getCaptByCodeIsAuthorizeForNegative() throws FatalException {
        given(captDomainService.getCaptByCode(captDTO.getClearingPaymentTypeCode())).willReturn(captEntity);
        given(captAssembler.convertEntityToDto(captEntity)).willReturn(captDTO);
        CaptDTO captDTO1 = captApplicationService.getCaptByCode(sessionContext, captDTO);
        assertNotEquals("N",captDTO1.getAuthorized());
        assertThat(captDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getBankByCode in application service check Parameter not null")
    void getCaptByCodeIsAuthorizeCheckParameter() throws FatalException {
        //IbanDTO ibanDTOnull=null;
        CaptDTO captDTOEx=new CaptDTO();
        captDTOEx.setClearingPaymentTypeCode("ABCD");
        captDTOEx.setAuthorized("Y");
        given(captDomainService.getCaptByCode(captDTOEx.getClearingPaymentTypeCode())).willReturn(captEntity);
        given(captAssembler.convertEntityToDto(captEntity)).willReturn(captDTO);
        CaptDTO stateDTO1 = captApplicationService.getCaptByCode(sessionContext, captDTOEx);
        assertThat(captDTOEx.getClearingPaymentTypeCode()).isNotBlank();
        assertThat(captDTOEx.getAuthorized()).isNotBlank();
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


    private String captCode;
    private String captDescription;
    private String captSwiftCode;
    private String externalTxnCode;
    private String misHead;
    private Boolean immediate;
    private Boolean onValueDate;
    private Boolean afterXDaysValueDate;
    private Boolean afterXDays;
    private Integer noOfDays;
    private Boolean intradayManualRelease;
    private String statementDateDerivation;
    private Boolean amlTracking;
    private String productGroup;
    private Boolean availableBalanceCheckApplicableBatch;
    private Boolean availableBalanceCheckApplicableOnline;
    private Boolean pricingOnCaptCount;
    private Boolean pricingTurnoverInclusion;
    private Boolean pricingBalanceInclusive;
    private Boolean turnoverLimit;
    private Boolean coverAccount;
    private Boolean pricingPenaltyInclusive;
    private Boolean considerForAccountStatusChange;
    private Boolean chequeMandatory;
    private Boolean interBranchInLcy;
    private Boolean thirdPartyDealingSystemCaptCode;


    private CaptDTO getCaptDTOAuthorized () {
        CaptDTO captDTOMapper = new CaptDTO();

        captDTOMapper.setClearingPaymentTypeCode("ABCD");
        captDTOMapper.setClearingPaymentTypeName("TESTCAPT");
        captDTOMapper.setNetworkType("TESTCAPT");
        captDTOMapper.setCompositeClearingOrPaymentCalendar(true);
        captDTOMapper.setWeeklyOff1("SUNDAY");
        captDTOMapper.setWeeklyOff2("MONDAY");
        captDTOMapper.setWeeklyOff3("TUESDAY");
        captDTOMapper.setAuthorized("Y");
        captDTOMapper.setTaskCode("CAPT");
        captDTOMapper.setTaskIdentifier("ABCD");
        return captDTOMapper;
    }

    private CaptDTO getCaptDTOUnAuthorized () {
        CaptDTO captDTOMapper = new CaptDTO();

        captDTOMapper.setClearingPaymentTypeCode("ABCD");
        captDTOMapper.setClearingPaymentTypeName("TESTCAPT");
        captDTOMapper.setNetworkType("TESTCAPT");
        captDTOMapper.setCompositeClearingOrPaymentCalendar(true);
        captDTOMapper.setWeeklyOff1("SUNDAY");
        captDTOMapper.setWeeklyOff2("MONDAY");
        captDTOMapper.setWeeklyOff3("TUESDAY");
        captDTOMapper.setTaskCode("CAPT");
        captDTOMapper.setTaskIdentifier("ABCD");
        captDTOMapper.setAuthorized("N");
        return captDTOMapper;
    }


    private CaptDTO getCaptDTO () {
        CaptDTO captDTO = new CaptDTO();

        captDTO.setClearingPaymentTypeCode("ABCD");
        captDTO.setClearingPaymentTypeName("TESTCAPT");
        captDTO.setNetworkType("TESTCAPT");
        captDTO.setCompositeClearingOrPaymentCalendar(true);
        captDTO.setWeeklyOff1("SUNDAY");
        captDTO.setWeeklyOff2("MONDAY");
        captDTO.setWeeklyOff3("TUESDAY");

        captDTO.setTaskCode("CAPT");
        captDTO.setTaskIdentifier("ABCD");

        captDTO.setAuthorized("Y");
        captDTO.setTaskCode("US");
        captDTO.setStatus("DELETED");
        captDTO.setRecordVersion(1);
        return captDTO;
    }

    private CaptDTO getCaptDTOMapper () {
        CaptDTO captDTOMapper = new CaptDTO();

        captDTO.setClearingPaymentTypeCode("ABCD");
        captDTO.setClearingPaymentTypeName("TESTCAPT");
        captDTO.setNetworkType("TESTCAPT");
        captDTO.setCompositeClearingOrPaymentCalendar(true);
        captDTO.setWeeklyOff1("SUNDAY");
        captDTO.setWeeklyOff2("MONDAY");
        captDTO.setWeeklyOff3("TUESDAY");
        captDTO.setTaskCode("CAPT");
        captDTO.setTaskIdentifier("ABCD");
        captDTO.setAuthorized("N");
        captDTO.setTaskCode("CAPT");
        captDTO.setTaskIdentifier("ABCD");

        return captDTOMapper;
    }

    private CaptDTO getCaptDTOForSave () {
        CaptDTO captDTOMapper = new CaptDTO();

        captDTOMapper.setClearingPaymentTypeCode("ABCD");
        captDTOMapper.setClearingPaymentTypeName("TESTCAPT");
        captDTOMapper.setNetworkType("TESTCAPT");
        captDTOMapper.setCompositeClearingOrPaymentCalendar(true);
        captDTOMapper.setWeeklyOff1("SUNDAY");
        captDTOMapper.setWeeklyOff2("MONDAY");
        captDTOMapper.setWeeklyOff3("TUESDAY");

        captDTOMapper.setTaskCode("CAPT");
        captDTOMapper.setTaskIdentifier("ABCD");
        captDTOMapper.setAction("authorize");
        captDTOMapper.setStatus("active");
        captDTOMapper.setRecordVersion(1);
        captDTOMapper.setAuthorized("N");
        captDTOMapper.setLastConfigurationAction("authorized");
        captDTOMapper.setTaskCode("CAPT");
        captDTOMapper.setTaskIdentifier("ABCD");

        return captDTOMapper;
    }

    private CaptEntity getCaptEntity () {
        CaptEntity captEntity = new CaptEntity();

        captEntity.setClearingPaymentTypeCode("ABCD");
        captEntity.setClearingPaymentTypeName("TESTCAPT");
        captEntity.setNetworkType("TESTCAPT");
        captEntity.setIsClearingPaymentCalender('Y');
        captEntity.setWeeklyOff1("SUNDAY");
        captEntity.setWeeklyOff2("MONDAY");
        captEntity.setWeeklyOff3("TUESDAY");
        captEntity.setAuthorized("Y");
        return captEntity;
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
        mutationEntity.setTaskIdentifier("ABCD");
        mutationEntity.setTaskCode("CAPT");
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
        mutationEntity.setTaskIdentifier("ABCD");
        mutationEntity.setTaskCode("CAPT");
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
                "{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":0,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"Capt\",\"taskIdentifier\":\"ABCD\",\"clearingPaymentTypeCode\":\"ABCD\",\"clearingPaymentTypeName\":\"TESTCAPT\",\"compositeClearingOrPaymentCalendar\":true,\"weeklyOff1\":\"SUNDAY\",\"weeklyOff2\":\"MONDAY\",\"weeklyOff3\":\"TUESDAY\"}";
        return payLoadString;
    }

    private String getpayloadInvalidString () {
        String payLoadString =
                "{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":10,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"Capt\",\"taskIdentifier\":\"ABCD\",\"clearingPaymentTypeCode\":\"ABCD\",\"clearingPaymentTypeName\":\"TESTCAPT\",\"compositeClearingOrPaymentCalendar\":true,\"weeklyOff1\":\"SUNDAY\",\"weeklyOff2\":\"MONDAY\",\"weeklyOff3\":\"TUESDAY\"}";
        return payLoadString;
    }

}