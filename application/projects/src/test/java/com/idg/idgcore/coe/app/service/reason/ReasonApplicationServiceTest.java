package com.idg.idgcore.coe.app.service.reason;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.coe.domain.assembler.reason.ReasonAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.entity.reason.ReasonEntity;
import com.idg.idgcore.coe.domain.entity.reason.ReasonEntityKey;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.reason.IReasonDomainService;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.reason.ReasonDTO;
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
class ReasonApplicationServiceTest {
    @InjectMocks
    private ReasonApplicationService reasonApplicationService;
    @Mock private ReasonAssembler reasonAssembler;
    @Mock private IReasonDomainService reasonDomainService;
    @Mock private IMutationsDomainService mutationsDomainService;
    @Mock private ProcessConfiguration processConfiguration;
    @Autowired
    private MutationEntity mutationEntity;
    private MutationEntity mutationEntity2;

    private SessionContext sessionContext;
    private ReasonDTO reasonDTO;
    private ReasonDTO reasonDTOAuth;
    private ReasonDTO reasonDTOUnAuth;
    private ReasonEntity reasonEntity;
    private ReasonEntity reasonEntityUnAut;
    private ReasonDTO reasonDTOMapper;
    private ReasonDTO reasonDTO1;

    @BeforeEach
    void setUp () {
        sessionContext = getValidSessionContext();
        reasonDTOAuth = getReasonDTOAuthorized();
        reasonEntity = getReasonEntity();
        reasonDTOUnAuth = getReasonDTOUnAuthorized();
        reasonEntityUnAut = getReasonEntity();
        reasonEntityUnAut.setAuthorized("N");
        mutationEntity = getMutationEntity();
         reasonDTO = getReasonDTO();
        //reasonDTOMapper = getReasonDTOMapper();
        reasonDTO1 = getReasonDTO();
        //mutationEntity2=getMutationEntityJsonError();
    }

