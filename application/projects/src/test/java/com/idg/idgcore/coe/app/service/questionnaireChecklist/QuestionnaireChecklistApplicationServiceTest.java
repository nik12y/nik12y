
package com.idg.idgcore.coe.app.service.questionnaireChecklist;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.domain.assembler.questionnaireChecklist.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.domain.entity.questionnaireChecklist.*;
import com.idg.idgcore.coe.domain.process.*;
import com.idg.idgcore.coe.domain.service.mutation.*;
import com.idg.idgcore.coe.domain.service.questionnaireChecklist.*;
import com.idg.idgcore.coe.dto.questionnaireChecklist.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import com.idg.idgcore.enumerations.core.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith (MockitoExtension.class)
class QuestionnaireChecklistApplicationServiceTest {
    @InjectMocks
    private QuestionnaireChecklistApplicationService questionnaireChecklistApplicationService;
    @Mock private QuestionnaireChecklistAssembler questionnaireChecklistAssembler;
    @Mock private IQuestionnaireChecklistDomainService questionnaireChecklistDomainService;
    @Mock private IMutationsDomainService mutationsDomainService;
    @Mock private ProcessConfiguration processConfiguration;
    @Autowired
    private MutationEntity mutationEntity;
    private MutationEntity mutationEntity2;
    private SessionContext sessionContext;

    private QuestionnaireChecklistDTO questionnaireChecklistDTOUnAuth;
    private QuestionnaireChecklistDTO questionnaireChecklistDTO;
    private QuestionnaireChecklistEntity questionnaireChecklistEntity;
    private QuestionnaireChecklistEntity questionnaireChecklistEntityUnAut;
    private QuestionnaireChecklistDTO questionnaireChecklistDTOMapper;
    private QuestionnaireChecklistDTO questionnaireChecklistDTO1;

    @BeforeEach
    void setUp () {
        sessionContext = getValidSessionContext();
         questionnaireChecklistEntity = getQuestionnaireChecklistEntity();
        questionnaireChecklistDTOUnAuth = getQuestionnaireChecklistDTOUnAuthorized();
        questionnaireChecklistDTOUnAuth.setAuthorized("N");
        questionnaireChecklistEntityUnAut = getQuestionnaireChecklistEntity();
        questionnaireChecklistEntityUnAut.setAuthorized("N");
        mutationEntity = getMutationEntity();
        questionnaireChecklistDTO = getQuestionnaireChecklistDTO();
        questionnaireChecklistDTOMapper = getQuestionnaireChecklistDTOMapper();
        questionnaireChecklistDTO1 = getQuestionnaireChecklistDTO();
        mutationEntity2 = getMutationEntityJsonError();
    }

    @Test
    @DisplayName ("JUnit for getBankByCode in application service when Authorize")
    void getQuestionnaireChecklistByCodeWithAuthRecord () throws FatalException {

        QuestionnaireChecklistDTO questionnaireChecklistDTOAuth= getQuestionnaireChecklistDTOAuthorized();
        given(questionnaireChecklistDomainService.getQuestionnaireChecklistById(
                questionnaireChecklistDTOAuth.getQuestionChecklistId())).willReturn(
                questionnaireChecklistEntity);
        given(questionnaireChecklistAssembler.convertEntityToDto(
                questionnaireChecklistEntity)).willReturn(
                questionnaireChecklistDTOAuth);
        QuestionnaireChecklistDTO questionnaireChecklistDTO1 = questionnaireChecklistApplicationService.getQuestionnaireChecklistById(
                sessionContext, questionnaireChecklistDTOAuth);
        assertThat(questionnaireChecklistDTO1.getAuthorized()).isEqualTo("Y");
        assertThat(questionnaireChecklistDTOAuth).isNotNull();
        assertThat(questionnaireChecklistDTOAuth.toString()).isNotNull();
        assertThat(questionnaireChecklistDTO.toString()).isNotNull();
    }




  /*  @Test
    @DisplayName ("JUnit for getBankByCode in application service when Not Authorize in catch block")
    void getQuestionnaireChecklistByCodeWhenNotAuthorizeCatchBlock () throws FatalException {
        String payLoadString1 = getpayloadInvalidString();
        given(mutationsDomainService.getConfigurationByCode(
                questionnaireChecklistDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        given(questionnaireChecklistAssembler.setAuditFields(mutationEntity2,
                questionnaireChecklistDTOUnAuth))
                .willReturn(questionnaireChecklistDTOUnAuth);

        Assertions.assertThrows(Exception.class, () -> {
            QuestionnaireChecklistDTO questionnaireChecklistDTO1 = questionnaireChecklistApplicationService.getQuestionnaireChecklistById(
                    sessionContext, questionnaireChecklistDTOUnAuth);
            assertEquals("N", questionnaireChecklistDTO1.getAuthorized());
            assertThat(questionnaireChecklistDTO1).isNotNull();
        });
    }*/
    /*//
    @Test
    @DisplayName("JUnit for getQuestionnaireChecklists in application service for catch block for checker")
    void getQuestionnaireChecklistsCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        //        unauthorizedEntities.setStatus("draft");
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        //        unauthorizedEntities1.setStatus("closed");
        //sessionContext.setRole(new String[] { "" });
        given(mutationsDomainService.getUnauthorizedMutation(
                questionnaireChecklistDTO1.getTaskCode(),AUTHORIZED_N))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
        Assertions.assertThrows(FatalException.class,()-> {
            List<QuestionnaireChecklistDTO> questionnaireChecklistDTO1 = questionnaireChecklistApplicationService.getQuestionnaireChecklists(sessionContext);
            assertThat(questionnaireChecklistDTO1).isNotNull();
        });
    }*/

