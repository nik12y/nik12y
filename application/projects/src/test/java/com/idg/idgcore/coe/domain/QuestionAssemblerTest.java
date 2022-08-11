package com.idg.idgcore.coe.domain;

import com.idg.idgcore.coe.domain.assembler.question.QuestionAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.question.QuestionEntity;
import com.idg.idgcore.coe.dto.question.QuestionDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static com.idg.idgcore.coe.common.Constants.QUESTION;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
class QuestionAssemblerTest {

    @Mock
    private QuestionAssembler questionAssembler=new QuestionAssembler();

    @Test
    void setAuditFields() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionId("Q01");
        questionDTO.setQuestionName("Education Loan Purpose");
        questionDTO.setQuestionText("What is  Loan Purpose ?");
        questionDTO.setQuestionDescription("Education Purpose helps to decide the further Application Treatment Workflow");
        questionDTO.setAnswerDisplayType("Radio");
        questionDTO.setManualFactBased("Manual");
        questionDTO.setAnswerValue("Education Amount");
        questionDTO.setAuthorized("Y");
        questionDTO = questionAssembler.setAuditFields(mutationEntity, questionDTO);
        assertEquals("Y", questionDTO.getAuthorized());

    }

   @Test
    @DisplayName("Junit test for convertDTO to Entity")
    void convertDtoToEntity() {
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

        QuestionDTO questionDTO1 = new QuestionDTO();
        questionDTO1.setQuestionId("Q01");
        questionDTO1.setQuestionName("Education Loan Purpose");
        questionDTO1.setQuestionText("What is  Loan Purpose ?");
        questionDTO1.setQuestionDescription("Education Purpose helps to decide the further Application Treatment Workflow");
        questionDTO1.setAnswerDisplayType("Radio");
        questionDTO1.setManualFactBased("Manual");
        questionDTO1.setAnswerValue("Education Amount");
        questionDTO1.setStatus("new");
        questionDTO1.setAuthorized("N");
        questionDTO1.setRecordVersion(0);
        questionDTO1.setTaskCode(QUESTION);
        questionDTO1.setLastConfigurationAction("unauthorized");

     assertEquals(questionEntity,questionAssembler.convertDtoToEntity(questionDTO1));
    }
}