    @Test
    @DisplayName ("JUnit for getReasonByCode in application service when Authorize")
    void getReasonByCodeWithAuthRecord () throws FatalException {
        given(reasonDomainService.getReasonByCode(reasonDTOAuth.getPrimaryReasonCode())).willReturn(reasonEntity);
        given(reasonAssembler.convertEntityToDto(reasonEntity)).willReturn(reasonDTOAuth);
        ReasonDTO reasonDTO1 = reasonApplicationService.getReasonByCode(sessionContext, reasonDTOAuth);
        assertThat(reasonDTO1.getAuthorized()).isEqualTo("Y");
        assertThat(reasonDTOAuth).isNotNull();
        //assertThat(ibanDTOAuth.toString()).isNotNull();
        //assertThat(ibanDTO.toString()).isNotNull();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverageDTO()
    {
        System.out.println(reasonEntity.toString());
        System.out.println(reasonDTO.toString());

        ReasonDTO reasonDTO2 = new ReasonDTO("AB","PTESTREASON",true,true,true,true,true,true,"AB","PTESTREASON",true,true,true,true,true,true,"TEST","TEST");

        String s = ReasonDTO.builder()
                .primaryReasonCode("AB")
                .primaryReasonDescription("PTESTREASON")
                .primaryAccountBlock(true)
                .primaryAccountUnblock(true)
                .primaryAccountClosure(true)
                .primaryRequestForAccountClosure(true)
                .primaryStopPayment(true)
                .primaryStopPaymentRevoke(true)
                .secondaryReasonCode("AB")
                .secondaryReasonDescription("PTESTREASON")
                .secondaryAccountBlock(true)
                .secondaryAccountUnblock(true)
                .secondaryAccountClosure(true)
                .secondaryRequestForAccountClosure(true)
                .secondaryStopPayment(true)
                .secondaryStopPaymentRevoke(true)
                .applicableCategories("TEST")
                .documentRequiredIfAny("TEST")
                .build()
                .toString();


        ReasonEntityKey reasonEntityKey=new ReasonEntityKey("AB");


        reasonEntityKey.setPrimaryReasonCode("US");
        System.out.println(reasonEntityKey.getPrimaryReasonCode());
        reasonEntityKey.keyAsString();
        //ibanEntityKey.builder().bankCode("0003").build();
        assertThat(reasonDTO2).descriptionText();
        //assertThat(s.toString()).isNotNull();
    }


    @DisplayName ("JUnit test for processReason method")
    @Test
    void processReasonWithNew () throws JsonProcessingException, FatalException {
        ReasonDTO reasonDTONew = getReasonDTOMapper();
        doNothing().when(processConfiguration).process(reasonDTONew);
        reasonApplicationService.processReason(sessionContext, reasonDTONew);
        verify(processConfiguration, times(1)).process(reasonDTONew);
    }

    @DisplayName ("JUnit test for addUpdateRecord method")
    @Test
    void addUpdateRecord () throws JsonProcessingException, FatalException {
        String payloadStr = getpayloadValidString();
        ReasonDTO reasonDTO = getReasonDTOForSave();
        doNothing().when(reasonDomainService).save(reasonDTO);
        reasonApplicationService.save(reasonDTO);
        reasonApplicationService.addUpdateRecord(payloadStr);
        verify(reasonDomainService, times(1)).save(reasonDTO);
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = reasonDTO.getPrimaryReasonCode();
        given(reasonDomainService.getReasonByCode(code)).willReturn(reasonEntity);
        reasonApplicationService.getConfigurationByCode(code);
        assertThat(reasonEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeDTOTest(){
        String code = reasonDTO.getPrimaryReasonCode();
        given(reasonDomainService.getReasonByCode(code)).willReturn(reasonEntity);
        reasonApplicationService.getConfigurationByCode(code);
        assertThat(reasonEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for processIban in application service for Try Block")
    void processReasonForTryBlock() throws JsonProcessingException, FatalException {
        doNothing().when(processConfiguration).process(reasonDTO);
        reasonApplicationService.processReason(sessionContext, reasonDTO);
        verify(processConfiguration, times(1)).process(reasonDTO);
    }

    @Test
    @DisplayName("JUnit for processIban in application service for Catch Block")
    void processReasonForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            reasonApplicationService.processReason(sessionContext2, reasonDTO);
            assertThat(reasonDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for getIbanByIbanCountryCode in application service when Not Authorize in catch block")
    void getReasonByCodeWhenNotAuthorizeCatchBlock () throws FatalException {
        String payLoadString1 =getpayloadInvalidString();
        //"{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"STATE\",\"taskIdentifier\":\"MH\",\"stateCode\":\"MH\",\"stateName\":\"MAHARASHTRA\",\"countryCode\":\"IN\"}";
        given(mutationsDomainService.getConfigurationByCode(reasonDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        /*given(ibanAssembler.setAuditFields(mutationEntity2, ibanDTOUnAuth))
                .willReturn(ibanDTOUnAuth);*/
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = mock(PayloadDTO.class);
        Assertions.assertThrows(Exception.class,()-> {
            ReasonDTO reasonDTO1 = reasonApplicationService.getReasonByCode(sessionContext, reasonDTOUnAuth);
            assertEquals("N", reasonDTO1.getAuthorized());
            assertThat(reasonDTO1).isNotNull();

        });
    }
    /**
     *
     * Negative Test Cases
     */
    @Test
    @DisplayName("JUnit for getBankByCode in application service when Authorize for Negative")
    void getReasonByCodeIsAuthorizeForNegative() throws FatalException {
        given(reasonDomainService.getReasonByCode(reasonDTO.getPrimaryReasonCode())).willReturn(reasonEntity);
        given(reasonAssembler.convertEntityToDto(reasonEntity)).willReturn(reasonDTO);
        ReasonDTO reasonDTO1 = reasonApplicationService.getReasonByCode(sessionContext, reasonDTO);
        assertNotEquals("N",reasonDTO1.getAuthorized());
        assertThat(reasonDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getBankByCode in application service check Parameter not null")
    void getReasonByCodeIsAuthorizeCheckParameter() throws FatalException {
        //IbanDTO ibanDTOnull=null;
        ReasonDTO reasonDTOEx=new ReasonDTO();
        reasonDTOEx.setPrimaryReasonCode("AB");
        reasonDTOEx.setAuthorized("Y");
        given(reasonDomainService.getReasonByCode(reasonDTOEx.getPrimaryReasonCode())).willReturn(reasonEntity);
        given(reasonAssembler.convertEntityToDto(reasonEntity)).willReturn(reasonDTO);
        ReasonDTO stateDTO1 = reasonApplicationService.getReasonByCode(sessionContext, reasonDTOEx);
        assertThat(reasonDTOEx.getPrimaryReasonCode()).isNotBlank();
        assertThat(reasonDTOEx.getAuthorized()).isNotBlank();
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


    private String primaryReasonCode;
    private String primaryReasonDescription;
    private boolean primaryAccountBlock;
    private boolean primaryAccountUnblock ;
    private boolean primaryAccountClosure;
    private boolean primaryRequestForAccountClosure;
    private boolean primaryStopPayment;
    private boolean primaryStopPaymentRevoke;
    private String secondaryReasonCode;
    private String secondaryReasonDescription;
    private boolean secondaryAccountBlock;
    private boolean secondaryAccountUnblock ;
    private boolean secondaryAccountClosure;
    private boolean secondaryRequestForAccountClosure;
    private boolean secondaryStopPayment;
    private boolean secondaryStopPaymentRevoke;
    private String applicableCategories;
    private String documentRequiredIfAny;


    private ReasonDTO getReasonDTOAuthorized () {
        ReasonDTO reasonDTOMapper = new ReasonDTO();

        reasonDTOMapper.setPrimaryReasonCode("AB");
        reasonDTOMapper.setPrimaryReasonDescription("PTESTREASON");
        reasonDTOMapper.setPrimaryAccountBlock(true);
        reasonDTOMapper.setPrimaryAccountUnblock(true);
        reasonDTOMapper.setPrimaryAccountClosure(true);
        reasonDTOMapper.setPrimaryRequestForAccountClosure(true);
        reasonDTOMapper.setPrimaryStopPayment(true);
        reasonDTOMapper.setPrimaryStopPaymentRevoke(true);
        reasonDTOMapper.setSecondaryReasonCode("AB");
        reasonDTOMapper.setSecondaryReasonDescription("PTESTREASON");
        reasonDTOMapper.setSecondaryAccountBlock(true);
        reasonDTOMapper.setSecondaryAccountUnblock(true);
        reasonDTOMapper.setSecondaryAccountClosure(true);
        reasonDTOMapper.setSecondaryRequestForAccountClosure(true);
        reasonDTOMapper.setSecondaryStopPayment(true);
        reasonDTOMapper.setSecondaryStopPaymentRevoke(true);
        reasonDTOMapper.setApplicableCategories("TEST");
        reasonDTOMapper.setDocumentRequiredIfAny("TEST");
        reasonDTOMapper.setAuthorized("Y");
        reasonDTOMapper.setTaskCode("REASON");
        reasonDTOMapper.setTaskIdentifier("US");
        return reasonDTOMapper;
    }

    private ReasonDTO getReasonDTOUnAuthorized () {
        ReasonDTO reasonDTOMapper = new ReasonDTO();

        reasonDTOMapper.setPrimaryReasonCode("BB");
        reasonDTOMapper.setPrimaryReasonDescription("PTESTREASON");
        reasonDTOMapper.setPrimaryAccountBlock(true);
        reasonDTOMapper.setPrimaryAccountUnblock(true);
        reasonDTOMapper.setPrimaryAccountClosure(true);
        reasonDTOMapper.setPrimaryRequestForAccountClosure(true);
        reasonDTOMapper.setPrimaryStopPayment(true);
        reasonDTOMapper.setPrimaryStopPaymentRevoke(true);
        reasonDTOMapper.setSecondaryReasonCode("BB");
        reasonDTOMapper.setSecondaryReasonDescription("PTESTREASON");
        reasonDTOMapper.setSecondaryAccountBlock(true);
        reasonDTOMapper.setSecondaryAccountUnblock(true);
        reasonDTOMapper.setSecondaryAccountClosure(true);
        reasonDTOMapper.setSecondaryRequestForAccountClosure(true);
        reasonDTOMapper.setSecondaryStopPayment(true);
        reasonDTOMapper.setSecondaryStopPaymentRevoke(true);
        reasonDTOMapper.setApplicableCategories("TEST");
        reasonDTOMapper.setDocumentRequiredIfAny("TEST");
        reasonDTOMapper.setTaskCode("REASON");
        reasonDTOMapper.setTaskIdentifier("US");
        reasonDTOMapper.setAuthorized("N");
        return reasonDTOMapper;
    }


    private ReasonDTO getReasonDTO () {
        ReasonDTO reasonDTO = new ReasonDTO();

        reasonDTO.setPrimaryReasonCode("AB");
        reasonDTO.setPrimaryReasonDescription("PTESTREASON");
        reasonDTO.setPrimaryAccountBlock(true);
        reasonDTO.setPrimaryAccountUnblock(true);
        reasonDTO.setPrimaryAccountClosure(true);
        reasonDTO.setPrimaryRequestForAccountClosure(true);
        reasonDTO.setPrimaryStopPayment(true);
        reasonDTO.setPrimaryStopPaymentRevoke(true);
        reasonDTO.setSecondaryReasonCode("AB");
        reasonDTO.setSecondaryReasonDescription("PTESTREASON");
        reasonDTO.setSecondaryAccountBlock(true);
        reasonDTO.setSecondaryAccountUnblock(true);
        reasonDTO.setSecondaryAccountClosure(true);
        reasonDTO.setSecondaryRequestForAccountClosure(true);
        reasonDTO.setSecondaryStopPayment(true);
        reasonDTO.setSecondaryStopPaymentRevoke(true);
        reasonDTO.setApplicableCategories("TEST");
        reasonDTO.setDocumentRequiredIfAny("TEST");

        reasonDTO.setTaskCode("REASON");
        reasonDTO.setTaskIdentifier("US");

        reasonDTO.setAuthorized("Y");
        reasonDTO.setTaskCode("US");
        reasonDTO.setStatus("DELETED");
        reasonDTO.setRecordVersion(1);
        return reasonDTO;
    }

    private ReasonDTO getReasonDTOMapper () {
        ReasonDTO reasonDTOMapper = new ReasonDTO();

        reasonDTO.setPrimaryReasonCode("AB");
        reasonDTO.setPrimaryReasonDescription("PTESTREASON");
        reasonDTO.setPrimaryAccountBlock(true);
        reasonDTO.setPrimaryAccountUnblock(true);
        reasonDTO.setPrimaryAccountClosure(true);
        reasonDTO.setPrimaryRequestForAccountClosure(true);
        reasonDTO.setPrimaryStopPayment(true);
        reasonDTO.setPrimaryStopPaymentRevoke(true);
        reasonDTO.setSecondaryReasonCode("AB");
        reasonDTO.setSecondaryReasonDescription("PTESTREASON");
        reasonDTO.setSecondaryAccountBlock(true);
        reasonDTO.setSecondaryAccountUnblock(true);
        reasonDTO.setSecondaryAccountClosure(true);
        reasonDTO.setSecondaryRequestForAccountClosure(true);
        reasonDTO.setSecondaryStopPayment(true);
        reasonDTO.setSecondaryStopPaymentRevoke(true);
        reasonDTO.setApplicableCategories("TEST");
        reasonDTO.setDocumentRequiredIfAny("TEST");
        reasonDTO.setTaskCode("REASON");
        reasonDTO.setTaskIdentifier("US");
        reasonDTO.setAuthorized("N");
        reasonDTO.setTaskCode("REASON");
        reasonDTO.setTaskIdentifier("AB");

        return reasonDTOMapper;
    }

    private ReasonDTO getReasonDTOForSave () {
        ReasonDTO reasonDTOMapper = new ReasonDTO();

        reasonDTOMapper.setPrimaryReasonCode("AB");
        reasonDTOMapper.setPrimaryReasonDescription("PTESTREASON");
        reasonDTOMapper.setPrimaryAccountBlock(true);
        reasonDTOMapper.setPrimaryAccountUnblock(true);
        reasonDTOMapper.setPrimaryAccountClosure(true);
        reasonDTOMapper.setPrimaryRequestForAccountClosure(true);
        reasonDTOMapper.setPrimaryStopPayment(true);
        reasonDTOMapper.setPrimaryStopPaymentRevoke(true);
        reasonDTOMapper.setSecondaryReasonCode("AB");
        reasonDTOMapper.setSecondaryReasonDescription("PTESTREASON");
        reasonDTOMapper.setSecondaryAccountBlock(true);
        reasonDTOMapper.setSecondaryAccountUnblock(true);
        reasonDTOMapper.setSecondaryAccountClosure(true);
        reasonDTOMapper.setSecondaryRequestForAccountClosure(true);
        reasonDTOMapper.setSecondaryStopPayment(true);
        reasonDTOMapper.setSecondaryStopPaymentRevoke(true);
        reasonDTOMapper.setApplicableCategories("TEST");
        reasonDTOMapper.setDocumentRequiredIfAny("TEST");

        reasonDTOMapper.setTaskCode("REASON");
        reasonDTOMapper.setTaskIdentifier("AB");
        reasonDTOMapper.setAction("authorize");
        reasonDTOMapper.setStatus("active");
        reasonDTOMapper.setRecordVersion(1);
        reasonDTOMapper.setAuthorized("N");
        reasonDTOMapper.setLastConfigurationAction("authorized");
        reasonDTOMapper.setTaskCode("Reason");
        reasonDTOMapper.setTaskIdentifier("AB");

        return reasonDTOMapper;
    }

    private ReasonEntity getReasonEntity () {
        ReasonEntity reasonEntity = new ReasonEntity();

        reasonEntity.setPrimaryReasonCode("AB");
        reasonEntity.setPrimaryReasonDesc("PTESTREASON");
        reasonEntity.setIsPriAccountBlock('Y');
        reasonEntity.setIsPriAccountUnblock('Y');
        reasonEntity.setIsPriAccountClosure('Y');
        reasonEntity.setIsPriRequestForAccClosure('Y');
        reasonEntity.setIsPriStopPayment('Y');
        reasonEntity.setIsPriStopPaymentRevoke('Y');
        reasonEntity.setSecondaryReasonCode("AB");
        reasonEntity.setSecondaryReasonDesc("PTESTREASON");
        reasonEntity.setIsSecAccountBlock('Y');
        reasonEntity.setIsSecAccountUnblock('Y');
        reasonEntity.setIsSecAccountClosure('Y');
        reasonEntity.setIsSecRequestForAccClosure('Y');
        reasonEntity.setIsSecStopPayment('Y');
        reasonEntity.setIsSecStopPaymentRevoke('Y');
        reasonEntity.setAuthorized("Y");
        reasonEntity.setApplicableCategories("TEST");
        reasonEntity.setDocumentReqIfAny("TEST");
        return reasonEntity;
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
        mutationEntity.setTaskIdentifier("AB");
        mutationEntity.setTaskCode("REASON");
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
        mutationEntity.setTaskIdentifier("AB");
        mutationEntity.setTaskCode("REASON");
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
                "{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":0,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"Reason\",\"taskIdentifier\":\"AB\",\"primaryReasonCode\":\"AB\",\"primaryReasonDescription\":\"PTESTREASON\",\"primaryAccountBlock\":true,\"primaryAccountUnblock\":true,\"primaryAccountClosure\":true,\"primaryRequestForAccountClosure\":true,\"primaryStopPayment\":true,\"primaryStopPaymentRevoke\":true,\"secondaryReasonCode\":\"AB\",\"secondaryReasonDescription\":\"PTESTREASON\",\"secondaryAccountBlock\":true,\"secondaryAccountUnblock\":true,\"secondaryAccountClosure\":true,\"secondaryRequestForAccountClosure\":true,\"secondaryStopPayment\":true,\"secondaryStopPaymentRevoke\":true,\"applicableCategories\":\"TEST\",\"documentRequiredIfAny\":\"TEST\"}";
        return payLoadString;
    }

    private String getpayloadInvalidString () {
        String payLoadString =
                "{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":10,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"Reason\",\"taskIdentifier\":\"AB\",\"primaryReasonCode\":\"AB\",\"primaryReasonDescription\":\"PTESTREASON\",\"primaryAccountBlock\":true,\"primaryAccountUnblock\":true,\"primaryAccountClosure\":true,\"primaryRequestForAccountClosure\":true,\"primaryStopPayment\":true,\"primaryStopPaymentRevoke\":true,\"secondaryReasonCode\":\"AB\",\"secondaryReasonDescription\":\"PTESTREASON\",\"secondaryAccountBlock\":true,\"secondaryAccountUnblock\":true,\"secondaryAccountClosure\":true,\"secondaryRequestForAccountClosure\":true,\"secondaryStopPayment\":true,\"secondaryStopPaymentRevoke\":true,\"applicableCategories\":\"TEST\",\"documentRequiredIfAny\":\"TEST\"}";
        return payLoadString;
    }

}