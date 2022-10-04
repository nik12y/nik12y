package com.idg.idgcore.coe.endpoint.graphql.resolver.city;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.city.CityApplicationService;
import com.idg.idgcore.coe.app.service.generic.IGenericApplicationService;
import com.idg.idgcore.coe.dto.city.CityDTO;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CityQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private CityApplicationService cityService;

    public CityDTO getCityByCode (SessionContext sessionContext, CityDTO cityDTO)
            throws FatalException, JsonProcessingException {
        return this.cityService.getByIdentifier(sessionContext, cityDTO);
    }

    public List<CityDTO> getCities (SessionContext sessionContext)
            throws FatalException {
        return this.cityService.getAll(sessionContext);
    }
}
