package com.idg.idgcore.coe.app.service.generic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.*;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;

import java.util.List;

public interface IGenericApplicationService < T_DTO > extends IBaseApplicationService {
    TransactionStatus process(SessionContext sessionContext, T_DTO dto) throws FatalException, JsonProcessingException;
    void save (T_DTO valDTO);
    T_DTO getByIdentifier(SessionContext sessionContext, T_DTO valDTO) throws FatalException, JsonProcessingException;
    List<T_DTO> getAll(SessionContext sessionContext) throws FatalException;
}
