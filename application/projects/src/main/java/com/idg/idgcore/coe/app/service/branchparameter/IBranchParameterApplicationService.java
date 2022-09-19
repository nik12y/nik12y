package com.idg.idgcore.coe.app.service.branchparameter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.*;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.coe.dto.branchparameter.BranchParameterDTO;

import java.util.*;

public interface IBranchParameterApplicationService extends IBaseApplicationService {
    TransactionStatus processBranchParameter (SessionContext sessionContext, BranchParameterDTO dto)
            throws FatalException,
            JsonProcessingException;
    void save (BranchParameterDTO bankParameterDTO);
    BranchParameterDTO getBranchParameterByBranchCode (SessionContext sessionContext,
            BranchParameterDTO branchParameterDTO)
            throws FatalException, JsonProcessingException;
    List<BranchParameterDTO> getBranchParameters (SessionContext sessionContext)
            throws FatalException;
    List<BranchParameterDTO> searchBranchParameter (SessionContext sessionContext,
            BranchParameterDTO branchParameterDTO) throws FatalException, JsonProcessingException;

}
