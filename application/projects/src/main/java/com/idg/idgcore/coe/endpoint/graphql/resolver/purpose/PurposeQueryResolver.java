package com.idg.idgcore.coe.endpoint.graphql.resolver.purpose;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.purpose.IPurposeApplicationService;
import com.idg.idgcore.coe.dto.purpose.PurposeDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class PurposeQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private IPurposeApplicationService purposeService;

    public PurposeDTO getPurposeByCode(SessionContext sessionContext, PurposeDTO purposeDTO) throws FatalException, JsonProcessingException {
        return this.purposeService.getPurposeByCode(sessionContext, purposeDTO);
    }

    public List<PurposeDTO> getPurposes(SessionContext sessionContext)
            throws FatalException {
        return this.purposeService.getPurposes(sessionContext);
    }
}
