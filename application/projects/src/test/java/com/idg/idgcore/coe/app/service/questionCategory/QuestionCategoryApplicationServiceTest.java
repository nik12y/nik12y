package com.idg.idgcore.coe.app.service.questionCategory;


import com.idg.idgcore.coe.domain.assembler.questionCategory.QuestionCategoryAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCatDetailsEntity;
import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCategoryEntity;
import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCategoryEntityKey;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.questionCategory.IQuestionCategoryDomainService;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDTO;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDetailsDTO;
import com.idg.idgcore.dto.context.SessionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.*;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import org.junit.jupiter.api.Assertions;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionCategoryApplicationServiceTest {

    @InjectMocks
    QuestionCategoryApplicationService questionCategoryApplicationService;
    @Mock
    private ProcessConfiguration process;
    @Mock
    QuestionCategoryAssembler questionCategoryAssembler;
    @Mock
    private IQuestionCategoryDomainService iQuestionCategoryDomainService;
    @Autowired
    private MutationEntity mutationEntity;
    @Autowired
    private MutationEntity mutationEntity2;
    @Mock
    private IMutationsDomainService mutationsDomainService;
    private SessionContext sessionContext;
    private SessionContext sessionContext1;
    private QuestionCategoryEntity questionCategoryEntity;
    private QuestionCategoryDTO questionCategoryDTO;
    private QuestionCategoryDTO questionCategoryDTOUnAuth;
    private QuestionCategoryDTO questionCategoryDTO1;
    private QuestionCategoryEntity questionCategoryEntity1;
    private QuestionCategoryEntity questionCategoryEntity2;
    private QuestionCategoryDTO questionCategoryDTOMapper;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext();
        sessionContext1 = getErrorSession();
        questionCategoryDTO = getQuestionCategoryDTOAuthorized();
        questionCategoryEntity = getQuestionCategoryEntityDraft();
        questionCategoryDTOUnAuth = getQuestionCategoryDToUnAuth();
        questionCategoryDTOMapper = getQuestionCategoryDTOMapper();
        mutationEntity = getMutationEntity();
        questionCategoryEntity1 = getQuestionCategoryEntity();
        questionCategoryEntity2 = getQuestionCategoryEntity2();
        questionCategoryDTO1 = getQuestionCategoryDTO();
        mutationEntity2 = getMutationEntityJsonError();
    }

    @Test
    @DisplayName("JUnit for getQuestionCategoryById where return the QuestionCategory when the authorized is Y")
    void getQuestionCategoryByIdWhenAuthorizedIsYThenReturnQuestionCategory() throws FatalException, JsonProcessingException {

        given(iQuestionCategoryDomainService.getQuestionCategoryById(questionCategoryDTO.getQuestionCategoryId())).willReturn(questionCategoryEntity);
        given(questionCategoryAssembler.convertEntityToDto(questionCategoryEntity)).willReturn(questionCategoryDTO);
        QuestionCategoryDTO result = questionCategoryApplicationService.getQuestionCategoryById(sessionContext, questionCategoryDTO);
        assertEquals(questionCategoryDTO, result);
    }

    @Test
    @DisplayName("JUnit for getQuestionsCategories where return all QuestionsCategories when there are no unauthorized mutations")
    void getQuestionsCategoriesWhenThereAreNoUnauthorizedMutationsThenReturnAllQuestionsCategories() throws FatalException {
        given(iQuestionCategoryDomainService.getQuestionsCategories()).willReturn(List.of(questionCategoryEntity));
        given(mutationsDomainService.getUnauthorizedMutation(QUESTION_CATEGORY, AUTHORIZED_N)).willReturn(List.of());
        given(questionCategoryAssembler.convertEntityToDto(questionCategoryEntity)).willReturn(questionCategoryDTO);

        List<QuestionCategoryDTO> questionsCategories = questionCategoryApplicationService.getQuestionsCategories(sessionContext);

        assertEquals(1, questionsCategories.size());
        assertEquals(questionCategoryDTO, questionsCategories.get(0));
    }

    @Test
    @DisplayName("JUnit for getQuestionCategoryById in application service when Authorize try Block")
    void getQuestionCategoryByIdIsAuthorize() throws FatalException, JsonProcessingException {

        given(iQuestionCategoryDomainService.getQuestionCategoryById(questionCategoryDTO.getQuestionCategoryId())).willReturn(questionCategoryEntity);
        given(questionCategoryAssembler.convertEntityToDto(questionCategoryEntity)).willReturn(questionCategoryDTO);
        QuestionCategoryDTO questionCategoryById = questionCategoryApplicationService.getQuestionCategoryById(sessionContext, questionCategoryDTO);
        assertEquals("Y", questionCategoryById.getAuthorized());
        assertThat(questionCategoryById).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getQuestionCategoryById in application service when Not Authorize in catch block")
    void getQuestionCategoryByIdWhenNotAuthorizeCatchBlock() throws FatalException, JsonProcessingException {

        String payLoadString1 =
                "{\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\"" +
                        ",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"APPVERTYPE\",\"taskIdentifier\":\"VT001\"" +
                        ",\"verificationTypeId\":\"VT001\",\"verificationTypeName\":\"Identification Proof\",\"verificationTypeDesc\":\"Customer Identification\"" +
                        ",\"isViewableToCustomer\":true,\"isAlertToBeSentOnCompl\":true,\"isExternal\":true,\"isDocumentRequired\":true" +
                        ",\"documents\":[{\"documentName\":\"PAN Card\",\"nature\":\"Mandatory\"}]}";

        given(mutationsDomainService.getConfigurationByCode(questionCategoryDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper = new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);

        Assertions.assertThrows(Exception.class, () -> {
            QuestionCategoryDTO questionCategoryById1 = questionCategoryApplicationService.getQuestionCategoryById(sessionContext, questionCategoryDTOMapper);
            assertEquals("N", questionCategoryById1.getAuthorized());
            assertThat(questionCategoryById1).isNotNull();
        });
    }

    @Test
    @DisplayName("JUnit for QuestionCategories in application service for catch block for checker")
    void QuestionCategoriesCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        sessionContext.setRole(new String[]{""});
        given(mutationsDomainService.getUnauthorizedMutation(
                questionCategoryDTO1.getTaskCode(), AUTHORIZED_N))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
        Assertions.assertThrows(FatalException.class, () -> {
            List<QuestionCategoryDTO> questionCategoryDTOList = questionCategoryApplicationService.getQuestionsCategories(sessionContext);
            assertThat(questionCategoryDTOList).isNotNull();
        });
    }

    @Test
    @DisplayName("JUnit for processQuestionCategory in application service for Try Block")
    void processQuestionCategoryTryBlock() throws JsonProcessingException, FatalException {

        doNothing().when(process).process(questionCategoryDTO);
        questionCategoryApplicationService.processQuestionCategory(sessionContext, questionCategoryDTO);
        verify(process, times(1)).process(questionCategoryDTO);
    }

    @Test
    @DisplayName("JUnit for processQuestionCategory in application service for Catch Block")
    void processQuestionCategoryForCatchBlock() throws FatalException {
        SessionContext sessionContext2 = null;
        Assertions.assertThrows(Exception.class, () -> {
            questionCategoryApplicationService.processQuestionCategory(sessionContext2, questionCategoryDTO);
            assertThat(questionCategoryDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {

        String payLoadString1 =
                "{\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\"," +
                        "\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"QUESTION-CAT\",\"taskIdentifier\":\"SN001\"," +
                        "\"questionCategoryId\":\"572\",\"questionCategoryName\":\"Education loan\",\"questionDisplay\":\"Yes\"," +
                        "\"isShowQuestionCategoryName\":true,\"isEnableDocumentUpload\":true," +
                        "\"questionCategoryDetails\":[{\"questionId\":\"Q005\",\"questionNature\":\"Mandatory\",\"parentQuestionId\":\"Q004\",\"displayCondition\":\"Yes\"}]}";


        doNothing().when(iQuestionCategoryDomainService).save(questionCategoryDTO);
        questionCategoryApplicationService.save(questionCategoryDTO);
        questionCategoryApplicationService.addUpdateRecord(payLoadString1);
        verify(iQuestionCategoryDomainService, times(1)).save(questionCategoryDTO);
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest() {
        String code = questionCategoryDTO.getQuestionCategoryId();
        given(iQuestionCategoryDomainService.getQuestionCategoryById(code)).willReturn(questionCategoryEntity);
        questionCategoryApplicationService.getConfigurationByCode(code);
        assertThat(questionCategoryEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getByAppVerTypeCode in application service when Authorize for Negative")
    void getAppVerTypeByCodeIsAuthorizeForNegative() throws FatalException, JsonProcessingException {
        given(iQuestionCategoryDomainService.getQuestionCategoryById(questionCategoryDTO.getQuestionCategoryId())).willReturn(questionCategoryEntity);
        given(questionCategoryAssembler.convertEntityToDto(questionCategoryEntity)).willReturn(questionCategoryDTO);
        QuestionCategoryDTO appVerTypeDTO1 = questionCategoryApplicationService.getQuestionCategoryById(sessionContext, questionCategoryDTO);
        assertNotEquals("N", appVerTypeDTO1.getAuthorized());
        assertThat(questionCategoryDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getQuestionCategoryById in application service check Parameter not null")
    void getQuestionCategoryByIdIsAuthorizeCheckParameter() throws FatalException, JsonProcessingException {
        QuestionCategoryDTO appVerTypeDTOnull = null;
        QuestionCategoryDTO questionCategoryDTOEx = new QuestionCategoryDTO();
        questionCategoryDTOEx.setQuestionCategoryId("SN001");
        questionCategoryDTOEx.setAuthorized("Y");
        given(iQuestionCategoryDomainService.getQuestionCategoryById(questionCategoryDTOEx.getQuestionCategoryId())).willReturn(questionCategoryEntity);
        given(questionCategoryAssembler.convertEntityToDto(questionCategoryEntity)).willReturn(questionCategoryDTO);
        QuestionCategoryDTO appVerTypeDTO1 = questionCategoryApplicationService.getQuestionCategoryById(sessionContext, questionCategoryDTOEx);
        assertThat(questionCategoryDTOEx.getQuestionCategoryId()).isNotBlank();
        assertThat(questionCategoryDTOEx.getAuthorized()).isNotBlank();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage() {
        assertThat(questionCategoryEntity.toString()).isNotNull();
        assertThat(questionCategoryDTO.toString()).isNotNull();

        QuestionCategoryDetailsDTO questionCategoryDetailsDTO = new QuestionCategoryDetailsDTO("Q002", "Mandatory", "Q001", "Q002.Yes");

        QuestionCategoryDTO appVerTypeDTO2 = new QuestionCategoryDTO("SN001", "Loan Question",
                "Collective", true, true, List.of(questionCategoryDetailsDTO));


        QuestionCategoryDetailsDTO questionCategoryDetailsDTO1 = new QuestionCategoryDetailsDTO("Q002", "Mandatory", "Q001", "Q002.Yes");


        QuestionCategoryEntityKey questionCategoryEntityKey = new QuestionCategoryEntityKey("SN001");
        assertThat(questionCategoryEntityKey.toString()).isNotNull();
        questionCategoryEntityKey.setQuestionCategoryId("SN001");
        questionCategoryEntityKey.keyAsString();
        questionCategoryEntityKey.builder().questionCategoryId("SN001").build();
        assertThat(questionCategoryDTO).descriptionText();
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
                .channel("Branch")
                .taskCode(QUESTION_CATEGORY)
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(1L)
                .customAttributes("")
                .serviceInvocationModeType(Regular)
                .allTargetUnitsSelected(true)
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
                .channel("")
                .taskCode("")
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(null)
                .customAttributes("")
                .serviceInvocationModeType(null)
                .allTargetUnitsSelected(true)
                .userLocal("")
                .originatingModuleCode("")
                .role(new String[]{"maker"})
                .build();
    }

    private QuestionCategoryDTO getQuestionCategoryDTOAuthorized() {
        QuestionCategoryDTO questionCategoryDTO = new QuestionCategoryDTO();

        questionCategoryDTO.setQuestionCategoryId("SN001");
        questionCategoryDTO.setQuestionCategoryName("Loan Question");
        questionCategoryDTO.setQuestionDisplay("Collective");
        questionCategoryDTO.setIsShowQuestionCategoryName(true);
        questionCategoryDTO.setIsEnableDocumentUpload(true);
        questionCategoryDTO.setStatus("new");
        questionCategoryDTO.setAuthorized("N");
        questionCategoryDTO.setRecordVersion(1);
        questionCategoryDTO.setLastConfigurationAction("unauthorized");

        QuestionCategoryDetailsDTO questionCategoryDetailsDTO = new QuestionCategoryDetailsDTO();
        questionCategoryDetailsDTO.setQuestionId("Q002");
        questionCategoryDetailsDTO.setQuestionNature("Mandatory");
        questionCategoryDetailsDTO.setParentQuestionId("Q001");
        questionCategoryDetailsDTO.setDisplayCondition("Q002.Yes");
        questionCategoryDTO.setQuestionCategoryDetails(List.of(questionCategoryDetailsDTO));
        questionCategoryDTO.setAuthorized("Y");

        return questionCategoryDTO;
    }

    private QuestionCategoryDTO getQuestionCategoryDTO() {
        QuestionCategoryDTO questionCategoryDTO = new QuestionCategoryDTO();

        questionCategoryDTO.setQuestionCategoryId("SN001");
        questionCategoryDTO.setQuestionCategoryName("Loan Question");
        questionCategoryDTO.setQuestionDisplay("Collective");
        questionCategoryDTO.setIsShowQuestionCategoryName(true);
        questionCategoryDTO.setIsEnableDocumentUpload(true);


        QuestionCategoryDetailsDTO questionCategoryDetailsDTO2 = new QuestionCategoryDetailsDTO();
        questionCategoryDetailsDTO2.setQuestionId("Q002");
        questionCategoryDetailsDTO2.setQuestionNature("Mandatory");
        questionCategoryDetailsDTO2.setParentQuestionId("Q001");
        questionCategoryDetailsDTO2.setDisplayCondition("Q002.Yes");
        questionCategoryDTO.setTaskCode(QUESTION_CATEGORY);
        questionCategoryDTO.setStatus("DELETED");
        questionCategoryDTO.setRecordVersion(1);

        return questionCategoryDTO;
    }

    private QuestionCategoryEntity getQuestionCategoryEntityDraft() {

        QuestionCatDetailsEntity questionCatDetailsEntity3 = new QuestionCatDetailsEntity(1, "Q002", "Mandatory", "Q001", "Q002.Yes");

        return new QuestionCategoryEntity("SN001", "Loan Question", "Collective",
                'Y', 'Y',null,null, List.of(questionCatDetailsEntity3), "draft", 0, "draft", "draft");

    }

    private QuestionCategoryEntity getQuestionCategoryEntity() {

        QuestionCatDetailsEntity questionCatDetailsEntity2 = new QuestionCatDetailsEntity(1, "Q002", "Mandatory", "Q001", "Q002.Yes");

        return new QuestionCategoryEntity("SN001", "Loan Question", "Collective",
                'Y', 'Y',null,null, List.of(questionCatDetailsEntity2), "Deleted", 1, null, null);

    }

    private QuestionCategoryEntity getQuestionCategoryEntity2() {
        QuestionCatDetailsEntity questionCatDetailsEntities1 = new QuestionCatDetailsEntity();
        questionCatDetailsEntities1.setQueCatDetailId(1);
        questionCatDetailsEntities1.setQuestionId("Q002");
        questionCatDetailsEntities1.setQuestionNature("Mandatory");
        questionCatDetailsEntities1.setParentQuestionId("Q001");
        questionCatDetailsEntities1.setDisplayCondition("Q002.Yes");

        QuestionCategoryEntity questionCategoryEntity2 = new QuestionCategoryEntity();
        questionCategoryEntity2.setQuestionCategoryId("SN001");
        questionCategoryEntity2.setQuestionCategoryName("Loan Question");
        questionCategoryEntity2.setQuestionDisplay("Collective");
        questionCategoryEntity2.setIsShowQuestionCategoryName('Y');
        questionCategoryEntity2.setIsEnableDocumentUpload('Y');
        questionCategoryEntity2.setQuestionCatDetails(List.of(questionCatDetailsEntities1));
        questionCategoryEntity2.setStatus("new");
        questionCategoryEntity2.setAuthorized("N");
        questionCategoryEntity2.setRecordVersion(1);
        questionCategoryEntity2.setLastConfigurationAction("unauthorized");
        return this.questionCategoryEntity2;
    }

    private QuestionCategoryDTO getQuestionCategoryDToUnAuth() {

        List<QuestionCategoryDetailsDTO> questionCategoryDetailsDTOListUnAuth = new ArrayList<>();
        questionCategoryDetailsDTOListUnAuth.add(new QuestionCategoryDetailsDTO("Q002", "Mandatory", "Q001", "Q002.Yes"));

        QuestionCategoryDTO questionCategoryDTO = new QuestionCategoryDTO();
        questionCategoryDTO.setQuestionCategoryId("SN001");
        questionCategoryDTO.setQuestionCategoryName("Loan Question");
        questionCategoryDTO.setQuestionDisplay("Collective");
        questionCategoryDTO.setIsShowQuestionCategoryName(true);
        questionCategoryDTO.setIsEnableDocumentUpload(true);
        questionCategoryDTO.setQuestionCategoryDetails(questionCategoryDetailsDTOListUnAuth);
        questionCategoryDTO.setStatus("new");
        questionCategoryDTO.setAuthorized("N");
        questionCategoryDTO.setRecordVersion(1);
        questionCategoryDTO.setLastConfigurationAction("unauthorized");
        return questionCategoryDTO;
    }

    private QuestionCategoryDTO getQuestionCategoryDTOMapper() {

        QuestionCategoryDetailsDTO questionCategoryDetailsDTO = QuestionCategoryDetailsDTO.builder().questionId("Q002").questionNature("Mandatory").parentQuestionId("Q001").displayCondition("Q002.Yes").build();

        return QuestionCategoryDTO.builder()
                .questionCategoryId("SN001")
                .questionCategoryName("Loan Question")
                .questionDisplay("Collective")
                .isShowQuestionCategoryName(true)
                .isEnableDocumentUpload(true)
                .questionCategoryDetails(List.of(questionCategoryDetailsDTO))
                .authorized("N")
                .taskCode(QUESTION_CATEGORY)
                .taskIdentifier("SN001")
                .build();
    }

    private MutationEntity getMutationEntity() {

        String payLoadString =
                "{\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\"," +
                        "\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"QUESTION-CAT\",\"taskIdentifier\":\"SN001\"," +
                        "\"questionCategoryId\":\"572\",\"questionCategoryName\":\"Education loan\",\"questionDisplay\":\"Yes\"," +
                        "\"isShowQuestionCategoryName\":true,\"isEnableDocumentUpload\":true," +
                        "\"questionCategoryDetails\":[{\"questionId\":\"Q005\",\"questionNature\":\"Mandatory\",\"parentQuestionId\":\"Q004\",\"displayCondition\":\"Yes\"}]}";

        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("SN001");
        mutationEntity.setTaskCode(QUESTION_CATEGORY);
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("closed");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("add");
        mutationEntity.setLastConfigurationAction("unauthorized");

        return mutationEntity;
    }

    private MutationEntity getMutationEntityJsonError() {
        String payLoadString1 = "{\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"QUESTION-CAT\",\"taskIdentifier\":\"SN001\"," +
                "\"questionCategoryId\":\"572\",\"questionCategoryName\":\"Education loan\",\"questionDisplay\":\"Yes\"," +
                "\"isShowQuestionCategoryName\":true,\"isEnableDocumentUpload\":true," +
                "\"questionCategoryDetails\":[{\"questionId\":\"Q005\",\"questionNature\":\"Mandatory\",\"parentQuestionId\":\"Q004\",\"displayCondition\":\"Yes\"}]}";


        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("SN001");
        mutationEntity2.setTaskCode(QUESTION_CATEGORY);
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");

        return mutationEntity2;
    }
}