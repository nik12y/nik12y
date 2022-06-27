package com.idg.idgcore.graphqlsdl.resolver;

import com.idg.idgcore.app.dto.CountryDTO;
import com.idg.idgcore.app.service.ICountryApplicationService;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.dto.core.*;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CountryMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private ICountryApplicationService countryService;

    public TransactionStatus processCountry (SessionContext sessionContext, CountryDTO countryDTO) {
        TransactionStatus result;
        try {
            result = this.countryService.processCountry(sessionContext, countryDTO);
        }
        catch (Throwable t) {
            log.info("Error while processing the record.");
            return null;
        }
        return result;
    }

}
