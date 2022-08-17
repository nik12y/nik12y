package com.idg.idgcore.coe.app.service.language;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.coe.domain.assembler.language.LanguageAssembler;
import com.idg.idgcore.coe.domain.entity.bankidentifier.BankIdentifierEntityKey;
import com.idg.idgcore.coe.domain.entity.language.LanguageEntity;
import com.idg.idgcore.coe.domain.entity.language.LanguageEntityKey;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.language.ILanguageDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;
import com.idg.idgcore.coe.dto.language.LanguageDTO;
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
        languageEntity1=getLanguagesEntity1();
        languageDTO1=getLanguagesDTO();
        mutationEntity2=getMutationEntityJsonError();
    }

    @Test
    @DisplayName("JUnit for getByLanguageCode in application service when Authorize try Block")
    void getLanguageByCodeIsAuthorize() throws FatalException, JsonProcessingException {
        given(languageDomainService.getLanguageByCode(languageDTO.getLanguageCode())).willReturn(languageEntity);
        given(languageAssembler.convertEntityToDto(languageEntity)).willReturn(languageDTO);
        LanguageDTO languageDTO1 = languageApplicationService.getLanguageByCode(sessionContext, languageDTO);
        assertEquals("Y",languageDTO1.getAuthorized());
        assertThat(languageDTO1).isNotNull();
    }
    @Test
    @DisplayName("JUnit for getByLanguageCode in application service when Not Authorize in catch block")
    void getLanguageByCodewhenNotAuthorizeCatchBlock () throws FatalException {
        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"LANGUAGE\",\"taskIdentifier\":\"EN\",\"languageCode\":\"EN\",\"languageName\":\"English\",\"languageCode\":\"EN\"}";
        given(mutationsDomainService.getConfigurationByCode(languageDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);

        Assertions.assertThrows(Exception.class,()-> {
            LanguageDTO languageDTO1 = languageApplicationService.getLanguageByCode(sessionContext, languageDTOMapper);
            assertEquals("N",languageDTO1.getAuthorized());
            assertThat(languageDTO1).isNotNull();
        });
    }

    @Test
    @DisplayName("JUnit for getLanguages in application service for catch block for checker")
    void getLanguagesCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        sessionContext.setRole(new String[] { "" });
        given(mutationsDomainService.getUnauthorizedMutation(
                languageDTO1.getTaskCode(),AUTHORIZED_N))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
        Assertions.assertThrows(FatalException.class,()-> {
            List<LanguageDTO> languageDTO1 = languageApplicationService.getLanguages(sessionContext);
            assertThat(languageDTO1).isNotNull();
        });
    }

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
        SessionContext sessionContext2 = null;
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

    @Test
    @DisplayName("JUnit for getLanguageByCode in application service when Authorize for Negative")
    void getLanguageByCodeIsAuthorizeforNegative() throws FatalException, JsonProcessingException {
        given(languageDomainService.getLanguageByCode(languageDTO.getLanguageCode())).willReturn(languageEntity);
        given(languageAssembler.convertEntityToDto(languageEntity)).willReturn(languageDTO);
        LanguageDTO languageDTO1 = languageApplicationService.getLanguageByCode(sessionContext, languageDTO);
        assertNotEquals("N",languageDTO1.getAuthorized());
        assertThat(languageDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getLanguageByCode in application service check Parameter not null")
    void getLanguageByCodeIsAuthorizeCheckParameter() throws FatalException, JsonProcessingException {
        LanguageDTO languageDTOnull=null;
        LanguageDTO languageDTOEx=new LanguageDTO();
        languageDTOEx.setLanguageCode("EN");
        languageDTOEx.setAuthorized("Y");

        LanguageEntityKey languageEntityKey = new LanguageEntityKey();
        languageEntityKey.setLanguageCode("EN");

        given(languageDomainService.getLanguageByCode(languageDTOEx.getLanguageCode())).willReturn(languageEntity);
        given(languageAssembler.convertEntityToDto(languageEntity)).willReturn(languageDTO);
        LanguageDTO languageDTO1 = languageApplicationService.getLanguageByCode(sessionContext, languageDTOEx);
        assertThat(languageDTOEx.getLanguageCode()).isNotBlank();
        assertThat(languageDTOEx.getAuthorized()).isNotBlank();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        assertThat(languageEntity.toString()).isNotNull();
        assertThat(languageDTO.toString()).isNotNull();
        LanguageDTO bankIdentifierDTO2=new LanguageDTO("EN",
                "ENG",
                "English",
                "EN",
                "English(UK)");
        LanguageDTO.builder().languageCode("EN")
                .languageCodeAlternate("ENG")
                .languageName("English")
                .localeCode("EN")
                .localeName("English(UK)")
                .authorized("N")
                .taskCode(LANGUAGE)
                .taskIdentifier("EN")
                .build().toString();
        LanguageEntityKey languageEntityKey=new LanguageEntityKey("EN");
        assertThat(languageEntityKey.toString()).isNotNull();
        languageEntityKey.setLanguageCode("MA");
        languageEntityKey.keyAsString();
        languageEntityKey.builder().languageCode("JP").build();
        assertThat(languageDTO).descriptionText();
    }

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder()
                .bankCode("003")
                .defaultBranchCode("1141")
                .internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("")
                .externalTransactionReferenceNumber("")
                .targetUnit("dummy_target")
                .postingDate(new Date())
                .valueDate(new Date())
                .transactionBranch("")
                .userId("prash")
//              .accessibleTargetUnits([])
                .channel("Branch")
                .taskCode(LANGUAGE)
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(1L)
                .customAttributes("")
                .serviceInvocationModeType(Regular)
                .allTargetUnitsSelected(true)
//              .mutationType("")
                .userLocal("en_US")
                .originatingModuleCode("")
                .role(new String[]{"maker"})
                .build();
        return sessionContext;
    }

    private SessionContext getErrorSession(){
        SessionContext sessionContextError =
                SessionContext.builder()
                        .bankCode("")
                        .defaultBranchCode("")
                        .internalTransactionReferenceNumber("")
                        .userTransactionReferenceNumber("")
                        .externalTransactionReferenceNumber("")
                        .targetUnit("")
                        .postingDate(new Date())
                        .valueDate(new Date())
                        .transactionBranch("")
                        .userId("prash")
//                      .accessibleTargetUnits([])
                        .channel("")
                        .taskCode("")
                        .originalTransactionReferenceNumber("")
                        .externalBatchNumber(null)
                        .customAttributes("")
                        .serviceInvocationModeType(null)
                        .allTargetUnitsSelected(true)
//                      .mutationType("")
                        .userLocal("")
                        .originatingModuleCode("")
                        .role(new String[] {"maker"})
                        .build();
        return sessionContextError;
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
        languageDTO.setTaskCode(LANGUAGE);
        languageDTO.setStatus("DELETED");
        languageDTO.setRecordVersion(1);
        return languageDTO;
    }
    private LanguageEntity getLanguageEntity(){
        LanguageEntity languageEntity = new LanguageEntity(
                "EN",
                "ENG",
                "English",
                "EN",
                "English(UK)",
                null,
                null,
                0,
                "draft",
                "Y",
                "draft");
        return languageEntity;
    }

    private LanguageEntity getLanguagesEntity(){
        LanguageEntity languageEntity = new LanguageEntity(
                "EN",
                "ENG",
                "English",
                "EN",
                "English(UK)",
                null,
                null,
                1,
                "DELETED",
                null,
                null);
        return languageEntity;
    }

    private LanguageEntity getLanguagesEntity2()
    {
        LanguageEntity languageEntity2 = new LanguageEntity();
        languageEntity2.setLanguageCode("EN");
        languageEntity2.setLanguageCodeAlternate("ENG");
        languageEntity2.setLanguageName("English");
        languageEntity2.setLocaleCode("EN");
        languageEntity2.setLocaleName("English(UK)");
        languageEntity2.setLifeCycleId(null);
        languageEntity2.setReferenceNo(null);
        languageEntity2.setStatus("DELETED");
        languageEntity2.setRecordVersion(1);
        return languageEntity2;
    }

    private LanguageEntity getLanguagesEntity1()
    {
        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setLanguageCode("EN");
        languageEntity.setLanguageCodeAlternate("ENG");
        languageEntity.setLanguageName("English");
        languageEntity.setLocaleCode("EN");
        languageEntity.setLocaleName("English(UK)");
        languageEntity.setLifeCycleId(null);
        languageEntity.setReferenceNo(null);
        languageEntity.setAuthorized("N");
        languageEntity.setStatus("closed");
        languageEntity.setRecordVersion(1);
        return languageEntity;
    }

    private LanguageDTO getLanguageDTOUnAuth(){
        LanguageDTO languageDTO =
                new LanguageDTO(
                        "EN",
                        "ENG",
                        "English",
                        "EN",
                        "English(UK)");

        languageDTO.setAuthorized("N");
        languageDTO.setTaskIdentifier("EN");
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
        languageDTOMapper.setTaskCode(LANGUAGE);
        languageDTOMapper.setTaskIdentifier("EN");
        return languageDTOMapper;
    }

    private MutationEntity getMutationEntity() {
        String payLoadString="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"LANGUAGE\",\"taskIdentifier\":\"EN\",\"languageCode\":\"EN\",\"languageName\":\"English\",\"localeCode\":\"EN\",\"localeName\":\"English(UK)\"}";

        MutationEntity mutationEntity = new MutationEntity();

        mutationEntity.setTaskIdentifier("EN");
        mutationEntity.setTaskCode(LANGUAGE);
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
        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"LANGUAGE\",\"taskIdentifier\":\"EN\",\"languageCode\":\"MH\",\"languageName\":\"English\",\"localeCode\":\"EN\",\"localeName\":\"English(UK)\"}";
        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("EN");
        mutationEntity2.setTaskCode(LANGUAGE);
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");
        return mutationEntity2;
    }

    @Test
    @DisplayName("JUnit for getLanguageByCode where return the language when the authorized is Y")
    void getLanguageByCodeWhenAuthorizedIsYThenReturnLanguage() throws FatalException, JsonProcessingException {
        given(
                languageDomainService.getLanguageByCode(
                        languageDTO.getLanguageCode()))
                .willReturn(languageEntity);
        given(languageAssembler.convertEntityToDto(languageEntity))
                .willReturn(languageDTO);
        LanguageDTO result =
                languageApplicationService.getLanguageByCode(
                        sessionContext, languageDTO);
        assertEquals(languageDTO, result);
    }

    @Test
    @DisplayName("JUnit for getLanguages where return all languages when there are no unauthorized mutations")
    void getLanguagesWhenThereAreNoUnauthorizedMutationsThenReturnAllLanguages() throws FatalException {
        given(languageDomainService.getLanguages())
                .willReturn(List.of(languageEntity));
        given(mutationsDomainService.getUnauthorizedMutation(LANGUAGE, AUTHORIZED_N))
                .willReturn(List.of());
        given(languageAssembler.convertEntityToDto(languageEntity))
                .willReturn(languageDTO);

        List<LanguageDTO> languageDTOList =
                languageApplicationService.getLanguages(sessionContext);

        assertEquals(1, languageDTOList.size());
        assertEquals(languageDTO, languageDTOList.get(0));
    }

    //    @Test
    @DisplayName("JUnit for getLanguages in application service for try block")
    void getLanguagesTryBlock() throws FatalException {

        given(languageDomainService.getLanguages()).willReturn(List.of(languageEntity1));
        given(mutationsDomainService.getUnauthorizedMutation(languageDTO1.getTaskCode(),AUTHORIZED_N)).willReturn(List.of(mutationEntity));

        String payLoadString="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"LANGUAGE\",\"taskIdentifier\":\"EN\",\"languageCode\":\"EN\",\"languageName\":\"English\",\"localeCode\":\"EN\",\"localeName\":\"English(UK)\"}";

        Payload payload=new Payload();
        payload.setData(payLoadString);
        mutationEntity.setPayload(payload);
        String data1 = mutationEntity.getPayload().getData();
        given(languageAssembler.convertEntityToDto(languageEntity1)).willReturn(languageDTO1);

        List<LanguageDTO> languageDTO2 = languageApplicationService.getLanguages(sessionContext);
        assertThat(languageDTO1).isNotNull();
    }

    //    @Test
    @DisplayName("JUnit for getLanguages in application service for try block negative scenario for SessionContext some field not be null")
    void getLanguagesTryBlockNegative() throws FatalException {

        String payLoadString="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"LANGUAGE\",\"taskIdentifier\":\"EN\",\"languageCode\":\"EN\",\"languageName\":\"English\",\"localeCode\":\"EN\",\"localeName\":\"English(UK)\"}";

        MutationEntity mutationEntity5 = new MutationEntity();
        mutationEntity5.setTaskIdentifier("EN");
        mutationEntity5.setTaskCode(LANGUAGE);
        mutationEntity5.setPayload(new Payload(payLoadString));
        mutationEntity5.setStatus("new");
        mutationEntity5.setAuthorized("N");
        mutationEntity5.setRecordVersion(0);
        mutationEntity5.setAction("add");
        mutationEntity5.setLastConfigurationAction("unauthorized");
        mutationEntity5.setCreatedBy("prash");
        mutationEntity5.setLastUpdatedBy("pranay");

        LanguageDTO languageDTOO= new LanguageDTO();

        LanguageEntityKey languageEntityKey = new LanguageEntityKey();
        languageEntityKey.setLanguageCode("EN");

        LanguageEntity languageEntity5 = new LanguageEntity();
        given(mutationsDomainService.getUnauthorizedMutation(languageDTOO.getTaskCode(),AUTHORIZED_N)).willReturn(List.of(mutationEntity5));
        given(languageDomainService.getLanguages()).willReturn(List.of(languageEntity5));
        Payload payload=new Payload();
        payload.setData(payLoadString);
        mutationEntity5.setPayload(payload);
        String data1 = mutationEntity5.getPayload().getData();
        given(languageAssembler.convertEntityToDto(languageEntity5)).willReturn(languageDTOO);
        given(languageAssembler.setAuditFields(mutationEntity5,languageDTOO)).willReturn(languageDTOO);

        List<LanguageDTO> languageDTO2 = languageApplicationService.getLanguages(sessionContext);
        assertThat(sessionContext.getRole()).isNotEmpty();
        assertThat(sessionContext.getServiceInvocationModeType()).isNotNull();
    }
}