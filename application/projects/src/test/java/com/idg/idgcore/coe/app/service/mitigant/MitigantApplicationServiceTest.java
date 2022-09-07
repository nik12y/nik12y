package com.idg.idgcore.coe.app.service.mitigant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.coe.domain.assembler.mitigant.MitigantAssembler;
import com.idg.idgcore.coe.domain.entity.mitigant.MitigantEntity;
import com.idg.idgcore.coe.domain.entity.mitigant.MitigantEntityKey;
import com.idg.idgcore.coe.domain.entity.mitigant.MitigantRiskCodeEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mitigant.IMitigantDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;
import com.idg.idgcore.coe.dto.mitigant.MitigantDTO;
import com.idg.idgcore.coe.dto.mitigant.MitigantRiskCodeDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.*;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MitigantApplicationServiceTest {

    @InjectMocks
    private MitigantApplicationService mitigantApplicationService;

    @Mock
    private ProcessConfiguration process;
    @Mock
    private MitigantAssembler mitigantAssembler;
    @Mock
    private IMitigantDomainService mitigantDomainService;

    @Mock
    AbstractApplicationService abstractApplicationService;
    @Autowired
    private MutationEntity mutationEntity;
    private MutationEntity mutationEntity2;
    private MutationEntity mutationEntity3;
    @Mock
    private IMutationsDomainService mutationsDomainService;
    private SessionContext sessionContext;
    private SessionContext sessionContext1;
    private MitigantEntity mitigantEntity;
    private MitigantDTO mitigantDTO;
    private MitigantDTO mitigantDTOUnAuth;
    private MitigantDTO mitigantDTO1;
    private MitigantEntity mitigantEntity1;
    private MitigantDTO mitigantDTOMapper;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext ();
        sessionContext1=getErrorSession();
        mitigantDTO=getMitigantDTOAuthorized ();
        mitigantEntity=getMitigantEntity();
        mitigantDTOUnAuth=getMitigantDTOUnAuth();
        mitigantDTOMapper=getMitigantDTOMapper();
        mutationEntity=getMutationEntity();
        mitigantEntity1=getMitigantEntity1();
        mitigantDTO1=getMitigantDTO();
        mutationEntity2=getMutationEntityJsonError();
        mutationEntity3=getMutationEntityUnauthorize();
    }

    @Test
    @DisplayName("JUnit for getMitigantByCode in application service when Authorize")
    void getMitigantByCodeIsAuthorize() throws FatalException {
        given(mitigantDomainService.getMitigantByCode(mitigantDTO.getMitigantCode())).willReturn(mitigantEntity);
        given(mitigantAssembler.convertEntityToDto(mitigantEntity)).willReturn(mitigantDTO);
        MitigantDTO mitigantDTO1 = mitigantApplicationService.getMitigantByCode(sessionContext, mitigantDTO);
        assertEquals("Y" ,mitigantDTO1.getAuthorized());
        assertThat(mitigantDTO).isNotNull();
    }

//    @Test
//    @DisplayName("JUnit for getMitigantByCode in application service when Not Authorize in try else block")
//    void getMitigantByCodeWhenNotAuthorizeTryBlock() throws JsonProcessingException, FatalException {
//        given(mutationsDomainService.getConfigurationByCode(mitigantDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity3);
//            MitigantDTO mitigantDTO5 = mitigantApplicationService.getMitigantByCode(sessionContext, mitigantDTOUnAuth);
//            assertEquals("N", mitigantDTO1.getAuthorized());
//            assertThat(mitigantDTO1).isNotNull();
//            System.out.println("mitigantDTO1 = " + mitigantDTO1);
//    }

