package com.idg.idgcore.graphqlsdl.resolver;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.app.dto.CountryDTO;
import com.idg.idgcore.app.service.ICountryApplicationService;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.infra.exception.*;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class CountryQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private ICountryApplicationService countryService;

    public CountryDTO getCountryByCode (SessionContext sessionContext, CountryDTO countryDTO)
            throws FatalException, JsonProcessingException {
            return this.countryService.getCountryByCode(sessionContext, countryDTO);
    }

    public List<CountryDTO> getCountries (SessionContext sessionContext)
            throws FatalException {
            return this.countryService.getCountries(sessionContext);
    }
}
