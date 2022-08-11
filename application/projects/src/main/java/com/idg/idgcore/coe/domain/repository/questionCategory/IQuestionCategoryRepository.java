package com.idg.idgcore.coe.domain.repository.questionCategory;

import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCategoryEntity;
import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCategoryEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuestionCategoryRepository extends JpaRepository<QuestionCategoryEntity, QuestionCategoryEntityKey> {

    QuestionCategoryEntity findByQuestionCategoryId(String questionCategoryId);

}
