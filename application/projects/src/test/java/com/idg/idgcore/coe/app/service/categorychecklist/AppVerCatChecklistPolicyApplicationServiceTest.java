package com.idg.idgcore.coe.app.service.categorychecklist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.coe.domain.assembler.categorychecklist.AppVerCatChecklistPolicyAssembler;
import com.idg.idgcore.coe.domain.entity.categorychecklist.AppVerCatChecklistPolicyEntity;
import com.idg.idgcore.coe.domain.entity.categorychecklist.AppVerCatChecklistPolicyEntityKey;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.categorychecklist.IAppVerCatChecklistPolicyDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;
import com.idg.idgcore.coe.dto.categorychecklist.AppVerCatChecklistPolicyDTO;
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

import static com.idg.idgcore.coe.common.Constants.*;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class AppVerCatChecklistPolicyApplicationServiceTest {

    @InjectMocks
    private AppVerCatChecklistPolicyApplicationService appVerCatChecklistPolicyApplicationService;

    @Mock
    private ProcessConfiguration process;

    @Mock
    private AppVerCatChecklistPolicyAssembler appVerCatChecklistPolicyAssembler;

    @Mock
    private IAppVerCatChecklistPolicyDomainService appVerCatChecklistPolicyDomainService;

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
    private AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity;
    private AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO;
    private AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTOUnAuth;
    private AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO1;
    private AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity1;

    private AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTOMapper;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext ();
        sessionContext1=getErrorSession();
        appVerCatChecklistPolicyDTO=getAppVerCatChecklistPolicyDTOAuthorized ();
        appVerCatChecklistPolicyEntity=getAppVerCatChecklistPolicyEntity();
        appVerCatChecklistPolicyDTOUnAuth=getAppVerCatChecklistPolicyDTOUnAuth();
        appVerCatChecklistPolicyDTOMapper=getAppVerCatChecklistPolicyDTOMapper();
        mutationEntity=getMutationEntity();
        appVerCatChecklistPolicyEntity1=getAppVerCatChecklistPolicyEntity1();
        appVerCatChecklistPolicyDTO1=getAppVerCatChecklistPolicyDTO();
        mutationEntity2=getMutationEntityJsonError();
        mutationEntity3=getMutationEntityUnauthorize();

    }


    @Test
    @DisplayName("JUnit for getByAppVerCatChecklistPolicyID in application service when Authorize try Block")
    void getAppVerCatgetByAppVerCatChecklistPolicyByIDIsAuthorize() throws FatalException {
        given(appVerCatChecklistPolicyDomainService.getAppVerChecklistPolicyById(appVerCatChecklistPolicyDTO.getAppVerChecklistPolicyId())).willReturn(appVerCatChecklistPolicyEntity);
        given(appVerCatChecklistPolicyAssembler.convertEntityToDto(appVerCatChecklistPolicyEntity)).willReturn(appVerCatChecklistPolicyDTO);
        AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO1 = appVerCatChecklistPolicyApplicationService.getAppVerCatChecklistPolicyById(sessionContext, appVerCatChecklistPolicyDTO);
        assertEquals("Y",appVerCatChecklistPolicyDTO1.getAuthorized());
        assertThat(appVerCatChecklistPolicyDTO1).isNotNull();
    }


    @Test
    @DisplayName("JUnit for getByAppVerCatChecklistPolicyID in application service when Not Authorize in try else block")
    void getByAppVerCatChecklistPolicyByIDwhenNotAuthorizeTryBlock() throws JsonProcessingException, FatalException {
        given(mutationsDomainService.getConfigurationByCode(appVerCatChecklistPolicyDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity3);
        AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO5 = appVerCatChecklistPolicyApplicationService.getAppVerCatChecklistPolicyById(sessionContext,appVerCatChecklistPolicyDTOUnAuth);
        assertEquals("N",appVerCatChecklistPolicyDTO5.getAuthorized());
        assertThat(appVerCatChecklistPolicyDTO5).isNotNull();
    }



    @Test
    @DisplayName("JUnit for getByAppVerCatChecklistPolicyID in application service when Not Authorize in catch block")
    void getByAppVerCatChecklistPolicyIDwhenNotAuthorizeCatchBlock () throws FatalException {
        given(mutationsDomainService.getConfigurationByCode(appVerCatChecklistPolicyDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);
        Assertions.assertThrows(Exception.class,()-> {
            AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO1 = appVerCatChecklistPolicyApplicationService.getAppVerCatChecklistPolicyById(sessionContext, appVerCatChecklistPolicyDTOMapper);
            assertEquals("N",appVerCatChecklistPolicyDTO1.getAuthorized());
            assertThat(appVerCatChecklistPolicyDTO1).isNotNull();
        });

    }


    @Test
    @DisplayName("Should return all getAppVerCatChecklistPolicies in application service for try block")
    void getAppVerCatChecklistPoliciesTryBlock() throws FatalException {
        given(mutationsDomainService.getMutations(CHECKLIST))
                .willReturn(List.of(mutationEntity));
        List<AppVerCatChecklistPolicyDTO> appVerCatChecklistPolicyDTOList1 =
                appVerCatChecklistPolicyApplicationService.getAppVerCatChecklistPolicies(sessionContext);
        assertThat(appVerCatChecklistPolicyDTOList1).isNotNull();
    }


    @Test
    @DisplayName("JUnit for getAppVerCatChecklistPolicies in application service for catch block for checker")
    void getAppVerCatChecklistPoliciesCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        sessionContext.setRole(new String[] { "" });
        given(mutationsDomainService.getMutations(
                appVerCatChecklistPolicyDTO1.getTaskCode()))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
        Assertions.assertThrows(FatalException.class,()-> {
            List<AppVerCatChecklistPolicyDTO> appVerCatChecklistPolicyDTO1 = appVerCatChecklistPolicyApplicationService.getAppVerCatChecklistPolicies(sessionContext);
            assertThat(appVerCatChecklistPolicyDTO1).isNotNull();
        });
    }

    @Test
    @DisplayName("JUnit for processAppVerCatChecklistPolicys in application service for Try Block")
    void processAppVerCatChecklistPolicysForTryBlock() throws JsonProcessingException, FatalException {
        doNothing().when(process).process(appVerCatChecklistPolicyDTO);
        appVerCatChecklistPolicyApplicationService.processAppVerCatChecklistPolicies(sessionContext, appVerCatChecklistPolicyDTO);
        verify(process, times(1)).process(appVerCatChecklistPolicyDTO);
    }


    @Test
    @DisplayName("JUnit for processAppVerCatChecklistPolicys in application service for Catch Block")
    void processAppVerCatChecklistPolicysForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            appVerCatChecklistPolicyApplicationService.processAppVerCatChecklistPolicies(sessionContext2, appVerCatChecklistPolicyDTO);
            assertThat(appVerCatChecklistPolicyDTO).descriptionText();
        });
    }


    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {
        doNothing().when(appVerCatChecklistPolicyDomainService).save(appVerCatChecklistPolicyDTO);
        appVerCatChecklistPolicyApplicationService.save(appVerCatChecklistPolicyDTO);
       String payload  ="{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":1,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"CHECKLIST\",\"taskIdentifier\":\"VCK0001\",\"appVerChecklistPolicyId\":\"VCK0001\",\"appVerChecklistPolicyDesc\":\"Address\",\"domainId\":\"DM0001\",\"domainCategoryId\":\"DC0001\",\"eventId\":\"EV0015\",\"effectiveDate\":\"2022-07-07\",\"entity\":\"customer\",\"ruleId\":\"RL0001\"}";
        appVerCatChecklistPolicyApplicationService.addUpdateRecord(payload);
        verify(appVerCatChecklistPolicyDomainService, times(1)).save(appVerCatChecklistPolicyDTO);

    }



    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = appVerCatChecklistPolicyDTO.getAppVerChecklistPolicyId();
        given(appVerCatChecklistPolicyDomainService.getAppVerChecklistPolicyById(code)).willReturn(appVerCatChecklistPolicyEntity);
        appVerCatChecklistPolicyApplicationService.getConfigurationByCode(code);
        assertThat(appVerCatChecklistPolicyEntity).isNotNull();
    }


    //--------------------------------------------------------Negative--------------------------------------------



    @Test
    @DisplayName("JUnit for getAppVerCatChecklistPolicyById in application service when Authorize for Negative")
    void getAppVerCatChecklistPolicyByIdIsAuthorizeforNegative() throws FatalException {
        given(appVerCatChecklistPolicyDomainService.getAppVerChecklistPolicyById(appVerCatChecklistPolicyDTO.getAppVerChecklistPolicyId())).willReturn(appVerCatChecklistPolicyEntity);
        given(appVerCatChecklistPolicyAssembler.convertEntityToDto(appVerCatChecklistPolicyEntity)).willReturn(appVerCatChecklistPolicyDTO);
        AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO1 = appVerCatChecklistPolicyApplicationService.getAppVerCatChecklistPolicyById(sessionContext, appVerCatChecklistPolicyDTO);
        assertNotEquals("N",appVerCatChecklistPolicyDTO1.getAuthorized());
        assertThat(appVerCatChecklistPolicyDTO1).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getAppVerCatChecklistPolicyById in application service when UnAuthorize fetche no Record from database")
    void getAppVerCatChecklistPolicyByIdNotAuthorizeNull() throws FatalException {
        given(mutationsDomainService.getConfigurationByCode(appVerCatChecklistPolicyDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity3);
        appVerCatChecklistPolicyDTO.setAuthorized("N");
        AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO1 = appVerCatChecklistPolicyApplicationService.getAppVerCatChecklistPolicyById(sessionContext, appVerCatChecklistPolicyDTO);
        assertNotEquals("Y",appVerCatChecklistPolicyDTO1.getAuthorized());
    }


    @Test
    @DisplayName("JUnit for getAppVerCatChecklistPolicyById in application service check Parameter not null")
    void getAppVerCatChecklistPolicyByIdIsAuthorizeCheckParameter() throws FatalException {
        given(appVerCatChecklistPolicyDomainService.getAppVerChecklistPolicyById(appVerCatChecklistPolicyDTO.getAppVerChecklistPolicyId())).willReturn(appVerCatChecklistPolicyEntity);
        given(appVerCatChecklistPolicyAssembler.convertEntityToDto(appVerCatChecklistPolicyEntity)).willReturn(appVerCatChecklistPolicyDTO);
        AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO1 = appVerCatChecklistPolicyApplicationService.getAppVerCatChecklistPolicyById(sessionContext, appVerCatChecklistPolicyDTO);
        assertThat(appVerCatChecklistPolicyDTO1.getAppVerChecklistPolicyId()).isNotBlank();
        assertThat(appVerCatChecklistPolicyDTO1.getAuthorized()).isNotBlank();
    }

    @Test
    @DisplayName("JUnit for getAppVerCatChecklistPolicyById in application service when Not Authorize in try block for Negative when getAuthorized unexpected is Y")
    void getAppVerCatChecklistPolicyByIdwhenNotAuthorizeTryBlockForNegative() throws JsonProcessingException, FatalException {
        given(mutationsDomainService.getConfigurationByCode(appVerCatChecklistPolicyDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity3);
//        mutationEntity.setPayload(new Payload(payLoadString1));
        appVerCatChecklistPolicyDTOUnAuth.setAuthorized("N");
        AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO1 = appVerCatChecklistPolicyApplicationService.getAppVerCatChecklistPolicyById(sessionContext,appVerCatChecklistPolicyDTOUnAuth);
        assertNotEquals("Y",appVerCatChecklistPolicyDTO1.getAuthorized());
        assertThat(appVerCatChecklistPolicyDTO1).isNotNull();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        assertThat(appVerCatChecklistPolicyEntity.toString()).isNotNull();
        assertThat(appVerCatChecklistPolicyDTO.toString()).isNotNull();
        AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO2=new AppVerCatChecklistPolicyDTO("VCK0001","Address","DM0001","DC0001","EV0001","2022-07-22","customer","RL0001");
//        AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity2=new AppVerCatChecklistPolicyEntity("VCK0001","Address","DM0001","DC0001","EV0001","customer","RL0001");
        AppVerCatChecklistPolicyDTO.builder().appVerChecklistPolicyId("VCK0001").appVerChecklistPolicyDesc("Address").domainId("DM0001").domainCategoryId("DC0001").eventId("EV001").entity("customer").ruleId("RL0001").effectiveDate("2022-07-07").build().toString();
        AppVerCatChecklistPolicyEntityKey appVerCatChecklistPolicyEntityKey=new AppVerCatChecklistPolicyEntityKey("VCK0001");
        AppVerCatChecklistPolicyEntityKey appVerCatChecklistPolicyEntityKey1=new AppVerCatChecklistPolicyEntityKey();
        appVerCatChecklistPolicyEntityKey1.toString();
        appVerCatChecklistPolicyEntityKey1.setAppVerChecklistPolicyId("VCK0001");
        System.out.println(appVerCatChecklistPolicyEntityKey1.getAppVerChecklistPolicyId());
        appVerCatChecklistPolicyEntityKey1.keyAsString();
        appVerCatChecklistPolicyEntityKey1.builder().appVerChecklistPolicyId("VCK0001").build();
        assertThat(appVerCatChecklistPolicyEntityKey1).descriptionText();
    }


    @Test
    @DisplayName("JUnit for getAppVerCatChecklistPolicies in application service for try block negative scenario for SessionContext some field not be null")
    void getAppVerCatChecklistPoliciesTryBlockNegative() throws JsonProcessingException, FatalException {

        AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTOO= new AppVerCatChecklistPolicyDTO();
        AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity5 = new AppVerCatChecklistPolicyEntity();
//        given(mutationsDomainService.getUnauthorizedMutation(appVerCatChecklistPolicyDTOO.getTaskCode(),AUTHORIZED_N)).willReturn(List.of(mutationEntity3));
//        given(appVerCatChecklistPolicyDomainService.getAppVerChecklistPolicies()).willReturn(List.of(appVerCatChecklistPolicyEntity5));
//        given(appVerCatChecklistPolicyAssembler.convertEntityToDto(appVerCatChecklistPolicyEntity5)).willReturn(appVerCatChecklistPolicyDTOO);
//        given(appVerCatChecklistPolicyAssembler.setAuditFields(mutationEntity,appVerCatChecklistPolicyDTOO)).willReturn(appVerCatChecklistPolicyDTOO);
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            List<AppVerCatChecklistPolicyDTO> appVerCatChecklistPolicyDTO2 = appVerCatChecklistPolicyApplicationService.getAppVerCatChecklistPolicies(sessionContext2);
            assertThat(sessionContext1.getRole()).isNotEmpty();
            assertThat(sessionContext1.getServiceInvocationModeType()).isNotNull();
        });

    }

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder()
                .bankCode("003").defaultBranchCode("1141").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("dummy_target").postingDate(new Date()).valueDate(new Date()).transactionBranch("")
                .userId("nikhiljagtap")
//                .accessibleTargetUnits([])
                .channel("Branch").taskCode("CHECKLIST")
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(1L)
                .customAttributes("")
                .serviceInvocationModeType(Regular)
                .allTargetUnitsSelected(true)
//                .mutationType("")
                .userLocal("es_US")
                .originatingModuleCode("")
                .role(new String[]{"maker"}).build();
        return sessionContext;
    }


    private SessionContext getErrorSession(){
        SessionContext sessionContext1=SessionContext.builder()
                .bankCode("003").defaultBranchCode("1141").internalTransactionReferenceNumber(null)
                .userTransactionReferenceNumber(null).externalTransactionReferenceNumber(null)
                .targetUnit("dummy_target").postingDate(new Date()).valueDate(new Date()).transactionBranch(null)
                .userId("nikhiljagtap")
//                .accessibleTargetUnits([])
                .channel("Branch").taskCode("CHECKLIST")
                .originalTransactionReferenceNumber(null)
                .externalBatchNumber(null)
                .customAttributes(null)
                .serviceInvocationModeType(null)
                .allTargetUnitsSelected(true)
//                .mutationType("")
                .userLocal("es_US")
                .originatingModuleCode(null)
                .role(null)
                .build();
        return sessionContext1;
    }

    private AppVerCatChecklistPolicyDTO getAppVerCatChecklistPolicyDTOAuthorized () {
        AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO = new AppVerCatChecklistPolicyDTO();
        appVerCatChecklistPolicyDTO.setAppVerChecklistPolicyId("VCK0001");
        appVerCatChecklistPolicyDTO.setAppVerChecklistPolicyDesc("Address");
        appVerCatChecklistPolicyDTO.setDomainId("DM0001");
        appVerCatChecklistPolicyDTO.setDomainCategoryId("DC0001");
        appVerCatChecklistPolicyDTO.setEffectiveDate("2022-07-20");
        appVerCatChecklistPolicyDTO.setEventId("EV0001");
        appVerCatChecklistPolicyDTO.setEntity("Customer");
        appVerCatChecklistPolicyDTO.setRuleId("RL0001");
        appVerCatChecklistPolicyDTO.setAuthorized("Y");
        return appVerCatChecklistPolicyDTO;
    }

    private AppVerCatChecklistPolicyDTO getAppVerCatChecklistPolicyDTO()
    {
        AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO = new AppVerCatChecklistPolicyDTO();
        appVerCatChecklistPolicyDTO.setAppVerChecklistPolicyId("VCK0001");
        appVerCatChecklistPolicyDTO.setAppVerChecklistPolicyDesc("Address");
        appVerCatChecklistPolicyDTO.setDomainId("DM0001");
        appVerCatChecklistPolicyDTO.setDomainCategoryId("DC0001");
        appVerCatChecklistPolicyDTO.setEffectiveDate("2022-07-20");
        appVerCatChecklistPolicyDTO.setEventId("EV0001");
        appVerCatChecklistPolicyDTO.setEntity("Customer");
        appVerCatChecklistPolicyDTO.setRuleId("RL0001");
        appVerCatChecklistPolicyDTO.setStatus("DELETED");
        appVerCatChecklistPolicyDTO.setRecordVersion(1);
        return appVerCatChecklistPolicyDTO;
    }
    private AppVerCatChecklistPolicyEntity getAppVerCatChecklistPolicyEntity(){
        AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity = new AppVerCatChecklistPolicyEntity();
        appVerCatChecklistPolicyEntity.setAppVerChecklistPolicyId("VCK0001");
        appVerCatChecklistPolicyEntity.setAppVerChecklistPolicyDesc("Address");
        appVerCatChecklistPolicyEntity.setDomainId("DM0001");
        appVerCatChecklistPolicyEntity.setDomainCategoryId("DC0001");
//        appVerCatChecklistPolicyEntity.setEffectiveDate(2022-07-20);
        appVerCatChecklistPolicyEntity.setEventId("EV0001");
        appVerCatChecklistPolicyEntity.setEntity("Customer");
        appVerCatChecklistPolicyEntity.setRuleId("RL0001");
        appVerCatChecklistPolicyEntity.setStatus("draft");
        appVerCatChecklistPolicyEntity.setLifeCycleId("LC001");
        appVerCatChecklistPolicyEntity.setReferenceNo("RN001");
        appVerCatChecklistPolicyEntity.setRecordVersion(0);
        return appVerCatChecklistPolicyEntity;
    }
    private AppVerCatChecklistPolicyEntity getAppVerCatChecklistPolicyEntity1()
    {
        AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity = new AppVerCatChecklistPolicyEntity();
        appVerCatChecklistPolicyEntity.setAppVerChecklistPolicyId("VCK0001");
        appVerCatChecklistPolicyEntity.setAppVerChecklistPolicyDesc("Address");
        appVerCatChecklistPolicyEntity.setDomainId("DM0001");
        appVerCatChecklistPolicyEntity.setDomainCategoryId("DC0001");
//        appVerCatChecklistPolicyEntity.setEffectiveDate(2022-07-20);
        appVerCatChecklistPolicyEntity.setEventId("EV0001");
        appVerCatChecklistPolicyEntity.setEntity("Customer");
        appVerCatChecklistPolicyEntity.setRuleId("RL0001");
        appVerCatChecklistPolicyEntity.setStatus("DELETED");
        appVerCatChecklistPolicyEntity.setRecordVersion(1);
        return appVerCatChecklistPolicyEntity;

    }
    private AppVerCatChecklistPolicyDTO getAppVerCatChecklistPolicyDTOUnAuth(){
        AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO = new AppVerCatChecklistPolicyDTO();
        appVerCatChecklistPolicyDTO.setAppVerChecklistPolicyId("VCK0001");
        appVerCatChecklistPolicyDTO.setAppVerChecklistPolicyDesc("Address");
        appVerCatChecklistPolicyDTO.setDomainId("DM0001");
        appVerCatChecklistPolicyDTO.setDomainCategoryId("DC0001");
        appVerCatChecklistPolicyDTO.setEffectiveDate("2022-07-20");
        appVerCatChecklistPolicyDTO.setEventId("EV0001");
        appVerCatChecklistPolicyDTO.setEntity("Customer");
        appVerCatChecklistPolicyDTO.setRuleId("RL0001");
        appVerCatChecklistPolicyDTO.setAuthorized("N");
        return appVerCatChecklistPolicyDTO;

    }

    private AppVerCatChecklistPolicyDTO getAppVerCatChecklistPolicyDTOMapper(){
        AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO = new AppVerCatChecklistPolicyDTO();
        appVerCatChecklistPolicyDTO.setAppVerChecklistPolicyId("VCK0001");
        appVerCatChecklistPolicyDTO.setAppVerChecklistPolicyDesc("Address");
        appVerCatChecklistPolicyDTO.setDomainId("DM0001");
        appVerCatChecklistPolicyDTO.setDomainCategoryId("DC0001");
        appVerCatChecklistPolicyDTO.setEffectiveDate("2022-07-20");
        appVerCatChecklistPolicyDTO.setEventId("EV0001");
        appVerCatChecklistPolicyDTO.setEntity("Customer");
        appVerCatChecklistPolicyDTO.setRuleId("RL0001");
        appVerCatChecklistPolicyDTO.setAuthorized("N");
        appVerCatChecklistPolicyDTO.setTaskCode("CHECKLIST");
        appVerCatChecklistPolicyDTO.setTaskIdentifier("VCK0001");
        return appVerCatChecklistPolicyDTO;
    }

    private MutationEntity getMutationEntity() {
        String payLoadString="{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":1,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"CHECKLIST\",\"taskIdentifier\":\"VCK0001\",\"appVerChecklistPolicyId\":\"VCK0001\",\"appVerChecklistPolicyDesc\":\"Address\",\"domainId\":\"DM0001\",\"domainCategoryId\":\"DC0001\",\"eventId\":\"EV0015\",\"effectiveDate\":\"2022-07-07\",\"entity\":\"customer\",\"ruleId\":\"RL0001\"}";

        MutationEntity mutationEntity = new MutationEntity();

        mutationEntity.setTaskIdentifier("VCK0001");
        mutationEntity.setTaskCode("CHECKLIST");
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("closed");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("add");
        mutationEntity.setLastConfigurationAction("unauthorized");
        return mutationEntity;

    }
    private MutationEntity getMutationEntityUnauthorize() {
        String payload  ="{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"CHECKLIST\",\"taskIdentifier\":\"VCK0001\",\"appVerChecklistPolicyId\":\"VCK0001\",\"appVerChecklistPolicyDesc\":\"Address\",\"domainId\":\"DM0001\",\"domainCategoryId\":\"DC0001\",\"eventId\":\"EV0015\",\"effectiveDate\":\"2022-07-07\",\"entity\":\"customer\",\"ruleId\":\"RL0001\"}";
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("VCK0001");
        mutationEntity.setTaskCode("CHECKLIST");
        mutationEntity.setPayload(new Payload(payload));
        mutationEntity.setStatus("closed");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("add");
        mutationEntity.setLastConfigurationAction("unauthorized");
        return mutationEntity;

    }

    private MutationEntity getMutationEntityJsonError()
    {
        String payLoadString1 ="{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":hhhhhh,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"CHECKLIST\",\"taskIdentifier\":\"VCK0001\",\"appVerChecklistPolicyId\":\"VCK0001\",\"appVerChecklistPolicyDesc\":\"Address\",\"domainId\":\"DM0001\",\"domainCategoryId\":\"DC0001\",\"eventId\":\"EV0015\",\"effectiveDate\":\"2022-07-07\",\"entity\":\"customer\",\"ruleId\":\"RL0001\"}";
        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("VCK0001");
        mutationEntity2.setTaskCode("CHECKLIST");
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");
        return mutationEntity2;
    }


    String payLoadString1="{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":1,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"CHECKLIST\",\"taskIdentifier\":\"VCK0001\",\"appVerChecklistPolicyId\":\"VCK0001\",\"appVerChecklistPolicyDesc\":\"Address\",\"domainId\":\"DM0001\",\"domainCategoryId\":\"DC0001\",\"eventId\":\"EV0015\",\"effectiveDate\":\"2022-07-07\",\"entity\":\"customer\",\"ruleId\":\"RL0001\"}";


}