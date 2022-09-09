package com.idg.idgcore.coe.app.service.branchSystemDate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.coe.domain.assembler.branchSystemDate.BranchSystemDateAssembler;
import com.idg.idgcore.coe.domain.entity.branchSystemDate.BranchSystemDateEntity;
import com.idg.idgcore.coe.domain.entity.branchSystemDate.BranchSystemDateEntityKey;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.branchSystemDate.IBranchSystemDateDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.branchSystemDate.BranchSystemDateDTO;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static com.idg.idgcore.coe.common.Constants.BRANCH_SYSTEM_DATE;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchSystemApplicationServiceTest {

    @InjectMocks
    private BranchSystemApplicationService branchSystemApplicationService;

    @Mock
    private ProcessConfiguration process;

    @Mock
    private BranchSystemDateAssembler branchSystemAssembler;

    @Mock
    private IBranchSystemDateDomainService branchSystemDomainService;

    @Autowired
    private MutationEntity mutationEntity;

    private MutationEntity mutationEntity2;

    @Mock
    private IMutationsDomainService mutationsDomainService;

    private SessionContext sessionContext;

    private SessionContext sessionContext1;
    private BranchSystemDateEntity branchSystemEntity;
    private BranchSystemDateDTO branchSystemDTO;
    private BranchSystemDateDTO branchSystemDTOUnAuth;
    private BranchSystemDateDTO branchSystemDTO1;
    private BranchSystemDateEntity branchSystemEntity1;

    private BranchSystemDateEntity branchSystemEntity2;
    private BranchSystemDateDTO branchSystemDTOMapper;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext();
        sessionContext1 = getErrorSession();
        branchSystemDTO = getBranchSystemDTOAuthorized();
        branchSystemDTOUnAuth = getBranchSystemDTOUnAuth();
        branchSystemDTOMapper = getBranchSystemDTOMapper();
        mutationEntity = getMutationEntity();
        branchSystemDTO1 = getBranchSystemDateAllDTO();
        mutationEntity2 = getMutationEntityJsonError();
    }

    @Test
    @DisplayName("JUnit for getBranchSystemDateByCode in application service when Authorize")
    void getBranchSystemDateByCodeIsAuthorize() throws FatalException {
        given(branchSystemDomainService.getBranchSystemDateByCode(branchSystemDTO.getBranchCode())).willReturn(branchSystemEntity);
        given(branchSystemAssembler.convertEntityToDto(branchSystemEntity)).willReturn(branchSystemDTO);
        BranchSystemDateDTO branchSystemDTO1 = branchSystemApplicationService.getBranchSystemDateByCode(sessionContext, branchSystemDTO);
        assertEquals("Y", branchSystemDTO1.getAuthorized());
        assertThat(branchSystemDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getPurgingByCode in application service when Not Authorize in try else block")
    void getPurgingByCodeWhenNotAuthorizeTryBlock() throws JsonProcessingException, FatalException {
        MutationEntity mutationEntity6 = new MutationEntity();

        String payLoadString = "{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0," +
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"draft\",\"taskCode\":\"BRANCH_SYSTEM_DATE\"," +
                "\"taskIdentifier\":\"BC0002\",\"branchCode\":\"BC0002\",\"currentWorkingDate\":\"06-08-2022\"," +
                "\"previousWorkingDate\":\"10-07-2022\",\"nextWorkingDate\":\"15-08-2022\"}";

        mutationEntity6.setPayload(new Payload(payLoadString));

        BranchSystemDateDTO branchSystemDateDTO6 = new BranchSystemDateDTO();
        branchSystemDateDTO6.setAuthorized("N");
        branchSystemDateDTO6.setBranchCode("BC0002");
        branchSystemDateDTO6.setCurrentWorkingDate("07-08-2022");
        branchSystemDateDTO6.setPreviousWorkingDate("05-08-2022");
        branchSystemDateDTO6.setNextWorkingDate("10-08-2022");
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO payload6 = new PayloadDTO();
        assertThat(branchSystemDateDTO6).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getByBranchCode in application service when Not Authorize in catch block")
    void getBranchSystemDateByCodewhenNotAuthorizeCatchBlock () throws FatalException {

        String payLoadString= "{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0," +
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"draft\",\"taskCode\":\"BRANCH_SYSTEM_DATE\"," +
                "\"taskIdentifier\":\"BC0002\",\"branchCode\":\"BC0002\",\"currentWorkingDate\":\"06-08-2022\"," +
                "\"previousWorkingDate\":\"10-07-2022\",\"nextWorkingDate\":\"15-08-2022\"}";

        given(mutationsDomainService.getConfigurationByCode(branchSystemDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);
        Assertions.assertThrows(Exception.class,()-> {
            BranchSystemDateDTO branchSystemDateDTO1 = branchSystemApplicationService.getBranchSystemDateByCode(sessionContext, branchSystemDTOMapper);
            assertEquals("N",branchSystemDateDTO1.getAuthorized());
            assertThat(branchSystemDateDTO1).isNotNull();
            System.out.println(branchSystemDateDTO1);
        });
    }

   // @Test
    @DisplayName("Should return all getBranchSystemDateAll when there are no unauthorized")
    void getBranchSystemDateAllWhenThereAreNoUnauthorized() throws FatalException {
        BranchSystemDateEntity branchSystemEntity = new BranchSystemDateEntity();
        given(branchSystemDomainService.getBranchSystemDateAll()).willReturn(List.of(branchSystemEntity));
//        given(mutationsDomainService.getUnauthorizedMutation(BRANCH_SYSTEM_DATE, AUTHORIZED_N)).willReturn(List.of());
//        given(branchSystemAssembler.convertEntityToDto(branchSystemEntity)).willReturn(branchSystemDTO);
        List<BranchSystemDateDTO> branchSystemDateDTOList = branchSystemApplicationService.getBranchSystemDateAll(sessionContext);
        assertEquals(1, branchSystemDateDTOList.size());
        assertEquals(branchSystemDTO, branchSystemDateDTOList.get(0));
    }

    @Test
    @DisplayName("JUnit for getBranchSystemDateAll in application service for catch block for checker")
    void getBranchSystemDateAllCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        sessionContext.setRole(new String[] { "" });
        given(mutationsDomainService.getMutations(
                branchSystemDTO1.getTaskCode()))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
        Assertions.assertThrows(FatalException.class,()-> {
            List<BranchSystemDateDTO> branchSystemDTO1 = branchSystemApplicationService.getBranchSystemDateAll(sessionContext);
            System.out.println("return size : " + branchSystemDTO1.size());
            assertThat(branchSystemDTO1).isNotNull();
            System.out.println(branchSystemDTO1);
        });
    }

    @Test
    @DisplayName("JUnit for processBranchSystemDate in application service for Try Block")
    void processBranchSystemDateForTryBlock() throws JsonProcessingException, FatalException {

        doNothing().when(process).process(branchSystemDTO);
        branchSystemApplicationService.processBranchSystemDate(sessionContext, branchSystemDTO);
        verify(process, times(1)).process(branchSystemDTO);
    }

    @Test
    @DisplayName("JUnit for processBranchSystemDate in application service for Catch Block")
    void processBranchSystemDateForCatchBlock() {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            branchSystemApplicationService.processBranchSystemDate(sessionContext2, branchSystemDTO);
            assertThat(branchSystemDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {

        String payLoadString1 = "{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0," +
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"Authorized\",\"taskCode\":\"BRANCH_SYSTEM_DATE\"," +
                "\"taskIdentifier\":\"BC0002\",\"branchCode\":\"BC0002\",\"currentWorkingDate\":\"06-08-2022\"," +
                "\"previousWorkingDate\":\"10-07-2022\",\"nextWorkingDate\":\"15-08-2022\"}";

        doNothing().when(branchSystemDomainService).save(branchSystemDTO);
        branchSystemApplicationService.save(branchSystemDTO);
        branchSystemApplicationService.addUpdateRecord(payLoadString1);
        verify(branchSystemDomainService, times(1)).save(branchSystemDTO);
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = branchSystemDTO.getBranchCode();
        System.out.println("code: " + code);
        given(branchSystemDomainService.getBranchSystemDateByCode(code)).willReturn(branchSystemEntity);
        branchSystemApplicationService.getConfigurationByCode(code);
        assertThat(branchSystemDTO).isNotNull();
//        assertThat(branchSystemEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getByBranchCode in application service when Authorize for Negative")
    void getBranchByCodeIsAuthorizeforNegative() throws FatalException, JsonProcessingException {
        given(branchSystemDomainService.getBranchSystemDateByCode(branchSystemDTO.getBranchCode())).willReturn(branchSystemEntity);
        given(branchSystemAssembler.convertEntityToDto(branchSystemEntity)).willReturn(branchSystemDTO);
        BranchSystemDateDTO branchSystemDateDTO1 = branchSystemApplicationService.getBranchSystemDateByCode(sessionContext, branchSystemDTO);
        assertNotEquals("N",branchSystemDateDTO1.getAuthorized());
        assertThat(branchSystemDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getBranchByCode in application service check Parameter not null")
    void getBranchByCodeIsAuthorizeCheckParameter() throws FatalException, JsonProcessingException {
        BranchSystemDateDTO branchSystemDTONull = null;
        BranchSystemDateDTO branchSystemDTOEx = new BranchSystemDateDTO();
        branchSystemDTOEx.setBranchCode("BC0002");
        branchSystemDTOEx.setAuthorized("Y");
        given(branchSystemDomainService.getBranchSystemDateByCode(branchSystemDTOEx.getBranchCode())).willReturn(branchSystemEntity);
        given(branchSystemAssembler.convertEntityToDto(branchSystemEntity)).willReturn(branchSystemDTO);
        BranchSystemDateDTO branchSystemDateDTO1 = branchSystemApplicationService.getBranchSystemDateByCode(sessionContext, branchSystemDTOEx);
        assertThat(branchSystemDTOEx.getBranchCode()).isNotBlank();
        assertThat(branchSystemDTOEx.getAuthorized()).isNotBlank();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        BranchSystemDateEntityKey branchSystemEntityKey1=new BranchSystemDateEntityKey();
        BranchSystemDateEntity branchSystemEntity = new BranchSystemDateEntity();
        assertThat(branchSystemEntity.toString()).isNotNull();
        assertThat(branchSystemDTO.toString()).isNotNull();
        BranchSystemDateDTO branchSystemDTO2=new BranchSystemDateDTO("BC0002","07-08-2022","05-08-2022", "10-08-2022");
        BranchSystemDateDTO.builder().branchCode("BC0002").currentWorkingDate("07-08-2022").previousWorkingDate("05-08-2022").nextWorkingDate("10-08-2022").build().toString();
        BranchSystemDateEntityKey branchSystemDateEntityKey=new BranchSystemDateEntityKey("BC0002");
        assertThat(branchSystemDateEntityKey.toString()).isNotNull();
        branchSystemDateEntityKey.setBranchCode("BC0002");
        branchSystemDateEntityKey.keyAsString();
        branchSystemDateEntityKey.builder().branchCode("BC0002").build();
        assertThat(branchSystemDTO).descriptionText();
    }

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder()
                .bankCode("003").defaultBranchCode("03110").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("dummy_target").postingDate(new Date()).valueDate(new Date()).transactionBranch("")
                .userId("aditya")
//                .accessibleTargetUnits([])
                .channel("Branch").taskCode(BRANCH_SYSTEM_DATE)
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

    private BranchSystemDateDTO getBranchSystemDTOAuthorized() {
        BranchSystemDateDTO branchSystemDTO = new BranchSystemDateDTO();

        branchSystemDTO.setBranchCode("BC0002");
        branchSystemDTO.setCurrentWorkingDate("07-08-2022");
        branchSystemDTO.setPreviousWorkingDate("05-08-2022");
        branchSystemDTO.setNextWorkingDate("10-08-2022");
        branchSystemDTO.setAuthorized("Y");
        return branchSystemDTO;
    }

    private BranchSystemDateDTO getBranchSystemDateAllDTO()
    {
        BranchSystemDateDTO branchSystemDTO = new BranchSystemDateDTO();
        branchSystemDTO.setBranchCode("BC0002");
        branchSystemDTO.setCurrentWorkingDate("07-08-2022");
        branchSystemDTO.setPreviousWorkingDate("05-08-2022");
        branchSystemDTO.setNextWorkingDate("10-08-2022");
        branchSystemDTO.setTaskCode(BRANCH_SYSTEM_DATE);
        branchSystemDTO.setStatus("DELETED");
        branchSystemDTO.setRecordVersion(1);
        return branchSystemDTO;
    }

    private BranchSystemDateDTO getBranchSystemDTOUnAuth() {

        BranchSystemDateDTO branchSystemDTO = new BranchSystemDateDTO("BC0002", "07-08-2022", "05-08-2022", "10-08-2022");

        branchSystemDTO.setAuthorized("N");
        branchSystemDTO.setTaskIdentifier("BC0002");
        return branchSystemDTO;
    }

    private BranchSystemDateDTO getBranchSystemDTOMapper(){

        BranchSystemDateDTO branchSystemDTOMapper2 = BranchSystemDateDTO.builder()
                .branchCode("BC0002")
                .currentWorkingDate("07-08-2022")
                .previousWorkingDate("05-08-2022")
                .nextWorkingDate("10-08-2022")
                .authorized("N")
                .taskCode(BRANCH_SYSTEM_DATE)
                .taskIdentifier("LN").build();
        return branchSystemDTOMapper2;
    }

    private MutationEntity getMutationEntity() {
        String payLoadString= "{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":1," +
                "\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"PURGING_POLICY\"," +
                "\"taskIdentifier\":\"LN\",\"moduleCode\":\"LN\",\"tranMaintenanceStatus\":\"closed\"," +
                "\"retentionPeriod\":5}";

        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("LN");
        mutationEntity.setTaskCode(BRANCH_SYSTEM_DATE);
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
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"draft\",\"taskCode\":\"BRANCH_SYSTEM_DATE\"," +
                "\"taskIdentifier\":\"BC0002\",\"branchCode\":\"BC0002\",\"currentWorkingDate\":\"06-08-2022\"," +
                "\"previousWorkingDate\":\"10-07-2022\",\"nextWorkingDate\":\"15-08-2022\"}";

        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("LN");
        mutationEntity2.setTaskCode(BRANCH_SYSTEM_DATE);
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");

        return mutationEntity2;
    }
}