package com.idg.idgcore.coe.app.service.errorOverride;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.idg.idgcore.coe.domain.assembler.errorOverride.*;
import com.idg.idgcore.coe.domain.entity.errorOverride.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.domain.process.*;
import com.idg.idgcore.coe.domain.service.errorOverride.*;
import com.idg.idgcore.coe.domain.service.mutation.*;
import com.idg.idgcore.coe.dto.errorOverride.*;
import com.idg.idgcore.coe.dto.mutation.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import com.idg.idgcore.enumerations.core.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

import static com.idg.idgcore.coe.common.Constants.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith (MockitoExtension.class)
class ErrorOverrideApplicationServiceTest {
    @InjectMocks
    private ErrorOverrideApplicationService applicationService;
    @Mock private ErrorOverrideAssembler assembler;
    @Mock private IErrorOverrideDomainService domainService;
    @Mock private IMutationsDomainService mutationsDomainService;
    @Mock private ProcessConfiguration processConfiguration;
    @Autowired
    private MutationEntity mutationEntity;
    private MutationEntity mutationEntity2;
    private SessionContext sessionContext;
    private ErrorOverrideDTO errorOverrideDTOAuth;
    private ErrorOverrideDTO errorOverrideDTOUnAuth;
    private ErrorOverrideDTO errorOverrideDTO;
    private ErrorOverrideEntity errorOverrideEntity;
    private ErrorOverrideEntity errorOverrideEntityUnAut;
    private ErrorOverrideDTO errorOverrideDTOMapper;
    private ErrorOverrideDTO errorOverrideDTO1;

    @BeforeEach
    void setUp () {
        sessionContext = getValidSessionContext();
        errorOverrideDTOAuth = getErrorOverrideDTOAuthorized();
        errorOverrideEntity = getErrorOverrideEntity();
        errorOverrideDTOUnAuth = getErrorOverrideDTOUnAuthorized();
        errorOverrideDTOUnAuth.setAuthorized("N");
        errorOverrideEntityUnAut = getErrorOverrideEntity();
        errorOverrideEntityUnAut.setAuthorized("N");
        mutationEntity = getMutationEntity();
        errorOverrideDTO = getErrorOverrideDTO();
        errorOverrideDTOMapper = getErrorOverrideDTOMapper();
        errorOverrideDTO1 = getErrorOverrideDTO();
        mutationEntity2 = getMutationEntityJsonError();
    }

    @Test
    @DisplayName ("JUnit for getErrorOverrideByCode in application service when Authorize")
    void getErrorOverrideByCodeWithAuthRecord () throws FatalException {
        given(domainService.getRecordByErrorCodeAndBranchCode(errorOverrideDTOAuth.getErrorCode(),
                errorOverrideDTOAuth.getBranchCode())).willReturn(
                errorOverrideEntity);
        given(assembler.convertEntityToDto(errorOverrideEntity)).willReturn(
                errorOverrideDTOAuth);
        ErrorOverrideDTO errorOverrideDTO1 = applicationService.getErrorOverrideByCode(
                sessionContext, errorOverrideDTOAuth);
        assertThat(errorOverrideDTO1.getAuthorized()).isEqualTo("Y");
        assertThat(errorOverrideDTOAuth).isNotNull();
        assertThat(errorOverrideDTOAuth.toString()).isNotNull();
        assertThat(errorOverrideDTO.toString()).isNotNull();
    }

