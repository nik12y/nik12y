package com.idg.idgcore.graphqlsdl.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.app.dto.CityDTO;
import com.idg.idgcore.app.service.ICityApplicationService;
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
    private ICityApplicationService cityService;

    public CityDTO getCityByCode (SessionContext sessionContext, CityDTO cityDTO)
            throws FatalException, JsonProcessingException {
        return this.cityService.getCityByCode(sessionContext, cityDTO);
    }

    public List<CityDTO> getCities (SessionContext sessionContext)
            throws FatalException {
        return this.cityService.getCities(sessionContext);
    }
}
