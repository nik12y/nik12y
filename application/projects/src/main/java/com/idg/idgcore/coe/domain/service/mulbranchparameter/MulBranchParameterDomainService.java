package com.idg.idgcore.coe.domain.service.mulbranchparameter;


import com.idg.idgcore.coe.domain.assembler.mulbranchparameter.MulBranchParameterAssembler;
import com.idg.idgcore.coe.domain.entity.currencypair.CurrencyPairEntity;
import com.idg.idgcore.coe.domain.entity.financialAccountingYear.FinancialAccountingYearEntity;
import com.idg.idgcore.coe.domain.entity.mulbranchparameter.MulBranchParameterEntity;

import com.idg.idgcore.coe.domain.repository.mulbranchparameter.IMulBranchParameterRepository;
import com.idg.idgcore.coe.dto.mulbranchparameter.MulBranchParameterDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class MulBranchParameterDomainService implements IMulBranchParameterDomainService
{

    @Autowired
    private IMulBranchParameterRepository mulBranchParameterRepository;

    @Autowired
    private MulBranchParameterAssembler mulBranchParameterAssembler;

    @Override
    public MulBranchParameterEntity getConfigurationByCode(MulBranchParameterDTO mulBranchParameterDTO) {
        MulBranchParameterEntity mulBranchParameterEntity = null;
        try {
            mulBranchParameterEntity = this.mulBranchParameterRepository.getByCurrencyCodeAndEntityCode(mulBranchParameterDTO.getCurrencyCode(),mulBranchParameterDTO.getEntityCode());
        } catch (Exception e)  {
            log.error("Exception in getConfigurationByCode",e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return mulBranchParameterEntity;
    }

    @Override
    public List<MulBranchParameterEntity> getMulBranchParameters() {
        return this.mulBranchParameterRepository.findAll();
    }

    @Override
    public MulBranchParameterEntity getByCurrencyCodeAndEntityCode(String currencyCode, String entityCode) {
        MulBranchParameterEntity mulBranchParameterEntity = null;
        try {
            mulBranchParameterEntity = this.mulBranchParameterRepository.getByCurrencyCodeAndEntityCode(currencyCode,entityCode);
            }
        catch (Exception e) {
            log.error("Exception in getByCurrencyCodeAndEntityCode",e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return mulBranchParameterEntity;
    }

    public void save(MulBranchParameterDTO mulBranchParameterDTO) {
        try {
            MulBranchParameterEntity mulBranchParameterEntity=mulBranchParameterAssembler.convertDtoToEntity(mulBranchParameterDTO);
            this.mulBranchParameterRepository.save(mulBranchParameterEntity);
        } catch (Exception e) {
            log.error("Exception in save",e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
    }

