package com.idg.idgcore.coe.endpoint.graphql.resolver.mitigant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.mitigant.IMitigantApplicationService;
import com.idg.idgcore.coe.dto.mitigant.MitigantDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MitigantQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private IMitigantApplicationService mitigantService;

    public MitigantDTO getMitigantByCode (SessionContext sessionContext, MitigantDTO mitigantDTO)
            throws FatalException, JsonProcessingException {
        return this.mitigantService.getMitigantByCode(sessionContext, mitigantDTO);
    }

    public List<MitigantDTO> getMitigantAll (SessionContext sessionContext) throws FatalException {
        return this.mitigantService.getMitigantAll(sessionContext);
    }
}
