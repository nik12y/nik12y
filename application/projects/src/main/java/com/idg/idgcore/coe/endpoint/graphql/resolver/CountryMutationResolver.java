package com.idg.idgcore.coe.endpoint.graphql.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.dto.CountryDTO;
import com.idg.idgcore.coe.app.service.ICountryApplicationService;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CountryMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private ICountryApplicationService countryService;

    public TransactionStatus processCountry (SessionContext sessionContext, CountryDTO countryDTO)
            throws FatalException, JsonProcessingException {
        return this.countryService.processCountry(sessionContext, countryDTO);
    }

}
