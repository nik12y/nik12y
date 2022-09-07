package com.idg.idgcore.coe.app.service.question;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.question.QuestionAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.entity.question.QuestionEntity;
import com.idg.idgcore.coe.domain.entity.question.QuestionEntityKey;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.question.IQuestionDomainService;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.question.QuestionDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.ServiceInvocationModeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.QUESTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionApplicationServiceTest {

    @Mock
    private ModelMapper mapper = new ModelMapper();
    @Mock
    ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private IProcessConfiguration process;
    @Mock
    private MappingConfig mappingConfig;
    @Mock
    private IMutationsDomainService mutationsDomainService;
    @Mock
    private QuestionAssembler questionAssembler;
    @Mock
    private IQuestionDomainService iQuestionDomainService;

    @Mock
    MutationEntity mutationEntity;
    SessionContext sessionContext;
    QuestionEntity questionEntity;
    QuestionEntity questionEntityNe;
    QuestionEntity questionEntityKey;

    QuestionDTO questionDTO;
    String payloads;
    QuestionEntity questionEntityAuth;
    QuestionDTO questionDTOAuth;
    QuestionDTO questionDTONe;
    QuestionDTO questionDTOAuditField;
    String updatePayloads;

    @InjectMocks
    private QuestionApplicationService questionApplicationService;

    @Test
    void contextLoads() {
        assertThat(questionApplicationService).isNotNull();
    }


    @BeforeEach
    void setUp() {
        mutationEntity = getMutationEntity();
        List<QuestionDTO> questionDTOList = getQuestionDTOsList();
        sessionContext = getValidSessionContext();
        questionEntity = getQuestionEntity();
        questionEntityKey=getQuestionEntityKey();
        questionDTO = getQuestionDTO();
        payloads = getPayloads();
        questionEntityAuth = getQuestionEntityAuth();
        questionDTOAuth = getQuestionDTOAuth();
        questionDTONe = getQuestionDTONe();
        questionEntityNe = getQuestionEntityNe();
        questionDTOAuditField = setAuditFields();
        updatePayloads = getUpdatePayloads();

        AbstractAuditableDomainEntity abstractAuditableDomainEntity = getAbstractAuditableDomainEntity();
    }

    @Test
    @DisplayName("JUnit Test for getGroupBanks for empty list")
    void getQuestionsEmptyList() throws FatalException {
        //given(iQuestionDomainService.getQuestions()).willReturn(Collections.emptyList());
        List<QuestionDTO> groupBanks = questionApplicationService.getQuestions(sessionContext);
        assertThat(groupBanks).isEmpty();
        System.out.println("Done");
    }

    @Test
    @DisplayName("JUnit Test for getGroupBanks for Unauthorized and Authorized records")
    void getQuestion() throws FatalException {

        List<QuestionEntity> questionEntityList = new ArrayList<>();
        questionEntityList.add(questionEntity);
        questionEntityList.add(questionEntityAuth);

        List<MutationEntity> unauthorizedEntites = new ArrayList<>();
        unauthorizedEntites.add(mutationEntity);

       // given(iQuestionDomainService.getQuestions()).willReturn(questionEntityList);
       // given(questionAssembler.convertEntityToDto(questionEntity)).willReturn(questionDTO);

        // given(mutationsDomainService.getUnauthorizedMutation(groupBankingDTO.getTaskCode())).willReturn(unauthorizedEntites);
        Payload payload = new Payload();
        payload.setData(payloads);
        mutationEntity.setPayload(payload);
        ObjectMapper objectMapper = mock(ObjectMapper.class);

//        given(questionAssembler.convertEntityToDto(questionEntity)).willReturn(questionDTO);
        List<QuestionDTO> questions = questionApplicationService.getQuestions(sessionContext);
        assertThat(questions).isNotNull();
        System.out.println("Done");
    }

    @Test
    @DisplayName("JUnit test cases for Authorized the user try block if condition")
    void getQuestionByIdAuthRecords() throws FatalException, JsonProcessingException {
        given(iQuestionDomainService.getQuestionById(questionDTOAuth.getQuestionId())).willReturn(questionEntityAuth);
        given(questionAssembler.convertEntityToDto(questionEntityAuth)).willReturn(questionDTOAuth);
        QuestionDTO groupBankByCode = questionApplicationService.getQuestionById(sessionContext, questionDTOAuth);
        assertEquals("Y", questionDTOAuth.getAuthorized());
        assertThat(groupBankByCode).isNotNull();
        System.out.println(groupBankByCode);
        System.out.println("Done");
    }

   /* @Test
    @DisplayName("Junit test for Unauthorized Records else block")
    void getQuestionByIdUnauthorizedRecords() throws JsonProcessingException, FatalException {
        String payload = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null"+
                "\"lastUpdatedTime\":null,\"action\":\"add\",\"status\":\"new\",\"recordVersion\":0,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"unauthorized\",\"taskCode\":\"QUESTION\",\"taskIdentifier\":\"Q01\"," +
                "\"questionId\":\"Q01\",\"questionName\":\"Education Loan Purpose\",\"questionText\":\"What is  Loan Purpose ?\"" +
                ",\"questionDescription\":\" Education Purpose helps to decide the further Application Treatment Workflow\"" +
                ",\"answerDisplayType\":\"Radio\",\"manualFactBased\":\"Manual\",\"answerValue\":\"Education Amount\"}";

        PayloadDTO payloadDTO = new PayloadDTO("{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"add\",\"status\":\"new\",\"recordVersion\":0,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"unauthorized\",\"taskCode\":\"QUESTION\",\"taskIdentifier\":\"Q01\"," +
                "\"questionId\":\"Q01\",\"questionName\":\"Education Loan Purpose\",\"questionText\":\"What is  Loan Purpose ?\"" +
                ",\"questionDescription\":\" Education Purpose helps to decide the further Application Treatment Workflow\"" +
                ",\"answerDisplayType\":\"Radio\",\"manualFactBased\":\"Manual\",\"answerValue\":\"Education Amount\"}");

        given(mutationsDomainService.getConfigurationByCode(questionDTO.getTaskIdentifier())).willReturn(mutationEntity);
        given(mapper.map(mutationEntity.getPayload(), PayloadDTO.class)).willReturn(payloadDTO);
        when(objectMapper.readValue(payloadDTO.getData(), QuestionDTO.class)).thenReturn(questionDTO);
        given(questionAssembler.setAuditFields(mutationEntity, questionDTO)).willReturn(questionDTO);
        QuestionDTO questionDTO1 = questionApplicationService.getQuestionById(sessionContext, questionDTO);
        System.out.println("questionDTO1 :" + questionDTO1);
        assertEquals("N", questionDTO1.getAuthorized());
        assertThat(questionDTO1).isNotNull();
        System.out.println("Done");
    }*/

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        QuestionEntityKey purposeEntityKey1=new QuestionEntityKey();
        assertThat(questionEntity.toString()).isNotNull();
        assertThat(questionDTO.toString()).isNotNull();
        QuestionDTO questionDTO2=new QuestionDTO("Q01","Education Loan Purpose","What is  Loan Purpose ?","Education Purpose helps to decide the further Application Treatment Workflow",
                "Radio","Manual","Education Amount");
        QuestionDTO.builder().questionId("Q01").questionName("Education Loan Purpose").questionText("What is  Loan Purpose ?").questionDescription("Education Purpose helps to decide the further Application Treatment Workflow").answerDisplayType("Radio").manualFactBased("Manual").answerValue("Education Amount").build().toString();
        QuestionEntityKey questionEntityKey=new QuestionEntityKey("Q01");
        assertThat(questionEntityKey.toString()).isNotNull();
        questionEntityKey.setQuestionId("Q01");
        questionEntityKey.keyAsString();
        QuestionEntityKey.builder().questionId("Q01").build();
        assertThat(questionDTO).descriptionText();
    }

    private QuestionEntity getQuestionEntityKey(){

        QuestionEntity questionEntityKey = new QuestionEntity("Q01","Education Loan Purpose","What is  Loan Purpose ?","Education Purpose helps to decide the further Application Treatment Workflow",
                "Radio","Manual","Education Amount",null,null,"add",0,"N","unauthorized");

        QuestionEntityKey questionEntityKey1 = new QuestionEntityKey("Q01");
        System.out.println(questionEntityKey1);
        System.out.println(questionEntityKey1);
        return questionEntityKey;
    }

    @Test
    @DisplayName("Junit test for process GroupBanking Code")
    void processTest() throws Exception {

        doNothing().when(process).process(questionDTO);
        questionApplicationService.processQuestion(sessionContext, questionDTO);
        verify(process, times(1)).process(questionDTO);
    }

