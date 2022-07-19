package com.idg.idgcore.coe.app.service.purpose;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.coe.domain.assembler.purpose.PurposeAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.entity.purpose.PurposeEntity;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.purpose.IPurposeDomainService;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.purpose.PurposeDTO;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
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

import java.util.Date;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Autowired
    private MutationEntity mutationEntity;

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

    private PurposeDTO purposeDTOMapper;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext ();
        sessionContext1=getErrorSession();
        purposeDTO=getpurposeDTOAuthorized ();
        purposeEntity=getPurposeEntity();
        purposeDTOUnAuth=getPurposeDTOUnAuth();
        purposeDTOMapper=getPurposeDTOMapper();
        mutationEntity=getMutationEntity();
        purposeEntity1=getPurposeEntity();
        purposeDTO1=getPurposesDTO();
        mutationEntity2=getMutationEntityJsonError();
    }

    @Test
    @DisplayName("JUnit for getPurposeByCode in application service when Authorize")
    void getPurposeByCodeIsAuthorize() throws FatalException {
        given(purposeDomainService.getPurposeByCode(purposeDTO.getPurposeCode())).willReturn(purposeEntity);
        given(purposeAssembler.convertEntityToDto(purposeEntity)).willReturn(purposeDTO);
        PurposeDTO purposeDTO1 = purposeApplicationService.getPurposeByCode(sessionContext, purposeDTO);
        assertEquals("Y" ,purposeDTO1.getAuthorized());
        assertThat(purposeDTO).isNotNull();
    }

