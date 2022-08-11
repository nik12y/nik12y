package com.idg.idgcore.coe.app.service.riskcode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.riskcode.RiskCodeDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface IRiskCodeApplicationService extends IBaseApplicationService {

    TransactionStatus processRiskCode(SessionContext sessionContext, RiskCodeDTO riskcodedto) throws
            FatalException, JsonProcessingException;

    void save(RiskCodeDTO riskcodedto);

    RiskCodeDTO getRiskCodeByCode(SessionContext sessionContext, RiskCodeDTO riskcodedto) throws FatalException, JsonProcessingException;

    List<RiskCodeDTO> getRiskCodes(SessionContext sessionContext) throws FatalException;

}

    /*NIKHIL CHANGES
TransactionStatus processRiskCategory (SessionContext sessionContext, RiskCategoryDTO dto)
        throws FatalException, JsonProcessingException;
    void save (RiskCategoryDTO countryDTO);
    RiskCategoryDTO getRiskCategoryBYCode (SessionContext sessionContext, RiskCategoryDTO riskCategoryDTO)
            throws FatalException, JsonProcessingException;
    List<RiskCategoryDTO> getRiskCategories (SessionContext sessionContext) throws FatalException;

}
*/