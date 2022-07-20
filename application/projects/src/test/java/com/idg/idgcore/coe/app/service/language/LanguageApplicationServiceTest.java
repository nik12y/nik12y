package com.idg.idgcore.coe.app.service.language;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.coe.domain.service.bank.language.LanguageApplicationService;
import com.idg.idgcore.coe.domain.assembler.language.LanguageAssembler;

import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.entity.language.LanguageEntity;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;

import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.language.ILanguageDomainService;

import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.language.LanguageDTO;
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

import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class LanguageApplicationServiceTest {

    @InjectMocks
    private LanguageApplicationService languageApplicationService;

    @Mock
    private ProcessConfiguration process;

    @Mock
    private LanguageAssembler languageAssembler;

    @Mock
    private ILanguageDomainService languageDomainService;

    @Mock
    AbstractApplicationService abstractApplicationService;

    @Autowired
    private MutationEntity mutationEntity;

    private MutationEntity mutationEntity2;

    @Mock
    private IMutationsDomainService mutationsDomainService;

    private SessionContext sessionContext;
    private SessionContext sessionContext1;

    private LanguageEntity languageEntity;
    private LanguageDTO languageDTO;
    private LanguageDTO languageDTOUnAuth;
    private LanguageDTO languageDTO1;
    private LanguageEntity languageEntity1;

    private LanguageDTO languageDTOMapper;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext ();
        sessionContext1=getErrorSession();
        languageDTO=getlanguageDTOAuthorized();
        languageEntity=getLanguageEntity();
        languageDTOUnAuth=getLanguageDTOUnAuth();
        languageDTOMapper=getLanguageDTOMapper();
        mutationEntity=getMutationEntity();
        languageEntity1=getLanguagesEntity();
        languageDTO1=getLanguagesDTO();
        mutationEntity2=getMutationEntityJsonError();
    }

    @Test
    @DisplayName("JUnit for getByLanguageCode in application service when Authorize try Block")
    void getLanguageByCodeIsAuthorize() throws FatalException {
        given(languageDomainService.getLanguageByCode(languageDTO.getLanguageCode())).willReturn(languageEntity);
        given(languageAssembler.convertEntityToDto(languageEntity)).willReturn(languageDTO);
        LanguageDTO languageDTO1 = languageApplicationService.getLanguageByCode(sessionContext, languageDTO);
        assertEquals("Y",languageDTO1.getAuthorized());
        assertThat(languageDTO1).isNotNull();
    }

