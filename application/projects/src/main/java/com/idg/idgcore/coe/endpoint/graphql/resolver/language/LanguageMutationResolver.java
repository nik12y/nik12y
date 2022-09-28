package com.idg.idgcore.coe.endpoint.graphql.resolver.language;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.language.ILanguageApplicationService;
import com.idg.idgcore.coe.app.service.language.LanguageApplicationService;
import com.idg.idgcore.coe.dto.language.LanguageDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LanguageMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private LanguageApplicationService languageService;

    public TransactionStatus processLanguage (SessionContext sessionContext, LanguageDTO languageDTO)
            throws FatalException, JsonProcessingException {
        return this.languageService.process(sessionContext, languageDTO);
    }
}
