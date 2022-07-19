package com.idg.idgcore.coe.app.service.state;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.state.StateDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface IStateApplicationService extends IBaseApplicationService {
    TransactionStatus processState (SessionContext sessionContext, StateDTO dto) throws FatalException,
            JsonProcessingException;
    void save (StateDTO stateDTO);
    StateDTO getStateByCode (SessionContext sessionContext, StateDTO stateDTO)
            throws FatalException, JsonProcessingException;
    List<StateDTO> getStates (SessionContext sessionContext)
            throws FatalException;

}