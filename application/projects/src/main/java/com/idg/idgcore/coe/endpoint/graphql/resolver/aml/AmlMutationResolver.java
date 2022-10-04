package com.idg.idgcore.coe.endpoint.graphql.resolver.aml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.aml.AmlApplicationService;
import com.idg.idgcore.coe.dto.aml.AmlDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class AmlMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private AmlApplicationService amlApplicationService;

    public TransactionStatus processAml (SessionContext sessionContext, AmlDTO amlDTO)
            throws FatalException, JsonProcessingException {
        return this.amlApplicationService.process(sessionContext, amlDTO);
    }
}