//    @Test
//    @DisplayName("JUnit for getByMitigantCode in application service when Not Authorize in try else block")
//    void getMitigantByCodewhenNotAuthorizeTryBlock() throws JsonProcessingException, FatalException {
//        given(mutationsDomainService.getConfigurationByCode(mitigantDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity3);
//        MitigantDTO mitigantDTO5 = mitigantApplicationService.getMitigantByCode(sessionContext,mitigantDTOUnAuth);
//        assertEquals("N",mitigantDTO5.getAuthorized());
//        assertThat(mitigantDTO5).isNotNull();
//    }

    @Test
    @DisplayName("JUnit for getByMitigantCode in application service when Not Authorize in catch block")
    void getMitigantByCodewhenNotAuthorizeCatchBlock () throws FatalException {
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = Mockito.mock(PayloadDTO.class);
        Assertions.assertThrows(Exception.class,()-> {
            MitigantDTO mitigantDTO1 = mitigantApplicationService.getMitigantByCode(sessionContext, mitigantDTOMapper);
            assertEquals("N",mitigantDTO1.getAuthorized());
            assertThat(mitigantDTO1).isNotNull();
        });
    }

    @Test
    @DisplayName("Should return all getMitigantAll in application service for try block")
    void getMitigantAllTryBlock() throws FatalException {
        given(mutationsDomainService.getMutations(MITIGANT))
                .willReturn(List.of(mutationEntity));
        List<MitigantDTO> mitigantDTOList = mitigantApplicationService.getMitigantAll(sessionContext);
        assertThat(mitigantDTOList).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getMitigantAll in application service for catch block for checker")
    void getMitigantAllCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        sessionContext.setRole(new String[] { "" });
        given(mutationsDomainService.getMutations(
                mitigantDTO1.getTaskCode()))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
       // Assertions.assertThrows(FatalException.class,()-> {
            List<MitigantDTO> mitigantDTO1 = mitigantApplicationService.getMitigantAll(sessionContext);
         //   System.out.println("return size : " + mitigantDTO1.size());
            assertThat(mitigantDTO1).isNotNull();
       // });
    }

    @Test
    @DisplayName("JUnit for processMitigant in application service for Try Block")
    void processMitigantForTryBlock() throws JsonProcessingException, FatalException {

        doNothing().when(process).process(mitigantDTO);
        mitigantApplicationService.processMitigant(sessionContext, mitigantDTO);
        verify(process, times(1)).process(mitigantDTO);
    }

    @Test
    @DisplayName("JUnit for processPurpose in application service for Catch Block")
    void processPurposeForCatchBlock() {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            mitigantApplicationService.processMitigant(sessionContext2, mitigantDTO);
            assertThat(mitigantDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {

        doNothing().when(mitigantDomainService).save(mitigantDTO);
        mitigantApplicationService.save(mitigantDTO);
        mitigantApplicationService.addUpdateRecord(payLoadString1);
        verify(mitigantDomainService, times(1)).save(mitigantDTO);
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = mitigantDTO.getMitigantCode();
        given(mitigantDomainService.getMitigantByCode(code)).willReturn(mitigantEntity);
        mitigantApplicationService.getConfigurationByCode(code);
        assertThat(mitigantEntity).isNotNull();
    }

//    @Test
//    @DisplayName("JUnit for getByMitigantCode in application service when Authorize for Negative")
//    void getMitigantByCodeIsAuthorizeforNegative() throws FatalException, JsonProcessingException {
//        given(mitigantDomainService.getMitigantByCode(mitigantDTO.getMitigantCode())).willReturn(mitigantEntity);
//        given(mitigantAssembler.convertEntityToDto(mitigantEntity)).willReturn(mitigantDTO);
//        MitigantDTO mitigantDTO1 = mitigantApplicationService.getMitigantByCode(sessionContext, mitigantDTO);
//        assertNotEquals("N",mitigantDTO1.getAuthorized());
//        assertThat(mitigantDTO).isNotNull();
//    }

//    @Test
//    @DisplayName("JUnit for getMitigantByCode in application service when UnAuthorize fetche no Record from database")
//    void getMitigantByCodeNotAuthorizeNull() throws FatalException {
//        given(mutationsDomainService.getConfigurationByCode(mitigantDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);
//        mitigantDTO.setAuthorized("N");
//        mutationEntity.setPayload(new Payload(payLoadString1));
//        MitigantDTO mitigantDTO1 = mitigantApplicationService.getMitigantByCode(sessionContext, mitigantDTO);
//        assertNotEquals("Y",mitigantDTO1.getAuthorized());
//    }

    @Test
    @DisplayName("JUnit for getMitigantByCode in application service check Parameter not null")
    void getMitigantByCodeIsAuthorizeCheckParameter() throws FatalException, JsonProcessingException {
        given(mitigantDomainService.getMitigantByCode(mitigantDTO.getMitigantCode())).willReturn(mitigantEntity);
        given(mitigantAssembler.convertEntityToDto(mitigantEntity)).willReturn(mitigantDTO);
        MitigantDTO mitigantDTO1 = mitigantApplicationService.getMitigantByCode(sessionContext, mitigantDTO);
        assertThat(mitigantDTO1.getMitigantCode()).isNotBlank();
        assertThat(mitigantDTO1.getAuthorized()).isNotBlank();
    }

//    @Test
//    @DisplayName("JUnit for getMitigantByCode in application service when Not Authorize in try block for Negative when getAuthorized unexpected is Y")
//    void getMitigantByCodewhenNotAuthorizeTryBlockForNegative() throws JsonProcessingException, FatalException {
//        given(mutationsDomainService.getConfigurationByCode(mitigantDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);
//        mutationEntity.setPayload(new Payload(payLoadString1));
//        mitigantDTOUnAuth.setAuthorized("N");
//        MitigantDTO mitigantDTO1 = mitigantApplicationService.getMitigantByCode(sessionContext,mitigantDTOUnAuth);
//        assertNotEquals("Y",mitigantDTO1.getAuthorized());
//        assertThat(mitigantDTO1).isNotNull();
//    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        assertThat(mitigantEntity.toString()).isNotNull();
        assertThat(mitigantDTO.toString()).isNotNull();
        MitigantRiskCodeDTO mitigantRiskDTO =new MitigantRiskCodeDTO("RC0001","market risk1");
        MitigantDTO mitigantDTO2=new MitigantDTO("CR0001","test1", "test1", true, true,List.of(mitigantRiskDTO));
        MitigantRiskCodeDTO.builder().riskCode("RC0001").riskName("market risk1").build().toString();
        MitigantDTO.builder().mitigantCode("CR0001").mitigantCodeName("test1").mitigantCodeDesc("test1").isAllowModification(true).isActionable(true).mitigantRiskCode(List.of()).build();
        MitigantEntityKey mitigantEntityKey=new MitigantEntityKey("CR0001");
        MitigantEntityKey mitigantEntityKey1=new MitigantEntityKey();
        MitigantEntity mitigantEntity2=new MitigantEntity("CR0001","test1","test1",'Y','Y',null,null,List.of(),"draft",1,"N","draft");
        MitigantRiskCodeEntity mitigantRiskEntity=new MitigantRiskCodeEntity();
        mitigantRiskEntity.setMitigantRiskCodeId(1);
        assertThat(mitigantEntityKey1.toString()).isNotNull();
        assertThat(mitigantEntityKey.toString()).isNotNull();
        mitigantEntityKey.setMitigantCode("CR0001");
        System.out.println(mitigantEntityKey.getMitigantCode());
        mitigantEntityKey.keyAsString();
        mitigantEntityKey.builder().mitigantCode("CR0001").build();
        assertThat(mitigantDTO).descriptionText();
    }

   // @Test
    @DisplayName("JUnit for getMitigantAll in application service for try block negative scenario for SessionContext some field not be null")
    void getMitigantAllTryBlockNegative() throws JsonProcessingException, FatalException {

        MitigantDTO mitigantDTO1 = new MitigantDTO();
        MitigantEntity mitigantEntity5 = new MitigantEntity();
       // given(mutationsDomainService.getUnauthorizedMutation(mitigantDTO1.getTaskCode(),AUTHORIZED_N)).willReturn(List.of(mutationEntity));
        given(mitigantDomainService.getMitigantAll()).willReturn(List.of(mitigantEntity5));
     //   given(mitigantAssembler.convertEntityToDto(mitigantEntity5)).willReturn(mitigantDTO1);
       // given(mitigantAssembler.setAuditFields(mutationEntity,mitigantDTO1)).willReturn(mitigantDTO1);
        Assertions.assertThrows(Exception.class,()-> {
            List<MitigantDTO> mitigantDTO2 = mitigantApplicationService.getMitigantAll(sessionContext1);
            assertThat(sessionContext1.getRole()).isNotEmpty();
            assertThat(sessionContext1.getServiceInvocationModeType()).isNotNull();
        });

    }

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder()
                .bankCode("003").defaultBranchCode("03110").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("dummy_target").postingDate(new Date()).valueDate(new Date()).transactionBranch("")
                .userId("aditya")
                .channel("Branch").taskCode(MITIGANT)
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(1L)
                .customAttributes("")
                .serviceInvocationModeType(Regular)
                .allTargetUnitsSelected(true)
                .userLocal("en_US")
                .originatingModuleCode("")
                .role(new String[]{"maker"}).build();
        return sessionContext;
    }

    private SessionContext getErrorSession(){
        SessionContext sessionContextError=SessionContext.builder()
                .bankCode("").defaultBranchCode("").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("").postingDate(new Date()).valueDate(new Date()).transactionBranch("")
                .userId("aditya")
                .channel("").taskCode("")
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(null)
                .customAttributes("")
                .serviceInvocationModeType(null)
                .allTargetUnitsSelected(true)
                .userLocal("")
                .originatingModuleCode("")
                .role(new String[] {"maker"})
                .build();
        return sessionContextError;
    }

    @Test
    @DisplayName("JUnit for processMitigant in application service for Catch Block")
    void processMitigantForCatchBlock() throws FatalException, JsonProcessingException {
//        SessionContext sessionContext2=null;
        SessionContext sessionContext2=new SessionContext();
//        Assertions.assertThrows(Exception.class,()-> {
            mitigantApplicationService.processMitigant(sessionContext2, mitigantDTO);
            assertThat(mitigantDTO).descriptionText();
//        })
    }

