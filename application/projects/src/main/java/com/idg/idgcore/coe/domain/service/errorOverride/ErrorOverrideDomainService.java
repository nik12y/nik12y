package com.idg.idgcore.coe.domain.service.errorOverride;

import com.idg.idgcore.coe.dto.errorOverride.ErrorOverrideDTO;
import com.idg.idgcore.coe.domain.assembler.errorOverride.ErrorOverrideAssembler;
import com.idg.idgcore.coe.domain.entity.errorOverride.ErrorOverrideEntity;
import com.idg.idgcore.coe.domain.repository.errorOverride.IErrorOverrideRepository;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class ErrorOverrideDomainService implements IErrorOverrideDomainService {
    @Autowired
    private IErrorOverrideRepository errorOverrideRepository;
    @Autowired
    private ErrorOverrideAssembler errorOverrideAssembler;

    public ErrorOverrideEntity getConfigurationByCode (ErrorOverrideDTO errorOverrideDTO) {
        System.out.println(" Domain errorOverrideDTO "+errorOverrideDTO);
        ErrorOverrideEntity errorOverrideEntity = null;
        try {
            errorOverrideEntity = this.errorOverrideRepository.findByErrorCode(errorOverrideDTO.getErrorCode());
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

    public void save (ErrorOverrideDTO errorOverrideDTO) {
        try {
            ErrorOverrideEntity errorOverrideEntity = errorOverrideAssembler.convertDtoToEntity(errorOverrideDTO);
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
