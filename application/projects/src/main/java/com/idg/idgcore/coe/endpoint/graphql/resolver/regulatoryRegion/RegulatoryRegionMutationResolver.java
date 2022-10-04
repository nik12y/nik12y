package com.idg.idgcore.coe.endpoint.graphql.resolver.regulatoryRegion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.regulatoryRegion.RegulatoryRegionApplicationService;
import com.idg.idgcore.coe.dto.regulatoryRegion.RegulatoryRegionConfigDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RegulatoryRegionMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private RegulatoryRegionApplicationService iRegulatoryRegionApplicationService;

    public TransactionStatus processRegulatoryRegion (SessionContext sessionContext, RegulatoryRegionConfigDTO regulatoryRegionConfigDTO)
            throws FatalException, JsonProcessingException {
        return this.iRegulatoryRegionApplicationService.process(sessionContext, regulatoryRegionConfigDTO);
    }
}
