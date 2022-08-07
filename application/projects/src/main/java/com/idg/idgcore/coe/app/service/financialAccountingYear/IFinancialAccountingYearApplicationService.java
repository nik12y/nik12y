package com.idg.idgcore.coe.app.service.financialAccountingYear;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.base.*;
import com.idg.idgcore.coe.dto.financialAccountingYear.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;

import java.util.*;

public interface IFinancialAccountingYearApplicationService extends IBaseApplicationService {
    TransactionStatus processFinancialAccountingYear (SessionContext sessionContext, FinancialAccountingYearDTO dto)
            throws FatalException, JsonProcessingException;
    void save (FinancialAccountingYearDTO dto);
    FinancialAccountingYearDTO getFinancialAccountingYearByCode (SessionContext sessionContext, FinancialAccountingYearDTO dto)
            throws FatalException, JsonProcessingException;
    List<FinancialAccountingYearDTO> getFinancialAccountingYears (SessionContext sessionContext) throws FatalException;

}
