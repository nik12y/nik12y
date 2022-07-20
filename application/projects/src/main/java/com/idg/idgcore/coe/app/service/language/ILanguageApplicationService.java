package com.idg.idgcore.coe.app.service.language;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.language.LanguageDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface ILanguageApplicationService extends IBaseApplicationService {
    TransactionStatus processLanguage (SessionContext sessionContext, LanguageDTO dto) throws FatalException,
            JsonProcessingException;
    void save (LanguageDTO languageDTO);
    LanguageDTO getLanguageByCode (SessionContext sessionContext, LanguageDTO languageDTO)
            throws FatalException, JsonProcessingException;
    List<LanguageDTO> getLanguages (SessionContext sessionContext)
            throws FatalException;
}
