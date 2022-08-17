package com.idg.idgcore.coe.domain.repository.branchtype;

import com.idg.idgcore.coe.domain.entity.branchtype.BranchTypeEntity;
import com.idg.idgcore.coe.domain.entity.riskcode.RiskCodeEntity;
import com.idg.idgcore.coe.domain.repository.riskcode.IRiskCodeRepository;
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

BranchTypeRepositoryTest {

    @Autowired
    IBranchTypeRepository iBranchTypeRepository;

    @Test
    void findAllBranchTypeCodes() {

//        List<RiskCodesEntity> riskCodeEntities01 = new ArrayList<>();
//        riskCodeEntities01.add(new RiskCodesEntity("RCK0001", "RICK CODE CATEGORY", "Mandatory", "Q001",'Y'));

        BranchTypeEntity branchTypeEntities1 = new BranchTypeEntity("CBB", "Corporate Banking Branch", "null","null",0,"null","Y","authorized");

        iBranchTypeRepository.save(branchTypeEntities1);

//        List<QuestionCatDetailsEntity> questionCatDetailsEntities2 = new ArrayList<>();
//        questionCatDetailsEntities2.add(new QuestionCatDetailsEntity(2, "Q003", "Optional", "Q002", "Q002.Yes"));
//
//        QuestionCategoryEntity questionCategoryEntity2 = new QuestionCategoryEntity("SN002", "Education Loan Question", "Mutual", 'Y', 'Y',null,null,
//                questionCatDetailsEntities2, "new", 1, "Y", "authorized");

//        iQuestionCategoryRepository.save(questionCategoryEntity2);
        //when - condition
        List<BranchTypeEntity> branchTypeEntities = iBranchTypeRepository.findAll();
        assertThat(branchTypeEntities.size()).isEqualTo(1);     //hasSize(7);
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
    void findByBranchTypeByCodeId() {

//        List<RiskCodeEntity> riskCodeEntities01 = new ArrayList<>();
//        riskCodeEntities01.add(new QuestionCatDetailsEntity(1, "Q002", "Mandatory", "Q001", "Q002.Yes"));

        BranchTypeEntity riskCodeEntity01 = new BranchTypeEntity("CBB", "Corporate Branch Banking", "null","null",0,"null","Y","authorized" );

        iBranchTypeRepository.save(riskCodeEntity01);
        BranchTypeEntity branchTypeByCode = iBranchTypeRepository.findByBranchTypeCode("CBB");

        assertThat(branchTypeByCode.getBranchTypeCode()).isEqualTo("CBB");

    }

    @Test
    @DisplayName("Junit tes for saveRiskCode")
    void saveRiskCode() {
//        List<RiskCodeEntity> riskCodeEntities02 = new ArrayList<>();
//        questionCatDetailsEntities1.add(new QuestionCatDetailsEntity(4, "Q008", "Optional", "Q007", "Q00.Porches"));

        BranchTypeEntity branchTypeEntity02 = new BranchTypeEntity("RN009", "Business Loan banking","null","null",0,"null","Y","authorized" );

        BranchTypeEntity branchTypeEntity03 = iBranchTypeRepository.save(branchTypeEntity02);
        System.out.println(branchTypeEntity02.getBranchTypeCode());

        assertThat(branchTypeEntity02.getBranchTypeCode()).isEqualTo("RN009");
    }
}
