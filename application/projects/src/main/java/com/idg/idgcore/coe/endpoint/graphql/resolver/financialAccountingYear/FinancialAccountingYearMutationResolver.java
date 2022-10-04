package com.idg.idgcore.coe.endpoint.graphql.resolver.financialAccountingYear;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.city.CityApplicationService;
import com.idg.idgcore.coe.app.service.financialAccountingYear.*;
import com.idg.idgcore.coe.dto.financialAccountingYear.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import graphql.kickstart.tools.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Slf4j
@Component
public class FinancialAccountingYearMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private FinancialAccountingYearApplicationService financialAccountingYearApplicationService;

    public TransactionStatus processFinancialAccountingYear (SessionContext sessionContext, FinancialAccountingYearDTO dto)
            throws FatalException, JsonProcessingException {
        return this.financialAccountingYearApplicationService.process(sessionContext, dto);
    }

}
