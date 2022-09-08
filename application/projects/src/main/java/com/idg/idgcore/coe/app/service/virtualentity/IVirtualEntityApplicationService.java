package com.idg.idgcore.coe.app.service.virtualentity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.virtualentity.VirtualEntityDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface IVirtualEntityApplicationService extends IBaseApplicationService {

    TransactionStatus processVirtualEntity(SessionContext sessionContext, VirtualEntityDTO dto) throws FatalException, JsonProcessingException;

    void addUpdateRecord(String data) throws JsonProcessingException;

    CoreEngineBaseDTO getConfigurationByCode(String code);

    void save(VirtualEntityDTO virtualEntityDTO);

    VirtualEntityDTO getVirtualEntityByEntityCode(SessionContext sessionContext, VirtualEntityDTO virtualEntityDTO) throws FatalException, JsonProcessingException;

    List<VirtualEntityDTO> getVirtualEntityAll(SessionContext sessionContext) throws FatalException;

}
