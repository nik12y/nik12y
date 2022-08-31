package com.idg.idgcore.coe.domain.service.errorOverride;

import com.idg.idgcore.coe.domain.assembler.errorOverride.*;
import com.idg.idgcore.coe.domain.entity.errorOverride.*;
import com.idg.idgcore.coe.domain.repository.errorOverride.*;
import com.idg.idgcore.coe.dto.errorOverride.*;
import com.idg.idgcore.coe.exception.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

import static com.idg.idgcore.coe.common.Constants.ALL;
import static com.idg.idgcore.coe.common.Constants.CLOSED;
import static com.idg.idgcore.coe.exception.Error.*;

@Slf4j
@Service
public class ErrorOverrideDomainService implements IErrorOverrideDomainService {
    @Autowired
    private IErrorOverrideRepository errorOverrideRepository;
    @Autowired
    private ErrorOverrideAssembler errorOverrideAssembler;

    public ErrorOverrideEntity getConfigurationByCode (ErrorOverrideDTO errorOverrideDTO) {
        ErrorOverrideEntity errorOverrideEntity = null;
        try {
            errorOverrideEntity = this.errorOverrideRepository.findByErrorCode(
                    errorOverrideDTO.getErrorCode());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return errorOverrideEntity;
    }

    public ErrorOverrideEntity getConfigurationByErrorCodeAndBranchCode (
            ErrorOverrideDTO errorOverrideDTO) {
        ErrorOverrideEntity errorOverrideEntity = null;
        try {
            errorOverrideEntity = this.errorOverrideRepository.findByErrorCodeAndBranchCode(
                    errorOverrideDTO.getErrorCode(),
                    errorOverrideDTO.getBranchCode());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return errorOverrideEntity;
    }

    public List<ErrorOverrideEntity> getErrorCodes () {
        return this.errorOverrideRepository.findAll();
    }

    public ErrorOverrideEntity getRecordByErrorCodeAndBranchCode (String errorCode,
            String branchCode) {
        ErrorOverrideEntity errorOverrideEntity = this.errorOverrideRepository.findByErrorCodeAndBranchCode(
                errorCode, branchCode);
        if (errorOverrideEntity == null) {
            try {
                errorOverrideEntity = this.errorOverrideRepository.findByErrorCodeAndBranchCode(
                        errorCode, ALL);
            }
            catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error(e.getMessage());
                }
                ExceptionUtil.handleException(DATA_ACCESS_ERROR);
            }
        }
        return errorOverrideEntity;
    }

    public ErrorOverrideEntity getByErrorCodeAndBranchCode (String errorCode, String branchCode) {
        ErrorOverrideEntity errorOverrideEntity = this.errorOverrideRepository.findByErrorCodeAndBranchCode(
                errorCode, ALL);
        if (errorOverrideEntity == null) {
            try {
                errorOverrideEntity = this.errorOverrideRepository.findByErrorCodeAndBranchCode(
                        errorCode, branchCode);
            }
            catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error(e.getMessage());
                }
                ExceptionUtil.handleException(DATA_ACCESS_ERROR);
            }
        }
        return errorOverrideEntity;
    }

    public ErrorOverrideEntity getErrorOverrideByCode (String errorOverrideCode) {
        ErrorOverrideEntity errorOverrideEntity = null;
        try {
            errorOverrideEntity = this.errorOverrideRepository.findByErrorCode(errorOverrideCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return errorOverrideEntity;
    }

    public void validateAndSave (ErrorOverrideDTO errorOverrideDTO) {
        try {
            if (errorOverrideDTO.getIsExcluded()) {
                save(errorOverrideDTO);
            }
            else {
                processSave(errorOverrideDTO);
            }
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            if (e.getMessage().contains("IDC_COE_0002"))
                ExceptionUtil.handleException(DUPLICATE_RECORD);
            else
                ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }

    private void processSave (ErrorOverrideDTO errorOverrideDTO) {
        if (ALL.equals(errorOverrideDTO.getBranchCode())) {
            save(errorOverrideDTO);
        }
        else {
            ErrorOverrideEntity withAll = this.errorOverrideRepository.findByErrorCodeAndBranchCodeAndStatusNot(
                    errorOverrideDTO.getErrorCode(), ALL,CLOSED);
            if (withAll == null) {
                save(errorOverrideDTO);
            }
            else {
                ExceptionUtil.handleException(DUPLICATE_RECORD);
            }
        }
    }

    public void save (ErrorOverrideDTO errorOverrideDTO) {
        try {
            ErrorOverrideEntity errorOverrideEntity = errorOverrideAssembler.convertDtoToEntity(
                    errorOverrideDTO);
            this.errorOverrideRepository.save(errorOverrideEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }

}
