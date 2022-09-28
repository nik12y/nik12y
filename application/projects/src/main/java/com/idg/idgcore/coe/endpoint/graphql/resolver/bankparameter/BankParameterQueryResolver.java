package com.idg.idgcore.coe.endpoint.graphql.resolver.bankparameter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.bankparameter.BankParameterApplicationService;
import com.idg.idgcore.coe.dto.bankparameter.BankParameterDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class BankParameterQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private BankParameterApplicationService bankParameterService;

    public BankParameterDTO getBankParameterByBankCode (SessionContext sessionContext, BankParameterDTO bankParameterDTO)
            throws FatalException, JsonProcessingException {
            return this.bankParameterService.getByIdentifier(sessionContext, bankParameterDTO);
    }

    public List<BankParameterDTO> getBankParameters(SessionContext sessionContext)
            throws FatalException {
            return this.bankParameterService.getAll(sessionContext);
    }

    public List<BankParameterDTO> searchBankParameter (SessionContext sessionContext, BankParameterDTO bankParameterDTO) throws FatalException, JsonProcessingException {
        return this.bankParameterService.searchBankParameter(sessionContext, bankParameterDTO);
    }

}
