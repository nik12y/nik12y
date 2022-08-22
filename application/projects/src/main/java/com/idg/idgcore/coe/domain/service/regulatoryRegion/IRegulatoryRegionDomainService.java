package com.idg.idgcore.coe.domain.service.regulatoryRegion;

import com.idg.idgcore.coe.domain.entity.regulatoryRegion.RegulatoryRegionConfigEntity;
import com.idg.idgcore.coe.dto.regulatoryRegion.RegulatoryRegionConfigDTO;

import java.util.List;

public interface IRegulatoryRegionDomainService {

    RegulatoryRegionConfigEntity getConfigurationByCode (RegulatoryRegionConfigDTO regulatoryRegionConfigDTO);
    List<RegulatoryRegionConfigEntity> getRegulatoryRegionCodes();
    RegulatoryRegionConfigEntity getRegulatoryRegionByCode(String regionCode);
    void save (RegulatoryRegionConfigDTO regulatoryRegionConfigDTO);
}