//    @Test
//    @DisplayName("JUnit for getByLanguageCode in application service when Not Authorize in try else block")
//    void getLanguageByCodewhenNotAuthorizeTryBlock() throws JsonProcessingException, FatalException {
//        given(mutationsDomainService.getConfigurationByCode(languageDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);
//        LanguageDTO languageDTO1 = languageApplicationService.getLanguageByCode(sessionContext,languageDTOMapper);
//        assertEquals("N",languageDTO1.getAuthorized());
//        assertThat(languageDTO1).isNotNull();
//        System.out.println(languageDTO1);
//    }

    @Test
    @DisplayName("JUnit for getByLanguageCode in application service when Not Authorize in catch block")
    void getLanguageByCodewhenNotAuthorizeCatchBlock () throws FatalException {
        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"LANGUAGE\",\"taskIdentifier\":\"EN\",\"languageCode\":\"EN\",\"languageName\":\"English\",\"languageCode\":\"EN\"}";
        given(mutationsDomainService.getConfigurationByCode(languageDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);
//                     Mockito.when(mockObjectMapper.readValue(mutationEntity.getPayload().getData(), StateDTO.class)).thenReturn(stateDTO);
        Assertions.assertThrows(Exception.class,()-> {
            LanguageDTO languageDTO1 = languageApplicationService.getLanguageByCode(sessionContext, languageDTOMapper);
            assertEquals("N",languageDTO1.getAuthorized());
            assertThat(languageDTO1).isNotNull();
            System.out.println(languageDTO1);
        });
    }

//---------------------------------------------getStates-------------------------------------
//    @Test
//    @DisplayName("JUnit for getStates in application service for try block for checker")
//    void getStatesTryBlockForChecker() throws JsonProcessingException, FatalException {
//
//        MutationEntity unauthorizedEntities = getMutationEntity();
//        unauthorizedEntities.setStatus("draft");
//        MutationEntity unauthorizedEntities1 = getMutationEntity();
//        unauthorizedEntities1.setStatus("closed");
//        sessionContext.setRole(new String[] { "checker" });
//        given(mutationsDomainService.getUnauthorizedMutation(
//                stateDTO1.getTaskCode(),AUTHORIZED_N))
//                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
//
////        given(stateDomainService.getStates()).willReturn(List.of(stateEntity));
////        given(stateAssembler.convertEntityToDto(stateEntity)).willReturn(stateDTO);
//        given(stateAssembler.setAuditFields(unauthorizedEntities,stateDTO)).willReturn(stateDTO);
////        stateDTO.setAuthorized("N");
//        List<StateDTO> stateDTO1 = stateApplicationService.getStates(sessionContext);
//        System.out.println("return size : " + stateDTO1.size());
//        assertThat(stateDTO1).isNotNull();
//        System.out.println(stateDTO1);
//    }
//
//    @Test
//    @DisplayName("JUnit for getStates in application service for catch block for checker")
//    void getStatesCatchBlockForChecker() throws JsonProcessingException, FatalException {
//
//        MutationEntity unauthorizedEntities = getMutationEntity();
////        unauthorizedEntities.setStatus("draft");
//        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
////        unauthorizedEntities1.setStatus("closed");
//        sessionContext.setRole(new String[] { "" });
//        given(mutationsDomainService.getUnauthorizedMutation(
//                stateDTO1.getTaskCode(),AUTHORIZED_N))
//                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
//        Assertions.assertThrows(Exception.class,()-> {
//            List<StateDTO> stateDTO1 = stateApplicationService.getStates(sessionContext);
//            System.out.println("return size : " + stateDTO1.size());
//            assertThat(stateDTO1).isNotNull();
//            System.out.println(stateDTO1);
//        });
//    }


//-----------------------------------------------------------------------------------------


    @Test
    @DisplayName("JUnit for processLanguage in application service for Try Block")
    void processLanguageForTryBlock() throws JsonProcessingException, FatalException {

        doNothing().when(process).process(languageDTO);
        languageApplicationService.processLanguage(sessionContext, languageDTO);
        verify(process, times(1)).process(languageDTO);
    }

    @Test
    @DisplayName("JUnit for processLanguage in application service for Catch Block")
    void processLanguageForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            languageApplicationService.processLanguage(sessionContext2, languageDTO);
            assertThat(languageDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {
        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"LANGUAGE\",\"taskIdentifier\":\"EN\",\"languageCode\":\"EN\",\"languageName\":\"English\",\"localeCode\":\"EN\"}";
        doNothing().when(languageDomainService).save(languageDTO);
        languageApplicationService.save(languageDTO);
        languageApplicationService.addUpdateRecord(payLoadString1);
        verify(languageDomainService, times(1)).save(languageDTO);
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = languageDTO.getLanguageCode();
        given(languageDomainService.getLanguageByCode(code)).willReturn(languageEntity);
        languageApplicationService.getConfigurationByCode(code);
        assertThat(languageEntity).isNotNull();
    }

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder()
                .bankCode("").defaultBranchCode("").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("").postingDate(new Date()).valueDate(new Date()).transactionBranch("")
                .userId("prash")
//                .accessibleTargetUnits([])
                .channel("").taskCode("")
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(1L)
                .customAttributes("")
                .serviceInvocationModeType(Regular)
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
                .role(new String[] {"maker"})
                .build();
        return sessionContext1;
    }

    private LanguageDTO getlanguageDTOAuthorized () {
        LanguageDTO languageDTO = new LanguageDTO();
        languageDTO.setLanguageCode("EN");
        languageDTO.setLanguageCodeAlternate("ENG");
        languageDTO.setLanguageName("English");
        languageDTO.setLocaleCode("EN");
        languageDTO.setLocaleName("English(UK)");
        languageDTO.setAuthorized("Y");
        return languageDTO;
    }
    private LanguageDTO getLanguagesDTO()
    {
        LanguageDTO languageDTO = new LanguageDTO();
        languageDTO.setLanguageCode("EN");
        languageDTO.setLanguageCodeAlternate("ENG");
        languageDTO.setLanguageName("English");
        languageDTO.setLocaleCode("EN");
        languageDTO.setLocaleName("English(UK)");
        languageDTO.setTaskCode("EN");
        languageDTO.setStatus("DELETED");
        languageDTO.setRecordVersion(1);
        return languageDTO;
    }
    private LanguageEntity getLanguageEntity(){
        LanguageEntity languageEntity = new LanguageEntity("EN","ENG","English","EN","English(UK)","draft",0, "Y","draft");
        return languageEntity;
    }

    private LanguageEntity getLanguagesEntity()
    {
        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setLanguageCode("EN");
        languageEntity.setLanguageCodeAlternate("ENG");
        languageEntity.setLanguageName("English");
        languageEntity.setLocaleCode("EN");
        languageEntity.setLocaleName("English(UK)");
        languageEntity.setStatus("DELETED");
        languageEntity.setRecordVersion(1);
        return languageEntity;
    }
    private LanguageDTO getLanguageDTOUnAuth(){
        LanguageDTO languageDTO = new LanguageDTO();
        languageDTO.setLanguageCode("EN");
        languageDTO.setLanguageCodeAlternate("ENG");
        languageDTO.setLanguageName("English");
        languageDTO.setLocaleCode("EN");
        languageDTO.setLocaleName("English(UK)");
        languageDTO.setAuthorized("N");
        return languageDTO;
    }

    private LanguageDTO getLanguageDTOMapper(){
        LanguageDTO languageDTOMapper= new LanguageDTO();
        languageDTOMapper.setLanguageCode("EN");
        languageDTOMapper.setLanguageCodeAlternate("ENG");
        languageDTOMapper.setLanguageName("English");
        languageDTOMapper.setLocaleCode("EN");
        languageDTOMapper.setLocaleName("English(UK)");
        languageDTOMapper.setAuthorized("N");
        languageDTOMapper.setTaskCode("LANGUAGE");
        languageDTOMapper.setTaskIdentifier("EN");
        return languageDTOMapper;
    }

    private MutationEntity getMutationEntity() {
        String payLoadString="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"LANGUAGE\",\"taskIdentifier\":\"EN\",\"languageCode\":\"EN\",\"languageName\":\"English\",\"localeCode\":\"EN\"}";

        MutationEntity mutationEntity = new MutationEntity();

        mutationEntity.setTaskIdentifier("EN");
        mutationEntity.setTaskCode("LANGUAGE");
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
        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"LANGUAGE\",\"taskIdentifier\":\"EN\",\"languageCode\":\"MH\",\"languageName\":\"English\",\"localeCode\":\"EN\"}";
        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("EN");
        mutationEntity2.setTaskCode("LANGUAGE");
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");
        return mutationEntity2;
    }
}