package com.idg.idgcore.coe.app.service.verificationcategory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.verificationcategory.AppVerCategoryConfigDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface IAppVerCategoryApplicationService extends IBaseApplicationService {

        TransactionStatus processAppVerCategoryConfigs (SessionContext sessionContext, AppVerCategoryConfigDTO dto) throws FatalException,
                JsonProcessingException;
        void save (AppVerCategoryConfigDTO appVerCategoryConfigDTO);
        AppVerCategoryConfigDTO getAppVerCategoryConfigByID (SessionContext sessionContext, AppVerCategoryConfigDTO appVerCategoryConfigDTO)
                throws FatalException, JsonProcessingException;
        List<AppVerCategoryConfigDTO> getAppVerCategoryConfigs (SessionContext sessionContext)
                throws FatalException;


}
