package com.idg.idgcore.coe.endpoint.graphql.resolver.city;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.city.CityApplicationService;
import com.idg.idgcore.coe.dto.city.CityDTO;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idg.idgcore.coe.app.service.generic.IGenericApplicationService;

@Slf4j
@Component
public class CityMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private CityApplicationService cityService;

    public TransactionStatus processCity (SessionContext sessionContext, CityDTO cityDTO)
            throws FatalException, JsonProcessingException {
        return this.cityService.process(sessionContext, cityDTO);
    }

}
