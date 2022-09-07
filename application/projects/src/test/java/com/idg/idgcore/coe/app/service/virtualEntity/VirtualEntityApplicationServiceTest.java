package com.idg.idgcore.coe.app.service.virtualEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.coe.app.service.virtualentity.VirtualEntityApplicationService;
import com.idg.idgcore.coe.domain.assembler.virtualentity.VirtualEntityAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.entity.virtualentity.VirtualEntity;
import com.idg.idgcore.coe.domain.entity.virtualentity.VirtualEntityKey;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.virtualentity.IVirtualEntityDomainService;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.virtualentity.VirtualEntityDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
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

import static com.idg.idgcore.coe.common.Constants.*;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class VirtualEntityApplicationServiceTest {

    @InjectMocks
    private VirtualEntityApplicationService virtualEntityApplicationService;

    @Mock
    private ProcessConfiguration process;

    @Mock
    private VirtualEntityAssembler virtualEntityAssembler;

    @Mock
    private IVirtualEntityDomainService virtualEntityDomainService;

    @Autowired
    private MutationEntity mutationEntity;

    private MutationEntity mutationEntity2;

    @Mock
    private IMutationsDomainService mutationsDomainService;

    private SessionContext sessionContext;

    private SessionContext sessionContext1;
    private VirtualEntity virtualEntity;
    private VirtualEntityDTO virtualEntityDTO;
    private VirtualEntityDTO virtualEntityDTOUnAuth;
    private VirtualEntityDTO virtualEntityDTO1;
    private VirtualEntity virtualEntity1;
    private VirtualEntity virtualEntity2;
    private VirtualEntityDTO virtualEntityDTOMapper;

    private MutationEntity mutationEntity3;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext();
        sessionContext1 = getErrorSession();
        virtualEntityDTO = getVirtualEntityDTOAuthorized();
        virtualEntityDTOUnAuth = getVirtualEntityDTOUnAuth();
        virtualEntityDTOMapper = getVirtualEntityDTOMapper();
        mutationEntity = getMutationEntity();
        virtualEntityDTO1 = getVirtualEntityAllDTO();
        mutationEntity2 = getMutationEntityJsonError();
        mutationEntity3 = getMutationEntityUnauthorize();
    }

    @Test
    @DisplayName("JUnit for getVirtualEntityByCode in application service when Authorize")
    void getVirtualEntityByCodeIsAuthorize() throws FatalException {
        given(virtualEntityDomainService.getByVirtualEntityCode(virtualEntityDTO.getEntityCode())).willReturn(virtualEntity);
        given(virtualEntityAssembler.convertEntityToDto(virtualEntity)).willReturn(virtualEntityDTO);
        VirtualEntityDTO virtualEntityDTO1 = virtualEntityApplicationService.getVirtualEntityByEntityCode(sessionContext, virtualEntityDTO);
        assertEquals("Y", virtualEntityDTO1.getAuthorized());
        assertThat(virtualEntityDTO).isNotNull();
    }

