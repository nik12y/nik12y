package com.idg.idgcore.coe.endpoint.graphql.resolver.language;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.language.ILanguageApplicationService;
import com.idg.idgcore.coe.dto.language.LanguageDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class LanguageQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private ILanguageApplicationService languageService;

    public LanguageDTO getLanguageByCode (SessionContext sessionContext, LanguageDTO languageDTO)
            throws FatalException, JsonProcessingException {
        return this.languageService.getLanguageByCode(sessionContext, languageDTO);
    }

    public List<LanguageDTO> getLanguages (SessionContext sessionContext)
            throws FatalException {
        return this.languageService.getLanguages(sessionContext);
    }
}