    /*@Test
    @DisplayName ("JUnit for getQuestionnaireChecklists in application service")
    void getQuestionnaireChecklists () throws JsonProcessingException, FatalException {
        QuestionnaireChecklistEntity questionnaireChecklistEntity = getQuestionnaireChecklistEntityDeleted();
        QuestionnaireChecklistDTO questionnaireChecklistDTO = getQuestionnaireChecklistDTODeleted();
        MutationEntity unauthorizedEntities = getMutationEntityDeleted();
        //String data = "\{\"createdBy":null,"creationTime":null,"lastUpdatedBy":null,"lastUpdatedTime":null,"action":"authorize","status":"active","recordVersion":1,"authorized":"Y","lastConfigurationAction":"authorized","taskCode":"BANKPARAMETER","taskIdentifier":"BB","bankCode":"BB","bankName":"State Bank of India","regulatoryBankCode":"BB","bankConciseName":"SBI","groupBankingCode":"SBI","questionnaireChecklistAddress":{"bankAddress1":"Kanakia Business Park 2","bankAddress2":"JB Nagar 2","bankAddress3":"Andheri East 2","bankAddress4":"Mumbai 2","country":"IN","state":"MH","city":"MUM"},"questionnaireChecklistContactInfo":{"telephoneNo":"022-4156271","faxNo":"022-4156271","emailId":"abc@xyz.com","bankWebsite":"www.bob.com"},"questionnaireChecklistCurrency":{"currencyCode":"INR","currencyName":"INDIAN RUPEES","isDenominationTrackingRequired":false,"currencyOfDenomination":"INR","denominationTrackingCurrency":"INR"},"questionnaireChecklistPreferences":{"weekBeginDay":"Monday","weeklyOff1":"Saturday","weeklyOff2":"Sunday","weeklyOff3":"","financialYearBeginMonth":"April"},"questionnaireChecklistForOdLoan":{"ruleIdForOd":"OD_01","ruleNameForOd":"Regulated rule for Overdraft decision","ruleIdForLoan":"LN_01","ruleNameForLoan":"Regulated rule for Loan decision"}";
        ObjectMapper objectMapper = new ObjectMapper();
        given(questionnaireChecklistDomainService.getQuestionnaireChecklists()).willReturn(
                List.of(questionnaireChecklistEntity));
        given(mutationsDomainService.getUnauthorizedMutation(
                "BANKPARAMETER", AUTHORIZED_N)).willReturn(
                List.of(unauthorizedEntities));
        given(questionnaireChecklistAssembler.setAuditFields(unauthorizedEntities,
                questionnaireChecklistDTO)).willReturn(questionnaireChecklistDTO);
        given(objectMapper.readValue(data, QuestionnaireChecklistDTO.class)).willReturn(questionnaireChecklistDTO);
        given(questionnaireChecklistAssembler.convertEntityToDto(questionnaireChecklistEntity)).willReturn(
                questionnaireChecklistDTO);
        Assertions.assertThrows(BusinessException.class, () -> {
            List<QuestionnaireChecklistDTO> questionnaireChecklistDTOList = questionnaireChecklistApplicationService.getQuestionnaireChecklists(
                    sessionContext);
            assertThat(questionnaireChecklistDTOList).isNotNull();
        });
        List<QuestionnaireChecklistDTO> questionnaireChecklistDTO1 = questionnaireChecklistApplicationService.getQuestionnaireChecklists(
                sessionContext);
        assertThat(questionnaireChecklistDTO1).isNotNull();
    }*/

   /* @Test
    @DisplayName ("JUnit for getQuestionnaireChecklist in application service for catch block")
    void getQuestionnaireChecklistsForException () throws FatalException {
        SessionContext sessionContext1 = null;
        Assertions.assertThrows(Exception.class, () -> {
            List<QuestionnaireChecklistDTO> cities = questionnaireChecklistApplicationService.getQuestionnaireChecklists(
                    sessionContext1);
            assertThat(cities).isNotNull();
        });
    }

    @Test
    @DisplayName ("JUnit for getStates in application service for catch block")
    void getQuestionnaireChecklistsException () throws FatalException {
        SessionContext sessionContext = getInValidSessionContext();
        given(questionnaireChecklistDomainService.getQuestionnaireChecklists()).willReturn(
                List.of(questionnaireChecklistEntity));
        given(mutationsDomainService.getConfigurationByCode(
                questionnaireChecklistDTO.getTaskIdentifier())).willReturn(
                (MutationEntity) List.of(mutationEntity));
        given(questionnaireChecklistAssembler.convertEntityToDto(questionnaireChecklistEntity)).willReturn(
                questionnaireChecklistDTO);
        Assertions.assertThrows(Exception.class, () -> {
            List<QuestionnaireChecklistDTO> cities = questionnaireChecklistApplicationService.getQuestionnaireChecklists(
                    sessionContext);
            assertThat(cities).isNotNull();
        });
    }*/