//    @Test
//    @DisplayName("JUnit for getByVirtualEntityCode in application service when Not Authorize in try else block")
//    void getByVirtualEntityCodeWhenNotAuthorizeTryBlock() throws JsonProcessingException, FatalException {
//
//        VirtualEntityDTO virtualEntity3 = new VirtualEntityDTO();
//        virtualEntity3.setEntityType("MKE");
//        virtualEntity3.setEntityCode("MKE01");
//        virtualEntity3.setEntityName("MKE01");
//        virtualEntity3.setParentEntityType("Legal Entity");
//        virtualEntity3.setParentEntityCode("LE");
//        virtualEntity3.setIsDefault(true);
//        virtualEntity3.setTaskIdentifier("MKE01");
//        virtualEntity3.setAuthorized("N");
//        given(mutationsDomainService.getConfigurationByCode(virtualEntity3.getTaskIdentifier())).willReturn(mutationEntity3);
//        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
//        PayloadDTO payload6 = new PayloadDTO(mutationEntity3.getPayload().getData());
//        System.out.println("payload6 = " + payload6);
////        Mockito.when(mockObjectMapper.readValue(payload6.getData(), VirtualEntityDTO.class)).thenReturn(virtualEntity3);
//        given(virtualEntityAssembler.setAuditFields(mutationEntity3, virtualEntity3)).willReturn(virtualEntity3);
//
//        VirtualEntityDTO virtualEntityDTO5 = virtualEntityApplicationService.getVirtualEntityByEntityCode(sessionContext,virtualEntity3);
//        assertEquals("N",virtualEntity3.getAuthorized());
//        assertThat(virtualEntity3).isNotNull();
//    }

    @Test
    @DisplayName("JUnit for getByEntityCode in application service when Not Authorize in catch block")
    void getViirtualEntityByEntityCodewhenNotAuthorizeCatchBlock () throws FatalException {

        String payLoadString= "{\"action\":\"draft\",\"status\":\"active\",\"recordVersion\":1," +
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"draft\",\"taskCode\":\"VIRTUAL_ENTITY\"," +
                "\"taskIdentifier\":\"MKE01\",\"entityType\":\"MKE\",\"entityCode\":\"MKE01\",\"entityName\":\"MKE01\"," +
                "\"parentEntityType\":\"Legal Entity\",\"parentEntityCode\":\"LE01\",\"isDefault\":true," +
                "\"effectiveDate\":\"08-08-2022\"}";

        given(mutationsDomainService.getConfigurationByCode(virtualEntityDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);
        Assertions.assertThrows(Exception.class,()-> {
            VirtualEntityDTO virtualEntityDTO1 = virtualEntityApplicationService.getVirtualEntityByEntityCode(sessionContext, virtualEntityDTOMapper);
            assertEquals("N",virtualEntityDTO1.getAuthorized());
            assertThat(virtualEntityDTO1).isNotNull();
            System.out.println(virtualEntityDTO1);
        });
    }

    @Test
    @DisplayName("Should return all getVirtualEntityAll when there are no unauthorized")
    void getVirtualEntityAllWhenThereAreNoUnauthorized() throws FatalException {
        VirtualEntity virtualEntity = new VirtualEntity();
        given(virtualEntityDomainService.getVirtualEntityAll()).willReturn(List.of(virtualEntity));
        given(mutationsDomainService.getUnauthorizedMutation(VIRTUAL_ENTITY, AUTHORIZED_N)).willReturn(List.of());
        given(virtualEntityAssembler.convertEntityToDto(virtualEntity)).willReturn(virtualEntityDTO);
        List<VirtualEntityDTO> virtualEntityDTOList = virtualEntityApplicationService.getVirtualEntityAll(sessionContext);
        assertEquals(1, virtualEntityDTOList.size());
        assertEquals(virtualEntityDTO, virtualEntityDTOList.get(0));
    }

    @Test
    @DisplayName("JUnit for getVirtualEntityAll in application service for catch block for checker")
    void getVirtualEntityAllCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        sessionContext.setRole(new String[] { "" });
        given(mutationsDomainService.getUnauthorizedMutation(
                virtualEntityDTO1.getTaskCode(),AUTHORIZED_N))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
        Assertions.assertThrows(FatalException.class,()-> {
            List<VirtualEntityDTO> virtualEntityDTO1 = virtualEntityApplicationService.getVirtualEntityAll(sessionContext);
            System.out.println("return size : " + virtualEntityDTO1.size());
            assertThat(virtualEntityDTO1).isNotNull();
            System.out.println(virtualEntityDTO1);
        });
    }

    @Test
    @DisplayName("JUnit for processVirtualEntity in application service for Try Block")
    void processVirtualEntityForTryBlock() throws JsonProcessingException, FatalException {

        doNothing().when(process).process(virtualEntityDTO);
        virtualEntityApplicationService.processVirtualEntity(sessionContext, virtualEntityDTO);
        verify(process, times(1)).process(virtualEntityDTO);
    }

    @Test
    @DisplayName("JUnit for processVirtualEntity in application service for Catch Block")
    void processVirtualEntityForCatchBlock() {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            virtualEntityApplicationService.processVirtualEntity(sessionContext2, virtualEntityDTO);
            assertThat(virtualEntityDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {

        String payLoadString1 = "{\"action\":\"draft\",\"status\":\"active\",\"recordVersion\":1," +
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"draft\",\"taskCode\":\"VIRTUAL_ENTITY\"," +
                "\"taskIdentifier\":\"MKE01\",\"entityType\":\"MKE\",\"entityCode\":\"MKE01\",\"entityName\":\"MKE01\"," +
                "\"parentEntityType\":\"Legal Entity\",\"parentEntityCode\":\"LE01\",\"isDefault\":true," +
                "\"effectiveDate\":\"08-08-2022\"}";

        doNothing().when(virtualEntityDomainService).save(virtualEntityDTO);
        virtualEntityApplicationService.save(virtualEntityDTO);
        virtualEntityApplicationService.addUpdateRecord(payLoadString1);
        verify(virtualEntityDomainService, times(1)).save(virtualEntityDTO);
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = virtualEntityDTO.getEntityCode();
        given(virtualEntityDomainService.getByVirtualEntityCode(code)).willReturn(virtualEntity);
        virtualEntityApplicationService.getConfigurationByCode(code);
    }

    @Test
    @DisplayName("JUnit for getByEntityCode in application service when Authorize for Negative")
    void getVirtualEntityByEntityCodeIsAuthorizeforNegative() throws FatalException, JsonProcessingException {
        given(virtualEntityDomainService.getByVirtualEntityCode(virtualEntityDTO.getEntityCode())).willReturn(virtualEntity);
        given(virtualEntityAssembler.convertEntityToDto(virtualEntity)).willReturn(virtualEntityDTO);
        VirtualEntityDTO virtualEntityDTO1 = virtualEntityApplicationService.getVirtualEntityByEntityCode(sessionContext, virtualEntityDTO);
        assertNotEquals("N",virtualEntityDTO1.getAuthorized());
        assertThat(virtualEntityDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getVirtualEntityByEntityCode in application service check Parameter not null")
    void getVirtualEntityByEntityCodeIsAuthorizeCheckParameter() throws FatalException, JsonProcessingException {
        VirtualEntityDTO virtualEntityDTONull = null;
        VirtualEntityDTO virtualEntityDTOEx = new VirtualEntityDTO();
        virtualEntityDTOEx.setEntityCode("MKE01");
        virtualEntityDTOEx.setAuthorized("Y");
        given(virtualEntityDomainService.getByVirtualEntityCode(virtualEntityDTOEx.getEntityCode())).willReturn(virtualEntity);
        given(virtualEntityAssembler.convertEntityToDto(virtualEntity)).willReturn(virtualEntityDTO);
        VirtualEntityDTO virtualEntityDTO1 = virtualEntityApplicationService.getVirtualEntityByEntityCode(sessionContext, virtualEntityDTOEx);
        assertThat(virtualEntityDTOEx.getEntityCode()).isNotBlank();
        assertThat(virtualEntityDTOEx.getAuthorized()).isNotBlank();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        VirtualEntityKey virtualEntityKey1=new VirtualEntityKey();
        VirtualEntity virtualEntity = new VirtualEntity();
        assertThat(virtualEntity.toString()).isNotNull();
        assertThat(virtualEntityDTO.toString()).isNotNull();
        VirtualEntityDTO virtualEntityDTO2=new VirtualEntityDTO("MKE","MKE01","MKE01","Legal Entity","LE01",true,"2022-08-05");
        VirtualEntityDTO.builder().entityType("MKE").entityCode("MKE01").entityName("MKE01").parentEntityType("Legal Entity").parentEntityCode("LE01").entityName("MKE01").isDefault(true).effectiveDate("").build().toString();
        VirtualEntityKey virtualEntityKey=new VirtualEntityKey("MKE01");
        assertThat(virtualEntityKey.toString()).isNotNull();
        virtualEntityKey.setEntityCode("MKE01");
        virtualEntityKey.keyAsString();
        virtualEntityKey.builder().entityCode("MKE01").build();
        assertThat(virtualEntityDTO).descriptionText();
    }

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder()
                .bankCode("003").defaultBranchCode("03110").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("dummy_target").postingDate(new Date()).valueDate(new Date()).transactionBranch("")
                .userId("aditya")
//                .accessibleTargetUnits([])
                .channel("Branch").taskCode(VIRTUAL_ENTITY)
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

    private VirtualEntityDTO getVirtualEntityDTOAuthorized() {
        VirtualEntityDTO virtualEntityDTO = new VirtualEntityDTO();

        virtualEntityDTO.setEntityType("MKE");
        virtualEntityDTO.setEntityCode("MKE01");
        virtualEntityDTO.setEntityName("MKE01");
        virtualEntityDTO.setParentEntityType("Legal Entity");
        virtualEntityDTO.setParentEntityCode("LE01");
        virtualEntityDTO.setIsDefault(true);
        virtualEntityDTO.setEffectiveDate("2022-08-05");
        virtualEntityDTO.setAuthorized("Y");
        return virtualEntityDTO;
    }

    private VirtualEntityDTO getVirtualEntityAllDTO()
    {
        VirtualEntityDTO virtualEntityDTO = new VirtualEntityDTO();
        virtualEntityDTO.setEntityType("MKE");
        virtualEntityDTO.setEntityCode("MKE01");
        virtualEntityDTO.setEntityName("MKE01");
        virtualEntityDTO.setParentEntityType("Legal Entity");
        virtualEntityDTO.setParentEntityCode("LE01");
        virtualEntityDTO.setIsDefault(true);
        virtualEntityDTO.setEffectiveDate("2022-08-05");
        virtualEntityDTO.setTaskCode(VIRTUAL_ENTITY);
        virtualEntityDTO.setStatus("DELETED");
        virtualEntityDTO.setRecordVersion(1);
        return virtualEntityDTO;
    }

    private VirtualEntityDTO getVirtualEntityDTOUnAuth() {

        VirtualEntityDTO virtualEntityDTO = new VirtualEntityDTO("MKE","MKE01","MKE01","Legal Entity","LE01",true,"2022-08-05");
        virtualEntityDTO.setAuthorized("N");
        virtualEntityDTO.setTaskIdentifier("MKE01");
        return virtualEntityDTO;
    }

    private VirtualEntityDTO getVirtualEntityDTOMapper(){

        VirtualEntityDTO virtualEntityDTOMapper2 = VirtualEntityDTO.builder()
                .entityType("MKE")
                .entityCode("MKE01")
                .entityName("MKE01")
                .parentEntityType("Legal Entity")
                .parentEntityCode("LE01")
                .isDefault(true)
                .effectiveDate("2022-08-05")
                .authorized("N")
                .taskCode(VIRTUAL_ENTITY)
                .taskIdentifier("LN").build();
        return virtualEntityDTOMapper2;
    }

    private MutationEntity getMutationEntity() {
        String payLoadString= "{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":1," +
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"VIRTUAL_ENTITY\"," +
                "\"taskIdentifier\":\"MKE01\",\"entity\":\"MKE\",\"entityCode\":\"MKE01\",\"entityName\":\"MKE01\"," +
                "\"parentEntity\":\"Legal Entity\",\"parentEntityCode\":\"LE01\",\"defaultt\":true," +
                "\"effectiveDate\":\"08-08-2022\"}";

        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("MKE01");
        mutationEntity.setTaskCode(VIRTUAL_ENTITY);
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
        String payLoadString1= "{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0," +
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"draft\",\"taskCode\":\"VIRTUAL_ENTITY\"," +
                "\"taskIdentifier\":\"MKE01\",\"entityType\":\"MKE\",\"entityCode\":\"MKE01\",\"entityName\":\"MKE01\"," +
                "\"parentEntitType\":\"Legal Entity\",\"parentEntityCode\":\"LE01\",\"isDefault\":true," +
                "\"effectiveDate\":\"08-08-2022\"}";
        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("MKE01");
        mutationEntity2.setTaskCode(VIRTUAL_ENTITY);
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");

        return mutationEntity2;
    }

    private MutationEntity getMutationEntityUnauthorize() {
        String payLoadString= "{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"VIRTUAL_ENTITY\",\"taskIdentifier\":\"MKE01\"," +
                "\"entityType\":\"MKE\",\"entityCode\":\"MKE01\",\"entityName\":\"MKE01\",\"parentEntityType\":\"Legal Entity\"," +
                "\"parentEntityCode\":\"LE01\",\"isDefault\":true,\"effectiveDate\":\"2022-08-07\"}";
        MutationEntity mutationEntity = new MutationEntity();

        mutationEntity.setTaskIdentifier("MKE01");
        mutationEntity.setTaskCode("VIRTUAL_ENTITY");
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("draft");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(0);
        mutationEntity.setAction("add");
        mutationEntity.setLastConfigurationAction("unauthorized");
        return mutationEntity;

    }
}
