package com.idg.idgcore.coe.domain.service.riskcategory;

import com.idg.idgcore.coe.domain.assembler.riskcategory.RiskCategoryAssembler;
import com.idg.idgcore.coe.domain.entity.riskcategory.RiskCategoryEntity;
import com.idg.idgcore.coe.domain.repository.riskcategory.IRiskCategoryRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.riskcategory.RiskCategoryDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class RiskCategoryDomainService extends DomainService<RiskCategoryDTO, RiskCategoryEntity> {

    @Autowired
    private IRiskCategoryRepository riskCategoryRepository;

    @Autowired
    private RiskCategoryAssembler riskCategoryAssembler;

    @Override
    public RiskCategoryEntity getEntityByIdentifier(String riskCategoryCode) {
        RiskCategoryEntity riskcategoryEntity = null;
        try {
            riskcategoryEntity = this.riskCategoryRepository.findByRiskCategoryCode(riskCategoryCode);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return riskcategoryEntity;
    }

    @Override
    public List<RiskCategoryEntity> getAllEntities() {
        return this.riskCategoryRepository.findAll();
    }

    public void save(RiskCategoryDTO riskCategoryDTO) {
        try {
            RiskCategoryEntity riskcategoryEntity = riskCategoryAssembler.toEntity(riskCategoryDTO);
            this.riskCategoryRepository.save(riskcategoryEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

