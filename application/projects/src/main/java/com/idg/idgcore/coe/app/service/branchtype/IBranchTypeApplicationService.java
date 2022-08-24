package com.idg.idgcore.coe.app.service.branchtype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.branchtype.BranchTypeDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;
public interface IBranchTypeApplicationService extends IBaseApplicationService {
    TransactionStatus processBranchType (SessionContext sessionContext, BranchTypeDTO dto)
            throws FatalException, JsonProcessingException;
    void save (BranchTypeDTO branchTypeDTO);
    BranchTypeDTO getBranchTypeByCode (SessionContext sessionContext, BranchTypeDTO branchTypeDTO)
            throws FatalException, JsonProcessingException;
    List<BranchTypeDTO> getBranches (SessionContext sessionContext) throws FatalException;

}