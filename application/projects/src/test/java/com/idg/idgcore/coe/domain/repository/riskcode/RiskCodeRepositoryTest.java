package com.idg.idgcore.coe.domain.repository.riskcode;

import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCatDetailsEntity;
import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCategoryEntity;
import com.idg.idgcore.coe.domain.entity.riskcode.RiskCodeEntity;
import com.idg.idgcore.coe.domain.repository.questionCategory.IQuestionCategoryRepository;
import org.junit.jupiter.api.Disabled;
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
class

RiskCodeRepositoryTest {

    @Autowired
    IRiskCodeRepository iRiskCodeRepository;

    @Test
    void findAllRiskCodes() {

//        List<RiskCodesEntity> riskCodeEntities01 = new ArrayList<>();
//        riskCodeEntities01.add(new RiskCodesEntity("RCK0001", "RICK CODE CATEGORY", "Mandatory", "Q001",'Y'));

        RiskCodeEntity riskCodeEntities1 = new RiskCodeEntity("RCK0001", "Risk Code Category", "Testing Purpose",
                "null", 'Y',null,null,"null,",1, "new", "Y", "authorized");

        iRiskCodeRepository.save(riskCodeEntities1);

//        List<QuestionCatDetailsEntity> questionCatDetailsEntities2 = new ArrayList<>();
//        questionCatDetailsEntities2.add(new QuestionCatDetailsEntity(2, "Q003", "Optional", "Q002", "Q002.Yes"));
//
//        QuestionCategoryEntity questionCategoryEntity2 = new QuestionCategoryEntity("SN002", "Education Loan Question", "Mutual", 'Y', 'Y',null,null,
//                questionCatDetailsEntities2, "new", 1, "Y", "authorized");

//        iQuestionCategoryRepository.save(questionCategoryEntity2);
        //when - condition
        List<RiskCodeEntity> riskCodeEntities = iRiskCodeRepository.findAll();
        assertThat(riskCodeEntities.size()).isEqualTo(3);     //hasSize(7);
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

    @Test
    @DisplayName("jUnit test to get Risk Code By Code")
    void findByRiskCodeByCodeId() {

//        List<RiskCodeEntity> riskCodeEntities01 = new ArrayList<>();
//        riskCodeEntities01.add(new QuestionCatDetailsEntity(1, "Q002", "Mandatory", "Q001", "Q002.Yes"));

        RiskCodeEntity riskCodeEntity01 = new RiskCodeEntity("RCK0010", "RISK MANGEMENT CODE", "TEsting Phase",
                "RCK0010", 'Y',null,null, null,1, "new", "Y", "authorized");

        iRiskCodeRepository.save(riskCodeEntity01);
        RiskCodeEntity riskCodeByCode = iRiskCodeRepository.findByRiskCode("RCK0010");

        assertThat(riskCodeByCode.getRiskCodeName()).isEqualTo("RISK MANGEMENT CODE");

    }

    @Test
    @DisplayName("Junit tes for saveRiskCode")
    void saveRiskCode() {
//        List<RiskCodeEntity> riskCodeEntities02 = new ArrayList<>();
//        questionCatDetailsEntities1.add(new QuestionCatDetailsEntity(4, "Q008", "Optional", "Q007", "Q00.Porches"));

        RiskCodeEntity riskCodeEntity02 = new RiskCodeEntity("RN009", "Business Loan banking", "Business Loan",
                "RCK0010", 'Y',null,null, null, 0, "draft", "N", "draft");

        RiskCodeEntity riskCodeEntity03 = iRiskCodeRepository.save(riskCodeEntity02);
        System.out.println(riskCodeEntity02.getRiskCodeName());

        assertThat(riskCodeEntity02.getRiskCodeName()).isEqualTo("Business Loan banking");
    }
}
