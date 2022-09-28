package com.idg.idgcore.coe.domain.service.financialAccountingYear;

import com.idg.idgcore.coe.domain.assembler.financialAccountingYear.FinancialAccountingYearAssembler;
import com.idg.idgcore.coe.domain.entity.financialAccountingYear.FinancialAccountingYearEntity;
import com.idg.idgcore.coe.domain.repository.financialAccountingYear.IFinancialAccountingYearRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.financialAccountingYear.FinancialAccountingYearDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.FIELD_SEPARATOR;
import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class FinancialAccountingYearDomainService extends DomainService<FinancialAccountingYearDTO, FinancialAccountingYearEntity> {

    @Autowired
    private IFinancialAccountingYearRepository repository;

    @Autowired
    private FinancialAccountingYearAssembler assembler;

    @Override
    public FinancialAccountingYearEntity getEntityByIdentifier(String identifier) {
        FinancialAccountingYearEntity entity = null;
        try {
            String[] fields = identifier.split(FIELD_SEPARATOR);
            if (fields.length == 3) {
                entity = this.repository.getByBankCodeAndBranchCodeAndFinancialAccountingYearCode(
                        fields[0], fields[1], fields[2]);
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return entity;
    }

    @Override
    public List<FinancialAccountingYearEntity> getAllEntities() {
        return repository.findAll();
    }

    @Override
    public void save(FinancialAccountingYearDTO valDTO) {
        try {
            FinancialAccountingYearEntity entity = assembler.toEntity(valDTO);
            this.repository.save(entity);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
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

//    public void save (FinancialAccountingYearDTO dto) {
//        try {
//            FinancialAccountingYearEntity entity = assembler.convertDtoToEntity(dto);
//            this.repository.save(entity);
//        }
//        catch (Exception e) {
//            if (log.isErrorEnabled()) {
//                log.error(e.getMessage());
//            }
//            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
//        }
//    }
//    @Override
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
