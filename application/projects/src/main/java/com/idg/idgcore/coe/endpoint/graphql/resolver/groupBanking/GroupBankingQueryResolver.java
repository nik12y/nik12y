package com.idg.idgcore.coe.endpoint.graphql.resolver.groupBanking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.groupBanking.IGroupBankingApplicationService;
import com.idg.idgcore.coe.dto.groupBanking.GroupBankingDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class GroupBankingQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private IGroupBankingApplicationService iGroupBankingApplicationService;

    public GroupBankingDTO getGroupBankByCode (SessionContext sessionContext, GroupBankingDTO groupBankingDTO)
            throws FatalException, JsonProcessingException {
        return this.iGroupBankingApplicationService.getGroupBankByCode(sessionContext, groupBankingDTO);
    }

    public List<GroupBankingDTO> getGroupBanks (SessionContext sessionContext)
            throws FatalException {
        return this.iGroupBankingApplicationService.getGroupBanks(sessionContext);
    }

}
