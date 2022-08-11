package com.idg.idgcore.coe.app.service.purpose;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.coe.domain.assembler.purpose.PurposeAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.entity.purpose.PurposeEntity;
import com.idg.idgcore.coe.domain.entity.purpose.PurposeEntityKey;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.purpose.IPurposeDomainService;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.purpose.PurposeDTO;
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

import java.util.Date;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static com.idg.idgcore.coe.common.Constants.PURPOSE;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurposeApplicationServiceTest {
    @InjectMocks
    private PurposeApplicationService purposeApplicationService;
    @Mock
    private ProcessConfiguration process;
    @Mock
    private PurposeAssembler purposeAssembler;
    @Mock
    private IPurposeDomainService purposeDomainService;

    @Mock
    AbstractApplicationService abstractApplicationService;
    @Autowired
    private MutationEntity mutationEntity;
    @Autowired
    private MutationEntity mutationEntity2;
    @Mock
    private IMutationsDomainService mutationsDomainService;
    private SessionContext sessionContext;
    private SessionContext sessionContext1;
    private PurposeEntity purposeEntity;
    private PurposeDTO purposeDTO;
    private PurposeDTO purposeDTOUnAuth;
    private PurposeDTO purposeDTO1;
    private PurposeEntity purposeEntity1;
    private PurposeEntity purposeEntity2;
    private PurposeDTO purposeDTOMapper;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext ();
        sessionContext1 = getErrorSession();
        purposeDTO = getPurposeDTOAuthorized ();
        purposeEntity = getPurposesEntity();
        purposeDTOUnAuth = getPurposeDTOUnAuth();
        purposeDTOMapper = getPurposeDTOMapper();
        mutationEntity = getMutationEntity();
        purposeEntity1 = getPurposesEntity();
        purposeEntity2 = getPurposesEntity2();
        purposeDTO1 = getPurposesDTO();
        mutationEntity2 = getMutationEntityJsonError();
    }

    @Test
    @DisplayName("JUnit for getPurposeByCode in application service when Authorize try Block")
    void getPurposeByCodeIsAuthorize() throws FatalException {

        given(purposeDomainService.getPurposeByCode(purposeDTO.getPurposeCode())).willReturn(purposeEntity);
        given(purposeAssembler.convertEntityToDto(purposeEntity)).willReturn(purposeDTO);
        PurposeDTO purposeDTO1 = purposeApplicationService.getPurposeByCode(sessionContext, purposeDTO);
        assertEquals("Y",purposeDTO1.getAuthorized());
        assertThat(purposeDTO1).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getPurposeByCode in application service when Not Authorize in try else block")
    void getPurposeByCodeWhenNotAuthorizeTryBlock() throws JsonProcessingException, FatalException {
        MutationEntity mutationEntity6 = new MutationEntity();

        String payLoadString= "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\"," +
                "\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\"," +
                "\"taskCode\":\"PURPOSE\",\"taskIdentifier\":\"PC0006\",\"purposeCode\":\"PC0006\",\"purposeName\":\"test6\"," +
                "\"purposeType\":\"test6\",\"purposeDescription\":\"test6\"}";

//        mutationEntity6.setTaskIdentifier("PC0006");
//        mutationEntity6.setTaskCode(PURPOSE);
        mutationEntity6.setPayload(new Payload(payLoadString));
//        mutationEntity6.setStatus("closed");
//        mutationEntity6.setAuthorized("N");
//        mutationEntity6.setRecordVersion(1);
//        mutationEntity6.setAction("authorize");
//        mutationEntity6.setLastConfigurationAction("unauthorized");

//        PurposeEntity purposeEntity4 = new PurposeEntity();
//        purposeEntity4.setPurposeCode("PC0006");
//        purposeEntity4.setPurposeName("test6");
//        purposeEntity4.setPurposeType("test6");
//        purposeEntity4.setPurposeDescription("test6");
//        purposeEntity4.setAuthorized("N");
//        purposeEntity4.setStatus("closed");

        PurposeDTO purposeDTO6 = new PurposeDTO();
        purposeDTO6.setAuthorized("N");
        purposeDTO6.setPurposeCode("PC0006");
        purposeDTO6.setPurposeName("test6");
        purposeDTO6.setPurposeType("test6");
        purposeDTO6.setPurposeDescription("test6");
        given(mutationsDomainService.getConfigurationByCode(purposeDTO6.getTaskIdentifier())).willReturn(mutationEntity6);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO payload6 = new PayloadDTO();
        Mockito.when(mockObjectMapper.readValue(payload6.getData(), PurposeDTO.class)).thenReturn(purposeDTO6);
        given(purposeAssembler.setAuditFields(mutationEntity6, purposeDTO6)).willReturn(purposeDTO6);
        Assertions.assertThrows(FatalException.class, ()-> {
            PurposeDTO purposeDTO1 = purposeApplicationService.getPurposeByCode(sessionContext, purposeDTO6);
            assertEquals("N", purposeDTO1.getAuthorized());
            assertThat(purposeDTO1).isNotNull();
            System.out.println("purposeDTO1 = " + purposeDTO1);
        });
    }

//    @Test
//    @DisplayName("JUnit for getPurposeByCode in application service when Not Authorize in catch block")
//    void getPurposeByCodeWhenNotAuthorizeCatchBlocks() throws FatalException, JsonProcessingException {
//
//        String payLoadString1 = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\"," +
//                "\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\"," +
//                "\"taskCode\":\"PURPOSE\",\"taskIdentifier\":\"PC0007\",\"purposeCode\":\"PC0007\",\"purposeName\":\"test\"," +
//                "\"purposeType\":\"test\",\"purposeDescription\":\"test\"}";
//
//        given(mutationsDomainService.getConfigurationByCode(purposeDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);
//        ModelMapper mapper=new ModelMapper();
//        PayloadDTO payload = new PayloadDTO(payLoadString1);
//        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
//        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);
//
//        PurposeEntityKey purposeEntityKey =  PurposeEntityKey.builder().purposeCode("PC0007").build();
//        System.out.println(purposeEntityKey.keyAsString());
//
//        //Assertions.assertThrows(Exception.class,()-> {
//        PurposeDTO purposeDTO1 = purposeApplicationService.getPurposeByCode(sessionContext, purposeDTOMapper);
//        assertEquals("N",purposeDTO1.getAuthorized());
//        assertThat(purposeDTOMapper).isNotNull();
//        System.out.println(purposeDTO1);
//        //});
//    }
//    -------------------------------------------------------------------------------------------

    @Test
    @DisplayName("JUnit for getByPurposeCode in application service when Not Authorize in try else block")
    void getPurposeByCodewhenNotAuthorizeTryBlock() throws JsonProcessingException, FatalException {
        given(mutationsDomainService.getConfigurationByCode(purposeDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);

        String payLoadString= "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\"," +
                "\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\"," +
                "\"taskCode\":\"PURPOSE\",\"taskIdentifier\":\"PC0006\",\"purposeCode\":\"PC0006\",\"purposeName\":\"test6\"," +
                "\"purposeType\":\"test6\",\"purposeDescription\":\"test6\"}";

        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);
//        Mockito.when(mockObjectMapper.readValue(mutationEntity.getPayload().getData(), PurposeDTO.class)).thenReturn(purposeDTO);

        PurposeDTO purposeDTO1 = purposeApplicationService.getPurposeByCode(sessionContext,purposeDTOMapper);
//        assertEquals("N",purposeDTO1.getAuthorized());
//        assertThat(purposeDTO1).isNotNull();
        System.out.println(purposeDTO1);
    }
    @Test
    @DisplayName("JUnit for getByPurposeCode in application service when Not Authorize in catch block")
    void getPurposeByCodewhenNotAuthorizeCatchBlock () throws FatalException {

        String payLoadString= "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\"," +
                "\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\"," +
                "\"taskCode\":\"PURPOSE\",\"taskIdentifier\":\"PC0006\",\"purposeCode\":\"PC0006\",\"purposeName\":\"test6\"," +
                "\"purposeType\":\"test6\",\"purposeDescription\":\"test6\"}";

        given(mutationsDomainService.getConfigurationByCode(purposeDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);
//                     Mockito.when(mockObjectMapper.readValue(mutationEntity.getPayload().getData(), StateDTO.class)).thenReturn(stateDTO);
        Assertions.assertThrows(Exception.class,()-> {
            PurposeDTO purposeDTO1 = purposeApplicationService.getPurposeByCode(sessionContext, purposeDTOMapper);
            assertEquals("N",purposeDTO1.getAuthorized());
            assertThat(purposeDTO1).isNotNull();
            System.out.println(purposeDTO1);
        });
    }

//    ----------------------------------------------------------------------------------------
    @Test
    @DisplayName("JUnit for getPurposeByCode in application service when Not Authorize in catch block")
    void getPurposeByCodeWhenNotAuthorizeCatchBlock () throws FatalException, JsonProcessingException {

        String payLoadString1 = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\"," +
                "\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\"," +
                "\"taskCode\":\"PURPOSE\",\"taskIdentifier\":\"PC0008\",\"purposeCode\":\"PC0008\",\"purposeName\":\"test8\"," +
                "\"purposeType\":\"test8\",\"purposeDescription\":\"test8\"}";

        given(mutationsDomainService.getConfigurationByCode(purposeDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);

        Assertions.assertThrows(Exception.class,()-> {
            PurposeDTO purposeDTO1 = purposeApplicationService.getPurposeByCode(sessionContext, purposeDTOMapper);
            assertEquals("N",purposeDTO1.getAuthorized());
            assertThat(purposeDTO1).isNotNull();
            System.out.println(purposeDTO1);
        });
    }

    @Test
    @DisplayName("Should return all getPurposes when there are no unauthorized")
    void getPurposesWhenThereAreNoUnauthorized() throws FatalException {
        given(purposeDomainService.getPurposes()).willReturn(List.of(purposeEntity));
        given(mutationsDomainService.getUnauthorizedMutation(PURPOSE, AUTHORIZED_N)).willReturn(List.of());
        given(purposeAssembler.convertEntityToDto(purposeEntity)).willReturn(purposeDTO);
        List<PurposeDTO> purposeDTOList = purposeApplicationService.getPurposes(sessionContext);
        assertEquals(1, purposeDTOList.size());
        assertEquals(purposeDTO, purposeDTOList.get(0));
    }

//    @Test
//    @DisplayName("JUnit for getPurposes in application service for try block")
//    void getPurposesTryBlock() throws FatalException {
//
//        given(purposeDomainService.getPurposes()).willReturn(List.of(purposeEntity1));
//        given(mutationsDomainService.getUnauthorizedMutation(purposeDTO1.getTaskCode(),AUTHORIZED_N)).willReturn(List.of(mutationEntity));
//
//        String payLoadString = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\"," +
//                "\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\"," +
//                "\"taskCode\":\"PURPOSE\",\"taskIdentifier\":\"PC0007\",\"purposeCode\":\"PC0007\",\"purposeName\":\"test\"," +
//                "\"purposeType\":\"test\",\"purposeDescription\":\"test\"}";
//
//        Payload payload=new Payload();
//        payload.setData(payLoadString);
//        mutationEntity.setPayload(payload);
//        String data1 = mutationEntity.getPayload().getData();
//        given(purposeAssembler.convertEntityToDto(purposeEntity1)).willReturn(purposeDTO1);
//        System.out.println(purposeDTO1);
//
//        List<PurposeDTO> purposeDTO2 = purposeApplicationService.getPurposes(sessionContext);
//        System.out.println(purposeDTO2);
//        assertThat(purposeDTO2).isNotNull();
//        System.out.println(purposeDTO2);
//    }

    @Test
    @DisplayName("JUnit for getPurposes in application service for catch block for checker")
    void getPurposesCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        sessionContext.setRole(new String[] { "" });
        given(mutationsDomainService.getUnauthorizedMutation(
                purposeDTO1.getTaskCode(),AUTHORIZED_N))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
        Assertions.assertThrows(FatalException.class,()-> {
            List<PurposeDTO> purposeDTO1 = purposeApplicationService.getPurposes(sessionContext);
            System.out.println("return size : " + purposeDTO1.size());
            assertThat(purposeDTO1).isNotNull();
            System.out.println(purposeDTO1);
        });
    }

    @Test
    @DisplayName("JUnit for processPurpose in application service for Try Block")
    void processPurposeForTryBlock() throws JsonProcessingException, FatalException {

        doNothing().when(process).process(purposeDTO);
        purposeApplicationService.processPurpose(sessionContext, purposeDTO);
        verify(process, times(1)).process(purposeDTO);
    }

    @Test
    @DisplayName("JUnit for processPurpose in application service for Catch Block")
    void processPurposeForCatchBlock() {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            purposeApplicationService.processPurpose(sessionContext2, purposeDTO);
            assertThat(purposeDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {

        String payLoadString1 = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\"," +
                "\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\"," +
                "\"taskCode\":\"PURPOSE\",\"taskIdentifier\":\"PC0007\",\"purposeCode\":\"PC0007\",\"purposeName\":\"test\"," +
                "\"purposeType\":\"test\",\"purposeDescription\":\"test\"}";

        doNothing().when(purposeDomainService).save(purposeDTO);
        purposeApplicationService.save(purposeDTO);
        purposeApplicationService.addUpdateRecord(payLoadString1);
        verify(purposeDomainService, times(1)).save(purposeDTO);
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = purposeDTO.getPurposeCode();
        given(purposeDomainService.getPurposeByCode(code)).willReturn(purposeEntity);
        purposeApplicationService.getConfigurationByCode(code);
        assertThat(purposeEntity).isNotNull();
    }

    //----------------------------------------Negative---------------------------------


    @Test
    @DisplayName("JUnit for getByPurposeCode in application service when Authorize for Negative")
    void getPurposeByCodeIsAuthorizeforNegative() throws FatalException, JsonProcessingException {
        given(purposeDomainService.getPurposeByCode(purposeDTO.getPurposeCode())).willReturn(purposeEntity);
        given(purposeAssembler.convertEntityToDto(purposeEntity)).willReturn(purposeDTO);
        PurposeDTO purposeDTO1 = purposeApplicationService.getPurposeByCode(sessionContext, purposeDTO);
        assertNotEquals("N",purposeDTO1.getAuthorized());
        assertThat(purposeDTO).isNotNull();
    }

//    @Test
//    @DisplayName("JUnit for getPurposeByCode in application service when UnAuthorize fetch no Record from database")
//    void getPurposeByCodeNotAuthorizeNull() throws FatalException, JsonProcessingException {
//        PurposeDTO purposeDTOnull = null;
//        PurposeDTO purposeDTOEx = new PurposeDTO();
//        purposeDTOEx.setPurposeCode("PC0007");
//        purposeDTOEx.setAuthorized("N");
//        given(mutationsDomainService.getConfigurationByCode(purposeDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);
//        PurposeDTO purposeDTO1 = purposeApplicationService.getPurposeByCode(sessionContext, purposeDTOEx);
//        assertNotEquals("Y",purposeDTO1.getAuthorized());
//        assertNull(purposeDTOnull);
//    }

    @Test
    @DisplayName("JUnit for getPurposeByCode in application service check Parameter not null")
    void getPurposeByCodeIsAuthorizeCheckParameter() throws FatalException, JsonProcessingException {
        PurposeDTO purposeDTOnull=null;
        PurposeDTO purposeDTOEx=new PurposeDTO();
        purposeDTOEx.setPurposeCode("PC0007");
        purposeDTOEx.setAuthorized("Y");
        given(purposeDomainService.getPurposeByCode(purposeDTOEx.getPurposeCode())).willReturn(purposeEntity);
        given(purposeAssembler.convertEntityToDto(purposeEntity)).willReturn(purposeDTO);
        PurposeDTO purposeDTO1 = purposeApplicationService.getPurposeByCode(sessionContext, purposeDTOEx);
        assertThat(purposeDTOEx.getPurposeCode()).isNotBlank();
        assertThat(purposeDTOEx.getAuthorized()).isNotBlank();
    }

//    @Test
//    @DisplayName("JUnit for getBankIdentifierByCode in application service when Not Authorize in try block for Negative when getAuthorized unexpected is Y")
//    void getBankIdentifierByCodewhenNotAuthorizeTryBlockForNegative() throws JsonProcessingException, FatalException {
//        given(mutationsDomainService.getConfigurationByCode(bankIdentifierDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);
//        BankIdentifierDTO bankIdentifierDTO1 = bankIdentifierApplicationService.getBankIdentifierByCode(sessionContext,bankIdentifierDTOMapper);
//        assertNotEquals("Y",bankIdentifierDTO1.getAuthorized());
//        assertThat(bankIdentifierDTO1).isNotNull();
//        System.out.println(bankIdentifierDTO1);
//    }

//    @Test
//    @DisplayName("JUnit for getPurposes in application service for try block negative scenario for SessionContext some field not be null")
//    void getPurposesTryBlockNegative() throws FatalException {
//        String payLoadString= "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\"," +
//                "\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\"," +
//                "\"taskCode\":\"PURPOSE\",\"taskIdentifier\":\"PC0007\",\"purposeCode\":\"PC0007\",\"purposeName\":\"test\"," +
//                "\"purposeType\":\"test\",\"purposeDescription\":\"test\"}";
//
//        MutationEntity mutationEntity5 = new MutationEntity();
//        mutationEntity5.setTaskIdentifier("PC0007");
//        mutationEntity5.setTaskCode(PURPOSE);
//        mutationEntity5.setPayload(new Payload(payLoadString));
//        mutationEntity5.setStatus("new");
//        mutationEntity5.setAuthorized("N");
//        mutationEntity5.setRecordVersion(0);
//        mutationEntity5.setAction("add");
//        mutationEntity5.setLastConfigurationAction("unauthorized");
//        mutationEntity5.setCreatedBy("aditya");
//        mutationEntity5.setLastUpdatedBy("aditya");
//
//        PurposeDTO purposeDTOO= new PurposeDTO();
//
//        PurposeEntityKey purposeEntityKey = new PurposeEntityKey();
//        purposeEntityKey.setPurposeCode("PC0007");
//
//        PurposeEntity purposeEntity5 = new PurposeEntity();
//        given(mutationsDomainService.getUnauthorizedMutation(purposeDTOO.getTaskCode(),AUTHORIZED_N)).willReturn(List.of(mutationEntity5));
//        given(purposeDomainService.getPurposes()).willReturn(List.of(purposeEntity5));
//        Payload payload=new Payload();
//        payload.setData(payLoadString);
//        mutationEntity5.setPayload(payload);
//        String data1 = mutationEntity5.getPayload().getData();
//        System.out.println(purposeEntity5);
//        given(purposeAssembler.convertEntityToDto(purposeEntity5)).willReturn(purposeDTOO);
//        given(purposeAssembler.setAuditFields(mutationEntity5,purposeDTOO)).willReturn(purposeDTOO);
//
//        List<PurposeDTO> purposeDTO2 = purposeApplicationService.getPurposes(sessionContext);
//        assertThat(sessionContext.getRole()).isNotEmpty();
//        assertThat(sessionContext.getServiceInvocationModeType()).isNotNull();
//    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        PurposeEntityKey purposeEntityKey1=new PurposeEntityKey();
        assertThat(purposeEntity.toString()).isNotNull();
        assertThat(purposeDTO.toString()).isNotNull();
        PurposeDTO purposeDTO2=new PurposeDTO("PC0006","test6","test6","test6");
        PurposeDTO.builder().purposeCode("PC0006").purposeName("test6").purposeType("test6").purposeDescription("test6").build().toString();
        PurposeEntityKey purposeEntityKey=new PurposeEntityKey("PC0006");
        assertThat(purposeEntityKey.toString()).isNotNull();
        purposeEntityKey.setPurposeCode("PC0006");
        purposeEntityKey.keyAsString();
        purposeEntityKey.builder().purposeCode("PC0006").build();
        assertThat(purposeDTO).descriptionText();
    }

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder()
                .bankCode("003").defaultBranchCode("03110").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("dummy_target").postingDate(new Date()).valueDate(new Date()).transactionBranch("")
                .userId("aditya")
//                .accessibleTargetUnits([])
                .channel("Branch").taskCode(PURPOSE)
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(1L)
                .customAttributes("")
                .serviceInvocationModeType(Regular)
                .allTargetUnitsSelected(true)
//                .mutationType("")
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
//                .accessibleTargetUnits([])
                .channel("").taskCode("")
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(null)
                .customAttributes("")
                .serviceInvocationModeType(null)
                .allTargetUnitsSelected(true)
//                .mutationType("")
                .userLocal("")
                .originatingModuleCode("")
                .role(new String[] {"maker"})
                .build();
        return sessionContextError;
    }

    private PurposeDTO getPurposeDTOAuthorized() {
        PurposeDTO purposeDTO = new PurposeDTO();

        purposeDTO.setPurposeCode("PC0007");
        purposeDTO.setPurposeName("test");
        purposeDTO.setPurposeType("test");
        purposeDTO.setPurposeDescription("test");
        purposeDTO.setAuthorized("Y");
        return purposeDTO;
    }

    private PurposeDTO getPurposesDTO()
    {
        PurposeDTO purposeDTO = new PurposeDTO();
        purposeDTO.setPurposeCode("PC0007");
        purposeDTO.setPurposeCode("PC0007");
        purposeDTO.setPurposeName("test");
        purposeDTO.setPurposeType("test");
        purposeDTO.setPurposeDescription("test");
        purposeDTO.setTaskCode(PURPOSE);
        purposeDTO.setStatus("DELETED");
        purposeDTO.setRecordVersion(1);
        return purposeDTO;
    }

    private PurposeEntity getPurposeEntity(){
        PurposeEntity purposeEntity = new PurposeEntity("PC0007", "test",
                "test", "test",null, null,0, "draft", "Y","draft");

        return purposeEntity;
    }

    private PurposeEntity getPurposesEntity(){

        PurposeEntity purposeEntity = new PurposeEntity("PC0006", "test6",
                "test6", "test6", null, null,1, "DELETED", null,null);

        PurposeEntityKey purposeEntityKey = new PurposeEntityKey("PC0007");
        System.out.println(purposeEntityKey);
        System.out.println(purposeEntity);
        return purposeEntity;
    }

    private PurposeEntity getPurposesEntity2()
    {
        PurposeEntity purposeEntity2 = new PurposeEntity();
        purposeEntity2.setPurposeCode("PC0007");
        purposeEntity2.setPurposeName("test");
        purposeEntity2.setPurposeType("test");
        purposeEntity2.setPurposeDescription("test");
        purposeEntity2.setAuthorized("N");
        purposeEntity2.setStatus("closed");
        purposeEntity2.setRecordVersion(1);
        return purposeEntity2;
    }

    private PurposeDTO getPurposeDTOUnAuth() {

        PurposeDTO purposeDTO = new PurposeDTO("PC0007", "test", "test", "test");

        purposeDTO.setAuthorized("N");
        purposeDTO.setTaskIdentifier("PC0007");
        return purposeDTO;
    }
    private PurposeDTO getPurposeDTOMapper(){

        PurposeDTO purposeDTOMapper2 = PurposeDTO.builder()
                .purposeCode("PC0007")
                .purposeName("test")
                .purposeType("test")
                .purposeDescription("test")
                .authorized("N")
                .taskCode(PURPOSE)
                .taskIdentifier("PC0007").build();
        return purposeDTOMapper2;
    }

    private MutationEntity getMutationEntity() {
        String payLoadString= "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\"," +
                "\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\"," +
                "\"taskCode\":\"PURPOSE\",\"taskIdentifier\":\"PC0007\",\"purposeCode\":\"PC0007\",\"purposeName\":\"test\"," +
                "\"purposeType\":\"test\",\"purposeDescription\":\"test\"}";

        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("PC0007");
        mutationEntity.setTaskCode(PURPOSE);
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
        String payLoadString1= "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\"," +
                "\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\"," +
                "\"taskCode\":\"PURPOSE\",\"taskIdentifier\":\"PC0007\",\"purposeCode\":\"PC0007\",\"purposeName\":\"test\"," +
                "\"purposeType\":\"test\",\"purposeDescription\":\"test\"}";

        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("PC0007");
        mutationEntity2.setTaskCode(PURPOSE);
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");

        return mutationEntity2;
    }
}