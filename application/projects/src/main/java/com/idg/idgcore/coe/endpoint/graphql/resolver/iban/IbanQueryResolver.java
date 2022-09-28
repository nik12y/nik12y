package com.idg.idgcore.coe.endpoint.graphql.resolver.iban;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.iban.IbanApplicationService;
import com.idg.idgcore.coe.dto.iban.IbanDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class IbanQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private IbanApplicationService ibanService;

    public IbanDTO getIbanByIbanCountryCode(SessionContext sessionContext, IbanDTO ibanDTO)
            throws FatalException, JsonProcessingException {
        return this.ibanService.getByIdentifier(sessionContext, ibanDTO);
    }

    public List<IbanDTO> getIban(SessionContext sessionContext)
            throws FatalException {
        return this.ibanService.getAll(sessionContext);
    }
}
