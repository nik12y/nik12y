package com.idg.idgcore.coe.endpoint.graphql.resolver.iban;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.iban.*;
import com.idg.idgcore.coe.dto.iban.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import graphql.kickstart.tools.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Slf4j
@Component
public class IbanMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private IIbanApplicationService ibanService;

    public TransactionStatus processIban (SessionContext sessionContext, IbanDTO ibanDTO)
            throws FatalException, JsonProcessingException {
        return this.ibanService.processIban(sessionContext, ibanDTO);
    }

}