    @DisplayName ("JUnit test for processQuestionnaireChecklist method")
    @Test
    void processQuestionnaireChecklistWithNew () throws JsonProcessingException, FatalException {
        QuestionnaireChecklistDTO questionnaireChecklistDTOAuth= getQuestionnaireChecklistDTOAuthorized();

        doNothing().when(processConfiguration).process(questionnaireChecklistDTOAuth);
        questionnaireChecklistApplicationService.processQuestionnaireChecklist(sessionContext,
                questionnaireChecklistDTOAuth);
        verify(processConfiguration, times(1)).process(questionnaireChecklistDTOAuth);

    }

    @DisplayName ("JUnit test for addUpdateRecord method")
//    @Test
    void addUpdateRecord () throws JsonProcessingException, FatalException {
        String payloadStr = getpayloadValidString();
        QuestionnaireChecklistDTO questionnaireChecklistDTO = getQuestionnaireChecklistDTOForSave();
        doNothing().when(questionnaireChecklistDomainService).save(questionnaireChecklistDTO);
        questionnaireChecklistApplicationService.save(questionnaireChecklistDTO);
        questionnaireChecklistApplicationService.addUpdateRecord(payloadStr);
        verify(questionnaireChecklistDomainService, times(1)).save(questionnaireChecklistDTO);
    }

    /**
     * New test cases
     */
    @Test
    @DisplayName ("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest () {
        String code = questionnaireChecklistDTO.getQuestionChecklistId();
        given(questionnaireChecklistDomainService.getQuestionnaireChecklistById(
                code)).willReturn(questionnaireChecklistEntity);
        questionnaireChecklistApplicationService.getConfigurationByCode(code);
        assertThat(questionnaireChecklistEntity).isNotNull();
    }

  /*  @Test
    @DisplayName ("JUnit for processQuestionnaireChecklist in application service for Try Block")
    void processQuestionnaireChecklistForTryBlock ()
            throws JsonProcessingException, FatalException {
        doNothing().when(processConfiguration).process(questionnaireChecklistDTO);
        questionnaireChecklistApplicationService.processQuestionnaireChecklist(sessionContext,
                questionnaireChecklistDTO);
        verify(processConfiguration, times(1)).process(questionnaireChecklistDTO);
    }*/

//    @Test
    @DisplayName ("JUnit for processQuestionnaireChecklist in application service for Catch Block")
    void processQuestionnaireChecklistForCatchBlock () throws FatalException {
        SessionContext sessionContext2 = null;
        Assertions.assertThrows(Exception.class, () -> {
            questionnaireChecklistApplicationService.processQuestionnaireChecklist(sessionContext2,
                    questionnaireChecklistDTO);
            assertThat(questionnaireChecklistDTO).descriptionText();
        });
    }

    /**
     * Negative Test Cases
     */
    @Test
    @DisplayName ("JUnit for getBankByCode in application service when Authorize for Negative")
    void getQuestionnaireChecklistBankByCodeIsAuthorizeForNegative () throws FatalException {
        given(questionnaireChecklistDomainService.getQuestionnaireChecklistById(
                questionnaireChecklistDTO.getQuestionChecklistId())).willReturn(questionnaireChecklistEntity);
        given(questionnaireChecklistAssembler.convertEntityToDto(
                questionnaireChecklistEntity)).willReturn(questionnaireChecklistDTO);
        QuestionnaireChecklistDTO questionnaireChecklistDTO1 = questionnaireChecklistApplicationService.getQuestionnaireChecklistById(
                sessionContext, questionnaireChecklistDTO);
        assertNotEquals("N", questionnaireChecklistDTO1.getAuthorized());
        assertThat(questionnaireChecklistDTO).isNotNull();
    }

    /*@Test
    @DisplayName("JUnit for getBankByCode in application service when UnAuthorize fetch no Record from database")
    void getQuestionnaireChecklistBankByCodeNotAuthorizeNull() throws FatalException {
        QuestionnaireChecklistDTO questionnaireChecklistDTO=null;
        QuestionnaireChecklistDTO questionnaireChecklistDTOEx=new QuestionnaireChecklistDTO();
        questionnaireChecklistDTOEx.setBankCode("BB");
        questionnaireChecklistDTOEx.setAuthorized("N");
        given(mutationsDomainService.getConfigurationByCode(questionnaireChecklistDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);
        QuestionnaireChecklistDTO questionnaireChecklistDTO1 = questionnaireChecklistApplicationService.getBankByCode(sessionContext, questionnaireChecklistDTOEx);
        assertNotEquals("Y",questionnaireChecklistDTO1.getAuthorized());
        assertNull(questionnaireChecklistDTOnull);
    }*/

