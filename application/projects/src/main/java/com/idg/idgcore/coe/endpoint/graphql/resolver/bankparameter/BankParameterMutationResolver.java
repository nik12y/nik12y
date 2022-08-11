package com.idg.idgcore.coe.endpoint.graphql.resolver.bankparameter;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.bankparameter.*;
import com.idg.idgcore.coe.dto.bankparameter.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import graphql.kickstart.tools.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Slf4j
@Component
public class BankParameterMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private IBankParameterApplicationService bankParameterService;

    public TransactionStatus processBankParameter (SessionContext sessionContext, BankParameterDTO bankParameterDTO)
            throws FatalException, JsonProcessingException {
            return this.bankParameterService.processBankParameter(sessionContext, bankParameterDTO);
    }

}
