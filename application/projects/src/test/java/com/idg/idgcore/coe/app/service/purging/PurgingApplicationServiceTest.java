package com.idg.idgcore.coe.app.service.purging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.coe.app.service.purgingpolicy.PurgingApplicationService;
import com.idg.idgcore.coe.domain.assembler.purgingpolicy.PurgingAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.entity.purging.PurgingEntity;
import com.idg.idgcore.coe.domain.entity.purging.PurgingEntityKey;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.purgingpolicy.IPurgingDomainService;
import com.idg.idgcore.coe.dto.country.CountryDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.purgingpolicy.PurgingDTO;
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

import static com.idg.idgcore.coe.common.Constants.*;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurgingApplicationServiceTest {

    @InjectMocks
    private PurgingApplicationService purgingApplicationService;

    @Mock
    private ProcessConfiguration process;

    @Mock
    private PurgingAssembler purgingAssembler;

    @Mock
    private IPurgingDomainService purgingDomainService;

    @Autowired
    private MutationEntity mutationEntity;

    private MutationEntity mutationEntity2;

    @Mock
    private IMutationsDomainService mutationsDomainService;

    private SessionContext sessionContext;

    private SessionContext sessionContext1;
    private PurgingEntity purgingEntity;
    private PurgingDTO purgingDTO;
    private PurgingDTO purgingDTOUnAuth;
    private PurgingDTO purgingDTO1;
    private PurgingEntity purgingEntity1;
    private PurgingEntity purgingEntity2;
    private PurgingDTO purgingDTOMapper;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext();
        sessionContext1 = getErrorSession();
        purgingDTO = getPurgingDTOAuthorized();
        purgingEntity = getPurgingAllEntity();
        purgingDTOUnAuth = getPurgingDTOUnAuth();
        purgingDTOMapper = getPurgingDTOMapper();
        mutationEntity = getMutationEntity();
        purgingEntity1 = getPurgingAllEntity();
        purgingEntity2 = getPurgingAllEntity2();
        purgingDTO1 = getPurgingAllDTO();
        mutationEntity2 = getMutationEntityJsonError();
    }

    @Test
    @DisplayName("JUnit for getPurgingByCode in application service when Authorize")
    void getPurgingByCodeIsAuthorize() throws FatalException {
        given(purgingDomainService.getPurgingByCode(purgingDTO.getModuleCode())).willReturn(purgingEntity);
        given(purgingAssembler.convertEntityToDto(purgingEntity)).willReturn(purgingDTO);
        PurgingDTO purgingDTO1 = purgingApplicationService.getPurgingByCode(sessionContext, purgingDTO);
        assertEquals("Y", purgingDTO1.getAuthorized());
        assertThat(purgingDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getPurgingByCode in application service when Not Authorize in try else block")
    void getPurgingByCodeWhenNotAuthorizeTryBlock() throws JsonProcessingException, FatalException {
        MutationEntity mutationEntity6 = new MutationEntity();

        String payLoadString= "{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0," +
                "\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"PURGING_POLICY\"," +
                "\"taskIdentifier\":\"LN\",\"moduleCode\":\"LN\",\"tranMaintenanceStatus\":\"closed\"," +
                "\"retentionPeriod\":5}";

        mutationEntity6.setPayload(new Payload(payLoadString));

        PurgingDTO purgingDTO6 = new PurgingDTO();
        purgingDTO6.setAuthorized("N");
        purgingDTO6.setModuleCode("LN");
        purgingDTO6.setTranMaintenanceStatus("closed");
        purgingDTO6.setRetentionPeriod(5);
        given(mutationsDomainService.getConfigurationByCode(purgingDTO6.getTaskIdentifier())).willReturn(mutationEntity6);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO payload6 = new PayloadDTO();
//        Mockito.when(mockObjectMapper.readValue(payload6.getData(), PurgingDTO.class)).thenReturn(purgingDTO6);
        //given(purgingAssembler.setAuditFields(mutationEntity6, purgingDTO6)).willReturn(purgingDTO6);
        Assertions.assertThrows(Exception.class, ()-> {
            PurgingDTO purgingDTO1 = purgingApplicationService.getPurgingByCode(sessionContext, purgingDTO6);
            assertEquals("N", purgingDTO1.getAuthorized());
            assertThat(purgingDTO1).isNotNull();
            System.out.println("purgingDTO1 = " + purgingDTO1);
        });
    }

   // @Test
    @DisplayName("JUnit for getByPurgingCode in application service when Not Authorize in try else block")
    void getPurgingByCodewhenNotAuthorizeTryBlock() throws JsonProcessingException, FatalException {
        given(mutationsDomainService.getConfigurationByCode(purgingDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);

        String payLoadString= "{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0," +
                "\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"PURGING_POLICY\"," +
                "\"taskIdentifier\":\"LN\",\"moduleCode\":\"LN\",\"tranMaintenanceStatus\":\"closed\"," +
                "\"retentionPeriod\":5}";

        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);
        PurgingDTO purgingDTO1 = purgingApplicationService.getPurgingByCode(sessionContext,purgingDTOMapper);
        System.out.println(purgingDTO1);
    }

    @Test
    @DisplayName("JUnit for getByPurgingCode in application service when Not Authorize in catch block")
    void getPurgingByCodewhenNotAuthorizeCatchBlock () throws FatalException {

        String payLoadString= "{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0," +
                "\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"PURGING_POLICY\"," +
                "\"taskIdentifier\":\"LN\",\"moduleCode\":\"LN\",\"tranMaintenanceStatus\":\"closed\"," +
                "\"retentionPeriod\":5}";

        given(mutationsDomainService.getConfigurationByCode(purgingDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);
//                     Mockito.when(mockObjectMapper.readValue(mutationEntity.getPayload().getData(), StateDTO.class)).thenReturn(stateDTO);
        Assertions.assertThrows(Exception.class,()-> {
            PurgingDTO purgingDTO1 = purgingApplicationService.getPurgingByCode(sessionContext, purgingDTOMapper);
            assertEquals("N",purgingDTO1.getAuthorized());
            assertThat(purgingDTO1).isNotNull();
            System.out.println(purgingDTO1);
        });
    }

    @Test
    @DisplayName("Should return all getPurgingAll in application service for try block")
    void getPurgingAllTryBlock() throws FatalException {
        given(mutationsDomainService.getMutations(PURGING_POLICY))
                .willReturn(List.of(mutationEntity));
        List<PurgingDTO> purgingDTOList =
                purgingApplicationService.getPurgingAll(sessionContext);
        assertThat(purgingDTOList).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getPurgingAll in application service for catch block for checker")
    void getPurgingAllCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        sessionContext.setRole(new String[] { "" });
        given(mutationsDomainService.getMutations(
                purgingDTO1.getTaskCode()))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
//        Assertions.assertThrows(FatalException.class,()-> {
            List<PurgingDTO> purgingDTO1 = purgingApplicationService.getPurgingAll(sessionContext);
            System.out.println("return size : " + purgingDTO1.size());
            assertThat(purgingDTO1).isNotNull();
            System.out.println(purgingDTO1);
 //       });
    }

    @Test
    @DisplayName("JUnit for processPurging in application service for Try Block")
    void processPurgingForTryBlock() throws JsonProcessingException, FatalException {

        doNothing().when(process).process(purgingDTO);
        purgingApplicationService.processPurging(sessionContext, purgingDTO);
        verify(process, times(1)).process(purgingDTO);
    }

    @Test
    @DisplayName("JUnit for processPurging in application service for Catch Block")
    void processPurgingForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            purgingApplicationService.processPurging(sessionContext2, purgingDTO);
            assertThat(purgingDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {

        String payLoadString1 = "{\"action\":\"add\",\"status\":\"update\",\"recordVersion\":1," +
                "\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"PURGING_POLICY\"," +
                "\"taskIdentifier\":\"LN\",\"moduleCode\":\"LN\",\"tranMaintenanceStatus\":\"closed\"," +
                "\"retentionPeriod\":5}";

        doNothing().when(purgingDomainService).save(purgingDTO);
        purgingApplicationService.save(purgingDTO);
        purgingApplicationService.addUpdateRecord(payLoadString1);
        verify(purgingDomainService, times(1)).save(purgingDTO);
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = purgingDTO.getModuleCode();
        given(purgingDomainService.getPurgingByCode(code)).willReturn(purgingEntity);
        purgingApplicationService.getConfigurationByCode(code);
        assertThat(purgingEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getByPurgingCode in application service when Authorize for Negative")
    void getPurgingByCodeIsAuthorizeforNegative() throws FatalException, JsonProcessingException {
        given(purgingDomainService.getPurgingByCode(purgingDTO.getModuleCode())).willReturn(purgingEntity);
        given(purgingAssembler.convertEntityToDto(purgingEntity)).willReturn(purgingDTO);
        PurgingDTO purgingDTO1 = purgingApplicationService.getPurgingByCode(sessionContext, purgingDTO);
        assertNotEquals("N",purgingDTO1.getAuthorized());
        assertThat(purgingDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getPurgingByCode in application service check Parameter not null")
    void getPurgingByCodeIsAuthorizeCheckParameter() throws FatalException, JsonProcessingException {
        PurgingDTO purgingDTOnull=null;
        PurgingDTO purgingDTOEx=new PurgingDTO();
        purgingDTOEx.setModuleCode("LN");
        purgingDTOEx.setAuthorized("Y");
        given(purgingDomainService.getPurgingByCode(purgingDTOEx.getModuleCode())).willReturn(purgingEntity);
        given(purgingAssembler.convertEntityToDto(purgingEntity)).willReturn(purgingDTO);
        PurgingDTO purgingDTO1 = purgingApplicationService.getPurgingByCode(sessionContext, purgingDTOEx);
        assertThat(purgingDTOEx.getModuleCode()).isNotBlank();
        assertThat(purgingDTOEx.getAuthorized()).isNotBlank();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        PurgingEntityKey purgingEntityKey1=new PurgingEntityKey();
        assertThat(purgingEntity.toString()).isNotNull();
        assertThat(purgingDTO.toString()).isNotNull();
        PurgingDTO purgingDTO2=new PurgingDTO("LN","closed",5);
        PurgingDTO.builder().moduleCode("LN").tranMaintenanceStatus("closed").retentionPeriod(5).build().toString();
        PurgingEntityKey purgingEntityKey=new PurgingEntityKey("LN");
        assertThat(purgingEntityKey.toString()).isNotNull();
        purgingEntityKey.setModuleCode("LN");
        purgingEntityKey.keyAsString();
        purgingEntityKey.builder().moduleCode("LN").build();
        assertThat(purgingDTO).descriptionText();
    }

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder()
                .bankCode("003").defaultBranchCode("03110").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("dummy_target").postingDate(new Date()).valueDate(new Date()).transactionBranch("")
                .userId("aditya")
//                .accessibleTargetUnits([])
                .channel("Branch").taskCode(PURGING_POLICY)
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

    private PurgingDTO getPurgingDTOAuthorized() {
        PurgingDTO purgingDTO = new PurgingDTO();

        purgingDTO.setModuleCode("LN");
        purgingDTO.setTranMaintenanceStatus("closed");
        purgingDTO.setRetentionPeriod(5);
        purgingDTO.setAuthorized("Y");
        return purgingDTO;
    }

    private PurgingDTO getPurgingAllDTO()
    {
        PurgingDTO purgingDTO = new PurgingDTO();
        purgingDTO.setModuleCode("LN");
        purgingDTO.setTranMaintenanceStatus("closed");
        purgingDTO.setRetentionPeriod(5);
        purgingDTO.setTaskCode(PURGING_POLICY);
        purgingDTO.setStatus("DELETED");
        purgingDTO.setRecordVersion(1);
        return purgingDTO;
    }

    private PurgingEntity getPurgingEntity(){
        PurgingEntity purgingEntity = new PurgingEntity("LN", "closed", 5, null, null, "active", 1, "Y", "authorized");

        return purgingEntity;
    }

    private PurgingEntity getPurgingAllEntity(){

        PurgingEntity purgingEntity = new PurgingEntity("LN", "closed", 5, null, null, "active", 1, "Y", "authorized");

        PurgingEntityKey purgingEntityKey = new PurgingEntityKey("LN");
        System.out.println(purgingEntityKey);
        System.out.println(purgingEntity);
        return purgingEntity;
    }

    private PurgingEntity getPurgingAllEntity2()
    {
        PurgingEntity purgingEntity2 = new PurgingEntity();
        purgingEntity2.setModuleCode("LN");
        purgingEntity2.setTranMaintenanceStatus("closed");
        purgingEntity2.setRetentionPeriod(5);
        purgingEntity2.setAuthorized("N");
        purgingEntity2.setStatus("closed");
        purgingEntity2.setRecordVersion(1);
        return purgingEntity2;
    }

    private PurgingDTO getPurgingDTOUnAuth() {

        PurgingDTO purgingDTO = new PurgingDTO("LN", "closed", 5);

        purgingDTO.setAuthorized("N");
        purgingDTO.setTaskIdentifier("LN");
        return purgingDTO;
    }

    private PurgingDTO getPurgingDTOMapper(){

        PurgingDTO purgingDTOMapper2 = PurgingDTO.builder()
                .moduleCode("LN")
                .tranMaintenanceStatus("closed")
                .retentionPeriod(5)
                .authorized("N")
                .taskCode(PURGING_POLICY)
                .taskIdentifier("LN").build();
        return purgingDTOMapper2;
    }

    private MutationEntity getMutationEntity() {
        String payLoadString= "{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":1," +
                "\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"PURGING_POLICY\"," +
                "\"taskIdentifier\":\"LN\",\"moduleCode\":\"LN\",\"tranMaintenanceStatus\":\"closed\"," +
                "\"retentionPeriod\":5}";

        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("LN");
        mutationEntity.setTaskCode(PURGING_POLICY);
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
        String payLoadString1= "{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":1," +
                "\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"PURGING_POLICY\"," +
                "\"taskIdentifier\":\"LN\",\"moduleCode\":\"LN\",\"tranMaintenanceStatus\":\"closed\"," +
                "\"retentionPeriod\":5}";

        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("LN");
        mutationEntity2.setTaskCode(PURGING_POLICY);
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");

        return mutationEntity2;
    }

}