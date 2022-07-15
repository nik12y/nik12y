package com.idg.idgcore.coe.app.service.memo;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.dto.memo.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.SessionContext;
public interface IMemoApplicationService {

    MemoDTO getMemo(SessionContext sessionContext,
                    MemoDTO memoDTO) throws FatalException;
    void save (MemoDTO memoDTO);

    TransactionStatus processMemo (SessionContext sessionContext, MemoDTO dto) throws
            FatalException,
            JsonProcessingException;
}
