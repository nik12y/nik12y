package com.idg.idgcore.coe.endpoint.graphql.resolver.zakat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.zakat.ZakatApplicationService;
import com.idg.idgcore.coe.dto.zakat.ZakatDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ZakatQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private ZakatApplicationService zakatService;

    public ZakatDTO getZakatByYear (SessionContext sessionContext, ZakatDTO zakatDTO)
            throws FatalException, JsonProcessingException {
        return this.zakatService.getByIdentifier(sessionContext, zakatDTO);
    }

    public List<ZakatDTO> getZakats (SessionContext sessionContext) throws FatalException {
        return this.zakatService.getAll(sessionContext);
    }
}
