package com.idg.idgcore.coe.endpoint.graphql.resolver.iban;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.iban.IbanApplicationService;
import com.idg.idgcore.coe.dto.iban.IbanDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IbanMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private IbanApplicationService ibanService;

    public TransactionStatus processIban (SessionContext sessionContext, IbanDTO ibanDTO)
            throws FatalException, JsonProcessingException {
        return this.ibanService.process(sessionContext, ibanDTO);
    }

}
