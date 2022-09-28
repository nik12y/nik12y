package com.idg.idgcore.coe.app.service.regulatoryRegion;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.regulatoryRegion.RegulatoryRegionAssembler;
import com.idg.idgcore.coe.domain.entity.regulatoryRegion.RegulatoryRegionConfigEntity;
import com.idg.idgcore.coe.domain.service.regulatoryRegion.RegulatoryRegionDomainService;
import com.idg.idgcore.coe.dto.regulatoryRegion.RegulatoryRegionConfigDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.REGULATORY_SERVICE;

@Slf4j
@Service("regulatoryRegionApplicationService")
public class RegulatoryRegionApplicationService extends GenericApplicationService<RegulatoryRegionConfigDTO,
        RegulatoryRegionConfigEntity, RegulatoryRegionDomainService, RegulatoryRegionAssembler> {

    protected RegulatoryRegionApplicationService() {
        super(REGULATORY_SERVICE);
    }

    public String getTaskCode() {
        return RegulatoryRegionConfigDTO.builder().build().getTaskCode();
    }

}
