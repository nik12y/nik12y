package com.idg.idgcore.coe.app.service.questionnaireChecklist;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.base.*;
import com.idg.idgcore.coe.dto.questionnaireChecklist.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;

import java.util.*;

public interface IQuestionnaireChecklistApplicationService extends IBaseApplicationService {
    TransactionStatus processQuestionnaireChecklist (SessionContext sessionContext, QuestionnaireChecklistDTO dto)
            throws FatalException, JsonProcessingException;
    void save (QuestionnaireChecklistDTO dto);
    QuestionnaireChecklistDTO getQuestionnaireChecklistById (SessionContext sessionContext, QuestionnaireChecklistDTO dto)
            throws FatalException, JsonProcessingException;
    List<QuestionnaireChecklistDTO> getQuestionnaireChecklists (SessionContext sessionContext) throws FatalException;

}

