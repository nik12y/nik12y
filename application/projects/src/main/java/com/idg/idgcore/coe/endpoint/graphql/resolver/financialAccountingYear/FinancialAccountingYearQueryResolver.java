package com.idg.idgcore.coe.endpoint.graphql.resolver.financialAccountingYear;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.financialAccountingYear.*;
import com.idg.idgcore.coe.dto.financialAccountingYear.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import graphql.kickstart.tools.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Slf4j
@Component
public class FinancialAccountingYearQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private FinancialAccountingYearApplicationService theService;

    public FinancialAccountingYearDTO getFinancialAccountingYearByCode (SessionContext sessionContext, FinancialAccountingYearDTO dto)
            throws FatalException, JsonProcessingException {
        return this.theService.getByIdentifier(sessionContext, dto);
    }

    public FinancialAccountingYearDTO getFinancialAccountByBankCodeAndBranchCodeAndFinancialAccountingYearCode (SessionContext sessionContext, FinancialAccountingYearDTO dto)
            throws FatalException, JsonProcessingException {
//        return this.theService.getByBankCodeAndBranchCodeAndFinancialAccountingYearCode(sessionContext, dto);
            return this.theService.getByIdentifier(sessionContext, dto);
    }


    public List<FinancialAccountingYearDTO> getFinancialAccountingYears (SessionContext sessionContext) throws FatalException {
        return this.theService.getAll(sessionContext);
    }

    public FinancialAccountingYearDTO getPeriodCodeDetails (SessionContext sessionContext,
                                                            FinancialAccountingYearDTO dto)
            throws FatalException, JsonProcessingException {
        return this.theService.getPeriodCodeDetails(sessionContext, dto);
    }

    public List<FinancialAccountingYearDTO> searchFinancialAccountingYear (SessionContext sessionContext, FinancialAccountingForSearchYearDTO financialAccountingYearDTO) throws FatalException, JsonProcessingException {
        return this.theService.searchFinancialAccountingYear(sessionContext, financialAccountingYearDTO);
    }

//    public List<FinancialAccountingYearDTO> getFinancialAccountingYearsFromMain (SessionContext sessionContext) throws FatalException {
//        return this.theService.getAllFromMain(sessionContext);
//    }
}
