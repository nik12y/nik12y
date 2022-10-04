package com.idg.idgcore.coe.endpoint.graphql.resolver.groupBanking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.groupBanking.GroupBankingApplicationService;
import com.idg.idgcore.coe.dto.groupBanking.GroupBankingDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GroupBankingMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private GroupBankingApplicationService groupBankingApplicationService;

    public TransactionStatus processGroupBanking (SessionContext sessionContext, GroupBankingDTO groupBankingDTO)
            throws FatalException, JsonProcessingException {
        return this.groupBankingApplicationService.process(sessionContext, groupBankingDTO);
    }

}
