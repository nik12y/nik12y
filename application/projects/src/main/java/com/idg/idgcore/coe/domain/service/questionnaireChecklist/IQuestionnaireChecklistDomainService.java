package com.idg.idgcore.coe.domain.service.questionnaireChecklist;

import com.idg.idgcore.coe.domain.entity.questionnaireChecklist.*;
import com.idg.idgcore.coe.dto.questionnaireChecklist.*;

import java.util.List;

public interface IQuestionnaireChecklistDomainService {
    QuestionnaireChecklistEntity getConfigurationById (QuestionnaireChecklistDTO dto);
    List<QuestionnaireChecklistEntity> getQuestionnaireChecklists ();
    QuestionnaireChecklistEntity getQuestionnaireChecklistById (String questionChecklistId);
    void save (QuestionnaireChecklistDTO dto);

}
