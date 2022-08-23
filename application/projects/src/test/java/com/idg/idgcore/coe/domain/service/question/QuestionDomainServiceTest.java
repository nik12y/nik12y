package com.idg.idgcore.coe.domain.service.question;

import com.idg.idgcore.coe.app.service.question.QuestionApplicationService;
import com.idg.idgcore.coe.domain.assembler.question.QuestionAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.question.QuestionEntity;
import com.idg.idgcore.coe.domain.repository.question.IQuestionRepository;
import com.idg.idgcore.coe.domain.service.question.QuestionDomainService;
import com.idg.idgcore.coe.dto.question.QuestionDTO;
import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.MutationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.QUESTION;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class QuestionDomainServiceTest {
    @Mock
    private IQuestionRepository iQuestionRepository;

    @InjectMocks
    private QuestionDomainService questionDomainService;

    @Mock
    private QuestionAssembler questionAssembler;

    @Mock
    MutationEntity mutationEntity;
    SessionContext sessionContext;
    QuestionEntity questionEntity;
    QuestionEntity questionEntityNe;

    QuestionDTO questionDTO;
    String payloads;
    QuestionEntity questionEntityAuth;
    QuestionDTO questionDTOAuth;
    QuestionDTO questionDTONe;

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
        questionDTO = getQuestionDTO();
        payloads = getPayloads();
        questionEntityAuth = getQuestionEntityAuth();
        questionDTOAuth = getQuestionDTOAuth();
        questionDTONe = getQuestionDTONe();
        questionEntityNe = getQuestionEntityNe();
        AbstractAuditableDomainEntity abstractAuditableDomainEntity = getAbstractAuditableDomainEntity();
    }

    @Test
    @DisplayName("Junit test for getQuestionById")
    void getQuestionByIdReturnEntity() {

        given(iQuestionRepository.findByQuestionId("Q01")).willReturn(questionEntity);
        QuestionEntity questionById = questionDomainService.getQuestionById(questionEntity.getQuestionId());
        assertThat(questionById).isNotNull();
    }

    @Test
    @DisplayName("Junit test for getConfigurationByCode")
    void getConfigurationByCodeReturnsQuestionEntity(){
        questionEntity =new QuestionEntity();
        questionEntity.setQuestionId("Q01");
        questionEntity.setQuestionName("Education Loan Purpose");
        QuestionDTO  questionDTO=new QuestionDTO();
        questionDTO.setQuestionId("Q01");
        questionDTO.setQuestionName("Education Loan Purpose");
        if(questionEntity.equals(questionDTO)){
            throw new RuntimeException("It should be Capital");
        }
        given(iQuestionRepository.findByQuestionId("Q01")).willReturn(questionEntity);

        QuestionEntity question = questionDomainService.getConfigurationByCode(questionDTO);
        assertThat(question).isNotNull();
    }

    @Test
    @DisplayName("Junit test for getQuestionById")
    void getQuestionByIdReturnQuestionEntityObject(){
        questionEntity =new QuestionEntity();
        questionEntity.setQuestionId("Q01");
        questionEntity.setQuestionName("Education Loan Purpose");

        given(iQuestionRepository.findByQuestionId("Q01")).willReturn(questionEntity);
        QuestionEntity groupBankByCode1 = questionDomainService.getQuestionById(questionEntity.getQuestionId());
        assertThat(groupBankByCode1).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getGroupBanks for negative scenario")
    void getQuestionEmptyReturnQuestionEntityObject() {
        questionEntity = new QuestionEntity();
        questionEntity.setQuestionId("Q01");
        questionEntity.setQuestionName("Education Loan Purpose");

        given(iQuestionRepository.findAll()).willReturn(Collections.emptyList());
        List<QuestionEntity> questionEntityList = questionDomainService.getQuestions();
        assertThat(questionEntityList).isEmpty();
        assertThat(questionEntityList.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("JUnit test for getQuestions list")
    void getQuestionsReturnListOfGroupBanks(){
        questionEntity = new QuestionEntity();
        questionEntity.setQuestionId("Q01");
        questionEntity.setQuestionName("Education Loan Purpose");

        given(iQuestionRepository.findAll()).willReturn(List.of(questionEntity));
        List<QuestionEntity> questions = questionDomainService.getQuestions();
        assertThat(questions).isNotNull();
        assertThat(questions.size()).isEqualTo(1);
    }
    @Test
    @DisplayName("JUnit test for save Question")
    void saveQuestionReturnTrue() {
        questionEntity = new QuestionEntity();
        questionEntity.setQuestionId("Q01");
        questionEntity.setQuestionName("Education Loan Purpose");

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionId("Q01");
        questionDTO.setQuestionName("Education Loan Purpose");
        given(questionAssembler.convertDtoToEntity(questionDTO)).willReturn(questionEntity);
        when(iQuestionRepository.save(any(QuestionEntity.class))).thenReturn(questionEntity);
        questionDomainService.save(questionDTO);
    assertThat(questionEntity).isNotNull();
    }
    private MutationEntity  getMutationEntity(){
        MutationEntity mutationEntity=new MutationEntity();
        mutationEntity.setTaskCode(QUESTION);
        mutationEntity.setTaskIdentifier("Q01");
        mutationEntity.setLastConfigurationAction("unauthorized");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setStatus("new");
        mutationEntity.setAction("add");
        return  mutationEntity;
    }

    private List<QuestionDTO> getQuestionDTOsList() {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        QuestionDTO questionDTO1 = new QuestionDTO("Q01","Education Loan Purpose","What is  Loan Purpose ?","Education Purpose helps to decide the further Application Treatment Workflow",
                "Radio","Manual","Education Amount");
        questionDTOList.add(questionDTO1);
       /* QuestionDTO groupBankingDTO2 = new QuestionDTO("BDO", "Bank Of Badoda");
        QuestionDTO groupBankingDTO3 = new QuestionDTO("BNP", "BNP Paribas");

        groupBankingDTOList.add(groupBankingDTO2);
        groupBankingDTOList.add(groupBankingDTO3);*/
        return questionDTOList;
    }

    private SessionContext getValidSessionContext(){
        SessionContext sessionContext=new SessionContext();
        sessionContext.setBankCode("");
        //   sessionContext.setAccessibleTargetUnits();
        sessionContext.setChannel("");                           sessionContext.setDefaultBranchCode("");
        sessionContext.setCustomAttributes("");                  sessionContext.setAllTargetUnitsSelected(false);
        // sessionContext.setExternalBatchNumber();
        sessionContext.setExternalTransactionReferenceNumber(""); sessionContext.setInternalTransactionReferenceNumber("");
        sessionContext.setLocalDateTime(new Date());              sessionContext.setMutationType(MutationType.ADDITION);
        //  sessionContext.setAccessibleTargetUnits("");
        sessionContext.setDefaultBranchCode("");                sessionContext.getOriginalTransactionReferenceNumber();
        sessionContext.setOriginatingModuleCode("");            sessionContext.setRole(new String[] {"maker"});
        sessionContext.setServiceInvocationModeType(Regular);   sessionContext.setPostingDate(new Date());
        sessionContext.setTargetUnit("");                       sessionContext.setTaskCode(QUESTION);
        sessionContext.setTransactionBranch("");                sessionContext.setUserId("");
        sessionContext.setUserTransactionReferenceNumber("");   sessionContext.setValueDate(new Date());
        return  sessionContext;
    }

    private QuestionEntity getQuestionEntity(){
        QuestionEntity questionEntity=new QuestionEntity();
       questionEntity.setQuestionId("Q01");
       questionEntity.setQuestionName("Education Loan Purpose");
       questionEntity.setQuestionText("What is  Loan Purpose ?");
       questionEntity.setQuestionDescription("Education Purpose helps to decide the further Application Treatment Workflow");
       questionEntity.setAnswerDisplayType("Radio");
       questionEntity.setManualFactBased("Manual");
       questionEntity.setAnswerValue("Education Amount");
        questionEntity.setStatus("new");
        questionEntity.setAuthorized("N");
        questionEntity.setRecordVersion(1);
        questionEntity.setLastConfigurationAction("unauthorized");
        return  questionEntity;
    }

    private QuestionEntity getQuestionEntityNe(){
        QuestionEntity questionEntityNe=new QuestionEntity();
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
        return  questionEntityNe;
    }

    private  QuestionDTO getQuestionDTO(){
        QuestionDTO questionDTO=new QuestionDTO();
        questionDTO.setQuestionId("Q01");
        questionDTO.setQuestionName("Education Loan Purpose");
        questionDTO.setQuestionText("What is  Loan Purpose ?");
        questionDTO.setQuestionDescription("Education Purpose helps to decide the further Application Treatment Workflow");
        questionDTO.setAnswerDisplayType("Radio");
        questionDTO.setManualFactBased("Manual");
        questionDTO.setAnswerValue("Education Amount");
        questionDTO.setStatus("new");
        questionDTO.setAuthorized("N");
        questionDTO.setRecordVersion(1);
        questionDTO.setTaskCode(QUESTION);
        questionDTO.setLastConfigurationAction("unauthorized");
        return questionDTO;
    }
    private  QuestionDTO getQuestionDTONe(){
        QuestionDTO questionDTONe=new QuestionDTO();
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
    private QuestionEntity getQuestionEntityAuth(){
        QuestionEntity questionEntityAuth=new QuestionEntity();
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

    private  QuestionDTO getQuestionDTOAuth(){
        QuestionDTO questionDTOAuth=new QuestionDTO();
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

    private String getPayloads(){
        String payloads= "{\"action\":\"add\",\"status\":\"new\",\"recordVersion\":1,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"QUESTION\",\"taskIdentifier\":\"Q01\"," +
                "\"questionId\":\"Q01\",\"questionName\":\"Education Loan Purpose\",\"questionText\":\"What is  Loan Purpose ?\"" +
                ",\"questionDescription\":\" Education Purpose helps to decide the further Application Treatment Workflow\"" +
                ",\"answerDisplayType\":\"Radio\",\"manualFactBased\":\"Manual\",\"answerValue\":\"Education Amount\"}";
        return payloads;
    }
}
