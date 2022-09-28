package com.idg.idgcore.coe.endpoint.graphql.resolver.country;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.country.CountryApplicationService;
import com.idg.idgcore.coe.dto.country.CountryDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CountryMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private CountryApplicationService countryService;

    public TransactionStatus processCountry (SessionContext sessionContext, CountryDTO countryDTO)
            throws FatalException, JsonProcessingException {
        return this.countryService.process(sessionContext, countryDTO);
    }

}
