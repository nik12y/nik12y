package com.idg.idgcore.coe.app.service.module;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.coe.domain.assembler.module.ModuleAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.entity.module.ModuleEntity;
import com.idg.idgcore.coe.domain.entity.module.ModuleEntityKey;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.module.IModuleDomainService;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.module.ModuleDTO;
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
class ModuleApplicationServiceTest {
    @InjectMocks
    private ModuleApplicationService moduleApplicationService;
    @Mock private ModuleAssembler moduleAssembler;
    @Mock private IModuleDomainService moduleDomainService;
    @Mock private IMutationsDomainService mutationsDomainService;
    @Mock private ProcessConfiguration processConfiguration;
    @Autowired
    private MutationEntity mutationEntity;
    private MutationEntity mutationEntity2;

    private SessionContext sessionContext;
    private ModuleDTO moduleDTO;
    private ModuleDTO moduleDTOAuth;
    private ModuleDTO moduleDTOUnAuth;
    private ModuleEntity moduleEntity;
    private ModuleEntity moduleEntityUnAut;
    private ModuleDTO moduleDTOMapper;
    private ModuleDTO moduleDTO1;

    @BeforeEach
    void setUp () {
        sessionContext = getValidSessionContext();
        moduleDTOAuth = getModuleDTOAuthorized();
        moduleEntity = getModuleEntity();
        moduleDTOUnAuth = getModuleDTOUnAuthorized();
        moduleEntityUnAut = getModuleEntity();
        moduleEntityUnAut.setAuthorized("N");
        mutationEntity = getMutationEntity();
        moduleDTO = getModuleDTO();
        //moduleDTOMapper = getModuleDTOMapper();
        moduleDTO1 = getModuleDTO();
        //mutationEntity2=getMutationEntityJsonError();
    }

