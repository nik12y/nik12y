package com.idg.idgcore.coe.domain.repository.regulatoryRegion;

import com.idg.idgcore.coe.domain.entity.regulatoryRegion.RegulatoryRegionConfigEntity;
import com.idg.idgcore.coe.domain.entity.regulatoryRegion.RegulatoryRegionConfigEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegulatoryRegionRepository extends JpaRepository<RegulatoryRegionConfigEntity, RegulatoryRegionConfigEntityKey> {
    RegulatoryRegionConfigEntity findByRegRegionCode(String regionCode);
}

