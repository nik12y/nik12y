package com.idg.idgcore.coe.domain.repository.question;

import com.idg.idgcore.coe.domain.entity.question.QuestionEntity;
import com.idg.idgcore.coe.domain.entity.question.QuestionEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuestionRepository extends JpaRepository<QuestionEntity, QuestionEntityKey> {

    QuestionEntity findByQuestionId(String questionId);
}