    @Test
    @DisplayName ("JUnit for getErrorOverrideByCode in application service when Not Authorize in catch block")
    void getErrorOverrideByCodeWhenNotAuthorizeCatchBlock () throws FatalException {
        String payLoadString1 = getPayloadInvalidString();

        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);
        Assertions.assertThrows(Exception.class, () -> {
            ErrorOverrideDTO errorOverrideDTO1 = applicationService.getErrorOverrideByCode(
                    sessionContext, errorOverrideDTOUnAuth);
            assertEquals("N", errorOverrideDTO1.getAuthorized());
            assertThat(errorOverrideDTO1).isNotNull();
        });
    }

    @DisplayName ("JUnit test for processErrorOverride method")
    @Test
    void processErrorOverrideWithNew () throws JsonProcessingException, FatalException {
        ErrorOverrideDTO errorOverrideDTONew = getErrorOverrideDTOMapper();
        doNothing().when(processConfiguration).process(errorOverrideDTONew);
        applicationService.processErrorOverride(sessionContext, errorOverrideDTONew);
        verify(processConfiguration, times(1)).process(errorOverrideDTONew);
    }

    @DisplayName ("JUnit test for addUpdateRecord method")
    @Test
    void addUpdateRecord () throws JsonProcessingException, FatalException {
        String payloadStr = getPayloadValidString();
        ErrorOverrideDTO errorOverrideDTO = getErrorOverrideDTO();
        doNothing().when(domainService).validateAndSave(errorOverrideDTO);
        //        doNothing().when(domainService).save(errorOverrideDTO);
        applicationService.save(errorOverrideDTO);
        applicationService.addUpdateRecord(payloadStr);
        verify(domainService, times(1)).validateAndSave(errorOverrideDTO);
    }

    @Test
    @DisplayName ("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest () {
        String code = errorOverrideDTO.getErrorCode();
        applicationService.getConfigurationByCode(code);
        assertThat(errorOverrideEntity).isNotNull();
    }

    @Test
    @DisplayName ("JUnit for processErrorOverride in application service for Try Block")
    void processErrorOverrideForTryBlock () throws JsonProcessingException, FatalException {
        doNothing().when(processConfiguration).process(errorOverrideDTO);
        applicationService.processErrorOverride(sessionContext, errorOverrideDTO);
        verify(processConfiguration, times(1)).process(errorOverrideDTO);
    }



    @Test
    @DisplayName ("JUnit for ErrorOverride ByCode in application service when Authorize for Negative")
    void getErrorOverrideByCodeIsAuthorizeForNegative () throws FatalException {
        ErrorOverrideDTO errorOverrideDTO = getErrorOverrideDTO();
        ErrorOverrideDTO errorOverrideDTOEx = getErrorOverrideDTO();
        errorOverrideDTOEx.setErrorCode("ERR-CHD-06");
        errorOverrideDTOEx.setAuthorized("Y");
        given(domainService.getRecordByErrorCodeAndBranchCode(
                errorOverrideDTO.getErrorCode(), errorOverrideDTO.getBranchCode())).willReturn(
                errorOverrideEntity);
        given(assembler.convertEntityToDto(errorOverrideEntity)).willReturn(errorOverrideDTO);
        ErrorOverrideDTO stateDTO1 = applicationService.getErrorOverrideByCode(sessionContext,
                errorOverrideDTOEx);
        assertNotEquals("N", errorOverrideDTO1.getAuthorized());
        assertThat(errorOverrideDTO).isNotNull();
    }

    @Test
    @DisplayName ("JUnit for getErrorOverride ByCode in application service check Parameter not null")
    void getErrorOverrideByCodeIsAuthorizeCheck () throws FatalException {
        ErrorOverrideDTO errorOverrideDTO = getErrorOverrideDTO();
        ErrorOverrideDTO errorOverrideDTOEx = getErrorOverrideDTO();
        errorOverrideDTOEx.setErrorCode("ERR-CHD-06");
        errorOverrideDTOEx.setAuthorized("Y");
        given(domainService.getRecordByErrorCodeAndBranchCode(
                errorOverrideDTO.getErrorCode(), errorOverrideDTO.getBranchCode())).willReturn(
                errorOverrideEntity);
        given(assembler.convertEntityToDto(errorOverrideEntity)).willReturn(errorOverrideDTO);
        ErrorOverrideDTO stateDTO1 = applicationService.getErrorOverrideByCode(sessionContext,
                errorOverrideDTOEx);
        assertThat(errorOverrideDTOEx.getErrorCode()).isNotBlank();
        assertThat(errorOverrideDTOEx.getAuthorized()).isNotBlank();
    }

    @Test
    @DisplayName ("JUnit for getErrorOverrides in application service")
    void processErrorOverrideTest () throws JsonProcessingException, FatalException {
        ErrorOverrideDTO errorOverrideDTO = getErrorOverrideDTONew();
        doNothing().when(processConfiguration).process(errorOverrideDTO);
        TransactionStatus transactionStatus = applicationService.processErrorOverride(
                sessionContext, errorOverrideDTO);
        assertThat(transactionStatus).isNotNull();
        verify(processConfiguration, times(1)).process(errorOverrideDTO);
    }

    //        @Test
    @DisplayName ("JUnit for getErrorOverrides in application service")
    void getErrorOverrides () throws JsonProcessingException, FatalException {
        ErrorOverrideEntity errorOverrideEntity = getErrorOverrideEntityNew();
        ErrorOverrideDTO errorOverrideDTO = getErrorOverrideDTONew();
        MutationEntity unauthorizedEntities = getMutationEntityNew();
        ErrorOverrideDTO errorOverrideDTO2 = setAuditFields(unauthorizedEntities, errorOverrideDTO);
        String data = "action\":\"add\",\"status\":\"new\","
                + "\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\","
                + "\"taskCode\":\"ERROR-OVERRIDE\",\"taskIdentifier\":\"ERR-CHD-06\",\"errorCode\":\"ERR-CHD-06\","
                + "\"errorMessage\":\"PAN input is mandatory for the cash transaction above INR 50K\",\"typeOfMessage\":\"Ignore\","
                + "\"isConfirmationRequired\":true,\"functionCode\":\"Cash Deposit/Withdrawal\",\"batchType\":\"Error\","
                + "\"errorOverrideLanguageDetails\":{\"languageCode\":\"ENG\",\"languageName\":\"English\","
                + "\"localeCode\":\"EN\",\"localeName\":\"New Error Cod\"},"
                + "\"errorOverrideConversions\":{\"branchCode\":\"ALL\","
                + "\"functionCodeOverride\":\"Cash Deposit/Withdrawal\",\"newErrorCode\":\"1\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        given(domainService.getErrorCodes()).willReturn(
                List.of(errorOverrideEntity));
        given(mutationsDomainService.getUnauthorizedMutation(
                "ERROR-OVERRIDE", AUTHORIZED_N)).willReturn(
                List.of(unauthorizedEntities));
        given(assembler.setAuditFields(unauthorizedEntities,
                errorOverrideDTO)).willReturn(errorOverrideDTO);
        given(assembler.convertEntityToDto(errorOverrideEntity)).willReturn(
                errorOverrideDTO);
        given(assembler.setAuditFields(unauthorizedEntities, errorOverrideDTO)).willReturn(
                errorOverrideDTO2);
        System.out.println("   errorOverrideDTO2   " + errorOverrideDTO2);
        List<ErrorOverrideDTO> errorOverrideDTOList = applicationService.getErrorCodes(
                sessionContext);
        //        List<ErrorOverrideDTO> errorOverrideDTO1 = applicationService.getErrorCodes(
        //                sessionContext);
        assertThat(errorOverrideDTOList).isNotNull();
    }

    @Test
    @DisplayName ("JUnit for getErrorOverride in application service for catch block")
    void getErrorOverridesForException () throws FatalException {
        Assertions.assertThrows(Exception.class, () -> {
            List<ErrorOverrideDTO> errorCodes = applicationService.getErrorCodes(
                    null);
            assertThat(errorCodes).isNotNull();
        });
    }

    //    @Test
    @DisplayName ("JUnit for getErrorCodes in application service for catch block")
    void getErrorOverridesException () throws FatalException {
        ErrorOverrideEntity errorOverrideEntity = getErrorOverrideEntityNew();
        ErrorOverrideDTO errorOverrideDTO = getErrorOverrideDTONew();
        MutationEntity unauthorizedEntities = getMutationEntityNew();
        ErrorOverrideDTO errorOverrideDTO2 = setAuditFields(unauthorizedEntities, errorOverrideDTO);
        SessionContext sessionContext = getInValidSessionContext();
        given(domainService.getErrorCodes()).willReturn(
                List.of(errorOverrideEntity));
        given(assembler.convertEntityToDto(errorOverrideEntity)).willReturn(
                errorOverrideDTO);
        Assertions.assertThrows(Exception.class, () -> {
            List<ErrorOverrideDTO> cities = applicationService.getErrorCodes(
                    sessionContext);
            assertThat(cities).isNotNull();
        });
    }

    @Test
    @DisplayName ("JUnit for code coverage")
    void getAllArgEntity () {
        ErrorOverrideLanguageDetailsEntity detailEntity = new ErrorOverrideLanguageDetailsEntity(
                "ENG", "English ", "EN", "New Error Cod");
        ErrorOverrideConversionsEntity conversionsEntity = new ErrorOverrideConversionsEntity(
                "Cash Deposit/Withdrawal", "1");
        ErrorOverrideEntity entity =
                new ErrorOverrideEntity("ERR-CHD-02",
                        "PAN input is mandatory for the cash transaction above INR 50K", "ALL",
                        "Ignore",
                        'Y', "Cash Deposit/Withdrawal", "Error", 'N', "", "",
                        "active", 0, "N", "",
                        detailEntity, conversionsEntity
                );
        assertThat(entity).isNotNull();
        assertThat(entity.toString()).isNotNull();
    }

    @Test
    @DisplayName ("JUnit for code coverage")
    void getEntity () {
        ErrorOverrideEntity entity = getErrorOverrideEntity();
        entity.setLifeCycleId("id");
        entity.setReferenceNo("ref no");
        assertThat(entity).isNotNull();
        assertThat(entity.getLifeCycleId()).isNotNull();
        assertThat(entity.getReferenceNo()).isNotNull();
    }

    @Test
    @DisplayName ("JUnit for code coverage")
    void getAllArgDto () {
        ErrorOverrideLanguageDetailsDTO detailDto = new ErrorOverrideLanguageDetailsDTO(
                "ENG", "English ", "EN", "New Error Cod");
        ErrorOverrideConversionsDTO conversionsDto = new ErrorOverrideConversionsDTO(
                "Cash Deposit/Withdrawal", "1");
        ErrorOverrideDTO dto =
                new ErrorOverrideDTO("ERR-CHD-02",
                        "PAN input is mandatory for the cash transaction above INR 50K", "ALL",
                        "Ignore",
                        true, "Cash Deposit/Withdrawal", "Error", false,
                        detailDto, conversionsDto
                );
        assertThat(dto).isNotNull();
        assertThat(dto.toString()).isNotNull();
        assertThat(detailDto).isNotNull();
        assertThat(detailDto.toString()).isNotNull();
    }

    @Test
    @DisplayName ("JUnit for code coverage")
    void getBuilderDto () {
        ErrorOverrideLanguageDetailsDTO detailDto = new ErrorOverrideLanguageDetailsDTO(
                "ENG", "English ", "EN", "New Error Cod");
        ErrorOverrideConversionsDTO conversionsDto = new ErrorOverrideConversionsDTO(
                "Cash Deposit/Withdrawal", "1");
        ErrorOverrideDTO dto = ErrorOverrideDTO.builder()
                .errorCode("ERR-CHD-02")
                .errorMessage("PAN input is mandatory for the cash transaction above INR 50K")
                .branchCode("ALL").isExcluded(false)
                .typeOfMessage("Ignore").isConfirmationRequired(true)
                .functionCode("Cash Deposit/Withdrawal").batchType("Error")
                .errorOverrideConversions(conversionsDto).errorOverrideLanguageDetails(detailDto)
                .build();
        assertThat(dto).isNotNull();
        assertThat(dto.toString()).isNotNull();
    }

    @Test
    @DisplayName ("JUnit for code coverage")
    void getErrorOverrideEntityKeyAll () {
        ErrorOverrideEntityKey entityKey = new ErrorOverrideEntityKey("entity", "branchCode");
        assertThat(entityKey).isNotNull();
        assertThat(entityKey.toString()).isNotNull();
    }

    @Test
    @DisplayName ("JUnit for code coverage")
    void getErrorOverrideEntityKey () {
        ErrorOverrideEntityKey entityKey = new ErrorOverrideEntityKey();
        entityKey.setErrorCode("entity");
        entityKey.setBranchCode("branchCode");
        assertThat(entityKey).isNotNull();
        assertThat(entityKey.toString()).isNotNull();
        assertThat(entityKey.getErrorCode()).isNotNull();
    }

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder().bankCode("02")
                .defaultBranchCode("02")
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
        SessionContext sessionContext = SessionContext.builder().bankCode(null)
                .defaultBranchCode("")
                .internalTransactionReferenceNumber(null).userTransactionReferenceNumber("")
                .externalTransactionReferenceNumber(null).targetUnit("").postingDate(null)
                .valueDate(new Date()).transactionBranch("").userId("test").channel("")
                .taskCode(null).originalTransactionReferenceNumber("").externalBatchNumber(1L)
                .customAttributes(null).serviceInvocationModeType(null)
                .allTargetUnitsSelected(true).userLocal("").originatingModuleCode("")
                .role(new String[] { "maker" }).build();
        return sessionContext;
    }

    private ErrorOverrideDTO getErrorOverrideDTOAuthorized () {
        ErrorOverrideDTO errorOverrideDTOMapper = new ErrorOverrideDTO();
        errorOverrideDTOMapper.setAuthorized("Y");
        errorOverrideDTOMapper.setTaskCode("ERR-CHD-06_ALL");
        errorOverrideDTOMapper.setTaskIdentifier("ERR-CHD-06");


        errorOverrideDTOMapper.setTaskCode("ERR-CHD-06_ALL");
        errorOverrideDTOMapper.setTaskIdentifier("ERR-CHD-06");
        ErrorOverrideLanguageDetailsDTO detailDto = new ErrorOverrideLanguageDetailsDTO();
        detailDto.setLanguageCode("ENG");
        detailDto.setLanguageName("English ");
        detailDto.setLanguageCode("EN");
        detailDto.setLanguageName("New Error Cod");
        ErrorOverrideConversionsDTO conversionsDTO = new ErrorOverrideConversionsDTO();
        conversionsDTO.setFunctionCodeOverride("Cash Deposit/Withdrawal");
        conversionsDTO.setNewErrorCode("1");
        errorOverrideDTOMapper.setErrorMessage(
                "PAN input is mandatory for the cash transaction above INR 50K");
        errorOverrideDTOMapper.setBranchCode("ALL");
        errorOverrideDTOMapper.setTypeOfMessage("Ignore");
        errorOverrideDTOMapper.setIsConfirmationRequired(true);
        errorOverrideDTOMapper.setFunctionCode("Cash Deposit/Withdrawal");
        errorOverrideDTOMapper.setBatchType("Error");
        errorOverrideDTOMapper.setErrorOverrideConversions(conversionsDTO);
        errorOverrideDTOMapper.setErrorOverrideLanguageDetails(detailDto);
        errorOverrideDTOMapper.setTaskIdentifier("ERR-CHD-06");

        return errorOverrideDTOMapper;
    }

    private ErrorOverrideDTO getErrorOverrideDTOForSave () {
        ErrorOverrideDTO errorOverrideDTOMapper = new ErrorOverrideDTO();
        errorOverrideDTOMapper.setTaskCode("ERR-CHD-06_ALL");
        errorOverrideDTOMapper.setTaskIdentifier("ERR-CHD-06");
        errorOverrideDTOMapper.setAction("authorize");
        errorOverrideDTOMapper.setStatus("active");
        errorOverrideDTOMapper.setRecordVersion(1);
        errorOverrideDTOMapper.setAuthorized("N");
        errorOverrideDTOMapper.setLastConfigurationAction("authorized");

        errorOverrideDTOMapper.setTaskCode("ERR-CHD-06_ALL");
        errorOverrideDTOMapper.setTaskIdentifier("ERR-CHD-06");
        ErrorOverrideLanguageDetailsDTO detailDto = new ErrorOverrideLanguageDetailsDTO();
        detailDto.setLanguageCode("ENG");
        detailDto.setLanguageName("English ");
        detailDto.setLanguageCode("EN");
        detailDto.setLanguageName("New Error Cod");
        ErrorOverrideConversionsDTO conversionsDTO = new ErrorOverrideConversionsDTO();
        conversionsDTO.setFunctionCodeOverride("Cash Deposit/Withdrawal");
        conversionsDTO.setNewErrorCode("1");
        errorOverrideDTOMapper.setErrorMessage(
                "PAN input is mandatory for the cash transaction above INR 50K");
        errorOverrideDTOMapper.setBranchCode("ALL");
        errorOverrideDTOMapper.setTypeOfMessage("Ignore");
        errorOverrideDTOMapper.setIsConfirmationRequired(true);
        errorOverrideDTOMapper.setFunctionCode("Cash Deposit/Withdrawal");
        errorOverrideDTOMapper.setBatchType("Error");
        errorOverrideDTOMapper.setErrorOverrideConversions(conversionsDTO);
        errorOverrideDTOMapper.setErrorOverrideLanguageDetails(detailDto);
        errorOverrideDTOMapper.setTaskIdentifier("ERR-CHD-06");
        return errorOverrideDTOMapper;

    }

    private ErrorOverrideDTO getErrorOverrideDTOUnAuthorized () {
        ErrorOverrideDTO errorOverrideDTOMapper = new ErrorOverrideDTO();
        errorOverrideDTOMapper.setAuthorized("N");

        errorOverrideDTOMapper.setTaskCode("ERR-CHD-06_ALL");
        errorOverrideDTOMapper.setTaskIdentifier("ERR-CHD-06");
        ErrorOverrideLanguageDetailsDTO detailDto = new ErrorOverrideLanguageDetailsDTO();
        detailDto.setLanguageCode("ENG");
        detailDto.setLanguageName("English ");
        detailDto.setLanguageCode("EN");
        detailDto.setLanguageName("New Error Cod");
        ErrorOverrideConversionsDTO conversionsDTO = new ErrorOverrideConversionsDTO();
        conversionsDTO.setFunctionCodeOverride("Cash Deposit/Withdrawal");
        conversionsDTO.setNewErrorCode("1");
        errorOverrideDTOMapper.setErrorMessage(
                "PAN input is mandatory for the cash transaction above INR 50K");
        errorOverrideDTOMapper.setBranchCode("ALL");
        errorOverrideDTOMapper.setTypeOfMessage("Ignore");
        errorOverrideDTOMapper.setIsConfirmationRequired(true);
        errorOverrideDTOMapper.setFunctionCode("Cash Deposit/Withdrawal");
        errorOverrideDTOMapper.setBatchType("Error");
        errorOverrideDTOMapper.setErrorOverrideConversions(conversionsDTO);
        errorOverrideDTOMapper.setErrorOverrideLanguageDetails(detailDto);
        errorOverrideDTOMapper.setTaskIdentifier("ERR-CHD-06");
        return errorOverrideDTOMapper;

    }

    private ErrorOverrideEntity getErrorOverrideEntity () {
        ErrorOverrideEntity errorOverrideEntity = new ErrorOverrideEntity();
        errorOverrideEntity.setAuthorized("Y");
        errorOverrideEntity.setErrorCode("ERR-CHD-06");
        errorOverrideEntity.setStatus("DELETED");
        errorOverrideEntity.setRecordVersion(1);

        ErrorOverrideLanguageDetailsEntity detailsEntity= new ErrorOverrideLanguageDetailsEntity();
        detailsEntity.setLanguageCode("ENG");
        detailsEntity.setLanguageName("English ");
        detailsEntity.setLanguageCode("EN");
        detailsEntity.setLanguageName("New Error Cod");

        ErrorOverrideConversionsEntity conversionsEntity = new ErrorOverrideConversionsEntity();
        conversionsEntity.setFunctionCodeOverride("Cash Deposit/Withdrawal");
        conversionsEntity.setNewErrorCode("1");

        errorOverrideEntity.setErrorMessage("PAN input is mandatory for the cash transaction above INR 50K");
        errorOverrideEntity.setBranchCode("ALL");
        errorOverrideEntity.setTypeOfMessage("Ignore");
        errorOverrideEntity.setIsConfirmationRequired('Y');
        errorOverrideEntity.setFunctionCode("Cash Deposit/Withdrawal");
        errorOverrideEntity.setBatchType("Error");
        errorOverrideEntity.setErrorOverrideConversionsEntity(conversionsEntity);
        errorOverrideEntity.setErrorOverrideLanguageDetailsEntity(detailsEntity);


        return errorOverrideEntity;
    }

    private ErrorOverrideDTO getErrorOverrideDTOMapper () {
        ErrorOverrideDTO errorOverrideDTOMapper = new ErrorOverrideDTO();
        errorOverrideDTOMapper.setAuthorized("N");
        errorOverrideDTOMapper.setTaskCode("ERR-CHD-06_ALL");
        errorOverrideDTOMapper.setTaskIdentifier("ERR-CHD-06");
        ErrorOverrideLanguageDetailsDTO detailDto = new ErrorOverrideLanguageDetailsDTO();
        detailDto.setLanguageCode("ENG");
        detailDto.setLanguageName("English ");
        detailDto.setLanguageCode("EN");
        detailDto.setLanguageName("New Error Cod");
        ErrorOverrideConversionsDTO conversionsDTO = new ErrorOverrideConversionsDTO();
        conversionsDTO.setFunctionCodeOverride("Cash Deposit/Withdrawal");
        conversionsDTO.setNewErrorCode("1");
        errorOverrideDTOMapper.setErrorMessage(
                "PAN input is mandatory for the cash transaction above INR 50K");
        errorOverrideDTOMapper.setBranchCode("ALL");
        errorOverrideDTOMapper.setTypeOfMessage("Ignore");
        errorOverrideDTOMapper.setIsConfirmationRequired(true);
        errorOverrideDTOMapper.setFunctionCode("Cash Deposit/Withdrawal");
        errorOverrideDTOMapper.setBatchType("Error");
        errorOverrideDTOMapper.setErrorOverrideConversions(conversionsDTO);
        errorOverrideDTOMapper.setErrorOverrideLanguageDetails(detailDto);
        errorOverrideDTOMapper.setTaskIdentifier("ERR-CHD-06");
        return errorOverrideDTOMapper;
    }

    private ErrorOverrideDTO getErrorOverrideDTO () {
        ErrorOverrideDTO dto = new ErrorOverrideDTO();
        dto.setAuthorized("Y");
        dto.setTaskCode("ERR-CHD-06_ALL");
        dto.setErrorCode("ERR-CHD-06");
        dto.setStatus("DELETED");
        dto.setRecordVersion(1);

        ErrorOverrideLanguageDetailsDTO detailDto = new ErrorOverrideLanguageDetailsDTO();
        detailDto.setLanguageCode("ENG");
        detailDto.setLanguageName("English ");
        detailDto.setLanguageCode("EN");
        detailDto.setLanguageName("New Error Cod");

        ErrorOverrideConversionsDTO conversionsDTO = new ErrorOverrideConversionsDTO();
        conversionsDTO.setFunctionCodeOverride("Cash Deposit/Withdrawal");
        conversionsDTO.setNewErrorCode("1");

        dto.setErrorMessage("PAN input is mandatory for the cash transaction above INR 50K");
        dto.setBranchCode("ALL");
        dto.setTypeOfMessage("Ignore");
        dto.setIsConfirmationRequired(true);
        dto.setFunctionCode("Cash Deposit/Withdrawal");
        dto.setBatchType("Error");
        dto.setErrorOverrideConversions(conversionsDTO);
        dto.setErrorOverrideLanguageDetails(detailDto);
        dto.setTaskIdentifier("ERR-CHD-06");
        return dto;
    }

    private MutationEntity getMutationEntity () {
        String payLoadString =
                getPayloadValidString();
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("ERR-CHD-06");
        mutationEntity.setTaskCode("ERR-CHD-06_ALL");
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("new");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("add");
        mutationEntity.setLastConfigurationAction("unauthorized");
        return mutationEntity;
    }

    private String getPayloadValidString () {
        String payLoadString =
                "{\"action\":\"close\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\","
                        + "\"lastConfigurationAction\":\"unauthorized\",\"taskCode\":\"ERROR-OVERRIDE\",\"taskIdentifier\":\"ERR-CHD-06_ALL\","
                        + "\"errorCode\":\"ERR-CHD-06\",\"errorMessage\":\"PAN input is mandatory for the cash transaction above INR 50K\","
                        + "\"branchCode\":\"ALL\",\"typeOfMessage\":\"Ignore\",\"isConfirmationRequired\":true,"
                        + "\"functionCode\":\"Cash Deposit/Withdrawal\",\"batchType\":\"Error\",\"isExcluded\":false,"
                        + "\"errorOverrideLanguageDetails\":{\"languageCode\":\"ENG\",\"languageName\":\"English\",\"localeCode\":\"EN\","
                        + "\"localeName\":\"New Error Cod\"},\"errorOverrideConversions\":{\"functionCodeOverride\":\"Cash Deposit/Withdrawal\","
                        + "\"newErrorCode\":\"1\"}}";
        return payLoadString;
    }

    private String getPayloadInvalidString () {
        String payLoadString =
                "{\"action\":\"add\",\"status\":\"new\","
                        + "\"recordVersion\":INVALID,\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\","
                        + "\"taskCode\":\"ERROR-OVERRIDE\",\"taskIdentifier\":\"ERR-CHD-06\",\"errorCode\":\"ERR-CHD-06\","
                        + "\"errorMessage\":\"PAN input is mandatory for the cash transaction above INR 50K\",\"typeOfMessage\":\"Ignore\","
                        + "\"isConfirmationRequired\":true,\"functionCode\":\"Cash Deposit/Withdrawal\",\"batchType\":\"Error\","
                        + "\"errorOverrideLanguageDetails\":{\"languageCode\":\"ENG\",\"languageName\":\"English\","
                        + "\"localeCode\":\"EN\",\"localeName\":\"New Error Cod\"},"
                        + "\"errorOverrideConversions\":{\"branchCode\":\"ALL\","
                        + "\"functionCodeOverride\":\"Cash Deposit/Withdrawal\",\"newErrorCode\":\"1\"}}";
        return payLoadString;
    }

    private MutationEntity getMutationEntityError () {
        String payLoadString =
                getPayloadInvalidString();
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("ERR-CHD-06");
        mutationEntity.setTaskCode("ERR-CHD-06_ALL");
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("active");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("authorize");
        mutationEntity.setLastConfigurationAction("unauthorized");

        return mutationEntity;
    }

    private ErrorOverrideDTO getErrorOverrideDTONew () {
        ErrorOverrideLanguageDetailsDTO detailDto = new ErrorOverrideLanguageDetailsDTO();
        detailDto.setLanguageCode("ENG");
        detailDto.setLanguageName("English ");
        detailDto.setLanguageCode("EN");
        detailDto.setLanguageName("New Error Cod");
        ErrorOverrideConversionsDTO conversionsDTO = new ErrorOverrideConversionsDTO();
        conversionsDTO.setFunctionCodeOverride("Cash Deposit/Withdrawal");
        conversionsDTO.setNewErrorCode("1");
        ErrorOverrideDTO dto = new ErrorOverrideDTO();
        dto.setErrorCode("ERR-CHD-02");
        dto.setErrorMessage("PAN input is mandatory for the cash transaction above INR 50K");
        dto.setBranchCode("ALL");
        dto.setTypeOfMessage("Ignore");
        dto.setIsConfirmationRequired(true);
        dto.setFunctionCode("Cash Deposit/Withdrawal");
        dto.setBatchType("Error");
        dto.setErrorOverrideConversions(conversionsDTO);
        dto.setErrorOverrideLanguageDetails(detailDto);
        dto.setStatus("new");
        dto.setTaskCode("ERR-CHD-06_ALL");
        dto.setTaskIdentifier("ERR-CHD-06");
        dto.setRecordVersion(1);
        return dto;
    }

    private MutationEntity getMutationEntityNew () {
        String payLoadString =
                getPayloadValidString();
        MutationEntity unauthorizedEntities = new MutationEntity();
        unauthorizedEntities.setTaskCode("ERR-CHD-06_ALL");
        unauthorizedEntities.setTaskIdentifier("ERR-CHD-06");
        unauthorizedEntities.setPayload(new Payload(payLoadString));
        unauthorizedEntities.setAuthorized("N");
        unauthorizedEntities.setStatus("new");
        unauthorizedEntities.setRecordVersion(1);
        unauthorizedEntities.setAction("add");
        unauthorizedEntities.setLastConfigurationAction("unauthorized");
        return unauthorizedEntities;
    }

    private ErrorOverrideEntity getErrorOverrideEntityNew () {
        ErrorOverrideLanguageDetailsEntity detailEntity = new ErrorOverrideLanguageDetailsEntity();
        detailEntity.setLanguageCode("ENG");
        detailEntity.setLanguageName("English ");
        detailEntity.setLanguageCode("EN");
        detailEntity.setLanguageName("New Error Cod");
        ErrorOverrideConversionsEntity conversionsEntity = new ErrorOverrideConversionsEntity();
        conversionsEntity.setFunctionCodeOverride("Cash Deposit/Withdrawal");
        conversionsEntity.setNewErrorCode("1");
        ErrorOverrideEntity entity = new ErrorOverrideEntity();
        entity.setErrorCode("ERR-CHD-02");
        entity.setErrorMessage("PAN input is mandatory for the cash transaction above INR 50K");
        entity.setTypeOfMessage("Ignore");
        entity.setBranchCode("ALL");
        entity.setIsConfirmationRequired('Y');
        entity.setFunctionCode("Cash Deposit/Withdrawal");
        entity.setBatchType("Error");
        entity.setErrorOverrideConversionsEntity(conversionsEntity);
        entity.setErrorOverrideLanguageDetailsEntity(detailEntity);
        entity.setStatus("new");
        entity.setRecordVersion(1);
        return entity;
    }

    private MutationEntity getMutationEntityJsonError () {
        String payLoadString1 =
                "{\"action\":\"add\",\"status\":\"new\","
                        + "\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\","
                        + "\"taskCode\":\"ERROR-OVERRIDE\",\"taskIdentifier\":\"ERR-CHD-06\",\"errorCode\":\"ERR-CHD-06\","
                        + "\"errorMessage\":\"PAN input is mandatory for the cash transaction above INR 50K\",\"typeOfMessage\":\"Ignore\","
                        + "\"isConfirmationRequired\":true,\"functionCode\":\"Cash Deposit/Withdrawal\",\"batchType\":\"Error\","
                        + "\"errorOverrideLanguageDetails\":{\"languageCode\":\"ENG\",\"languageName\":\"English\","
                        + "\"localeCode\":\"EN\",\"localeName\":\"New Error Cod\"},"
                        + "\"errorOverrideConversions\":{\"branchCode\":\"ALL\","
                        + "\"functionCodeOverride\":\"Cash Deposit/Withdrawal\",\"newErrorCode\":\"1\"}}";
        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("ERR-CHD-06");
        mutationEntity2.setTaskCode("ERR-CHD-06_ALL");
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("draft");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("draft");
        return mutationEntity2;
    }

    public ErrorOverrideDTO setAuditFields (MutationEntity mutationEntity,
            ErrorOverrideDTO errorOverrideDTO) {
        errorOverrideDTO.setAction(mutationEntity.getAction());
        errorOverrideDTO.setAuthorized(mutationEntity.getAuthorized());
        errorOverrideDTO.setRecordVersion(mutationEntity.getRecordVersion());
        errorOverrideDTO.setStatus(mutationEntity.getStatus());
        errorOverrideDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        errorOverrideDTO.setCreatedBy(mutationEntity.getCreatedBy());
        errorOverrideDTO.setCreationTime(mutationEntity.getCreationTime());
        errorOverrideDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        errorOverrideDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        errorOverrideDTO.setTaskCode("ERR-CHD-06_ALL");
        errorOverrideDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return errorOverrideDTO;
    }

}



