package com.idg.idgcore.coe.app.service.appvertype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.appvertype.AppVerTypeDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface IAppVerTypeApplicationService extends IBaseApplicationService {

    TransactionStatus processAppVerType (SessionContext sessionContext, AppVerTypeDTO appVerTypeDTO) throws FatalException,
            JsonProcessingException;

    void save (AppVerTypeDTO appVerTypeDTO);

    AppVerTypeDTO getAppVerTypeByID (SessionContext sessionContext, AppVerTypeDTO appVerTypeDTO)
            throws FatalException, JsonProcessingException;

    List<AppVerTypeDTO> getAppVerTypes (SessionContext sessionContext)
            throws FatalException;
}
