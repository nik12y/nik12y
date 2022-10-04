package com.idg.idgcore.coe.endpoint.graphql.resolver.riskcategory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.riskcategory.RiskCategoryApplicationService;
import com.idg.idgcore.coe.dto.riskcategory.RiskCategoryDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RiskCategoryQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private RiskCategoryApplicationService riskCategoryApplicationService;

    public RiskCategoryDTO getRiskCategoryByCode (SessionContext sessionContext, RiskCategoryDTO riskCategoryDTO)
            throws FatalException, JsonProcessingException {
        return this.riskCategoryApplicationService.getByIdentifier(sessionContext, riskCategoryDTO);
    }

    public List<RiskCategoryDTO> getRiskCategories (SessionContext sessionContext)
            throws FatalException {
        return this.riskCategoryApplicationService.getAll(sessionContext);
    }
}
