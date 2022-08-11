package com.idg.idgcore.coe.domain;

import com.idg.idgcore.coe.domain.entity.question.QuestionEntity;
import com.idg.idgcore.coe.domain.repository.question.IQuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QuestionRepositoryTest {

    @Autowired
    IQuestionRepository iQuestionRepository;

    @DisplayName("jUnit test to get all question ")
    @Test
    void findAllQuestion() {
        QuestionEntity questionEntity = new QuestionEntity("Q01", "Education Loan Purpose", "What is  Loan Purpose ?", "Education Purpose helps to decide the further Application Treatment Workflow",
                "Radio", "Manual", "Education Amount",null,null, "new", 1, "Y", "authorized");
        iQuestionRepository.save(questionEntity);

        QuestionEntity questionEntity1 = new QuestionEntity("Q02", "Business Loan Purpose", "What is  Loan Purpose ?", "Business Purpose helps to decide the further Application Treatment Workflow",
                "Radio", "Fact", "Business Amount",null,null, "new", 1, "Y", "authorized");
        iQuestionRepository.save(questionEntity1);

        //when - condition
        List<QuestionEntity> questionEntityList = iQuestionRepository.findAll();

        //then - output
        assertThat(questionEntityList).isNotNull();
//        assertThat(questionEntityList.size()).isEqualTo(2);

    }

    @DisplayName("jUnit test to get bank by code ")
    @Test
    void findByQuestionId() {
        QuestionEntity questionEntity = new QuestionEntity("Q01", "Education Loan Purpose", "What is  Loan Purpose ?", "Education Purpose helps to decide the further Application Treatment Workflow",
                "Radio", "Manual", "Education Amount",null,null, "draft", 0, "Y", "draft");
        iQuestionRepository.save(questionEntity);

        QuestionEntity questionEntity1 = iQuestionRepository.findByQuestionId("Q01");

        assertThat(questionEntity1.getQuestionName().equals("Education Loan Purpose"));

    }

    @Test
    void saveBankTest() {
        QuestionEntity questionEntity = new QuestionEntity("Q01", "Education Loan Purpose", "What is  Loan Purpose ?", "Education Purpose helps to decide the further Application Treatment Workflow",
                "Radio", "Manual", "Education Amount",null,null, "draft", 0, "Y", "draft");

        QuestionEntity questionEntity1 = iQuestionRepository.save(questionEntity);

        assertThat(questionEntity1).isNotNull();
        assertThat(questionEntity1.getQuestionId()).isEqualTo("Q01");


    }
}
