package com.idg.idgcore.coe.endpoint.graphql.resolver.riskcategory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.riskcategory.IRiskCategoryApplicationService;
import com.idg.idgcore.coe.dto.riskcategory.RiskCategoryDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RiskCategoryMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private IRiskCategoryApplicationService riskcategoryService;

    public TransactionStatus processRiskCategory (SessionContext sessionContext, RiskCategoryDTO riskcategoryDTO)
            throws FatalException, JsonProcessingException {
        return riskcategoryService.processRiskCategory(sessionContext, riskcategoryDTO);
    }

}
