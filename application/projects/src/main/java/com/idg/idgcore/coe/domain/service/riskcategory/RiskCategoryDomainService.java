package com.idg.idgcore.coe.domain.service.riskcategory;

import com.idg.idgcore.coe.domain.assembler.riskcategory.RiskCategoryAssembler;
import com.idg.idgcore.coe.domain.entity.riskcategory.RiskCategoryEntity;
import com.idg.idgcore.coe.domain.repository.riskcategory.IRiskCategoryRepository;
import com.idg.idgcore.coe.dto.riskcategory.RiskCategoryDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class RiskCategoryDomainService implements IRiskCategoryDomainService {


    @Autowired
    private IRiskCategoryRepository riskCategoryRepository;

    @Autowired
    private RiskCategoryAssembler riskCategoryAssembler;



    public RiskCategoryEntity getConfigurationByCode(RiskCategoryDTO riskcategoryDTO) {
        RiskCategoryEntity riskcategoryEntity = null;
        try {
            riskcategoryEntity = this.riskCategoryRepository.findByRiskCategoryCode(riskcategoryDTO.getRiskCategoryCode());
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }

        return riskcategoryEntity;
    }

    public List<RiskCategoryEntity> getRiskCategories() {
        return this.riskCategoryRepository.findAll();
    }

    @Override
    public RiskCategoryEntity getRiskCategoryByCode(String riskCategoryCode) {
        RiskCategoryEntity riskcategoryEntity = null;
        try {
            riskcategoryEntity = this.riskCategoryRepository.findByRiskCategoryCode(riskCategoryCode);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return riskcategoryEntity;


    }


    public void save(RiskCategoryDTO riskCategoryDTO) {
        try {
            RiskCategoryEntity riskcategoryEntity = riskCategoryAssembler.convertDtoToEntity(riskCategoryDTO);
            this.riskCategoryRepository.save(riskcategoryEntity);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

