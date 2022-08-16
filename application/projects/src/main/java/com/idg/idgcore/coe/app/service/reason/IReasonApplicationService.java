package com.idg.idgcore.coe.app.service.reason;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.*;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.coe.dto.reason.ReasonDTO;

import java.util.List;

public interface IReasonApplicationService extends IBaseApplicationService {
    TransactionStatus processReason (SessionContext sessionContext, ReasonDTO dto)
            throws FatalException, JsonProcessingException;
    void save (ReasonDTO reasonDTO);
    ReasonDTO getReasonByCode (SessionContext sessionContext, ReasonDTO reasonDTO)
            throws FatalException, JsonProcessingException;
    List<ReasonDTO> getReasons (SessionContext sessionContext) throws FatalException;

}
