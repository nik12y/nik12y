package com.idg.idgcore.coe.app.service.branchSystemDate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.branchSystemDate.BranchSystemDateDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface IBranchSystemApplicationService extends IBaseApplicationService {

    TransactionStatus processBranchSystemDate (SessionContext sessionContext, BranchSystemDateDTO branchSystemDateDTO)
            throws FatalException, JsonProcessingException;
    void save (BranchSystemDateDTO branchSystemDateDTO);
    BranchSystemDateDTO getBranchSystemDateByCode (SessionContext sessionContext, BranchSystemDateDTO branchSystemDateDTO)
            throws FatalException, JsonProcessingException;

    List<BranchSystemDateDTO> getBranchSystemDateAll (SessionContext sessionContext) throws FatalException;

}
