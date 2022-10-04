package com.idg.idgcore.coe.endpoint.graphql.resolver.regulatoryRegion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.regulatoryRegion.RegulatoryRegionApplicationService;
import com.idg.idgcore.coe.dto.regulatoryRegion.RegulatoryRegionConfigDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RegulatoryRegionQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private RegulatoryRegionApplicationService iRegulatoryRegionApplicationService;

    public RegulatoryRegionConfigDTO getRegulatoryRegionByCode (SessionContext sessionContext, RegulatoryRegionConfigDTO regulatoryRegionConfigDTO)
            throws FatalException, JsonProcessingException {
        return this.iRegulatoryRegionApplicationService.getByIdentifier(sessionContext, regulatoryRegionConfigDTO);
    }

    public List<RegulatoryRegionConfigDTO> getRegulatoryRegionCodes (SessionContext sessionContext) throws FatalException {
        return this.iRegulatoryRegionApplicationService.getAll(sessionContext);
    }
}
