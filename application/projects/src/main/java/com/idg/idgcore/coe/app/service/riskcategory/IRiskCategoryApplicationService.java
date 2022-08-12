package com.idg.idgcore.coe.app.service.riskcategory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.riskcategory.RiskCategoryDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface IRiskCategoryApplicationService extends IBaseApplicationService {

    TransactionStatus processRiskCategory(SessionContext sessionContext, RiskCategoryDTO riskcategorydto) throws
            FatalException, JsonProcessingException;

    void save(RiskCategoryDTO riskcategorydto);

    RiskCategoryDTO getRiskCategoryByCode(SessionContext sessionContext, RiskCategoryDTO riskcategorydto) throws FatalException, JsonProcessingException;

    List<RiskCategoryDTO> getRiskCategories(SessionContext sessionContext) throws FatalException;

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
