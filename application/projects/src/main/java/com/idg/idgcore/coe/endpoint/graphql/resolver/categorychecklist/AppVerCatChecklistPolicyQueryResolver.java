package com.idg.idgcore.coe.endpoint.graphql.resolver.categorychecklist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.categorychecklist.IAppVerCatChecklistPolicyApplicationService;
import com.idg.idgcore.coe.dto.categorychecklist.AppVerCatChecklistPolicyDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
public class AppVerCatChecklistPolicyQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private IAppVerCatChecklistPolicyApplicationService appVerCatChecklistPolicyApplicationService;

    public AppVerCatChecklistPolicyDTO getAppVerCatChecklistPolicyById(SessionContext sessionContext, AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO)
            throws FatalException, JsonProcessingException {
        return this.appVerCatChecklistPolicyApplicationService.getAppVerCatChecklistPolicyById(sessionContext, appVerCatChecklistPolicyDTO);
    }

    public List<AppVerCatChecklistPolicyDTO> getAppVerCatChecklistPolicies(SessionContext sessionContext)
            throws FatalException {
        return this.appVerCatChecklistPolicyApplicationService.getAppVerCatChecklistPolicies(sessionContext);
    }
}
