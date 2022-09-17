package com.idg.idgcore.coe.endpoint.graphql.resolver.bankparameter;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.bankparameter.*;
import com.idg.idgcore.coe.dto.bankparameter.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import graphql.kickstart.tools.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Slf4j
@Component
public class BankParameterQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private IBankParameterApplicationService bankParameterService;

    public BankParameterDTO getBankParameterByBankCode (SessionContext sessionContext, BankParameterDTO bankParameterDTO)
            throws FatalException, JsonProcessingException {
            return this.bankParameterService.getBankParameterByBankCode(sessionContext, bankParameterDTO);
    }

    public List<BankParameterDTO> getBankParameters(SessionContext sessionContext)
            throws FatalException {
            return this.bankParameterService.getBankParameters(sessionContext);
    }

    public List<BankParameterDTO> searchBankParameter (SessionContext sessionContext, BankParameterDTO bankParameterDTO) throws FatalException, JsonProcessingException {
        return this.bankParameterService.searchBankParameter(sessionContext, bankParameterDTO);
    }

}
