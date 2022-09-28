package com.idg.idgcore.coe.domain.service.regulatoryRegion;

import com.idg.idgcore.coe.domain.assembler.regulatoryRegion.RegulatoryRegionAssembler;
import com.idg.idgcore.coe.domain.entity.regulatoryRegion.RegulatoryRegionConfigEntity;
import com.idg.idgcore.coe.domain.repository.regulatoryRegion.IRegulatoryRegionRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.regulatoryRegion.RegulatoryRegionConfigDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class RegulatoryRegionDomainService extends DomainService<RegulatoryRegionConfigDTO, RegulatoryRegionConfigEntity> {

    @Autowired
    private IRegulatoryRegionRepository iRegulatoryRegionRepository;

    @Autowired
    private RegulatoryRegionAssembler regulatoryRegionAssembler;

    @Override
    public RegulatoryRegionConfigEntity getEntityByIdentifier(String regionCode) {
        RegulatoryRegionConfigEntity regulatoryRegionConfigEntity = null;
        try {
            regulatoryRegionConfigEntity = this.iRegulatoryRegionRepository.findByRegRegionCode(regionCode);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return regulatoryRegionConfigEntity;
    }

    @Override
    public List<RegulatoryRegionConfigEntity> getAllEntities() {
        return this.iRegulatoryRegionRepository.findAll();
    }

    public void save(RegulatoryRegionConfigDTO regulatoryRegionConfigDTO) {
        try {
            RegulatoryRegionConfigEntity regulatoryRegionConfigEntity = this.regulatoryRegionAssembler.toEntity(regulatoryRegionConfigDTO);
            this.iRegulatoryRegionRepository.save(regulatoryRegionConfigEntity);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}
