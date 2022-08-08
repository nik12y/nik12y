package com.idg.idgcore.coe.app.service.categorychecklist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.categorychecklist.AppVerCatChecklistPolicyDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

    public interface IAppVerCatChecklistPolicyApplicationService extends IBaseApplicationService {
        TransactionStatus processAppVerCatChecklistPolicys (SessionContext sessionContext, AppVerCatChecklistPolicyDTO dto) throws FatalException,
                JsonProcessingException;
        void save (AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO);
        AppVerCatChecklistPolicyDTO getAppVerCatChecklistPolicyById(SessionContext sessionContext, AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO)
                throws FatalException, JsonProcessingException;
        List<AppVerCatChecklistPolicyDTO> getAppVerCatChecklistPolicys (SessionContext sessionContext)
                throws FatalException;
    }
