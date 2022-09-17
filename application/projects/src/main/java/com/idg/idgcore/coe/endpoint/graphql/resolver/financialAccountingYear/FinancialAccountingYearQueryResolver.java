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
    private IFinancialAccountingYearApplicationService appService;

    public FinancialAccountingYearDTO getFinancialAccountingYearByCode (
            SessionContext sessionContext, FinancialAccountingYearDTO dto)
            throws FatalException, JsonProcessingException {
        return this.appService.getFinancialAccountingYearByCode(sessionContext, dto);
    }

    public FinancialAccountingYearDTO getFinancialAccountByBankCodeAndBranchCodeAndFinancialAccountingYearCode (
            SessionContext sessionContext, FinancialAccountingYearDTO dto)
            throws FatalException, JsonProcessingException {
        return this.appService.getByBankCodeAndBranchCodeAndFinancialAccountingYearCode(
                sessionContext, dto);
    }

    public List<FinancialAccountingYearDTO> getFinancialAccountingYears (
            SessionContext sessionContext) throws FatalException {
        return this.appService.getFinancialAccountingYears(sessionContext);
    }

    public FinancialAccountingYearDTO getPeriodCodeDetails (SessionContext sessionContext,
            FinancialAccountingYearDTO dto)
            throws FatalException, JsonProcessingException {
        return this.appService.getPeriodCodeDetails(sessionContext, dto);
    }

    public List<FinancialAccountingYearDTO> searchFinancialAccountingYear (SessionContext sessionContext, FinancialAccountingYearDTO financialAccountingYearDTO) throws FatalException, JsonProcessingException {
        return this.appService.searchFinancialAccountingYear(sessionContext, financialAccountingYearDTO);
    }

}
