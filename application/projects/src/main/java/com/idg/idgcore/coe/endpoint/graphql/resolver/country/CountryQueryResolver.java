package com.idg.idgcore.coe.endpoint.graphql.resolver.country;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.dto.country.CountryDTO;
import com.idg.idgcore.coe.app.service.country.ICountryApplicationService;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CountryQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private ICountryApplicationService countryService;

    public CountryDTO getCountryByCode (SessionContext sessionContext, CountryDTO countryDTO)
            throws FatalException, JsonProcessingException {
        return this.countryService.getCountryByCode(sessionContext, countryDTO);
    }

    public List<CountryDTO> getCountries (SessionContext sessionContext) throws FatalException {
        return this.countryService.getCountries(sessionContext);
    }

}