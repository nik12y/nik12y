package com.idg.idgcore.coe.app.service.capt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.*;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;

import com.idg.idgcore.coe.dto.capt.CaptDTO;

import java.util.List;

public interface ICaptApplicationService extends IBaseApplicationService {
    TransactionStatus processCapt (SessionContext sessionContext, CaptDTO dto) throws FatalException,
            JsonProcessingException;
    void save (CaptDTO captDTO);
    CaptDTO getCaptByCode (SessionContext sessionContext, CaptDTO captDTO)
            throws FatalException, JsonProcessingException;
    List<CaptDTO> getCaptAll (SessionContext sessionContext)
            throws FatalException;

}
