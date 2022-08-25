package com.idg.idgcore.coe.domain.service.regulatoryRegion;

import com.idg.idgcore.coe.domain.assembler.regulatoryRegion.RegulatoryRegionAssembler;
import com.idg.idgcore.coe.domain.entity.regulatoryRegion.RegulatoryRegionConfigEntity;
import com.idg.idgcore.coe.domain.repository.regulatoryRegion.IRegulatoryRegionRepository;
import com.idg.idgcore.coe.dto.regulatoryRegion.RegulatoryRegionConfigDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class RegulatoryRegionDomainService implements IRegulatoryRegionDomainService {

    @Autowired
    private IRegulatoryRegionRepository iRegulatoryRegionRepository;

    @Autowired
    private RegulatoryRegionAssembler regulatoryRegionAssembler;


    public RegulatoryRegionConfigEntity getConfigurationByCode(RegulatoryRegionConfigDTO regulatoryRegionConfigDTO ) {
        RegulatoryRegionConfigEntity regulatoryRegionConfigEntity = null;
        try {
            regulatoryRegionConfigEntity = this.iRegulatoryRegionRepository.findByRegRegionCode(regulatoryRegionConfigDTO.getRegRegionCode());
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return regulatoryRegionConfigEntity;
    }

    public List<RegulatoryRegionConfigEntity> getRegulatoryRegionCodes() {
        return this.iRegulatoryRegionRepository.findAll();
    }

    public RegulatoryRegionConfigEntity getRegulatoryRegionByCode(String regionCode) {
        RegulatoryRegionConfigEntity regulatoryRegionConfigEntity = null;
        try {
            regulatoryRegionConfigEntity = this.iRegulatoryRegionRepository.findByRegRegionCode(regionCode);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return regulatoryRegionConfigEntity;

    }

    public void save(RegulatoryRegionConfigDTO regulatoryRegionConfigDTO) {
        try {
            RegulatoryRegionConfigEntity regulatoryRegionConfigEntity = this.regulatoryRegionAssembler.convertDtoToEntity(regulatoryRegionConfigDTO);
            this.iRegulatoryRegionRepository.save(regulatoryRegionConfigEntity);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}