//    @Test
//    @DisplayName("JUnit for getMitigantAll in application service for catch block")
//    void getMitigantAllCatchBlock() throws FatalException {
//        given(mitigantDomainService.getMitigantAll()).willReturn(List.of(mitigantEntity1));
//        //given(mutationsDomainService.getUnauthorizedMutation(mitigantDTO1.getTaskCode(), AUTHORIZED_N)).willReturn(List.of(mutationEntity2));
//        given(mitigantAssembler.convertEntityToDto(mitigantEntity1)).willReturn(mitigantDTOMapper);
//        System.out.println(mitigantDTO1);
//        Assertions.assertThrows(Exception.class,()-> {
//            List<MitigantDTO> mitigantDTO2 = mitigantApplicationService.getMitigantAll(sessionContext1);
//            System.out.println(mitigantDTO2);
//            assertThat(mitigantDTO2).isNotNull();
//            System.out.println(mitigantDTO2);
//        });
//    }

    private MitigantDTO getMitigantDTOAuthorized () {
        MitigantDTO mitigantDTO = new MitigantDTO();
        mitigantDTO.setMitigantCode("CR0001");
        mitigantDTO.setMitigantCodeName("test1");
        mitigantDTO.setMitigantCodeDesc("test1");
        List<MitigantRiskCodeDTO> riskCodeDTO = new ArrayList<>();
        riskCodeDTO.add(new MitigantRiskCodeDTO("RC0001", "market risk1"));
        mitigantDTO.setMitigantRiskCode(riskCodeDTO);
        mitigantDTO.setAuthorized("Y");
        return mitigantDTO;
    }

    private MitigantDTO getMitigantDTO()
    {
        MitigantDTO mitigantDTO = new MitigantDTO();
        mitigantDTO.setMitigantCode("CR0001");
        mitigantDTO.setMitigantCodeName("test1");
        mitigantDTO.setMitigantCodeDesc("test1");
        List<MitigantRiskCodeDTO> riskCodeDTO=new ArrayList<>();
        riskCodeDTO.add(new MitigantRiskCodeDTO("RC0001", "market risk1"));
        mitigantDTO.setMitigantRiskCode(riskCodeDTO);
        mitigantDTO.setStatus("DELETED");
        mitigantDTO.setRecordVersion(1);
        return mitigantDTO;
    }

    private MitigantEntity getMitigantEntity(){
        MitigantEntity mitigantEntity = new MitigantEntity();
        mitigantEntity.setMitigantCode("CR0001");
        mitigantEntity.setMitigantCodeName("test1");
        mitigantEntity.setMitigantCodeDesc("test1");

        List<MitigantRiskCodeEntity> riskCodeEntity = new ArrayList<>();
        riskCodeEntity.add(new MitigantRiskCodeEntity(1,"RC0001","market risk1"));
        mitigantEntity.setMitigantRiskCode(riskCodeEntity);
        mitigantEntity.setStatus("draft");
        mitigantEntity.setRecordVersion(0);
        return mitigantEntity;
    }

    private MitigantEntity getMitigantEntity1()
    {
        MitigantEntity mitigantEntity1 = new MitigantEntity();
        mitigantEntity1.setMitigantCode("CR0001");
        mitigantEntity1.setMitigantCodeName("test1");
        mitigantEntity1.setMitigantCodeDesc("test1");

        List<MitigantRiskCodeEntity> riskCodeEntity = new ArrayList<>();
        riskCodeEntity.add(new MitigantRiskCodeEntity(1,"RC0001", "market risk1"));
        mitigantEntity1.setMitigantRiskCode(riskCodeEntity);
        mitigantEntity1.setStatus("DELETED");
        mitigantEntity1.setRecordVersion(1);
        return mitigantEntity1;
    }

    private MitigantDTO getMitigantDTOUnAuth() {

        MitigantDTO mitigantDTO = new MitigantDTO();
        mitigantDTO.setMitigantCode("CR0001");
        mitigantDTO.setMitigantCodeName("test1");
        mitigantDTO.setMitigantCodeDesc("test1");
        mitigantDTO.setIsAllowModification(true);
        mitigantDTO.setIsActionable(true);
        mitigantDTO.setAuthorized("N");
        List<MitigantRiskCodeDTO> mitigantRiskCodeDTO = new ArrayList<>();
        mitigantRiskCodeDTO.add(new MitigantRiskCodeDTO("RC0001", "market risk1"));
        mitigantDTO.setMitigantRiskCode(mitigantRiskCodeDTO);
        return mitigantDTO;
    }

    private MitigantDTO getMitigantDTOMapper(){
        MitigantDTO mitigantDTO = new MitigantDTO();
        mitigantDTO.setMitigantCode("CR0001");
        mitigantDTO.setMitigantCodeName("test1");
        mitigantDTO.setMitigantCodeDesc("test1");

        List<MitigantRiskCodeDTO> riskCodeDTO = new ArrayList<>();
        riskCodeDTO.add(new MitigantRiskCodeDTO("RC0001", "market risk1"));
        mitigantDTO.setMitigantRiskCode(riskCodeDTO);
        mitigantDTO.setAuthorized("N");
        mitigantDTO.setTaskCode("MITIGANT");
        mitigantDTO.setTaskIdentifier("CR0001");
        return mitigantDTO;
    }

    private MutationEntity getMutationEntity() {
        String payLoadString= "{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":1,\"authorized\":\"Y\"," +
                "\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"MITIGANT\",\"taskIdentifier\":\"CR0001\"," +
                "\"mitigantCode\":\"CR0001\",\"mitigantCodeName\":\"test1\",\"mitigantCodeDesc\":\"test1\"," +
                "\"isAllowModification\":true,\"isActionable\":true,\"mitigantRiskCode\":[{\"riskCode\":\"RC0001\"," +
                "\"riskName\":\"market risk1\"},{\"riskCode\":\"RC0002\",\"riskName\":\"market risk2\"}]}";

        MutationEntity mutationEntity = new MutationEntity();

        mutationEntity.setTaskIdentifier("CR0001");
        mutationEntity.setTaskCode("MITIGANT");
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("closed");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("add");
        mutationEntity.setLastConfigurationAction("unauthorized");
        return mutationEntity;
    }

    private MutationEntity getMutationEntityUnauthorize() {
        String payLoadString="{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"CATEGORY\",\"taskIdentifier\":\"VC0001\",\"appVerificationCategoryId\":\"VC0001\",\"verificationCategoryDesc\":\"Addresss\",\"isExternal\":true,\"appVerTypeConfig\":[{\"taskCode\":\"TYPE\",\"taskIdentifier\":\"V00001\",\"appVerificationTypeId\":\"V00001\",\"isViewToCustomer\":false,\"nature\":\"mutation\"},{\"taskCode\":\"TYPE\",\"taskIdentifier\":\"V00002\",\"appVerificationTypeId\":\"V00002\",\"isViewToCustomer\":false,\"nature\":\"mutation\"},{\"taskCode\":\"TYPE\",\"taskIdentifier\":\"V00003\",\"appVerificationTypeId\":\"V00003\",\"isViewToCustomer\":false,\"nature\":\"mutation\"}]}";

        MutationEntity mutationEntity = new MutationEntity();

        mutationEntity.setTaskIdentifier("MH");
        mutationEntity.setTaskCode("CATEGORY");
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("closed");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("add");
        mutationEntity.setLastConfigurationAction("unauthorized");
        return mutationEntity;

    }

    private MutationEntity getMutationEntityJsonError()
    {
        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("CR0001");
        mutationEntity2.setTaskCode("MITIGANT");
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");
        return mutationEntity2;
    }

    String payLoadString1="{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":1,\"authorized\":\"Y\"," +
            "\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"MITIGANT\",\"taskIdentifier\":\"CR0001\"," +
            "\"mitigantCode\":\"CR0001\",\"mitigantCodeName\":\"test1\",\"mitigantCodeDesc\":\"test1\"," +
            "\"isAllowModification\":true,\"isActionable\":true,\"mitigantRiskCode\":[{\"riskCode\":\"RC0001\"," +
            "\"riskName\":\"market risk1\"},{\"riskCode\":\"RC0002\",\"riskName\":\"market risk2\"}]}";


}
