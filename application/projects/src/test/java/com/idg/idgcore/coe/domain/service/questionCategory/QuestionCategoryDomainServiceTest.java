package com.idg.idgcore.coe.domain.service.questionCategory;

import com.idg.idgcore.coe.app.service.questionCategory.QuestionCategoryApplicationService;
import com.idg.idgcore.coe.domain.assembler.questionCategory.QuestionCategoryAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCatDetailsEntity;
import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCategoryEntity;
import com.idg.idgcore.coe.domain.repository.questionCategory.IQuestionCategoryRepository;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDTO;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDetailsDTO;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.MutationType;
import org.jetbrains.annotations.NotNull;
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

import static com.idg.idgcore.coe.common.Constants.QUESTION_CATEGORY;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionCategoryDomainServiceTest {

    @Mock
    private IQuestionCategoryRepository iQuestionCategoryRepository;

    @InjectMocks
    private QuestionCategoryDomainService questionCategoryDomainService;

    @Mock
    private QuestionCategoryAssembler questionCategoryAssembler;

    @InjectMocks
    private QuestionCategoryApplicationService questionCategoryApplicationService;

    @Mock
    QuestionCategoryEntity questionCategoryEntity;
    SessionContext sessionContext;
    QuestionCategoryDTO questionCategoryDTO;
    MutationEntity mutationEntity;


    @BeforeEach
    void setUp(){
        sessionContext= getValidSessionContext();
        questionCategoryEntity=getQuestionCategoryEntityUnAuth();
        questionCategoryDTO=getQuestionCategoryDToUnAuth();
        mutationEntity=getMutationEntity();
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode")
    void getConfigurationByCodeReturnEntity(){
        List<QuestionCatDetailsEntity> questionCatDetailsEntities1 = new ArrayList<>();
        questionCatDetailsEntities1.add(new QuestionCatDetailsEntity(1, "Q002", "Mandatory", "Q001", "Q002.Yes","new", 1, "Y", "authorized"));

        QuestionCategoryEntity questionCategoryEntity1 = new QuestionCategoryEntity("SN001", "Loan Question", "Collective",
                'Y', 'Y',null,null, questionCatDetailsEntities1, "new", 1, "Y", "authorized");

        List<QuestionCategoryDetailsDTO> questionCategoryDetailsDTOList = new ArrayList<>();
        questionCategoryDetailsDTOList.add(new QuestionCategoryDetailsDTO( "Q002", "Mandatory", "Q001", "Q002.Yes"));

        QuestionCategoryDTO questionCategoryDTO = new QuestionCategoryDTO("SN001", "Loan Question", "Collective",
                true, true,questionCategoryDetailsDTOList);

        given(iQuestionCategoryRepository.findByQuestionCategoryId(questionCategoryDTO.getQuestionCategoryId())).willReturn(questionCategoryEntity1);
        QuestionCategoryEntity questionCategoryEntity = questionCategoryDomainService.getConfigurationByCode(questionCategoryDTO);
        assertThat(questionCategoryEntity).isNotNull();
    }

    @Test
    @DisplayName("Junit Test for getQuestionCategoryById")
    void getQuestionCategoryById(){

        given(iQuestionCategoryRepository.findByQuestionCategoryId(questionCategoryDTO.getQuestionCategoryId())).willReturn(questionCategoryEntity);
        QuestionCategoryEntity questionCategoryById = questionCategoryDomainService.getEntityByIdentifier(questionCategoryEntity.getQuestionCategoryId());
          assertThat(questionCategoryById).isNotNull();
    }

    @Test
    @DisplayName("Junit Test for geAllQuestionCategories empty list")
    void getAllQuestionsCategories(){
        given(iQuestionCategoryRepository.findAll()).willReturn(Collections.emptyList());
        List<QuestionCategoryEntity> questionsCategories = questionCategoryDomainService.getAllEntities();
          assertThat(questionsCategories).isEmpty();
    }

    @Test
    @DisplayName("Junit test for getAllQuestionsCategories with list of Data")
    void getAllQuestionCategoriesWithList(){

        given(iQuestionCategoryRepository.findAll()).willReturn(List.of(questionCategoryEntity));
        List<QuestionCategoryEntity> questionsCategories = questionCategoryDomainService.getAllEntities();
     assertThat(questionsCategories).isNotNull().hasSize(1);
    }

    @Test
    @DisplayName("Junit test for save all QuestionCategories")
    void saveQuestionCategories(){

        given(questionCategoryAssembler.toEntity(questionCategoryDTO)).willReturn(questionCategoryEntity);
        when(iQuestionCategoryRepository.save(any(QuestionCategoryEntity.class))).thenReturn(questionCategoryEntity);
        questionCategoryDomainService.save(questionCategoryDTO);
        assertThat(questionCategoryEntity).isNotNull();

    }

    private MutationEntity getMutationEntity(){
        MutationEntity mutationEntity=new MutationEntity();
        mutationEntity.setTaskCode(QUESTION_CATEGORY);
        mutationEntity.setTaskIdentifier("Q001");
        mutationEntity.setLastConfigurationAction("unauthorized");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setStatus("new");
        mutationEntity.setAction("add");
        return  mutationEntity;
    }


    @NotNull
    private QuestionCategoryEntity getQuestionCategoryEntityUnAuth(){
        List<QuestionCatDetailsEntity> questionCatDetailsEntities1 = new ArrayList<>();
        questionCatDetailsEntities1.add(new QuestionCatDetailsEntity(1, "Q002", "Mandatory", "Q001", "Q002.Yes","new",1,"N","unauthorized"));

        QuestionCategoryEntity questionCategoryEntity=new QuestionCategoryEntity();
        questionCategoryEntity.setQuestionCategoryId("SN001");
        questionCategoryEntity.setQuestionCategoryName("Loan Question");
        questionCategoryEntity.setQuestionDisplay("Collective");
        questionCategoryEntity.setIsShowQuestionCategoryName('Y');
        questionCategoryEntity.setIsEnableDocumentUpload('Y');
        questionCategoryEntity.setQuestionCatDetails(questionCatDetailsEntities1);
        questionCategoryEntity.setStatus("new");
        questionCategoryEntity.setAuthorized("N");
        questionCategoryEntity.setRecordVersion(1);
        questionCategoryEntity.setLastConfigurationAction("unauthorized");
        return questionCategoryEntity;
    }

    @NotNull
    private QuestionCategoryDTO getQuestionCategoryDToUnAuth(){

        List<QuestionCategoryDetailsDTO> questionCategoryDetailsDTOListUnAuth = new ArrayList<>();
        questionCategoryDetailsDTOListUnAuth.add(new QuestionCategoryDetailsDTO( "Q002", "Mandatory", "Q001", "Q002.Yes"));

        QuestionCategoryDTO questionCategoryDTO=new QuestionCategoryDTO();
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


    @NotNull
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
        sessionContext.setTargetUnit("");                       sessionContext.setTaskCode(QUESTION_CATEGORY);
        sessionContext.setTransactionBranch("");                sessionContext.setUserId("");
        sessionContext.setUserTransactionReferenceNumber("");   sessionContext.setValueDate(new Date());
        return  sessionContext;
    }
}
