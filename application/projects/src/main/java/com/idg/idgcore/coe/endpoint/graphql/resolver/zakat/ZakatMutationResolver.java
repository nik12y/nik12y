package com.idg.idgcore.coe.endpoint.graphql.resolver.zakat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.zakat.IZakatApplicationService;
import com.idg.idgcore.coe.dto.zakat.ZakatDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ZakatMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private IZakatApplicationService zakatService;

    public TransactionStatus processZakat (SessionContext sessionContext, ZakatDTO zakatDTO)
            throws FatalException, JsonProcessingException {
        return this.zakatService.processZakat(sessionContext, zakatDTO);
    }

}