    @Test
    @DisplayName ("JUnit for getBankByCode in application service check Parameter not null")
    void getQuestionnaireChecklistBankByCodeIsAuthorizeCheckParameter () throws FatalException {
        QuestionnaireChecklistDTO questionnaireChecklistDTOEx = getQuestionnaireChecklistDTO();
        questionnaireChecklistDTOEx.setQuestionChecklistId("BB");
        questionnaireChecklistDTOEx.setAuthorized("Y");
        given(questionnaireChecklistDomainService.getQuestionnaireChecklistById(
                questionnaireChecklistDTOEx.getQuestionChecklistId())).willReturn(
                questionnaireChecklistEntity);
        given(questionnaireChecklistAssembler.convertEntityToDto(
                questionnaireChecklistEntity)).willReturn(null);
        QuestionnaireChecklistDTO questionnaireChecklistDTO  = questionnaireChecklistApplicationService.getQuestionnaireChecklistById(
                sessionContext, questionnaireChecklistDTOEx);
        assertThat(questionnaireChecklistDTO).isNull();
        assertThat(questionnaireChecklistDTOEx.getQuestionChecklistId()).isNotBlank();
        assertThat(questionnaireChecklistDTOEx.getAuthorized()).isNotBlank();
    }

    /*@Test
    @DisplayName("JUnit for getBankByCode in application service when Not Authorize in try block for Negative when getAuthorized unexpected is Y")
    void getQuestionnaireChecklistBankByCodeWhenNotAuthorizeTryBlockForNegative() throws JsonProcessingException, FatalException {
        given(mutationsDomainService.getConfigurationByCode(questionnaireChecklistDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);
        QuestionnaireChecklistDTO questionnaireChecklistDTO1 = questionnaireChecklistApplicationService.getBankByCode(sessionContext,questionnaireChecklistDTOMapper);
        assertNotEquals("Y",questionnaireChecklistDTO1.getAuthorized());
        assertThat(questionnaireChecklistDTO1).isNotNull();
    }*/

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder().bankCode("02")
                .defaultBranchCode("02")
                .internalTransactionReferenceNumber(null).userTransactionReferenceNumber(null)
                .externalTransactionReferenceNumber(null).targetUnit("").postingDate(new Date())
                .valueDate(new Date()).transactionBranch("").userId("TestMaker").channel("")
                .taskCode("QUESTION_CKLIST").localDateTime(new Date())
                .originalTransactionReferenceNumber("").externalBatchNumber(1L)
                .customAttributes("").serviceInvocationModeType(ServiceInvocationModeType.Regular)
                .allTargetUnitsSelected(true).userLocal("").originatingModuleCode("").mutationType(MutationType.ADDITION)
                .role(new String[] { "maker" }).build();
        return sessionContext;
    }

    private SessionContext getInValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder().bankCode("").defaultBranchCode("")
                .internalTransactionReferenceNumber(null).userTransactionReferenceNumber("")
                .externalTransactionReferenceNumber(null).targetUnit("").postingDate(new Date())
                .valueDate(new Date()).transactionBranch("").userId("TestMaker").channel("")
                .taskCode("QUESTION_CKLIST").originalTransactionReferenceNumber("").externalBatchNumber(1L)
                .customAttributes("").serviceInvocationModeType(null)
                .allTargetUnitsSelected(true).userLocal("").originatingModuleCode("")
                .role(null).build();
        return sessionContext;
    }

    private QuestionnaireChecklistDTO getQuestionnaireChecklistDTOAuthorized () {
        QuestionnaireChecklistDTO questionnaireChecklistDTOMapper = new QuestionnaireChecklistDTO();

        QuestionnaireChecklistDisplayDTO checklistDisplayDTO = new QuestionnaireChecklistDisplayDTO();
        QuestionnaireChecklistDetailsCategoryDTO checklistDetailsCategoryDTO = new QuestionnaireChecklistDetailsCategoryDTO();
        /**
         * QuestionnaireChecklistDisplayDTO
         */
        checklistDisplayDTO.setRuleId("ruleid");
        /**
         * QuestionnaireChecklistDetailsCategoryDTO
         */
        List<QuestionnaireChecklistDetailsCategoryDTO> detailsDTOList = new ArrayList<>();
        QuestionnaireChecklistDetailsCategoryDTO entity1 = new QuestionnaireChecklistDetailsCategoryDTO(
                1L,  "test");
        QuestionnaireChecklistDetailsCategoryDTO entity2 = new QuestionnaireChecklistDetailsCategoryDTO();
        entity2.setQuestionChecklistDetailsId(2L);
        entity2.setQuestionCategoryId("test1");
        detailsDTOList.add(entity1);
        detailsDTOList.add(entity2);
        checklistDetailsCategoryDTO.setQuestionChecklistDetailsId(1L);
        /**
         * QuestionnaireChecklistDTO
         */
        questionnaireChecklistDTOMapper.setQuestionChecklistId("BB");
        questionnaireChecklistDTOMapper.setQuestionChecklistName("testbb");
        questionnaireChecklistDTOMapper.setQuestionCategory("tt");
        questionnaireChecklistDTOMapper.setQuestionnaireChecklistDisplay(
                checklistDisplayDTO);
        questionnaireChecklistDTOMapper.setQuestionnaireChecklistDetailsCategory(entity1);

        questionnaireChecklistDTOMapper.setTaskIdentifier("BB");
        questionnaireChecklistDTOMapper.setAction("authorize");
        questionnaireChecklistDTOMapper.setStatus("active");
        questionnaireChecklistDTOMapper.setRecordVersion(1);
        questionnaireChecklistDTOMapper.setLastConfigurationAction("authorized");
        questionnaireChecklistDTOMapper.setAuthorized("Y");
        questionnaireChecklistDTOMapper.setTaskCode("QUESTION_CKLIST");
        questionnaireChecklistDTOMapper.setQuestionnaireChecklistDetailsCategory(entity1);


        return questionnaireChecklistDTOMapper;
    }

    private QuestionnaireChecklistDTO getQuestionnaireChecklistDTOForSave () {
        QuestionnaireChecklistDTO questionnaireChecklistDTOMapper = new QuestionnaireChecklistDTO();
        QuestionnaireChecklistDisplayDTO checklistDisplayDTO = new QuestionnaireChecklistDisplayDTO();
        QuestionnaireChecklistDetailsCategoryDTO checklistDetailsCategoryDTO = new QuestionnaireChecklistDetailsCategoryDTO();
        /**
         * QuestionnaireChecklistDisplayDTO
         */
        checklistDisplayDTO.setRuleId("ruleid");
        /**
         * QuestionnaireChecklistDetailsCategoryDTO
         */
        List<QuestionnaireChecklistDetailsCategoryDTO> detailsDTOList = new ArrayList<>();
        QuestionnaireChecklistDetailsCategoryDTO entity1 = new QuestionnaireChecklistDetailsCategoryDTO(
                1L,  "test");
        QuestionnaireChecklistDetailsCategoryDTO entity2 = new QuestionnaireChecklistDetailsCategoryDTO();
        entity2.setQuestionChecklistDetailsId(2L);
        entity2.setQuestionCategoryId("test1");
        detailsDTOList.add(entity1);
        detailsDTOList.add(entity2);
        checklistDetailsCategoryDTO.setQuestionChecklistDetailsId(1L);
        /**
         * QuestionnaireChecklistDTO
         */
        questionnaireChecklistDTOMapper.setQuestionChecklistId("BB");
        questionnaireChecklistDTOMapper.setQuestionChecklistName("testbb");
        questionnaireChecklistDTOMapper.setQuestionCategory("tt");
        questionnaireChecklistDTOMapper.setQuestionnaireChecklistDisplay(
                checklistDisplayDTO);
        questionnaireChecklistDTOMapper.setQuestionnaireChecklistDetailsCategory(entity1);

        questionnaireChecklistDTOMapper.setTaskIdentifier("BB");
        questionnaireChecklistDTOMapper.setAction("authorize");
        questionnaireChecklistDTOMapper.setStatus("active");
        questionnaireChecklistDTOMapper.setRecordVersion(1);
        questionnaireChecklistDTOMapper.setAuthorized("N");
        questionnaireChecklistDTOMapper.setLastConfigurationAction("authorized");
        questionnaireChecklistDTOMapper.setTaskCode("QUESTION_CKLIST");
        questionnaireChecklistDTOMapper.setQuestionnaireChecklistDetailsCategory(entity1);

        return questionnaireChecklistDTOMapper;
    }

    private QuestionnaireChecklistDTO getQuestionnaireChecklistDTOUnAuthorized () {
        QuestionnaireChecklistDTO questionnaireChecklistDTOMapper = new QuestionnaireChecklistDTO();
        QuestionnaireChecklistDisplayDTO checklistDisplayDTO = new QuestionnaireChecklistDisplayDTO();
        QuestionnaireChecklistDetailsCategoryDTO checklistDetailsCategoryDTO = new QuestionnaireChecklistDetailsCategoryDTO();
        /**
         * QuestionnaireChecklistDisplayDTO
         */
        checklistDisplayDTO.setRuleId("ruleid");
        /**
         * QuestionnaireChecklistDetailsCategoryDTO
         */
        List<QuestionnaireChecklistDetailsCategoryDTO> detailsDTOList = new ArrayList<>();
        QuestionnaireChecklistDetailsCategoryDTO entity1 = new QuestionnaireChecklistDetailsCategoryDTO(
                1L,  "test");
        QuestionnaireChecklistDetailsCategoryDTO entity2 = new QuestionnaireChecklistDetailsCategoryDTO();
        entity2.setQuestionChecklistDetailsId(2L);
        entity2.setQuestionCategoryId("test1");
        detailsDTOList.add(entity1);
        detailsDTOList.add(entity2);
        checklistDetailsCategoryDTO.setQuestionChecklistDetailsId(1L);
        /**
         * QuestionnaireChecklistDTO
         */
        questionnaireChecklistDTOMapper.setQuestionChecklistId("BB");
        questionnaireChecklistDTOMapper.setQuestionChecklistName("testbb");
        questionnaireChecklistDTOMapper.setQuestionCategory("tt");
        questionnaireChecklistDTOMapper.setStatus("deleted");
        questionnaireChecklistDTOMapper.setRecordVersion(1);
        questionnaireChecklistDTOMapper.setQuestionnaireChecklistDisplay(
                checklistDisplayDTO);
        questionnaireChecklistDTOMapper.setQuestionnaireChecklistDetailsCategory(entity1);

        questionnaireChecklistDTOMapper.setTaskCode("BB");
        questionnaireChecklistDTOMapper.setStatus("DELETED");
        questionnaireChecklistDTOMapper.setRecordVersion(1);


        questionnaireChecklistDTOMapper.setTaskCode("QUESTION_CKLIST");
        questionnaireChecklistDTOMapper.setTaskIdentifier("BB");

        questionnaireChecklistDTOMapper.setQuestionnaireChecklistDetailsCategory(entity1);

        questionnaireChecklistDTOMapper.setAuthorized("N");
        return questionnaireChecklistDTOMapper;
    }

    private QuestionnaireChecklistEntity getQuestionnaireChecklistEntity () {
        QuestionnaireChecklistEntity questionnaireChecklistEntity = new QuestionnaireChecklistEntity();
        QuestionnaireChecklistDisplayEntity checklistDisplayEntity = new QuestionnaireChecklistDisplayEntity();
        QuestionnaireChecklistDetailsCategoryEntity checklistDetailsCategoryEntity = new QuestionnaireChecklistDetailsCategoryEntity();
        /**
         * QuestionnaireChecklistDisplayEntity
         */
        checklistDisplayEntity.setRuleId("ruleid");
        /**
         * QuestionnaireChecklistDetailsCategoryEntity
         */
        List<QuestionnaireChecklistDetailsCategoryEntity> detailsEntityList = new ArrayList<>();
        QuestionnaireChecklistDetailsCategoryEntity entity1 = new QuestionnaireChecklistDetailsCategoryEntity(
                "test");


        /**
         * QuestionnaireChecklistEntity
         */
        questionnaireChecklistEntity.setQuestionChecklistId("BB");
        questionnaireChecklistEntity.setQuestionChecklistName("testbb");
        questionnaireChecklistEntity.setQuestionCategory("tt");
        questionnaireChecklistEntity.setStatus("deleted");
        questionnaireChecklistEntity.setRecordVersion(1);
        questionnaireChecklistEntity.setChecklistDisplayEntity(
                checklistDisplayEntity);
        questionnaireChecklistEntity.setQuestionnaireChecklistDetailsCategory(entity1);
        questionnaireChecklistEntity.setAuthorized("Y");
        return questionnaireChecklistEntity;
    }

    private QuestionnaireChecklistDTO getQuestionnaireChecklistDTOMapper () {
        QuestionnaireChecklistDTO questionnaireChecklistDTOMapper = new QuestionnaireChecklistDTO();
        QuestionnaireChecklistDisplayDTO checklistDisplayDTO = new QuestionnaireChecklistDisplayDTO();
        QuestionnaireChecklistDetailsCategoryDTO checklistDetailsCategoryDTO = new QuestionnaireChecklistDetailsCategoryDTO();
        /**
         * QuestionnaireChecklistDisplayDTO
         */
        checklistDisplayDTO.setRuleId("ruleid");
        /**
         * QuestionnaireChecklistDetailsCategoryDTO
         */
        List<QuestionnaireChecklistDetailsCategoryDTO> detailsDTOList = new ArrayList<>();
        QuestionnaireChecklistDetailsCategoryDTO entity1 = new QuestionnaireChecklistDetailsCategoryDTO(
                1L,  "test");
        QuestionnaireChecklistDetailsCategoryDTO entity2 = new QuestionnaireChecklistDetailsCategoryDTO();
        entity2.setQuestionChecklistDetailsId(2L);
        entity2.setQuestionCategoryId("test1");
        detailsDTOList.add(entity1);
        detailsDTOList.add(entity2);
        checklistDetailsCategoryDTO.setQuestionChecklistDetailsId(1L);
        /**
         * QuestionnaireChecklistDTO
         */
        questionnaireChecklistDTOMapper.setQuestionChecklistId("BB");
        questionnaireChecklistDTOMapper.setQuestionChecklistName("testbb");
        questionnaireChecklistDTOMapper.setQuestionCategory("tt");
        questionnaireChecklistDTOMapper.setStatus("deleted");
        questionnaireChecklistDTOMapper.setRecordVersion(1);
        questionnaireChecklistDTOMapper.setQuestionnaireChecklistDisplay(
                checklistDisplayDTO);
        questionnaireChecklistDTOMapper.setQuestionnaireChecklistDetailsCategory(entity1);



        questionnaireChecklistDTOMapper.setAuthorized("N");
        questionnaireChecklistDTOMapper.setTaskCode("QUESTION_CKLIST");
        questionnaireChecklistDTOMapper.setTaskIdentifier("BB");

        return questionnaireChecklistDTOMapper;
    }

    private QuestionnaireChecklistDTO getQuestionnaireChecklistDTO () {
        QuestionnaireChecklistDTO questionnaireChecklistDTO = new QuestionnaireChecklistDTO();
        QuestionnaireChecklistDisplayDTO checklistDisplayDTO = new QuestionnaireChecklistDisplayDTO();
        QuestionnaireChecklistDetailsCategoryDTO checklistDetailsCategoryDTO = new QuestionnaireChecklistDetailsCategoryDTO();
        /**
         * QuestionnaireChecklistDisplayDTO
         */
        checklistDisplayDTO.setRuleId("ruleid");
        /**
         * QuestionnaireChecklistDetailsCategoryDTO
         */
        List<QuestionnaireChecklistDetailsCategoryDTO> detailsDTOList = new ArrayList<>();
        QuestionnaireChecklistDetailsCategoryDTO entity1 = new QuestionnaireChecklistDetailsCategoryDTO(
                1L,  "test");
        QuestionnaireChecklistDetailsCategoryDTO entity2 = new QuestionnaireChecklistDetailsCategoryDTO();
        entity2.setQuestionChecklistDetailsId(2L);
        entity2.setQuestionCategoryId("test1");
        detailsDTOList.add(entity1);
        detailsDTOList.add(entity2);
        checklistDetailsCategoryDTO.setQuestionChecklistDetailsId(1L);
        /**
         * QuestionnaireChecklistDTO
         */
        questionnaireChecklistDTO.setQuestionChecklistId("BB");
        questionnaireChecklistDTO.setQuestionChecklistName("testbb");
        questionnaireChecklistDTO.setQuestionCategory("tt");
        questionnaireChecklistDTO.setStatus("deleted");
        questionnaireChecklistDTO.setRecordVersion(1);
        questionnaireChecklistDTO.setQuestionnaireChecklistDisplay(
                checklistDisplayDTO);
        questionnaireChecklistDTO.setQuestionnaireChecklistDetailsCategory(entity1);

        questionnaireChecklistDTO.setAuthorized("Y");
        questionnaireChecklistDTO.setTaskCode("BB");
        questionnaireChecklistDTO.setStatus("DELETED");
        questionnaireChecklistDTO.setRecordVersion(1);

        return questionnaireChecklistDTO;
    }

    private MutationEntity getMutationEntity () {
        String payLoadString =
                getpayloadValidString();
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("BB");
        mutationEntity.setTaskCode("QUESTION_CKLIST");
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
                "{\"action\":\"authorize\",\"status\":\"active\","
                        + "\"recordVersion\":1,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\","
                        + "\"taskCode\":\"QUESTION_CKLIST\",\"taskIdentifier\":\"BB\","
                        + "\"questionChecklistId\":\"BB\",\"questionChecklistName\":\"testbb\","
                        + "\"questionCategory\":\"tt\","
                        + "\"questionnaireChecklistDetailsCategory\":[{\"createdBy\":null,"
                        + "\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":null,"
                        + "\"status\":null,\"recordVersion\":null,\"authorized\":null,"
                        + "\"lastConfigurationAction\":null,\"taskCode\":null,\"taskIdentifier\":null,"
                        + "\"questionChecklistDetailsId\":null,\"questionCategoryId\":\"test\"},"
                        + "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,"
                        + "\"lastUpdatedTime\":null,\"action\":null,\"status\":null,\"recordVersion\":null,"
                        + "\"authorized\":null,\"lastConfigurationAction\":null,\"taskCode\":null,"
                        + "\"taskIdentifier\":null,\"questionChecklistDetailsId\":null,\"questionCategoryId\":\"test1\"}],"
                        + "\"questionnaireChecklistDisplay\":{\"ruleId\":\"ruleid\"}}";
        return payLoadString;
    }

    private String getpayloadInvalidString () {
        String payLoadString =
                "{\"action\":\"authorize\",\"status\":\"active\","
                        + "\"recordVersion\":invalid,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\","
                        + "\"taskCode\":\"QUESTION_CKLIST\",\"taskIdentifier\":\"BB\","
                        + "\"questionChecklistId\":\"BB\",\"questionChecklistName\":\"testbb\","
                        + "\"questionCategory\":\"tt\","
                        + "\"questionnaireChecklistDetailsCategory\":[{\"createdBy\":null,"
                        + "\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":null,"
                        + "\"status\":null,\"recordVersion\":null,\"authorized\":null,"
                        + "\"lastConfigurationAction\":null,\"taskCode\":null,\"taskIdentifier\":null,"
                        + "\"questionChecklistDetailsId\":null,\"questionCategoryId\":\"test\"},"
                        + "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,"
                        + "\"lastUpdatedTime\":null,\"action\":null,\"status\":null,\"recordVersion\":null,"
                        + "\"authorized\":null,\"lastConfigurationAction\":null,\"taskCode\":null,"
                        + "\"taskIdentifier\":null,\"questionChecklistDetailsId\":null,\"questionCategoryId\":\"test1\"}],"
                        + "\"questionnaireChecklistDisplay\":{\"ruleId\":\"ruleid\"}}";
        return payLoadString;
    }

    private MutationEntity getMutationEntityError () {
        String payLoadString =
                getpayloadInvalidString();
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("BB");
        mutationEntity.setTaskCode("QUESTION_CKLIST");
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("active");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("authorize");
        mutationEntity.setLastConfigurationAction("unauthorized");
        return mutationEntity;
    }

    private QuestionnaireChecklistDTO getQuestionnaireChecklistDTODeleted () {
        QuestionnaireChecklistDTO questionnaireChecklistDtoDeleted = new QuestionnaireChecklistDTO();

        questionnaireChecklistDtoDeleted.setAction("");
        questionnaireChecklistDtoDeleted.setStatus("deleted");
        questionnaireChecklistDtoDeleted.setTaskCode("QUESTION_CKLIST");
        questionnaireChecklistDtoDeleted.setTaskIdentifier("BB");
        questionnaireChecklistDtoDeleted.setRecordVersion(1);
        return questionnaireChecklistDtoDeleted;
    }

    private MutationEntity getMutationEntityDeleted () {
        String payLoadString =
                getpayloadValidString();
        MutationEntity unauthorizedEntities = new MutationEntity();
        unauthorizedEntities.setTaskCode("QUESTION_CKLIST");
        unauthorizedEntities.setTaskIdentifier("BB");
        unauthorizedEntities.setPayload(new Payload(payLoadString));
        unauthorizedEntities.setAuthorized("N");
        unauthorizedEntities.setStatus("DELETED");
        unauthorizedEntities.setRecordVersion(1);
        return unauthorizedEntities;
    }

    private QuestionnaireChecklistEntity getQuestionnaireChecklistEntityDeleted () {
        QuestionnaireChecklistEntity questionnaireChecklistEntityDeleted = new QuestionnaireChecklistEntity();
        QuestionnaireChecklistDisplayEntity checklistDisplayEntity = new QuestionnaireChecklistDisplayEntity();
        QuestionnaireChecklistDetailsCategoryEntity checklistDetailsCategoryEntity = new QuestionnaireChecklistDetailsCategoryEntity();
        /**
         * QuestionnaireChecklistDisplayEntity
         */
        checklistDisplayEntity.setRuleId("ruleid");
        /**
         * QuestionnaireChecklistDetailsCategoryEntity
         */
        List<QuestionnaireChecklistDetailsCategoryEntity> detailsEntityList = new ArrayList<>();
        QuestionnaireChecklistDetailsCategoryEntity entity1 = new QuestionnaireChecklistDetailsCategoryEntity(
 "test");
        /**
         * QuestionnaireChecklistEntity
         */
        questionnaireChecklistEntityDeleted.setQuestionChecklistId("BB");
        questionnaireChecklistEntityDeleted.setQuestionChecklistName("testbb");
        questionnaireChecklistEntityDeleted.setQuestionCategory("tt");
        questionnaireChecklistEntityDeleted.setStatus("deleted");
        questionnaireChecklistEntityDeleted.setRecordVersion(1);
        questionnaireChecklistEntityDeleted.setChecklistDisplayEntity(
                checklistDisplayEntity);
        questionnaireChecklistEntityDeleted.setQuestionnaireChecklistDetailsCategory(entity1);
        return questionnaireChecklistEntityDeleted;
    }

    private MutationEntity getMutationEntityJsonError () {
        String payLoadString1 = "{\"action\":\"authorize\",\"status\":\"active\","
                + "\"recordVersion\":1,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\","
                + "\"taskCode\":\"QUESTION_CKLIST\",\"taskIdentifier\":\"BB\","
                + "\"questionChecklistId\":\"BB\",\"questionChecklistName\":\"testbb\","
                + "\"questionCategory\":\"tt\","
                + "\"questionnaireChecklistDetailsCategory\":[{\"createdBy\":null,"
                + "\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":null,"
                + "\"status\":null,\"recordVersion\":null,\"authorized\":null,"
                + "\"lastConfigurationAction\":null,\"taskCode\":null,\"taskIdentifier\":null,"
                + "\"questionChecklistDetailsId\":null,\"questionCategoryId\":\"test\"},"
                + "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,"
                + "\"lastUpdatedTime\":null,\"action\":null,\"status\":null,\"recordVersion\":null,"
                + "\"authorized\":null,\"lastConfigurationAction\":null,\"taskCode\":null,"
                + "\"taskIdentifier\":null,\"questionChecklistDetailsId\":null,\"questionCategoryId\":\"test1\"}],"
                + "\"questionnaireChecklistDisplay\":{\"ruleId\":\"ruleid\"}}";
        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("BB");
        mutationEntity2.setTaskCode("QUESTION_CKLIST");
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("draft");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("draft");
        return mutationEntity2;
    }

}


