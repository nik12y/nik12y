package com.idg.idgcore.coe.domain.service.reason;

import com.idg.idgcore.coe.dto.reason.ReasonDTO;
import com.idg.idgcore.coe.domain.assembler.reason.ReasonAssembler;
import com.idg.idgcore.coe.domain.entity.reason.ReasonEntity;
import com.idg.idgcore.coe.domain.repository.reason.IReasonRepository;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class ReasonDomainService implements IReasonDomainService {
    @Autowired
    private IReasonRepository reasonRepository;
    @Autowired
    private ReasonAssembler reasonAssembler;

    public ReasonEntity getConfigurationByCode (ReasonDTO reasonDTO) {
        ReasonEntity reasonEntity = null;
        try {
            reasonEntity = this.reasonRepository.findByPrimaryReasonCode(reasonDTO.getPrimaryReasonCode());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return reasonEntity;
    }

    public List<ReasonEntity> getReasons () {
        return this.reasonRepository.findAll();
    }

    public ReasonEntity getReasonByCode (String reasonCode) {
        ReasonEntity reasonEntity = null;
        try {
            reasonEntity = this.reasonRepository.findByPrimaryReasonCode(reasonCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return reasonEntity;
    }

    public void save (ReasonDTO reasonDTO) {
        try {
            ReasonEntity reasonEntity = reasonAssembler.convertDtoToEntity(reasonDTO);
            this.reasonRepository.save(reasonEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }

}
