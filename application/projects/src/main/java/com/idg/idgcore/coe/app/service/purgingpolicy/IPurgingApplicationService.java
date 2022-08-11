package com.idg.idgcore.coe.app.service.purgingpolicy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.purgingpolicy.PurgingDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface IPurgingApplicationService extends IBaseApplicationService {

    TransactionStatus processPurging (SessionContext sessionContext, PurgingDTO purgingDTO)
            throws FatalException, JsonProcessingException;
    void save (PurgingDTO purgingDTO);
    PurgingDTO getPurgingByCode (SessionContext sessionContext, PurgingDTO purgingDTO)
            throws FatalException, JsonProcessingException;
    List<PurgingDTO> getPurgingAll (SessionContext sessionContext) throws FatalException;


}
