package com.idg.idgcore.coe.app.service.question;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.country.CountryDTO;
import com.idg.idgcore.coe.dto.question.QuestionDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface IQuestionApplicationService extends IBaseApplicationService {
    TransactionStatus processQuestion (SessionContext sessionContext, QuestionDTO questionDTO)
            throws FatalException, JsonProcessingException;
    void save (QuestionDTO questionDTO);
    QuestionDTO getQuestionById (SessionContext sessionContext, QuestionDTO questionDTO)
            throws FatalException, JsonProcessingException;
    List<QuestionDTO> getQuestions (SessionContext sessionContext) throws FatalException;

}
