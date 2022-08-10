package com.idg.idgcore.coe.endpoint.graphql.resolver.categorychecklist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.categorychecklist.IAppVerCatChecklistPolicyApplicationService;
import com.idg.idgcore.coe.dto.categorychecklist.AppVerCatChecklistPolicyDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppVerCatChecklistPolicyMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private IAppVerCatChecklistPolicyApplicationService appVerCatChecklistPolicyApplicationService;

    public TransactionStatus processAppVerCatChecklistPolicy(SessionContext sessionContext, AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO)
            throws FatalException, JsonProcessingException {
        return this.appVerCatChecklistPolicyApplicationService.processAppVerCatChecklistPolicies(sessionContext, appVerCatChecklistPolicyDTO);
    }

}
