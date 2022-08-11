package com.idg.idgcore.coe.endpoint.graphql.resolver.mitigant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.mitigant.IMitigantApplicationService;
import com.idg.idgcore.coe.dto.mitigant.MitigantDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MitigantMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private IMitigantApplicationService mitigantService;

    public TransactionStatus processMitigant (SessionContext sessionContext, MitigantDTO mitigantDTO)
            throws FatalException, JsonProcessingException {
        return this.mitigantService.processMitigant(sessionContext, mitigantDTO);
    }
}