//    @Test
//    @DisplayName("Junit test for getConfigurationByCode")
//    void getConfigurationByCodeTest() {
//        String taskIdentifier = questionDTO.getTaskIdentifier();
//        CoreEngineBaseDTO configurationByCode = questionApplicationService.getConfigurationByCode(taskIdentifier);
//        System.out.println("GetConfiguration by code" + configurationByCode);
//        assertThat(configurationByCode).isNotNull();
//    }

    @Test
    @DisplayName("Junit test for AddUpdate Records")
    void addUpdatedRecords() throws JsonProcessingException {

        doNothing().when(iQuestionDomainService).save(questionDTO);
        questionApplicationService.save(questionDTO);
        questionApplicationService.addUpdateRecord(updatePayloads);
        verify(iQuestionDomainService, times(1)).save(questionDTO);
        System.out.println("Records Updated Successfully");
    }

    private MutationEntity getMutationEntity() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskCode(QUESTION);
        mutationEntity.setTaskIdentifier("Q01");
        mutationEntity.setLastConfigurationAction("unauthorized");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(0);
        mutationEntity.setStatus("new");
        mutationEntity.setAction("add");
        mutationEntity.setPayload(new Payload(payloads));
        return mutationEntity;
    }

    private List<QuestionDTO> getQuestionDTOsList() {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        QuestionDTO questionDTO1 = new QuestionDTO("Q01","Education Loan Purpose","What is  Loan Purpose ?","Education Purpose helps to decide the further Application Treatment Workflow",
                "Radio","Manual","Education Amount");
        questionDTOList.add(questionDTO1);
        return questionDTOList;
    }

    private SessionContext getValidSessionContext() {
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
                .serviceInvocationModeType(ServiceInvocationModeType.Regular)
                .allTargetUnitsSelected(true)
//                .mutationType("")
                .userLocal("")
                .originatingModuleCode("")
                .role(new String[]{"maker"}).build();
        return sessionContext;
    }

    private QuestionEntity getQuestionEntity() {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setQuestionId("Q01");
        questionEntity.setQuestionName("Education Loan Purpose");
        questionEntity.setQuestionText("What is  Loan Purpose ?");
        questionEntity.setQuestionDescription("Education Purpose helps to decide the further Application Treatment Workflow");
        questionEntity.setAnswerDisplayType("Radio");
        questionEntity.setManualFactBased("Manual");
        questionEntity.setAnswerValue("Education Amount");
        questionEntity.setStatus("new");
        questionEntity.setAuthorized("N");
        questionEntity.setRecordVersion(0);
        questionEntity.setLastConfigurationAction("unauthorized");
        return questionEntity;
    }

    private QuestionEntity getQuestionEntityNe() {
        QuestionEntity questionEntityNe = new QuestionEntity();
        questionEntityNe.setQuestionId("Q01");
        questionEntityNe.setQuestionName("Education Loan Purpose");
        questionEntityNe.setQuestionText("What is  Loan Purpose ?");
        questionEntityNe.setQuestionDescription("Education Purpose helps to decide the further Application Treatment Workflow");
        questionEntityNe.setAnswerDisplayType("Radio");
        questionEntityNe.setManualFactBased("Manual");
        questionEntityNe.setAnswerValue("Education Amount");
        questionEntityNe.setStatus("draft");
        questionEntityNe.setAuthorized("N");
        questionEntityNe.setRecordVersion(0);
        questionEntityNe.setLastConfigurationAction("unauthorized");
        return questionEntityNe;
    }

    private QuestionDTO getQuestionDTO() {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionId("Q01");
        questionDTO.setQuestionName("Education Loan Purpose");
        questionDTO.setQuestionText("What is  Loan Purpose ?");
        questionDTO.setQuestionDescription("Education Purpose helps to decide the further Application Treatment Workflow");
        questionDTO.setAnswerDisplayType("Radio");
        questionDTO.setManualFactBased("Manual");
        questionDTO.setAnswerValue("Education Amount");
        questionDTO.setStatus("new");
        questionDTO.setAuthorized("N");
        questionDTO.setRecordVersion(0);
        questionDTO.setTaskCode(QUESTION);
        questionDTO.setTaskIdentifier("Q01");
        questionDTO.setLastConfigurationAction("unauthorized");
        return questionDTO;
    }

    private QuestionDTO getQuestionDTONe() {
        QuestionDTO questionDTONe = new QuestionDTO();
        questionDTONe.setQuestionId("Q01");
        questionDTONe.setQuestionName("Education Loan Purpose");
        questionDTONe.setQuestionText("What is  Loan Purpose ?");
        questionDTONe.setQuestionDescription("Education Purpose helps to decide the further Application Treatment Workflow");
        questionDTONe.setAnswerDisplayType("Radio");
        questionDTONe.setManualFactBased("Manual");
        questionDTONe.setAnswerValue("Education Amount");
        questionDTONe.setStatus("draft");
        questionDTONe.setAuthorized("N");
        questionDTONe.setRecordVersion(0);
        questionDTONe.setTaskCode(QUESTION);
        questionDTONe.setLastConfigurationAction("unauthorized");
        return questionDTONe;
    }

    private QuestionEntity getQuestionEntityAuth() {
        QuestionEntity questionEntityAuth = new QuestionEntity();
        questionEntityAuth.setQuestionId("Q01");
        questionEntityAuth.setQuestionName("Education Loan Purpose");
        questionEntityAuth.setQuestionText("What is  Loan Purpose ?");
        questionEntityAuth.setQuestionDescription("Education Purpose helps to decide the further Application Treatment Workflow");
        questionEntityAuth.setAnswerDisplayType("Radio");
        questionEntityAuth.setManualFactBased("Manual");
        questionEntityAuth.setAnswerValue("Education Amount");
        questionEntityAuth.setStatus("active");
        questionEntityAuth.setAuthorized("Y");
        questionEntityAuth.setRecordVersion(1);
        questionEntityAuth.setLastConfigurationAction("authorized");
        return questionEntityAuth;
    }

    private QuestionDTO getQuestionDTOAuth() {
        QuestionDTO questionDTOAuth = new QuestionDTO();
      questionDTOAuth.setQuestionId("Q01");
      questionDTOAuth.setQuestionName("Education Loan Purpose");
      questionDTOAuth.setQuestionText("What is  Loan Purpose ?");
      questionDTOAuth.setQuestionDescription("Education Purpose helps to decide the further Application Treatment Workflow");
      questionDTOAuth.setAnswerDisplayType("Radio");
      questionDTOAuth.setManualFactBased("Manual");
      questionDTOAuth.setAnswerValue("Education Amount");
      questionDTOAuth.setStatus("active");
      questionDTOAuth.setAuthorized("Y");
      questionDTOAuth.setRecordVersion(1);
      questionDTOAuth.setLastConfigurationAction("authorized");
        return questionDTOAuth;
    }

    private AbstractAuditableDomainEntity getAbstractAuditableDomainEntity() {
        AbstractAuditableDomainEntity abstractAuditableDomainEntity = new AbstractAuditableDomainEntity();
        abstractAuditableDomainEntity.setCreatedBy("Nikhil");
        abstractAuditableDomainEntity.setCreationTime(new Date());
        abstractAuditableDomainEntity.setLastUpdatedBy("Prashant");
        abstractAuditableDomainEntity.setLastUpdatedTime(new Date());
        return abstractAuditableDomainEntity;
    }

    private String getPayloads() {
        String payloads = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"add\",\"status\":\"new\",\"recordVersion\":0,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"unauthorized\",\"taskCode\":\"QUESTION\",\"taskIdentifier\":\"Q01\"," +
                "\"questionId\":\"Q01\",\"questionName\":\"Education Loan Purpose\",\"questionText\":\"What is  Loan Purpose ?\"" +
                ",\"questionDescription\":\" Education Purpose helps to decide the further Application Treatment Workflow\"" +
                ",\"answerDisplayType\":\"Radio\",\"manualFactBased\":\"Manual\",\"answerValue\":\"Education Amount\"}";
        return payloads;
    }

    private String getUpdatePayloads() {
        String updatePayloads ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"add\",\"status\":\"new\",\"recordVersion\":0,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"unauthorized\",\"taskCode\":\"QUESTION\",\"taskIdentifier\":\"Q01\"," +
                "\"questionId\":\"Q01\",\"questionName\":\"Education Loan Purpose\",\"questionText\":\"What is  Loan Purpose ?\"" +
                ",\"questionDescription\":\" Education Purpose helps to decide the further Application Treatment Workflow\"" +
                ",\"answerDisplayType\":\"Radio\",\"manualFactBased\":\"Manual\",\"answerValue\":\"Education Amount\"}";
        return updatePayloads;
    }

    public QuestionDTO setAuditFields() {
        QuestionDTO questionDTOAuditField = new QuestionDTO();
        MutationEntity mutationEntity1 = new MutationEntity();
        questionDTOAuditField.setAction(mutationEntity1.getAction());
        questionDTOAuditField.setAuthorized(mutationEntity1.getAuthorized());
        questionDTOAuditField.setRecordVersion(mutationEntity1.getRecordVersion());
        questionDTOAuditField.setStatus(mutationEntity1.getStatus());
        questionDTOAuditField.setLastConfigurationAction(mutationEntity1.getLastConfigurationAction());
        questionDTOAuditField.setCreatedBy(mutationEntity1.getCreatedBy());
        questionDTOAuditField.setCreationTime(mutationEntity1.getCreationTime());
        questionDTOAuditField.setLastUpdatedBy(mutationEntity1.getLastUpdatedBy());
        questionDTOAuditField.setLastUpdatedTime(mutationEntity1.getLastUpdatedTime());
        questionDTOAuditField.setTaskCode(mutationEntity1.getTaskCode());
        questionDTOAuditField.setTaskIdentifier(mutationEntity1.getTaskIdentifier());
        questionDTOAuditField.setQuestionId("Q01");
        questionDTOAuditField.setQuestionName("Education Loan Purpose");
        questionDTOAuditField.setQuestionText("What is  Loan Purpose ?");
        questionDTOAuditField.setQuestionDescription("Education Purpose helps to decide the further Application Treatment Workflow");
        questionDTOAuditField.setAnswerDisplayType("Radio");
        questionDTOAuditField.setManualFactBased("Manual");
        questionDTOAuditField.setRecordVersion(0);
        questionDTOAuditField.setAnswerValue("Education Amount");
        return questionDTOAuditField;
    }

}