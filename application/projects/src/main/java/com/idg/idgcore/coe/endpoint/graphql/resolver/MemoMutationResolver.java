package com.idg.idgcore.coe.endpoint.graphql.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.*;
import com.idg.idgcore.coe.dto.*;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MemoMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private IMemoApplicationService memoService;

    public TransactionStatus processMemo (SessionContext sessionContext, MemoDTO memoDTO)
            throws FatalException, JsonProcessingException {
        return this.memoService.processMemo(sessionContext, memoDTO);
    }

}
