package com.idg.idgcore.coe.domain;

import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCatDetailsEntity;
import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCategoryEntity;
import com.idg.idgcore.coe.domain.repository.questionCategory.IQuestionCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QuestionCategoryRepositoryTest {

    @Autowired
    IQuestionCategoryRepository iQuestionCategoryRepository;

//    @Test
    void findAllQuestionsCategories() {

        List<QuestionCatDetailsEntity> questionCatDetailsEntities1 = new ArrayList<>();
        questionCatDetailsEntities1.add(new QuestionCatDetailsEntity(1, "Q002", "Mandatory", "Q001", "Q002.Yes"));

        QuestionCategoryEntity questionCategoryEntity1 = new QuestionCategoryEntity("SN001", "Loan Question", "Collective",
                'Y', 'Y',null,null, questionCatDetailsEntities1, "new", 1, "Y", "authorized");

        iQuestionCategoryRepository.save(questionCategoryEntity1);

        List<QuestionCatDetailsEntity> questionCatDetailsEntities2 = new ArrayList<>();
        questionCatDetailsEntities2.add(new QuestionCatDetailsEntity(2, "Q003", "Optional", "Q002", "Q002.Yes"));

        QuestionCategoryEntity questionCategoryEntity2 = new QuestionCategoryEntity("SN002", "Education Loan Question", "Mutual", 'Y', 'Y',null,null,
                questionCatDetailsEntities2, "new", 1, "Y", "authorized");

        iQuestionCategoryRepository.save(questionCategoryEntity2);
        //when - condition
        List<QuestionCategoryEntity> questionCategoryEntityList = iQuestionCategoryRepository.findAll();
        assertThat(questionCategoryEntityList).isNotNull().hasSize(7);
        /*
     questionCategoryEntity1.setQuestionCategoryId("SN001");
        questionCategoryEntity1.setQuestionCategoryName("Loan Question");
        questionCategoryEntity1.setIsShowQuestionCategoryName('Y');
        questionCategoryEntity1.setIsEnableDocumentUpload('Y');
        questionCategoryEntity1.setStatus("new");
        questionCategoryEntity1.setRecordVersion(1);
        questionCategoryEntity1.setAuthorized("Y");
        questionCategoryEntity1.setLastConfigurationAction("authorized");
       // questionCategoryEntity1.setQuestionCatDetails(questionCatDetailsEntities);*/
    }

//    @Test
    @DisplayName("jUnit test to get questionCategory by Id ")
    void findByQuestionCategoryId() {

        List<QuestionCatDetailsEntity> questionCatDetailsEntities1 = new ArrayList<>();
        questionCatDetailsEntities1.add(new QuestionCatDetailsEntity(1, "Q002", "Mandatory", "Q001", "Q002.Yes"));

        QuestionCategoryEntity questionCategoryEntity1 = new QuestionCategoryEntity("SN001", "Loan Question", "Collective",
                'Y', 'Y',null,null, questionCatDetailsEntities1, "new", 1, "Y", "authorized");

        iQuestionCategoryRepository.save(questionCategoryEntity1);
        QuestionCategoryEntity questionCategoryId = iQuestionCategoryRepository.findByQuestionCategoryId("SN001");

        assertThat(questionCategoryId.getQuestionCategoryName()).isEqualTo("Loan Question");

    }

//    @Test
    @DisplayName("Junit tes for saveQuestionCategory")
    void saveQuestionCategory() {
        List<QuestionCatDetailsEntity> questionCatDetailsEntities1 = new ArrayList<>();
        questionCatDetailsEntities1.add(new QuestionCatDetailsEntity(4, "Q008", "Optional", "Q007", "Q00.Porches"));

        QuestionCategoryEntity questionCategoryEntity1 = new QuestionCategoryEntity("SN009", "Business Loan Question", "Manual",
                'Y', 'Y',null,null, questionCatDetailsEntities1, "draft", 0, "N", "draft");

        QuestionCategoryEntity questionCategoryEntity = iQuestionCategoryRepository.save(questionCategoryEntity1);

        assertThat(questionCategoryEntity.getQuestionCategoryName()).isEqualTo("Business Loan Question");
    }
}
