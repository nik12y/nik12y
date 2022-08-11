package com.idg.idgcore.coe.domain;


import com.idg.idgcore.coe.domain.assembler.questionCategory.QuestionCategoryAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCatDetailsEntity;
import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCategoryEntity;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDTO;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDetailsDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestionCategoryAssemblerTest {

    @Autowired
    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private QuestionCategoryAssembler questionCategoryAssembler = new QuestionCategoryAssembler();


    @Test
    void setAuditFields() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
        QuestionCategoryEntity questionCategoryEntity = new QuestionCategoryEntity();
        List<QuestionCatDetailsEntity> questionCatDetailsList = questionCategoryEntity.getQuestionCatDetails();

        Type listType = new TypeToken<List<QuestionCategoryDetailsDTO>>() {
        }.getType();
        List<QuestionCategoryDetailsDTO> questionCategoryDetailsDTOList = modelMapper.map(questionCatDetailsList, listType);
        questionCategoryDetailsDTOList.add(new QuestionCategoryDetailsDTO("Q002", "Mandatory", "Q001", "Q002.Yes"));

        QuestionCategoryDTO questionCategoryDTO = new QuestionCategoryDTO();
        questionCategoryDTO.setQuestionCategoryId("SN001");
        questionCategoryDTO.setQuestionCategoryName("Loan Question");
        questionCategoryDTO.setIsShowQuestionCategoryName(true);
        questionCategoryDTO.setIsEnableDocumentUpload(true);
        questionCategoryDTO.setQuestionCategoryDetails(questionCategoryDetailsDTOList);

        questionCategoryDTO = questionCategoryAssembler.setAuditFields(mutationEntity, questionCategoryDTO);
        assertEquals("Y", questionCategoryDTO.getAuthorized());
    }

    @Test
    @DisplayName("Junit test for Conversion of Entity To DTO")
    void convertDToToEntity() {
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

        QuestionCategoryDetailsDTO questionCatDetailsEntities2 = new QuestionCategoryDetailsDTO();
        questionCatDetailsEntities2.setQuestionId("Q002");
        questionCatDetailsEntities2.setQuestionNature("Mandatory");
        questionCatDetailsEntities2.setParentQuestionId("Q001");
        questionCatDetailsEntities2.setDisplayCondition("Q002.Yes");

        QuestionCategoryDTO questionCategoryDTO = new QuestionCategoryDTO();
        questionCategoryDTO.setQuestionCategoryId("SN001");
        questionCategoryDTO.setQuestionCategoryName("Loan Question");
        questionCategoryDTO.setQuestionDisplay("Collective");
        questionCategoryDTO.setIsShowQuestionCategoryName(true);
        questionCategoryDTO.setIsEnableDocumentUpload(true);
        questionCategoryDTO.setQuestionCategoryDetails(List.of(questionCatDetailsEntities2));
        questionCategoryDTO.setStatus("new");
        questionCategoryDTO.setAuthorized("N");
        questionCategoryDTO.setRecordVersion(1);
        questionCategoryDTO.setLastConfigurationAction("unauthorized");

        assertEquals(questionCategoryEntity2, questionCategoryAssembler.convertDtoTOEntity(questionCategoryDTO));
    }

    @Test
    @DisplayName("Junit test for getChar value and getBoolean value")
    void testValues() {
        QuestionCategoryEntity questionCategoryEntity2 = new QuestionCategoryEntity();
        questionCategoryEntity2.setIsShowQuestionCategoryName('Y');
        questionCategoryEntity2.setIsEnableDocumentUpload('Y');
        QuestionCategoryDTO questionCategoryDTO = new QuestionCategoryDTO();
        questionCategoryDTO.setIsEnableDocumentUpload(true);
        questionCategoryDTO.setIsShowQuestionCategoryName(true);

        assertEquals('Y', questionCategoryAssembler.getCharValueFromBoolean(questionCategoryDTO.getIsShowQuestionCategoryName()));
        assertEquals('Y', questionCategoryAssembler.getCharValueFromBoolean(questionCategoryDTO.getIsEnableDocumentUpload()));
        assertTrue(questionCategoryAssembler.getBooleanValueFromChar(questionCategoryEntity2.getIsShowQuestionCategoryName()));
        assertTrue(questionCategoryAssembler.getBooleanValueFromChar(questionCategoryEntity2.getIsEnableDocumentUpload()));
    }


}
