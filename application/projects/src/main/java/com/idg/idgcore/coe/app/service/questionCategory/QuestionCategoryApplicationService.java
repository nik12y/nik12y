package com.idg.idgcore.coe.app.service.questionCategory;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.questionCategory.QuestionCategoryAssembler;
import com.idg.idgcore.coe.domain.entity.questionCategory.QuestionCategoryEntity;
import com.idg.idgcore.coe.domain.service.questionCategory.QuestionCategoryDomainService;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.QUESTION_CATEGORY;

@Slf4j
@Service("questionCategoryApplicationService")
public class QuestionCategoryApplicationService extends GenericApplicationService<QuestionCategoryDTO,
        QuestionCategoryEntity, QuestionCategoryDomainService, QuestionCategoryAssembler> {

    protected QuestionCategoryApplicationService() {
        super(QUESTION_CATEGORY);
    }

    public String getTaskCode () {
        return QuestionCategoryDTO.builder().build().getTaskCode();
    }

}
