package com.idg.idgcore.coe.domain.repository.riskcategory;

import com.idg.idgcore.coe.domain.entity.branchtype.BranchTypeEntity;
import com.idg.idgcore.coe.domain.entity.riskcategory.RiskCategoryEntity;
import com.idg.idgcore.coe.domain.repository.branchtype.IBranchTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class

RiskCategoryRepositoryTest {

    @Autowired
    IRiskCategoryRepository iRiskCategoryRepository;

    @Test
    void findAllRiskCategoryCodes() {

//        List<RiskCodesEntity> riskCodeEntities01 = new ArrayList<>();
//        riskCodeEntities01.add(new RiskCodesEntity("RCK0001", "RICK CODE CATEGORY", "Mandatory", "Q001",'Y'));

        RiskCategoryEntity riskcategoryEntities1 = new RiskCategoryEntity("RCKP001", "RISK CATEGORY001", "KEY PERSON RISK","null","null",0,"null","null","null");

        iRiskCategoryRepository.save(riskcategoryEntities1);

//        List<QuestionCatDetailsEntity> questionCatDetailsEntities2 = new ArrayList<>();
//        questionCatDetailsEntities2.add(new QuestionCatDetailsEntity(2, "Q003", "Optional", "Q002", "Q002.Yes"));
//
//        QuestionCategoryEntity questionCategoryEntity2 = new QuestionCategoryEntity("SN002", "Education Loan Question", "Mutual", 'Y', 'Y',null,null,
//                questionCatDetailsEntities2, "new", 1, "Y", "authorized");

//        iQuestionCategoryRepository.save(questionCategoryEntity2);
        //when - condition
        List<RiskCategoryEntity> riskCategoryEntityList = iRiskCategoryRepository.findAll();
        assertThat(riskCategoryEntityList.size()).isEqualTo(1);     //hasSize(7);
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
    void findByRiskCategoryByCode() {

//        List<RiskCodeEntity> riskCodeEntities01 = new ArrayList<>();
//        riskCodeEntities01.add(new QuestionCatDetailsEntity(1, "Q002", "Mandatory", "Q001", "Q002.Yes"));

        RiskCategoryEntity riskcategory01 = new RiskCategoryEntity("RCKP001", "RISK CATEGORY001", "KEY PERSON RISK","null","null",0,"null","null","null");

        iRiskCategoryRepository.save(riskcategory01);
        RiskCategoryEntity riskCategoryByCode = iRiskCategoryRepository.findByRiskCategoryCode("RCKP001");

        assertThat(riskCategoryByCode.getRiskCategoryCode()).isEqualTo("RCKP001");

    }

    @Test
    @DisplayName("Junit tes for saveRiskCode")
    void saveRiskCode() {
//        List<RiskCodeEntity> riskCodeEntities02 = new ArrayList<>();
//        questionCatDetailsEntities1.add(new QuestionCatDetailsEntity(4, "Q008", "Optional", "Q007", "Q00.Porches"));

        RiskCategoryEntity riskcategoryentity02= new RiskCategoryEntity("RCKP001", "RISK CATEGORY001", "KEY PERSON RISK","null","null",0,"null","null","null");

        RiskCategoryEntity riskcategoryEntity03 = iRiskCategoryRepository.save(riskcategoryentity02);
        System.out.println(riskcategoryentity02.getRiskCategoryCode());

        assertThat(riskcategoryentity02.getRiskCategoryCode()).isEqualTo("RCKP001");
    }
}
