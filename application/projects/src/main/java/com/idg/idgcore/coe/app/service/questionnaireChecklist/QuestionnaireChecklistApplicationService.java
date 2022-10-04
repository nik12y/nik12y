package com.idg.idgcore.coe.app.service.questionnaireChecklist;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.questionnaireChecklist.QuestionnaireChecklistAssembler;
import com.idg.idgcore.coe.domain.entity.questionnaireChecklist.QuestionnaireChecklistEntity;
import com.idg.idgcore.coe.domain.service.questionnaireChecklist.QuestionnaireChecklistDomainService;
import com.idg.idgcore.coe.dto.questionnaireChecklist.QuestionnaireChecklistDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.QUE_CHECKLIST;

@Slf4j
@Service ("questionnaireChecklistApplicationService")
public class QuestionnaireChecklistApplicationService extends GenericApplicationService<QuestionnaireChecklistDTO,
        QuestionnaireChecklistEntity, QuestionnaireChecklistDomainService, QuestionnaireChecklistAssembler> {

    protected QuestionnaireChecklistApplicationService() {
        super(QUE_CHECKLIST);
    }

    public String getTaskCode () {
        return QuestionnaireChecklistDTO.builder().build().getTaskCode();
    }

}