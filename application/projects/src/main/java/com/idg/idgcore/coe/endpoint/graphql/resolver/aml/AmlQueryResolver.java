package com.idg.idgcore.coe.endpoint.graphql.resolver.aml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.aml.IAmlApplicationService;
import com.idg.idgcore.coe.dto.aml.AmlDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AmlQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private IAmlApplicationService amlApplicationService;

    public AmlDTO getAmlByCode (SessionContext sessionContext, AmlDTO amlDTO)
            throws FatalException, JsonProcessingException {
        return this.amlApplicationService.getAmlByCode(sessionContext, amlDTO);
    }

    public List<AmlDTO> getAmls (SessionContext sessionContext) throws FatalException {
        return this.amlApplicationService.getAmls(sessionContext);
    }
}