    @Test
    @DisplayName ("JUnit for getModuleByCode in application service when Authorize")
    void getModuleByCodeWithAuthRecord () throws FatalException {
        given(moduleDomainService.getModuleByCode(moduleDTOAuth.getModuleCode())).willReturn(moduleEntity);
        given(moduleAssembler.convertEntityToDto(moduleEntity)).willReturn(moduleDTOAuth);
        ModuleDTO moduleDTO1 = moduleApplicationService.getModuleByCode(sessionContext, moduleDTOAuth);
        assertThat(moduleDTO1.getAuthorized()).isEqualTo("Y");
        assertThat(moduleDTOAuth).isNotNull();
        //assertThat(ibanDTOAuth.toString()).isNotNull();
        //assertThat(ibanDTO.toString()).isNotNull();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverageDTO()
    {
        System.out.println(moduleEntity.toString());
        System.out.println(moduleDTO.toString());

        ModuleDTO moduleDTO2 = new ModuleDTO("AB","TESTModule","SBI",12,12,true,true,true,true);

        String s = ModuleDTO.builder()
                .moduleCode("AB")
                .moduleName("TESTModule")
                .bankCode("SBI")
                .moduleUsers(12)
                .moduleCurrentUsers(12)
                .licensed(true)
                .purgeAvailable(true)
                .userDefinedFields(true)
                .installed(true)
                .build()
                .toString();


        ModuleEntityKey moduleEntityKey=new ModuleEntityKey("IA");


        moduleEntityKey.setModuleCode("IA");
        System.out.println(moduleEntityKey.getModuleCode());
        moduleEntityKey.keyAsString();
        //ibanEntityKey.builder().bankCode("0003").build();
        assertThat(moduleDTO2).descriptionText();
        //assertThat(s.toString()).isNotNull();
    }


    @DisplayName ("JUnit test for processModule method")
    @Test
    void processModuleWithNew () throws JsonProcessingException, FatalException {
        ModuleDTO moduleDTONew = getModuleDTOMapper();
        doNothing().when(processConfiguration).process(moduleDTONew);
        moduleApplicationService.processModule(sessionContext, moduleDTONew);
        verify(processConfiguration, times(1)).process(moduleDTONew);
    }

    @DisplayName ("JUnit test for addUpdateRecord method")
    @Test
    void addUpdateRecord () throws JsonProcessingException, FatalException {
        String payloadStr = getpayloadValidString();
        ModuleDTO moduleDTO = getModuleDTOForSave();
        doNothing().when(moduleDomainService).save(moduleDTO);
        moduleApplicationService.save(moduleDTO);
        moduleApplicationService.addUpdateRecord(payloadStr);
        verify(moduleDomainService, times(1)).save(moduleDTO);
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = moduleDTO.getModuleCode();
        given(moduleDomainService.getModuleByCode(code)).willReturn(moduleEntity);
        moduleApplicationService.getConfigurationByCode(code);
        assertThat(moduleEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeDTOTest(){
        String code = moduleDTO.getModuleCode();
        given(moduleDomainService.getModuleByCode(code)).willReturn(moduleEntity);
        moduleApplicationService.getConfigurationByCode(code);
        assertThat(moduleEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for processIban in application service for Try Block")
    void processModuleForTryBlock() throws JsonProcessingException, FatalException {
        doNothing().when(processConfiguration).process(moduleDTO);
        moduleApplicationService.processModule(sessionContext, moduleDTO);
        verify(processConfiguration, times(1)).process(moduleDTO);
    }

    @Test
    @DisplayName("JUnit for processIban in application service for Catch Block")
    void processModuleForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            moduleApplicationService.processModule(sessionContext2, moduleDTO);
            assertThat(moduleDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for getIbanByIbanCountryCode in application service when Not Authorize in catch block")
    void getModuleByCodeWhenNotAuthorizeCatchBlock () throws FatalException {
        String payLoadString1 =getpayloadInvalidString();
        //"{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"STATE\",\"taskIdentifier\":\"MH\",\"stateCode\":\"MH\",\"stateName\":\"MAHARASHTRA\",\"countryCode\":\"IN\"}";
        given(mutationsDomainService.getConfigurationByCode(moduleDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        /*given(ibanAssembler.setAuditFields(mutationEntity2, ibanDTOUnAuth))
                .willReturn(ibanDTOUnAuth);*/
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = mock(PayloadDTO.class);
        Assertions.assertThrows(Exception.class,()-> {
            ModuleDTO moduleDTO1 = moduleApplicationService.getModuleByCode(sessionContext, moduleDTOUnAuth);
            assertEquals("N", moduleDTO1.getAuthorized());
            assertThat(moduleDTO1).isNotNull();

        });
    }
    /**
     *
     * Negative Test Cases
     */
    @Test
    @DisplayName("JUnit for getBankByCode in application service when Authorize for Negative")
    void getModuleByCodeIsAuthorizeForNegative() throws FatalException {
        given(moduleDomainService.getModuleByCode(moduleDTO.getModuleCode())).willReturn(moduleEntity);
        given(moduleAssembler.convertEntityToDto(moduleEntity)).willReturn(moduleDTO);
        ModuleDTO moduleDTO1 = moduleApplicationService.getModuleByCode(sessionContext, moduleDTO);
        assertNotEquals("N",moduleDTO1.getAuthorized());
        assertThat(moduleDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getBankByCode in application service check Parameter not null")
    void getModuleByCodeIsAuthorizeCheckParameter() throws FatalException {
        //IbanDTO ibanDTOnull=null;
        ModuleDTO moduleDTOEx=new ModuleDTO();
        moduleDTOEx.setModuleCode("AB");
        moduleDTOEx.setAuthorized("Y");
        given(moduleDomainService.getModuleByCode(moduleDTOEx.getModuleCode())).willReturn(moduleEntity);
        given(moduleAssembler.convertEntityToDto(moduleEntity)).willReturn(moduleDTO);
        ModuleDTO stateDTO1 = moduleApplicationService.getModuleByCode(sessionContext, moduleDTOEx);
        assertThat(moduleDTOEx.getModuleCode()).isNotBlank();
        assertThat(moduleDTOEx.getAuthorized()).isNotBlank();
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


    private String moduleCode;
    private String moduleName;
    private String bankCode;
    private Integer moduleUsers;
    private Integer moduleCurrentUsers;
    private boolean licensed;
    private boolean purgeAvailable;
    private boolean userDefinedFields;
    private boolean installed;


    private ModuleDTO getModuleDTOAuthorized () {
        ModuleDTO moduleDTOMapper = new ModuleDTO();

        moduleDTOMapper.setModuleCode("AB");
        moduleDTOMapper.setModuleName("TESTMODULE");
        moduleDTOMapper.setBankCode("SBI");
        moduleDTOMapper.setModuleUsers(12);
        moduleDTOMapper.setModuleCurrentUsers(12);
        moduleDTOMapper.setLicensed(true);
        moduleDTOMapper.setPurgeAvailable(true);
        moduleDTOMapper.setUserDefinedFields(true);
        moduleDTOMapper.setInstalled(true);

        moduleDTOMapper.setAuthorized("Y");
        moduleDTOMapper.setTaskCode("MODULE");
        moduleDTOMapper.setTaskIdentifier("AB");
        return moduleDTOMapper;
    }

    private ModuleDTO getModuleDTOUnAuthorized () {
        ModuleDTO moduleDTOMapper = new ModuleDTO();

        moduleDTOMapper.setModuleCode("AB");
        moduleDTOMapper.setModuleName("TESTMODULE");
        moduleDTOMapper.setBankCode("SBI");
        moduleDTOMapper.setModuleUsers(12);
        moduleDTOMapper.setModuleCurrentUsers(12);
        moduleDTOMapper.setLicensed(true);
        moduleDTOMapper.setPurgeAvailable(true);
        moduleDTOMapper.setUserDefinedFields(true);
        moduleDTOMapper.setInstalled(true);
        moduleDTOMapper.setTaskCode("MODULE");
        moduleDTOMapper.setTaskIdentifier("AB");
        moduleDTOMapper.setAuthorized("N");
        return moduleDTOMapper;
    }


    private ModuleDTO getModuleDTO () {
        ModuleDTO moduleDTO = new ModuleDTO();

        moduleDTO.setModuleCode("AB");
        moduleDTO.setModuleName("TESTMODULE");
        moduleDTO.setBankCode("SBI");
        moduleDTO.setModuleUsers(12);
        moduleDTO.setModuleCurrentUsers(12);
        moduleDTO.setLicensed(true);
        moduleDTO.setPurgeAvailable(true);
        moduleDTO.setUserDefinedFields(true);
        moduleDTO.setInstalled(true);

        moduleDTO.setTaskCode("MODULE");
        moduleDTO.setTaskIdentifier("AB");

        moduleDTO.setAuthorized("Y");
        moduleDTO.setTaskCode("AB");
        moduleDTO.setStatus("DELETED");
        moduleDTO.setRecordVersion(1);
        return moduleDTO;
    }

    private ModuleDTO getModuleDTOMapper () {
        ModuleDTO moduleDTOMapper = new ModuleDTO();

        moduleDTO.setModuleCode("AB");
        moduleDTO.setModuleName("TESTMODULE");
        moduleDTO.setBankCode("SBI");
        moduleDTO.setModuleUsers(12);
        moduleDTO.setModuleCurrentUsers(12);
        moduleDTO.setLicensed(true);
        moduleDTO.setPurgeAvailable(true);
        moduleDTO.setUserDefinedFields(true);
        moduleDTO.setInstalled(true);
        moduleDTO.setTaskCode("MODULE");
        moduleDTO.setTaskIdentifier("AB");
        moduleDTO.setAuthorized("N");
        moduleDTO.setTaskCode("MODULE");
        moduleDTO.setTaskIdentifier("AB");

        return moduleDTOMapper;
    }

    private ModuleDTO getModuleDTOForSave () {
        ModuleDTO moduleDTOMapper = new ModuleDTO();

        moduleDTOMapper.setModuleCode("AB");
        moduleDTOMapper.setModuleName("TESTMODULE");
        moduleDTOMapper.setBankCode("SBI");
        moduleDTOMapper.setModuleUsers(12);
        moduleDTOMapper.setModuleCurrentUsers(12);
        moduleDTOMapper.setLicensed(true);
        moduleDTOMapper.setPurgeAvailable(true);
        moduleDTOMapper.setUserDefinedFields(true);
        moduleDTOMapper.setInstalled(true);

        moduleDTOMapper.setTaskCode("MODULE");
        moduleDTOMapper.setTaskIdentifier("AB");
        moduleDTOMapper.setAction("authorize");
        moduleDTOMapper.setStatus("active");
        moduleDTOMapper.setRecordVersion(1);
        moduleDTOMapper.setAuthorized("N");
        moduleDTOMapper.setLastConfigurationAction("authorized");
        moduleDTOMapper.setTaskCode("Module");
        moduleDTOMapper.setTaskIdentifier("AB");

        return moduleDTOMapper;
    }

    private ModuleEntity getModuleEntity () {
        ModuleEntity moduleEntity = new ModuleEntity();

        moduleEntity.setModuleCode("AB");
        moduleEntity.setModuleName("TESTMODULE");
        moduleEntity.setBankCode("SBI");
        moduleEntity.setModuleUsers(12);
        moduleEntity.setModuleCurrentUser(12);
        moduleEntity.setIsLicensed('Y');
        moduleEntity.setIsPurgeAvailable('Y');
        moduleEntity.setIsUdf('Y');
        moduleEntity.setIsInstalled('Y');
        moduleEntity.setAuthorized("Y");
        return moduleEntity;
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
        mutationEntity.setTaskCode("MODULE");
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
        mutationEntity.setTaskCode("MODULE");
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
                "{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":0,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"Module\",\"taskIdentifier\":\"AB\",\"moduleCode\":\"AB\",\"moduleName\":\"TESTModule\",\"bankCode\":\"SBI\",\"moduleUsers\":12,\"moduleCurrentUsers\":1,\"licensed\":true,\"purgeAvailable\":true,\"userDefinedFields\":true,\"afterXDays\":true,\"installed\":true}";
        return payLoadString;
    }

    private String getpayloadInvalidString () {
        String payLoadString =
                "{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":10,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"Module\",\"taskIdentifier\":\"AB\",\"moduleCode\":\"AB\",\"moduleName\":\"TESTModule\",\"bankCode\":\"SBI\",\"moduleUsers\":12,\"moduleCurrentUsers\":1,\"licensed\":true,\"purgeAvailable\":true,\"userDefinedFields\":true,\"afterXDays\":true,\"installed\":true}";
        return payLoadString;
    }

}