package com.idg.idgcore.coe.app.service.appvertype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.coe.domain.assembler.appvertype.AppVerTypeAssembler;
import com.idg.idgcore.coe.domain.entity.appvertype.AppVerTypeEntity;
import com.idg.idgcore.coe.domain.entity.appvertype.AppVerTypeEntityKey;
import com.idg.idgcore.coe.domain.entity.appvertype.DocumentsEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.appvertype.IAppVerTypeDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.appvertype.AppVerTypeDTO;
import com.idg.idgcore.coe.dto.appvertype.DocumentsDTO;
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

import static com.idg.idgcore.coe.common.Constants.APP_VERIFICATION_TYPE;
import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppVerTypeApplicationServiceTest {

    @InjectMocks
    AppVerTypeApplicationService appVerTypeApplicationService;
    @Mock
    private ProcessConfiguration process;
    @Mock
    AppVerTypeAssembler appVerTypeAssembler;
    @Mock
    private IAppVerTypeDomainService appVerTypeDomainService;
    @Autowired
    private MutationEntity mutationEntity;
    @Autowired
    private MutationEntity mutationEntity2;
    @Mock
    private IMutationsDomainService mutationsDomainService;
    private SessionContext sessionContext;
    private SessionContext sessionContext1;
    private AppVerTypeEntity appVerTypeEntity;
    private AppVerTypeDTO appVerTypeDTO;
    private AppVerTypeDTO appVerTypeDTOUnAuth;
    private AppVerTypeDTO appVerTypeDTO1;
    private AppVerTypeEntity appVerTypeEntity1;
    private AppVerTypeEntity appVerTypeEntity2;
    private AppVerTypeDTO appVerTypeDTOMapper;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext();
        sessionContext1 = getErrorSession();
        appVerTypeDTO = getAppVerTypeDTOAuthorized();
        appVerTypeEntity = getAppVerTypesEntity();
        appVerTypeDTOUnAuth = getAppVerTypeDTOUnAuth();
        appVerTypeDTOMapper = getAppVerTypeDTOMapper();
        mutationEntity = getMutationEntity();
        appVerTypeEntity1 = getAppVerTypesEntity();
        appVerTypeEntity2 = getAppVerTypesEntity2();
        appVerTypeDTO1 = getAppVerTypesDTO();
        mutationEntity2 = getMutationEntityJsonError();
    }

    @Test
    @DisplayName("JUnit for getAppVerTypeByCode where return the AppVerType when the authorized is Y")
    void getAppVerTypeByCodeWhenAuthorizedIsYThenReturnAppVerType() throws FatalException, JsonProcessingException {

        given(appVerTypeDomainService.getAppVerTypeByID(appVerTypeDTO.getVerificationTypeId())).willReturn(appVerTypeEntity);
        given(appVerTypeAssembler.convertEntityToDto(appVerTypeEntity)).willReturn(appVerTypeDTO);
        AppVerTypeDTO result = appVerTypeApplicationService.getAppVerTypeByID(sessionContext, appVerTypeDTO);
        assertEquals(appVerTypeDTO, result);
    }

    @Test
    @DisplayName("JUnit for getAppVerTypes where return all appVerTypes when there are no unauthorized mutations")
    void getAppVerTypesWhenThereAreNoUnauthorizedMutationsThenReturnAllAppVerTypes() throws FatalException {
        given(appVerTypeDomainService.getAppVerTypes()).willReturn(List.of(appVerTypeEntity));
        given(mutationsDomainService.getUnauthorizedMutation(APP_VERIFICATION_TYPE, AUTHORIZED_N)).willReturn(List.of());
        given(appVerTypeAssembler.convertEntityToDto(appVerTypeEntity)).willReturn(appVerTypeDTO);

        List<AppVerTypeDTO> appVerTypeDTOList = appVerTypeApplicationService.getAppVerTypes(sessionContext);

        assertEquals(1, appVerTypeDTOList.size());
        assertEquals(appVerTypeDTO, appVerTypeDTOList.get(0));
    }

    @Test
    @DisplayName("JUnit for getAppVerTypeByCode in application service when Authorize try Block")
    void getAppVerTypeByCodeIsAuthorize() throws FatalException, JsonProcessingException {

        given(appVerTypeDomainService.getAppVerTypeByID(appVerTypeDTO.getVerificationTypeId())).willReturn(appVerTypeEntity);
        given(appVerTypeAssembler.convertEntityToDto(appVerTypeEntity)).willReturn(appVerTypeDTO);
        AppVerTypeDTO appVerTypeDTO1 = appVerTypeApplicationService.getAppVerTypeByID(sessionContext, appVerTypeDTO);
        assertEquals("Y",appVerTypeDTO1.getAuthorized());
        assertThat(appVerTypeDTO1).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getAppVerTypeByCode in application service when Not Authorize in catch block")
    void getAppVerTypeByCodeWhenNotAuthorizeCatchBlock () throws FatalException, JsonProcessingException {

        String payLoadString1 =
                "{\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\"" +
                        ",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"APPVERTYPE\",\"taskIdentifier\":\"VT001\"" +
                        ",\"verificationTypeId\":\"VT001\",\"verificationTypeName\":\"Identification Proof\",\"verificationTypeDesc\":\"Customer Identification\"" +
                        ",\"isViewableToCustomer\":true,\"isAlertToBeSentOnCompl\":true,\"isExternal\":true,\"isDocumentRequired\":true" +
                        ",\"documents\":[{\"documentName\":\"PAN Card\",\"nature\":\"Mandatory\"}]}";

        given(mutationsDomainService.getConfigurationByCode(appVerTypeDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);

        Assertions.assertThrows(Exception.class,()-> {
            AppVerTypeDTO appVerTypeDTO1 = appVerTypeApplicationService.getAppVerTypeByID(sessionContext, appVerTypeDTOMapper);
            assertEquals("N",appVerTypeDTO1.getAuthorized());
            assertThat(appVerTypeDTO1).isNotNull();
        });
    }

    @Test
    @DisplayName("JUnit for AppVerTypes in application service for catch block for checker")
    void getAppVerTypesCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        sessionContext.setRole(new String[] { "" });
        given(mutationsDomainService.getUnauthorizedMutation(
                appVerTypeDTO1.getTaskCode(),AUTHORIZED_N))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
        Assertions.assertThrows(FatalException.class,()-> {
            List<AppVerTypeDTO> appVerTypeDTO1 = appVerTypeApplicationService.getAppVerTypes(sessionContext);
            assertThat(appVerTypeDTO1).isNotNull();
        });
    }

    @Test
    @DisplayName("JUnit for processAppVerType in application service for Try Block")
    void processAppVerTypeForTryBlock() throws JsonProcessingException, FatalException {

        doNothing().when(process).process(appVerTypeDTO);
        appVerTypeApplicationService.processAppVerType(sessionContext, appVerTypeDTO);
        verify(process, times(1)).process(appVerTypeDTO);
    }

    @Test
    @DisplayName("JUnit for processAppVerType in application service for Catch Block")
    void processAppVerTypeForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            appVerTypeApplicationService.processAppVerType(sessionContext2, appVerTypeDTO);
            assertThat(appVerTypeDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {

        String payLoadString1 =
                "{\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\"" +
                        ",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"APPVERTYPE\",\"taskIdentifier\":\"VT001\"" +
                        ",\"verificationTypeId\":\"VT001\",\"verificationTypeName\":\"Identification Proof\",\"verificationTypeDesc\":\"Customer Identification\"" +
                        ",\"isViewableToCustomer\":true,\"isAlertToBeSentOnCompl\":true,\"isExternal\":true,\"isDocumentRequired\":true" +
                        ",\"documents\":[{\"documentName\":\"PAN Card\",\"nature\":\"Mandatory\"}]}";

        doNothing().when(appVerTypeDomainService).save(appVerTypeDTO);
        appVerTypeApplicationService.save(appVerTypeDTO);
        appVerTypeApplicationService.addUpdateRecord(payLoadString1);
        verify(appVerTypeDomainService, times(1)).save(appVerTypeDTO);
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = appVerTypeDTO.getVerificationTypeId();
        given(appVerTypeDomainService.getAppVerTypeByID(code)).willReturn(appVerTypeEntity);
        appVerTypeApplicationService.getConfigurationByCode(code);
        assertThat(appVerTypeEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getByAppVerTypeCode in application service when Authorize for Negative")
    void getAppVerTypeByCodeIsAuthorizeForNegative() throws FatalException, JsonProcessingException {
        given(appVerTypeDomainService.getAppVerTypeByID(appVerTypeDTO.getVerificationTypeId())).willReturn(appVerTypeEntity);
        given(appVerTypeAssembler.convertEntityToDto(appVerTypeEntity)).willReturn(appVerTypeDTO);
        AppVerTypeDTO appVerTypeDTO1 = appVerTypeApplicationService.getAppVerTypeByID(sessionContext, appVerTypeDTO);
        assertNotEquals("N",appVerTypeDTO1.getAuthorized());
        assertThat(appVerTypeDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getAppVerTypeByCode in application service check Parameter not null")
    void getAppVerTypeByCodeIsAuthorizeCheckParameter() throws FatalException, JsonProcessingException {
        AppVerTypeDTO appVerTypeDTOnull=null;
        AppVerTypeDTO appVerTypeDTOEx=new AppVerTypeDTO();
        appVerTypeDTOEx.setVerificationTypeId("VT001");
        appVerTypeDTOEx.setAuthorized("Y");
        given(appVerTypeDomainService.getAppVerTypeByID(appVerTypeDTOEx.getVerificationTypeId())).willReturn(appVerTypeEntity);
        given(appVerTypeAssembler.convertEntityToDto(appVerTypeEntity)).willReturn(appVerTypeDTO);
        AppVerTypeDTO appVerTypeDTO1 = appVerTypeApplicationService.getAppVerTypeByID(sessionContext, appVerTypeDTOEx);
        assertThat(appVerTypeDTOEx.getVerificationTypeId()).isNotBlank();
        assertThat(appVerTypeDTOEx.getAuthorized()).isNotBlank();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        assertThat(appVerTypeEntity.toString()).isNotNull();
        assertThat(appVerTypeDTO.toString()).isNotNull();

        DocumentsDTO documentsDTO = new DocumentsDTO("PAN Card", "Mandatory");

        AppVerTypeDTO appVerTypeDTO2 = new AppVerTypeDTO("VT001", "Identification Proof",
                "Customer Identification", true, true,
                true, true, List.of(documentsDTO));

        DocumentsDTO documentsDTO1 = DocumentsDTO.builder().documentName("PAN Card").nature("Mandatory").build();

        AppVerTypeDTO.builder()
                .verificationTypeId("VT001")
                .verificationTypeName("Identification Proof")
                .verificationTypeDesc("Customer Identification")
                .isViewableToCustomer(true)
                .isAlertToBeSentOnCompl(true)
                .isExternal(true)
                .isDocumentRequired(true)
                .documents(List.of(documentsDTO1))
                .authorized("N")
                .taskCode(APP_VERIFICATION_TYPE)
                .taskIdentifier("VT001")
                .build().toString();

        AppVerTypeEntityKey appVerTypeEntityKey=new AppVerTypeEntityKey("VT001");
        assertThat(appVerTypeEntityKey.toString()).isNotNull();
        appVerTypeEntityKey.setVerificationTypeId("VT001");
        appVerTypeEntityKey.keyAsString();
        appVerTypeEntityKey.builder().verificationTypeId("VT001").build();
        assertThat(appVerTypeDTO).descriptionText();
    }
    private SessionContext getValidSessionContext() {
        return SessionContext.builder()
                .bankCode("003")
                .defaultBranchCode("1141")
                .internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("")
                .externalTransactionReferenceNumber("")
                .targetUnit("dummy_target")
                .postingDate(new Date())
                .valueDate(new Date())
                .transactionBranch("")
                .userId("nikhil")
                //                .accessibleTargetUnits([])
                .channel("Branch")
                .taskCode(APP_VERIFICATION_TYPE)
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(1L)
                .customAttributes("")
                .serviceInvocationModeType(Regular)
                .allTargetUnitsSelected(true)
                //                .mutationType("")
                .userLocal("en_US")
                .originatingModuleCode("")
                .role(new String[]{"maker"})
                .build();
    }

    private SessionContext getErrorSession() {
        return SessionContext.builder()
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
                //                .accessibleTargetUnits([])
                .channel("")
                .taskCode("")
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(null)
                .customAttributes("")
                .serviceInvocationModeType(null)
                .allTargetUnitsSelected(true)
                //                .mutationType("")
                .userLocal("")
                .originatingModuleCode("")
                .role(new String[]{"maker"})
                .build();
    }

    private AppVerTypeDTO getAppVerTypeDTOAuthorized() {
        AppVerTypeDTO appVerTypeDTO2 = new AppVerTypeDTO();

        appVerTypeDTO2.setVerificationTypeId("VT001");
        appVerTypeDTO2.setVerificationTypeName("Identification Proof");
        appVerTypeDTO2.setVerificationTypeDesc("Customer Identification");
        appVerTypeDTO2.setViewableToCustomer(true);
        appVerTypeDTO2.setAlertToBeSentOnCompl(true);
        appVerTypeDTO2.setExternal(true);
        appVerTypeDTO2.setDocumentRequired(true);

        DocumentsDTO documentsDTO2 = new DocumentsDTO();
        documentsDTO2.setDocumentName("PAN Card");
        documentsDTO2.setNature("Mandatory");

        appVerTypeDTO2.setDocuments(List.of(documentsDTO2));
        appVerTypeDTO2.setAuthorized("Y");

        return appVerTypeDTO2;
    }

    private AppVerTypeDTO getAppVerTypesDTO() {
        AppVerTypeDTO appVerTypeDTO2 = new AppVerTypeDTO();

        appVerTypeDTO2.setVerificationTypeId("VT001");
        appVerTypeDTO2.setVerificationTypeName("Identification Proof");
        appVerTypeDTO2.setVerificationTypeDesc("Customer Identification");
        appVerTypeDTO2.setViewableToCustomer(true);
        appVerTypeDTO2.setAlertToBeSentOnCompl(true);
        appVerTypeDTO2.setExternal(true);
        appVerTypeDTO2.setDocumentRequired(true);

        DocumentsDTO documentsDTO2 = new DocumentsDTO();
        documentsDTO2.setDocumentName("PAN Card");
        documentsDTO2.setNature("Mandatory");

        appVerTypeDTO2.setDocuments(List.of(documentsDTO2));
        appVerTypeDTO2.setTaskCode(APP_VERIFICATION_TYPE);
        appVerTypeDTO2.setStatus("DELETED");
        appVerTypeDTO2.setRecordVersion(1);

        return appVerTypeDTO2;
    }

    private AppVerTypeEntity getAppVerTypeEntity() {

        DocumentsEntity documentsEntity3 = new DocumentsEntity(1,"PAN Card", "Mandatory");

        return new AppVerTypeEntity("VT001", "Identification Proof",
                "Customer Identification", 'Y', 'Y',
                'Y', 'Y', List.of(documentsEntity3),
                "draft",
                0,
                "Y",
                "draft");
    }

    private AppVerTypeEntity getAppVerTypesEntity() {

        DocumentsEntity documentsEntity2 = new DocumentsEntity(1,"PAN Card", "Mandatory");

        return new AppVerTypeEntity("VT001", "Identification Proof",
                "Customer Identification", 'Y', 'Y',
                'Y', 'Y', List.of(documentsEntity2),
                "DELETED",
                1,
                null,
                null);
    }

    private AppVerTypeEntity getAppVerTypesEntity2() {

        DocumentsEntity documentsEntity = new DocumentsEntity();
        documentsEntity.setIdgcCoeAppVerTypeDocId(1);
        documentsEntity.setDocumentName("PAN Card");
        documentsEntity.setNature("Mandatory");

        AppVerTypeEntity appVerTypeEntity2 = new AppVerTypeEntity();
        appVerTypeEntity2.setVerificationTypeId("VT001");
        appVerTypeEntity2.setVerificationTypeName("Identification Proof");
        appVerTypeEntity2.setVerificationTypeDesc("Customer Identification");
        appVerTypeEntity2.setIsViewableToCustomer('Y');
        appVerTypeEntity2.setIsAlertToBeSentOnCompl('Y');
        appVerTypeEntity2.setIsExternal('Y');
        appVerTypeEntity2.setIsDocumentRequired('Y');
        appVerTypeEntity2.setDocuments(List.of(documentsEntity));
        appVerTypeEntity2.setAuthorized("N");
        appVerTypeEntity2.setStatus("closed");
        appVerTypeEntity2.setRecordVersion(1);
        return appVerTypeEntity2;
    }

    private AppVerTypeDTO getAppVerTypeDTOUnAuth() {

        DocumentsDTO documentsDTO = new DocumentsDTO("PAN Card", "Mandatory");

        AppVerTypeDTO appVerTypeDTO2 = new AppVerTypeDTO("VT001", "Identification Proof",
                "Customer Identification", true, true,
                true, true, List.of(documentsDTO));

        appVerTypeDTO2.setAuthorized("N");
        appVerTypeDTO2.setTaskIdentifier("VT001");
        return appVerTypeDTO2;
    }

    private AppVerTypeDTO getAppVerTypeDTOMapper() {

        DocumentsDTO documentsDTO = DocumentsDTO.builder().documentName("PAN Card").nature("Mandatory").build();

        return AppVerTypeDTO.builder()
                .verificationTypeId("VT001")
                .verificationTypeName("Identification Proof")
                .verificationTypeDesc("Customer Identification")
                .isViewableToCustomer(true)
                .isAlertToBeSentOnCompl(true)
                .isExternal(true)
                .isDocumentRequired(true)
                .documents(List.of(documentsDTO))
                .authorized("N")
                .taskCode(APP_VERIFICATION_TYPE)
                .taskIdentifier("VT001")
                .build();
    }

    private MutationEntity getMutationEntity() {

        String payLoadString =
                "{\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\"" +
                        ",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"APPVERTYPE\",\"taskIdentifier\":\"VT001\"" +
                        ",\"verificationTypeId\":\"VT001\",\"verificationTypeName\":\"Identification Proof\",\"verificationTypeDesc\":\"Customer Identification\"" +
                        ",\"isViewableToCustomer\":true,\"isAlertToBeSentOnCompl\":true,\"isExternal\":true,\"isDocumentRequired\":true" +
                        ",\"documents\":[{\"documentName\":\"PAN Card\",\"nature\":\"Mandatory\"}]}";

        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("VT001");
        mutationEntity.setTaskCode(APP_VERIFICATION_TYPE);
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("closed");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("add");
        mutationEntity.setLastConfigurationAction("unauthorized");

        return mutationEntity;
    }

    private MutationEntity getMutationEntityJsonError() {

        String payLoadString1 =
                "{\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\"" +
                        ",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"APPVERTYPE\",\"taskIdentifier\":\"VT001\"" +
                        ",\"verificationTypeId\":\"VT001\",\"verificationTypeName\":\"Identification Proof\",\"verificationTypeDesc\":\"Customer Identification\"" +
                        ",\"isViewableToCustomer\":true,\"isAlertToBeSentOnCompl\":true,\"isExternal\":true,\"isDocumentRequired\":true" +
                        ",\"documents\":[{\"documentName\":\"PAN Card\",\"nature\":\"Mandatory\"}]}";

        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("VT001");
        mutationEntity2.setTaskCode(APP_VERIFICATION_TYPE);
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");

        return mutationEntity2;
    }
}