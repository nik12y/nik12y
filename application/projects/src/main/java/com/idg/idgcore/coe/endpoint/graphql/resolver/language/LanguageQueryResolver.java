package com.idg.idgcore.coe.endpoint.graphql.resolver.language;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.language.ILanguageApplicationService;
import com.idg.idgcore.coe.app.service.language.LanguageApplicationService;
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
    private LanguageApplicationService languageService;

    public LanguageDTO getLanguageByCode (SessionContext sessionContext, LanguageDTO languageDTO)
            throws FatalException, JsonProcessingException {
        return this.languageService.getByIdentifier(sessionContext, languageDTO);
    }

    public List<LanguageDTO> getLanguages (SessionContext sessionContext)
            throws FatalException {
        return this.languageService.getAll(sessionContext);
    }
}
