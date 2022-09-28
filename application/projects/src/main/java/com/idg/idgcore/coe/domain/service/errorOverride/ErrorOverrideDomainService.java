package com.idg.idgcore.coe.domain.service.errorOverride;

import com.idg.idgcore.coe.domain.assembler.errorOverride.ErrorOverrideAssembler;
import com.idg.idgcore.coe.domain.entity.errorOverride.ErrorOverrideEntity;
import com.idg.idgcore.coe.domain.repository.errorOverride.IErrorOverrideRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.errorOverride.ErrorOverrideDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.common.Constants.ALL;
import static com.idg.idgcore.coe.common.Constants.FIELD_SEPARATOR;
import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class ErrorOverrideDomainService extends DomainService<ErrorOverrideDTO, ErrorOverrideEntity> {
    @Autowired
    private IErrorOverrideRepository errorOverrideRepository;
    @Autowired
    private ErrorOverrideAssembler errorOverrideAssembler;

    private ErrorOverrideEntity getRecordByErrorCodeAndBranchCode (String errorCode,
                                                                   String branchCode) {
        ErrorOverrideEntity errorOverrideEntity = this.errorOverrideRepository.findByErrorCodeAndBranchCode(
                errorCode, branchCode);
        if (errorOverrideEntity == null) {
            try {
                errorOverrideEntity = this.errorOverrideRepository.findByErrorCodeAndBranchCode(
                        errorCode, ALL);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                ExceptionUtil.handleException(DATA_ACCESS_ERROR);
            }
        }
        return errorOverrideEntity;
    }

    @Override
    public ErrorOverrideEntity getEntityByIdentifier(String errorOverrideCode) {
        String[] fields = errorOverrideCode.split(FIELD_SEPARATOR);
        if (fields.length == 2) {
            return getRecordByErrorCodeAndBranchCode(fields[0], fields[1]);
        }
        return null;
    }

    @Override
    public List<ErrorOverrideEntity> getAllEntities() {
        return this.errorOverrideRepository.findAll();
    }

    public void save (ErrorOverrideDTO errorOverrideDTO) {
        try {
            ErrorOverrideEntity errorOverrideEntity = errorOverrideAssembler.toEntity(
                    errorOverrideDTO);
            this.errorOverrideRepository.save(errorOverrideEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}
