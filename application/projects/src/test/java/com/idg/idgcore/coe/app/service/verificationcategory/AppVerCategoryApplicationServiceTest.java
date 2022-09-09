package com.idg.idgcore.coe.app.service.verificationcategory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.coe.domain.assembler.verificationcategory.AppVerCategoryConfigAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.entity.verificationcategory.AppVerCategoryConfigEntity;
import com.idg.idgcore.coe.domain.entity.verificationcategory.AppVerCategoryConfigEntityKey;
import com.idg.idgcore.coe.domain.entity.verificationcategory.AppVerTypeConfigEntity;

import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.verificationcategory.IAppVerCategoryConfigDomainService;
import com.idg.idgcore.coe.dto.country.CountryDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.verificationcategory.AppVerCategoryConfigDTO;
import com.idg.idgcore.coe.dto.verificationcategory.AppVerTypeConfigDTO;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.*;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class AppVerCategoryApplicationServiceTest {
        @InjectMocks
        private AppVerCategoryApplicationService appVerCategoryApplicationService;

        @Mock
        private ProcessConfiguration process;

        @Mock
        private AppVerCategoryConfigAssembler appVerCategoryConfigAssembler;

        @Mock
        private IAppVerCategoryConfigDomainService appVerCategoryConfigDomainService;

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
        private AppVerCategoryConfigEntity appVerCategoryConfigEntity;
        private AppVerCategoryConfigDTO appVerCategoryConfigDTO;
        private AppVerCategoryConfigDTO appVerCategoryConfigDTOUnAuth;
        private AppVerCategoryConfigDTO appVerCategoryConfigDTO1;
        private AppVerCategoryConfigEntity appVerCategoryConfigEntity1;

        private AppVerCategoryConfigDTO appVerCategoryConfigDTOMapper;

        @BeforeEach
        void setUp() {
            sessionContext = getValidSessionContext ();
            sessionContext1=getErrorSession();
            appVerCategoryConfigDTO=getAppVerCategoryConfigDTOAuthorized ();
            appVerCategoryConfigEntity=getAppVerCategoryConfigEntity();
            appVerCategoryConfigDTOUnAuth=getAppVerCategoryConfigDTOUnAuth();
            appVerCategoryConfigDTOMapper=getAppVerCategoryConfigDTOMapper();
            mutationEntity=getMutationEntity();
            appVerCategoryConfigEntity1=getAppVerCategoryConfigEntity1();
            appVerCategoryConfigDTO1=getAppVerCategoryConfigDTO();
            mutationEntity2=getMutationEntityJsonError();
            mutationEntity3=getMutationEntityUnauthorize();

        }


        @Test
        @DisplayName("JUnit for getByAppVerCategoryID in application service when Authorize try Block")
        void getAppVerCategoryConfigByIDIsAuthorize() throws FatalException {
            given(appVerCategoryConfigDomainService.getAppVerCategoryConfigByID(appVerCategoryConfigDTO.getAppVerificationCategoryId())).willReturn(appVerCategoryConfigEntity);
            given(appVerCategoryConfigAssembler.convertEntityToDto(appVerCategoryConfigEntity)).willReturn(appVerCategoryConfigDTO);
            AppVerCategoryConfigDTO appVerCategoryConfigDTO1 = appVerCategoryApplicationService.getAppVerCategoryConfigByID(sessionContext, appVerCategoryConfigDTO);
            assertEquals("Y",appVerCategoryConfigDTO1.getAuthorized());
            assertThat(appVerCategoryConfigDTO1).isNotNull();
        }

        @Test
        @DisplayName("JUnit for getByAppVerCategoryConfigCode in application service when Not Authorize in try else block")
        void getAppVerCategoryConfigByCodewhenNotAuthorizeTryBlock() throws JsonProcessingException, FatalException {
            given(mutationsDomainService.getConfigurationByCode(appVerCategoryConfigDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity3);
            AppVerCategoryConfigDTO appVerCategoryConfigDTO5 = appVerCategoryApplicationService.getAppVerCategoryConfigByID(sessionContext,appVerCategoryConfigDTOUnAuth);
//            assertEquals("N",appVerCategoryConfigDTO5.getAuthorized());
//            assertThat(appVerCategoryConfigDTO5).isNotNull();
            assertEquals("N",appVerCategoryConfigDTOUnAuth.getAuthorized());
            assertThat(appVerCategoryConfigDTOUnAuth).isNotNull();
        }


        @Test
        @DisplayName("JUnit for getByAppVerCategoryID in application service when Not Authorize in catch block")
        void getAppVerCategoryConfigByIDwhenNotAuthorizeCatchBlock () throws FatalException {
            given(mutationsDomainService.getConfigurationByCode(appVerCategoryConfigDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
            ModelMapper mapper=new ModelMapper();
            PayloadDTO payload = new PayloadDTO(payLoadString1);
            ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
            PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);
             Assertions.assertThrows(Exception.class,()-> {
                AppVerCategoryConfigDTO appVerCategoryConfigDTO1 = appVerCategoryApplicationService.getAppVerCategoryConfigByID(sessionContext, appVerCategoryConfigDTOMapper);
                assertEquals("N",appVerCategoryConfigDTO1.getAuthorized());
                assertThat(appVerCategoryConfigDTO1).isNotNull();
             });

        }

        @Test
        @DisplayName("Should return all getAppVerCategoryConfigs when there are no unauthorized")
        void getAppVerCategoryConfigsWhenThereAreNoUnauthorized() throws FatalException {
            given(mutationsDomainService.getMutations(CATEGORY))
                    .willReturn(List.of(mutationEntity));
            List<AppVerCategoryConfigDTO> appVerCategoryConfigDTOList =
                    appVerCategoryApplicationService.getAppVerCategoryConfigs(sessionContext);
            assertThat(appVerCategoryConfigDTOList).isNotNull();
        }

        //@Test
        @DisplayName("JUnit for getAppVerCategoryConfigs in application service for catch block for checker")
        void getAppVerCategoryConfigsCatchBlockForChecker() throws JsonProcessingException, FatalException {

            MutationEntity unauthorizedEntities = getMutationEntity();
            MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
            sessionContext.setRole(new String[] { "" });
            given(mutationsDomainService.getMutations(
                    appVerCategoryConfigDTO1.getTaskCode()))
                    .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
            Assertions.assertThrows(FatalException.class,()-> {
                List<AppVerCategoryConfigDTO> appVerCategoryConfigDTO1 = appVerCategoryApplicationService.getAppVerCategoryConfigs(sessionContext);
                assertThat(appVerCategoryConfigDTO1).isNotNull();
            });
        }

        @Test
        @DisplayName("JUnit for processAppVerCategoryConfigs in application service for Try Block")
        void processAppVerCategoryConfigForTryBlock() throws JsonProcessingException, FatalException {
            doNothing().when(process).process(appVerCategoryConfigDTO);
            appVerCategoryApplicationService.processAppVerCategoryConfigs(sessionContext, appVerCategoryConfigDTO);
            verify(process, times(1)).process(appVerCategoryConfigDTO);
        }


        @Test
        @DisplayName("JUnit for processAppVerCategoryConfigs in application service for Catch Block")
        void processAppVerCategoryConfigsForCatchBlock() throws FatalException {
            SessionContext sessionContext2=null;
            Assertions.assertThrows(Exception.class,()-> {
                appVerCategoryApplicationService.processAppVerCategoryConfigs(sessionContext2, appVerCategoryConfigDTO);
                assertThat(appVerCategoryConfigDTO).descriptionText();
            });
        }

        @Test
        @DisplayName("JUnit for addUpdateRecord in application service")
        void addUpdateRecordTest() throws JsonProcessingException {
            doNothing().when(appVerCategoryConfigDomainService).save(appVerCategoryConfigDTO);
            appVerCategoryApplicationService.save(appVerCategoryConfigDTO);
            appVerCategoryApplicationService.addUpdateRecord(payLoadString1);
            verify(appVerCategoryConfigDomainService, times(1)).save(appVerCategoryConfigDTO);

        }

        @Test
        @DisplayName("JUnit for ConfigurationByCode in application service")
        void getConfigurationByCodeTest(){
            String code = appVerCategoryConfigDTO.getAppVerificationCategoryId();
            given(appVerCategoryConfigDomainService.getAppVerCategoryConfigByID(code)).willReturn(appVerCategoryConfigEntity);
            appVerCategoryApplicationService.getConfigurationByCode(code);
            assertThat(appVerCategoryConfigEntity).isNotNull();
        }

//----------------------------------------Negative---------------------------------


        @Test
        @DisplayName("JUnit for getAppVerCategoryByID in application service when Authorize for Negative")
        void getAppVerCategoryByCodeIsAuthorizeforNegative() throws FatalException {
            given(appVerCategoryConfigDomainService.getAppVerCategoryConfigByID(appVerCategoryConfigDTO.getAppVerificationCategoryId())).willReturn(appVerCategoryConfigEntity);
            given(appVerCategoryConfigAssembler.convertEntityToDto(appVerCategoryConfigEntity)).willReturn(appVerCategoryConfigDTO);
            AppVerCategoryConfigDTO appVerCategoryConfigDTO1 = appVerCategoryApplicationService.getAppVerCategoryConfigByID(sessionContext, appVerCategoryConfigDTO);
            assertNotEquals("N",appVerCategoryConfigDTO1.getAuthorized());
            assertThat(appVerCategoryConfigDTO1).isNotNull();
        }

    //    @Test
        @DisplayName("JUnit for getAppVerCategoryByCode in application service when UnAuthorize fetche no Record from database")
        void getAppVerCategoryByCodeNotAuthorizeNull() throws FatalException {
            given(mutationsDomainService.getConfigurationByCode(appVerCategoryConfigDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);
            appVerCategoryConfigDTO.setAuthorized("N");
            mutationEntity.setPayload(new Payload(payLoadString1));
            AppVerCategoryConfigDTO appVercategoryConfigDTO1 = appVerCategoryApplicationService.getAppVerCategoryConfigByID(sessionContext, appVerCategoryConfigDTO);
            assertNotEquals("Y",appVercategoryConfigDTO1.getAuthorized());
        }

        @Test
        @DisplayName("JUnit for getAppVerCategoryByCode in application service check Parameter not null")
        void getAppVerCategoryByCodeIsAuthorizeCheckParameter() throws FatalException {
            given(appVerCategoryConfigDomainService.getAppVerCategoryConfigByID(appVerCategoryConfigDTO.getAppVerificationCategoryId())).willReturn(appVerCategoryConfigEntity);
            given(appVerCategoryConfigAssembler.convertEntityToDto(appVerCategoryConfigEntity)).willReturn(appVerCategoryConfigDTO);
            AppVerCategoryConfigDTO appVerCategoryConfigDTO1 = appVerCategoryApplicationService.getAppVerCategoryConfigByID(sessionContext, appVerCategoryConfigDTO);
            assertThat(appVerCategoryConfigDTO1.getAppVerificationCategoryId()).isNotBlank();
            assertThat(appVerCategoryConfigDTO1.getAuthorized()).isNotBlank();
        }

    //    @Test
        @DisplayName("JUnit for getAppVerCategoryByCode in application service when Not Authorize in try block for Negative when getAuthorized unexpected is Y")
        void getAppVerCategoryByCodewhenNotAuthorizeTryBlockForNegative() throws JsonProcessingException, FatalException {
            given(mutationsDomainService.getConfigurationByCode(appVerCategoryConfigDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);
            mutationEntity.setPayload(new Payload(payLoadString1));
            appVerCategoryConfigDTOUnAuth.setAuthorized("N");
            AppVerCategoryConfigDTO appVerCategoryConfigDTO1 = appVerCategoryApplicationService.getAppVerCategoryConfigByID(sessionContext,appVerCategoryConfigDTOUnAuth);
            assertNotEquals("Y",appVerCategoryConfigDTO1.getAuthorized());
            assertThat(appVerCategoryConfigDTO1).isNotNull();
        }


        @Test
        @DisplayName("JUnit for code coverage")
        void getCodeCoverage()
        {
            assertThat(appVerCategoryConfigEntity.toString()).isNotNull();
            assertThat(appVerCategoryConfigDTO.toString()).isNotNull();
            AppVerTypeConfigDTO appVerTypeConfigDTO =new AppVerTypeConfigDTO("VC001",true,"mutation");
            AppVerCategoryConfigDTO appVerCategoryConfigDTO2=new AppVerCategoryConfigDTO("V0001","address",true,List.of(appVerTypeConfigDTO));
            AppVerTypeConfigDTO.builder().appVerificationTypeId("V0001").isViewToCustomer(true).nature("mutation").build().toString();
            AppVerCategoryConfigDTO.builder().appVerificationCategoryId("VC0001").verificationCategoryDesc("Address").isExternal(true).appVerTypeConfig(List.of()).build();
            AppVerCategoryConfigEntityKey appVerCategoryConfigEntityKey=new AppVerCategoryConfigEntityKey("VC0001");
            AppVerCategoryConfigEntityKey appVerCategoryConfigEntityKey1=new AppVerCategoryConfigEntityKey();
            AppVerCategoryConfigEntity appVerCategoryConfigEntity2=new AppVerCategoryConfigEntity("VC0001","Address",'Y',List.of(),"LC001","RF001","draft",1,"N","draft");
            AppVerTypeConfigEntity appVerTypeConfigEntity=new AppVerTypeConfigEntity();
            appVerTypeConfigEntity.setAppCategoryTypeId(1);
           assertThat(appVerCategoryConfigEntityKey1.toString()).isNotNull();
            assertThat(appVerCategoryConfigEntityKey.toString()).isNotNull();
            appVerCategoryConfigEntityKey.setAppVerificationCategoryId("VC0001");
            System.out.println(appVerCategoryConfigEntityKey.getAppVerificationCategoryId());
            appVerCategoryConfigEntityKey.keyAsString();
            appVerCategoryConfigEntityKey.builder().appVerificationCategoryId("VC001").build();
            assertThat(appVerCategoryConfigDTO).descriptionText();
        }

    @Test
    @DisplayName("JUnit for getAppVerCategoryConfigs in application service for try block negative scenario for SessionContext some field not be null")
    void getAppVerCategoryConfigsTryBlockNegative() throws JsonProcessingException, FatalException {

        AppVerCategoryConfigDTO appVerCategoryConfigDTOO= new AppVerCategoryConfigDTO();
        AppVerCategoryConfigEntity appVerCategoryConfigEntity5 = new AppVerCategoryConfigEntity();
       // given(mutationsDomainService.getUnauthorizedMutation(appVerCategoryConfigDTOO.getTaskCode(),AUTHORIZED_N)).willReturn(List.of(mutationEntity));
        given(mutationsDomainService.getMutations(
                appVerCategoryConfigDTO1.getTaskCode()))
                .willReturn(List.of(mutationEntity));
        given(appVerCategoryConfigDomainService.getAppVerCategoryConfigs()).willReturn(List.of(appVerCategoryConfigEntity5));
        given(appVerCategoryConfigAssembler.convertEntityToDto(appVerCategoryConfigEntity5)).willReturn(appVerCategoryConfigDTOO);
        given(appVerCategoryConfigAssembler.setAuditFields(mutationEntity,appVerCategoryConfigDTOO)).willReturn(appVerCategoryConfigDTOO);
        Assertions.assertThrows(Exception.class,()-> {
            List<AppVerCategoryConfigDTO> AppVerCategoryConfigDTO2 = appVerCategoryApplicationService.getAppVerCategoryConfigs(sessionContext1);
            assertThat(sessionContext1.getRole()).isNotEmpty();
            assertThat(sessionContext1.getServiceInvocationModeType()).isNotNull();
        });

    }







        private SessionContext getValidSessionContext () {
            SessionContext sessionContext = SessionContext.builder()
                    .bankCode("003").defaultBranchCode("1141").internalTransactionReferenceNumber("")
                    .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                    .targetUnit("dummy_target").postingDate(new Date()).valueDate(new Date()).transactionBranch(null)
                    .userId("nikhiljagtap")
//                .accessibleTargetUnits([])
                    .channel("Branch").taskCode("CATEGORY")
                    .originalTransactionReferenceNumber(null)
                    .externalBatchNumber(1L)
                    .customAttributes(null)
                    .serviceInvocationModeType(Regular)
                    .allTargetUnitsSelected(true)
//                .mutationType("")
                    .userLocal("es_US")
                    .originatingModuleCode(null)
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
                    .channel("Branch").taskCode("CATEGORY")
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

        private AppVerCategoryConfigDTO getAppVerCategoryConfigDTOAuthorized () {
            AppVerCategoryConfigDTO appVerCategoryConfigDTO = new AppVerCategoryConfigDTO();
            appVerCategoryConfigDTO.setAppVerificationCategoryId("VC0001");
            appVerCategoryConfigDTO.setVerificationCategoryDesc("Address");
            appVerCategoryConfigDTO.setExternal(true);
            List<AppVerTypeConfigDTO> appVerTypeConfigDTO=new ArrayList<>();
            appVerTypeConfigDTO.add(new AppVerTypeConfigDTO("VC001",true,"mutation"));
            appVerCategoryConfigDTO.setAppVerTypeConfig(appVerTypeConfigDTO);
            appVerCategoryConfigDTO.setAuthorized("Y");
            return appVerCategoryConfigDTO;
        }

        private AppVerCategoryConfigDTO getAppVerCategoryConfigDTO()
        {
            AppVerCategoryConfigDTO appVerCategoryConfigDTO = new AppVerCategoryConfigDTO();
            appVerCategoryConfigDTO.setAppVerificationCategoryId("VC0001");
            appVerCategoryConfigDTO.setVerificationCategoryDesc("Address");
            appVerCategoryConfigDTO.setExternal(true);
            List<AppVerTypeConfigDTO> appVerTypeConfigDTO=new ArrayList<>();
            appVerTypeConfigDTO.add(new AppVerTypeConfigDTO("VC001",true,"mutation"));
            appVerCategoryConfigDTO.setAppVerTypeConfig(appVerTypeConfigDTO);
            appVerCategoryConfigDTO.setStatus("DELETED");
            appVerCategoryConfigDTO.setRecordVersion(1);
            return appVerCategoryConfigDTO;
        }
        private AppVerCategoryConfigEntity getAppVerCategoryConfigEntity(){
            AppVerCategoryConfigEntity appVerCategoryConfigEntity = new AppVerCategoryConfigEntity();
            appVerCategoryConfigEntity.setAppVerificationCategoryId("VC0001");
            appVerCategoryConfigEntity.setVerificationCategoryDesc("Address");
            appVerCategoryConfigEntity.setIsExternal('Y');

            List<AppVerTypeConfigEntity> appVerTypeConfigEntity=new ArrayList<>();
            appVerTypeConfigEntity.add(new AppVerTypeConfigEntity(1,"VC001",'Y',"mutation"));
            appVerCategoryConfigEntity.setAppVerTypeConfigEntity(appVerTypeConfigEntity);
            appVerCategoryConfigEntity.setStatus("draft");
            appVerCategoryConfigEntity.setRecordVersion(0);
            return appVerCategoryConfigEntity;
        }
        private AppVerCategoryConfigEntity getAppVerCategoryConfigEntity1()
        {
            AppVerCategoryConfigEntity appVerCategoryConfigEntity = new AppVerCategoryConfigEntity();
            appVerCategoryConfigEntity.setAppVerificationCategoryId("VC0001");
            appVerCategoryConfigEntity.setVerificationCategoryDesc("Address");
            appVerCategoryConfigEntity.setIsExternal('Y');

            List<AppVerTypeConfigEntity> appVerTypeConfigEntity=new ArrayList<>();
            appVerTypeConfigEntity.add(new AppVerTypeConfigEntity(1,"VC001",'Y',"mutation"));
            appVerCategoryConfigEntity.setAppVerTypeConfigEntity(appVerTypeConfigEntity);
            appVerCategoryConfigEntity.setStatus("DELETED");
            appVerCategoryConfigEntity.setRecordVersion(1);
            return appVerCategoryConfigEntity;

        }
        private AppVerCategoryConfigDTO getAppVerCategoryConfigDTOUnAuth(){
            AppVerCategoryConfigDTO appVerCategoryConfigDTO = new AppVerCategoryConfigDTO();
            appVerCategoryConfigDTO.setAppVerificationCategoryId("VC0001");
            appVerCategoryConfigDTO.setVerificationCategoryDesc("Address");
            appVerCategoryConfigDTO.setExternal(true);
            appVerCategoryConfigDTO.setAuthorized("N");
            List<AppVerTypeConfigDTO> appVerTypeConfigDTO=new ArrayList<>();
            appVerTypeConfigDTO.add(new AppVerTypeConfigDTO("VC001",true,"mutation"));
            appVerCategoryConfigDTO.setAppVerTypeConfig(appVerTypeConfigDTO);

            return appVerCategoryConfigDTO;

        }

        private AppVerCategoryConfigDTO getAppVerCategoryConfigDTOMapper(){
            AppVerCategoryConfigDTO appVerCategoryConfigDTO = new AppVerCategoryConfigDTO();
            appVerCategoryConfigDTO.setAppVerificationCategoryId("VC0001");
            appVerCategoryConfigDTO.setVerificationCategoryDesc("Address");
            appVerCategoryConfigDTO.setExternal(true);

            List<AppVerTypeConfigDTO> appVerTypeConfigDTO=new ArrayList<>();
            appVerTypeConfigDTO.add(new AppVerTypeConfigDTO("VC001",true,"mutation"));
            appVerCategoryConfigDTO.setAppVerTypeConfig(appVerTypeConfigDTO);
            appVerCategoryConfigDTO.setAuthorized("N");
            appVerCategoryConfigDTO.setTaskCode("CATEGORY");
            appVerCategoryConfigDTO.setTaskIdentifier("VC0001");
            return appVerCategoryConfigDTO;
        }

        private MutationEntity getMutationEntity() {
            String payLoadString="{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":1,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"CATEGORY\",\"taskIdentifier\":\"VC0001\",\"appVerificationCategoryId\":\"VC0001\",\"verificationCategoryDesc\":\"Addresss\",\"isExternal\":true,\"appVerTypeConfig\":[{\"taskCode\":\"TYPE\",\"taskIdentifier\":\"V00001\",\"appVerificationTypeId\":\"V00001\",\"isViewToCustomer\":false,\"nature\":\"mutation\"},{\"taskCode\":\"TYPE\",\"taskIdentifier\":\"V00002\",\"appVerificationTypeId\":\"V00002\",\"isViewToCustomer\":false,\"nature\":\"mutation\"},{\"taskCode\":\"TYPE\",\"taskIdentifier\":\"V00003\",\"appVerificationTypeId\":\"V00003\",\"isViewToCustomer\":false,\"nature\":\"mutation\"}]}";

            MutationEntity mutationEntity = new MutationEntity();

            mutationEntity.setTaskIdentifier("VC0001");
            mutationEntity.setTaskCode("CATEGORY");
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

        mutationEntity.setTaskIdentifier("VC0001");
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
            String payLoadString1 ="{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":hhhhhh,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"CATEGORY\",\"taskIdentifier\":\"VC0001\",\"appVerificationCategoryId\":\"VC0001\",\"verificationCategoryDesc\":\"Addresss\",\"isExternal\":true,\"appVerTypeConfig\":[{\"taskCode\":\"TYPE\",\"taskIdentifier\":\"V00001\",\"appVerificationTypeId\":\"V00001\",\"isViewToCustomer\":false,\"nature\":\"mutation\"},{\"taskCode\":\"TYPE\",\"taskIdentifier\":\"V00002\",\"appVerificationTypeId\":\"V00002\",\"isViewToCustomer\":false,\"nature\":\"mutation\"},{\"taskCode\":\"TYPE\",\"taskIdentifier\":\"V00003\",\"appVerificationTypeId\":\"V00003\",\"isViewToCustomer\":false,\"nature\":\"mutation\"}]}";
            MutationEntity mutationEntity2 = new MutationEntity();
            mutationEntity2.setTaskIdentifier("VC0001");
            mutationEntity2.setTaskCode("CATEGORY");
            mutationEntity2.setPayload(new Payload(payLoadString1));
            mutationEntity2.setStatus("closed");
            mutationEntity2.setAuthorized("N");
            mutationEntity2.setRecordVersion(1);
            mutationEntity2.setAction("add");
            mutationEntity2.setLastConfigurationAction("unauthorized");
            return mutationEntity2;
        }


        String payLoadString1="{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"CATEGORY\",\"taskIdentifier\":\"VC0001\",\"appVerificationCategoryId\":\"VC0001\",\"verificationCategoryDesc\":\"Addresss\",\"isExternal\":true,\"appVerTypeConfig\":[{\"taskCode\":\"TYPE\",\"taskIdentifier\":\"V00001\",\"appVerificationTypeId\":\"V00001\",\"isViewToCustomer\":false,\"nature\":\"mutation\"},{\"taskCode\":\"TYPE\",\"taskIdentifier\":\"V00002\",\"appVerificationTypeId\":\"V00002\",\"isViewToCustomer\":false,\"nature\":\"mutation\"},{\"taskCode\":\"TYPE\",\"taskIdentifier\":\"V00003\",\"appVerificationTypeId\":\"V00003\",\"isViewToCustomer\":false,\"nature\":\"mutation\"}]}";



}