package com.idg.idgcore.coe.domain.service.financialAccountingYear;

import com.idg.idgcore.coe.domain.assembler.financialAccountingYear.*;
import com.idg.idgcore.coe.domain.entity.financialAccountingYear.*;
import com.idg.idgcore.coe.domain.repository.financialAccountingYear.*;
import com.idg.idgcore.coe.dto.financialAccountingYear.*;
import com.idg.idgcore.coe.exception.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

import static com.idg.idgcore.coe.exception.Error.*;

@Slf4j
@Service
public class FinancialAccountingYearDomainService implements IFinancialAccountingYearDomainService {
    @Autowired
    private IFinancialAccountingYearRepository repository;
    @Autowired
    private FinancialAccountingYearAssembler assembler;

    public FinancialAccountingYearEntity getConfigurationByCode (
            FinancialAccountingYearDTO dto) {
        FinancialAccountingYearEntity entity = null;
        try {
            entity = this.repository.findByBankCode(dto.getBankCode());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return entity;
    }

    public List<FinancialAccountingYearEntity> getFinancialAccountingYears () {
        return this.repository.findAll();
    }

    public FinancialAccountingYearEntity getFinancialAccountingYearByCode (String bankCode) {
        FinancialAccountingYearEntity entity = null;
        try {
            entity = this.repository.findByBankCode(bankCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return entity;
    }

    public FinancialAccountingYearEntity getByBankCodeAndBranchCodeAndFinancialAccountingYearCode (
            String bankCode,
            String branchCode, String financialAccountingYearCode) {
        FinancialAccountingYearEntity entity = null;
        try {
            entity = this.repository.getByBankCodeAndBranchCodeAndFinancialAccountingYearCode(
                    bankCode, branchCode, financialAccountingYearCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return entity;
    }

    public void save (FinancialAccountingYearDTO dto) {
        try {
            FinancialAccountingYearEntity entity = assembler.convertDtoToEntity(dto);
            this.repository.save(entity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
    @Override
    public FinancialAccountingYearEntity getFinancialAccountingYearForProcessCall (
            String branchCode, Date inputDate) {
        log.info(" IN getFinancialAccountingYearForProcessCall[",
                branchCode + ", " + inputDate
                        + "] ");
        FinancialAccountingYearEntity entity = null;
        try {
            entity = this.repository.findByBranchCodeAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                    branchCode, inputDate, inputDate);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return entity;
    }
}
