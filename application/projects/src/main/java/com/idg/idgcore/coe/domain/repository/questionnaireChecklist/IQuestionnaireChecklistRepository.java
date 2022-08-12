package com.idg.idgcore.coe.domain.repository.questionnaireChecklist;


import com.idg.idgcore.coe.domain.entity.questionnaireChecklist.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface IQuestionnaireChecklistRepository extends JpaRepository<QuestionnaireChecklistEntity,QuestionnaireChecklistEntityKey> {
    QuestionnaireChecklistEntity findByQuestionChecklistId (String questionChecklistId );
}