//    @Test
//    @DisplayName("JUnit for getPurposeByCode in application service when Not Authorize in try block")
//    void getPurposeByCodewhenNotAuthorizeTryBlock() throws JsonProcessingException, FatalException {
//        given(mutationsDomainService.getConfigurationByCode(purposeDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);
//        PurposeDTO purposeDTO1 = purposeApplicationService.getPurposeByCode(sessionContext, purposeDTOMapper);
//        System.out.println("purposeDTOMapper--------------"+purposeDTOMapper);
//        assertEquals("N", purposeDTO1.getAuthorized());
//        assertThat(purposeDTO1).isNotNull();
//        System.out.println(purposeDTO1);
//    }

    @Test
    @DisplayName("JUnit for getByPurposeCode in application service when Not Authorize in catch block")
    void getPurposeByCodewhenNotAuthorizeCatchBlock () throws FatalException, JsonProcessingException {
        String payLoadString1 = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null," +
                "\"action\":\"add\",\"status\":\"new\",\"recordVersion\":HHHHH,\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\"," +
                "\"purposeCode\":\"PC0014\",\"purposeName\":\"test\",\"purposeDescription\":\"test\",\"purposeType\":\"test\"," +
                "\"taskCode\":\"PURPOSE\",\"taskIdentifier\":\"PC0014\"}";

        given(mutationsDomainService.getConfigurationByCode(purposeDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = mock(PayloadDTO.class);
//                     Mockito.when(mockObjectMapper.readValue(mutationEntity.getPayload().getData(), StateDTO.class)).thenReturn(stateDTO);
        Assertions.assertThrows(BusinessException.class,()-> {
            PurposeDTO purposeDTO1 = purposeApplicationService.getPurposeByCode(sessionContext, purposeDTOMapper);
            assertEquals("N",purposeDTO1.getAuthorized());
            assertThat(purposeDTO1).isNotNull();
            System.out.println(purposeDTO1);
        });
    }

//    @Test
//    @DisplayName("JUnit for getPurposes in application service for try block")
//    void getPurposesTryBlock() throws JsonProcessingException, FatalException {
//        given(purposeDomainService.getPurposes()).willReturn(List.of(purposeEntity1));
//        given(mutationsDomainService.getUnauthorizedMutation(purposeDTO1.getTaskCode(), AUTHORIZED_N)).willReturn(List.of(mutationEntity));
//        String payLoadString = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null," +
//                "\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\"," +
//                "\"purposeCode\":\"PC0014\",\"purposeName\":\"test\",\"purposeDescription\":\"test\",\"purposeType\":\"test\"," +
//                "\"taskCode\":\"PURPOSE\",\"taskIdentifier\":\"PC0014\"}";
//
//        Payload payload=new Payload();
//        payload.setData(payLoadString);
//        mutationEntity.setPayload(payload);
//        String data1 = mutationEntity.getPayload().getData();
//        given(purposeAssembler.convertEntityToDto(purposeEntity1)).willReturn(purposeDTO1);
//        System.out.println(purposeDTO1);
//        List<PurposeDTO> stateDTO2 = purposeApplicationService.getPurposes(sessionContext);
//        System.out.println(purposeDTO1);
//        assertThat(purposeDTO1).isNotNull();
//        System.out.println(purposeDTO1);
//    }

    @Test
    @DisplayName("JUnit for processPurpose in application service for Try Block")
    void processPurposeForTryBlock() throws JsonProcessingException, FatalException {

        doNothing().when(process).process(purposeDTO);
        purposeApplicationService.processPurpose(sessionContext, purposeDTO);
        verify(process, times(1)).process(purposeDTO);
    }

    @Test
    @DisplayName("JUnit for processState in application service for Catch Block")
    void processStateForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            purposeApplicationService.processPurpose(sessionContext2, purposeDTO);
            assertThat(purposeDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for getPurposes in application service for catch block")
    void getPurposesCatchBlock() throws FatalException {
        given(purposeDomainService.getPurposes()).willReturn(List.of(purposeEntity1));
        given(mutationsDomainService.getUnauthorizedMutation(purposeDTO1.getTaskCode(), AUTHORIZED_N)).willReturn(List.of(mutationEntity2));
        given(purposeAssembler.convertEntityToDto(purposeEntity1)).willReturn(purposeDTOMapper);
        System.out.println(purposeDTO1);
        Assertions.assertThrows(Exception.class,()-> {
            List<PurposeDTO> purposeDTO2 = purposeApplicationService.getPurposes(sessionContext1);
            System.out.println(purposeDTO2);
            assertThat(purposeDTO2).isNotNull();
            System.out.println(purposeDTO2);
        });
    }


/*

  @Test
    @DisplayName("JUnit for processState in application service for Catch Block")
    void processStateForCatchBlock() throws JsonProcessingException, FatalException {
        StateDTO stateDTO12=new StateDTO();
        MappingDTO mappingDTO=new MappingDTO();
        mappingDTO.setStatus("closed");
        mappingDTO.setAuthorized("hhhhhhhh");
        mappingDTO.setRole("defaulter");

        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"STATE\",\"taskIdentifier\":\"MH\",\"stateCode\":\"MH\",\"stateName\":\"MAHARASHTRA\",\"countryCode\":\"IN\"}";

        PayloadDTO payload=new PayloadDTO(payLoadString1);

        MutationDTO mutationDTO=new MutationDTO();
        mutationDTO.setAuthorized("hhhhhhhh");
        mutationDTO.setPayload(payload);
        mutationDTO.setTaskCode("MH");
        mutationDTO.setAction("closed");
        mutationDTO.setRecordVersion(5);

        sessionContext1=SessionContext.builder()
                .bankCode("").defaultBranchCode("").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("").postingDate(new Date()).valueDate(new Date()).transactionBranch("")
                .userId("prash")
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
                .role(null)
                .build();

        stateDTO12.setStateCode("MH");
        stateDTO12.setStateName("MAHARASHTRA");
        stateDTO12.setCountryCode("IN");
        stateDTO12.setTaskCode("MH");
        stateDTO12.setStatus(null);
        stateDTO12.setRecordVersion(5);




        doNothing().when(process).process(stateDTO12);
//        doThrow(process).thenThrow(FatalException.class);


//        given(process.process(stateDTO12)).willThrow(FatalException.class);
//        Mockito.when().thenThrow(FatalException.class);
//        Assertions.assertThrows(Exception.class,()-> {
            stateApplicationService.processState(sessionContext, stateDTO12);
            verify(process, times(1)).process(stateDTO);
//        });


    }

 */

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = purposeDTO.getTaskIdentifier();
        given(purposeDomainService.getPurposeByCode(code)).willReturn(purposeEntity);
        purposeApplicationService.getConfigurationByCode(code);
        assertThat(purposeEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {
        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null," +
                "\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\"," +
                "\"purposeCode\":\"PC0014\",\"purposeName\":\"test\",\"purposeDescription\":\"test\",\"purposeType\":\"test\"," +
                "\"taskCode\":\"PURPOSE\",\"taskIdentifier\":\"PC0014\"}";

        doNothing().when(purposeDomainService).save(purposeDTO);
        purposeApplicationService.save(purposeDTO);
        purposeApplicationService.addUpdateRecord(payLoadString1);
        verify(purposeDomainService, times(1)).save(purposeDTO);

    }

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder()
                .bankCode("").defaultBranchCode("").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("").postingDate(new Date()).valueDate(new Date()).transactionBranch("")
                .userId("aditya")
//                .accessibleTargetUnits([])
                .channel("").taskCode("")
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(1L)
                .customAttributes("")
                .serviceInvocationModeType(ServiceInvocationModeType.Regular)
                .allTargetUnitsSelected(true)
//                .mutationType("")
                .userLocal("")
                .originatingModuleCode("")
                .role(new String[]{"maker"}).build();
        return sessionContext;
    }


    private SessionContext getErrorSession(){
        SessionContext sessionContext1=SessionContext.builder()
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
        return sessionContext1;
    }

    private PurposeDTO getpurposeDTOAuthorized () {
        PurposeDTO purposeDTO = new PurposeDTO();
        purposeDTO.setPurposeCode("PC0014");
        purposeDTO.setPurposeName("test");
        purposeDTO.setPurposeDescription("test");
        purposeDTO.setPurposeType("test");
        purposeDTO.setAuthorized("Y");
        return purposeDTO;
    }
    private PurposeDTO getPurposesDTO()
    {
        PurposeDTO purposeDTO = new PurposeDTO();
        purposeDTO.setPurposeCode("PC0014");
        purposeDTO.setPurposeName("test");
        purposeDTO.setPurposeType("test");
        purposeDTO.setPurposeType("test");
        purposeDTO.setStatus("DELETED");
        purposeDTO.setRecordVersion(1);
        return purposeDTO;
    }
    private PurposeEntity getPurposeEntity(){
        PurposeEntity purposeEntity = new PurposeEntity();
        purposeEntity.setPurposeCode("PC0014");
        purposeEntity.setPurposeName("test");
        purposeEntity.setPurposeDescription("test");
        purposeEntity.setPurposeType("test");
        purposeEntity.setStatus("DELETED");
        purposeEntity.setRecordVersion(1);
        return purposeEntity;
    }
    private PurposeDTO getPurposeDTOUnAuth(){
        PurposeDTO purposeDTO = new PurposeDTO();
        purposeDTO.setPurposeCode("PC0014");
        purposeDTO.setPurposeName("test");
        purposeDTO.setPurposeDescription("test");
        purposeDTO.setPurposeType("test");
        purposeDTO.setAuthorized("N");
        return purposeDTO;
    }

    private PurposeDTO getPurposeDTOMapper(){
        PurposeDTO purposeDTOMapper= new PurposeDTO();
        purposeDTOMapper.setPurposeCode("PC0014");
        purposeDTOMapper.setPurposeName("test");
        purposeDTOMapper.setPurposeDescription("test");
        purposeDTOMapper.setPurposeType("test");
        purposeDTOMapper.setAuthorized("N");
        purposeDTOMapper.setTaskCode("PURPOSE");
        purposeDTOMapper.setTaskIdentifier("PC0014");
        return purposeDTOMapper;
    }

    private MutationEntity getMutationEntity() {
        String payLoadString= "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null," +
                "\"action\":\"add\",\"status\":\"new\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\"," +
                "\"purposeCode\":\"PC0014\",\"purposeName\":\"test\",\"purposeDescription\":\"test\",\"purposeType\":\"test\"," +
                "\"taskCode\":\"PURPOSE\",\"taskIdentifier\":\"PC0014\"}";

        MutationEntity mutationEntity = new MutationEntity();

        mutationEntity.setTaskIdentifier("PC0014");
        mutationEntity.setTaskCode("PURPOSE");
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
        String payLoadString1 = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null," +
                "\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":HHHH,\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\"," +
                "\"purposeCode\":\"PC0014\",\"purposeName\":\"test\",\"purposeDescription\":\"test\",\"purposeType\":\"test\"," +
                "\"taskCode\":\"PURPOSE\",\"taskIdentifier\":\"PC0014\"}";

        MutationEntity mutationEntity2 = new MutationEntity();

        mutationEntity2.setTaskIdentifier("MH");
        mutationEntity2.setTaskCode("STATE");
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");
        return mutationEntity2;
    }
}
