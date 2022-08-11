package com.idg.idgcore.coe.domain.service.question;

import com.idg.idgcore.coe.domain.entity.question.QuestionEntity;
import com.idg.idgcore.coe.dto.question.QuestionDTO;

import java.util.List;

public interface IQuestionDomainService {
    QuestionEntity getConfigurationByCode (QuestionDTO questionDTO);
    List<QuestionEntity> getQuestions ();
    QuestionEntity getQuestionById(String questionId);
    void save (QuestionDTO questionDTO);
}
