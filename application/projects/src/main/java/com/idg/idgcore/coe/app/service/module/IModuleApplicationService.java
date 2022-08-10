package com.idg.idgcore.coe.app.service.module;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.*;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;

import com.idg.idgcore.coe.dto.module.ModuleDTO;

import java.util.List;

public interface IModuleApplicationService extends IBaseApplicationService {
    TransactionStatus processModule (SessionContext sessionContext, ModuleDTO dto) throws FatalException,
            JsonProcessingException;
    void save (ModuleDTO moduleDTO);
    ModuleDTO getModuleByCode (SessionContext sessionContext, ModuleDTO moduleDTO)
            throws FatalException, JsonProcessingException;
    List<ModuleDTO> getModules (SessionContext sessionContext)
            throws FatalException;

}
