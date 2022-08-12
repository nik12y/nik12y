package com.idg.idgcore.coe.domain.service.riskcode;


import com.idg.idgcore.coe.domain.assembler.riskcode.RiskCodeAssembler;
import com.idg.idgcore.coe.domain.entity.riskcode.RiskCodeEntity;
import com.idg.idgcore.coe.domain.repository.riskcode.IRiskCodeRepository;
import com.idg.idgcore.coe.dto.riskcode.RiskCodeDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class RiskCodeDomainService implements IRiskCodeDomainService {

    @Autowired
    private IRiskCodeRepository riskCodeRepository;
    @Autowired
    private RiskCodeAssembler riskCodeAssembler;

    public RiskCodeEntity getConfigurationByCode (RiskCodeDTO riskCodeDTO) {
        RiskCodeEntity riskCodeEntity = null;
        try {
            riskCodeEntity = this.riskCodeRepository.findByRiskCode(riskCodeDTO.getRiskCode());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return riskCodeEntity;
    }

    public List<RiskCodeEntity> getRiskCodes () {
        return this.riskCodeRepository.findAll();
    }

    public RiskCodeEntity getRiskCodeByCode (String riskCode) {
        RiskCodeEntity riskcodeEntity = null;
        try {
            riskcodeEntity = this.riskCodeRepository.findByRiskCode(riskCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return riskcodeEntity;
    }

    public void save (RiskCodeDTO riskCodeDTO) {
        try {
            RiskCodeEntity riskCodeEntity = riskCodeAssembler.convertDtoToEntity(riskCodeDTO);
            this.riskCodeRepository.save(riskCodeEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }

}

